package client.models;

import java.io.IOException;
import java.net.URL;
import java.util.*;

/**
 * Created by Matt on 4/14/2015.
 */
public class QualityCheckModel {
    private Trie dictionary;
    private Map<String,Trie> tries = new HashMap<String, Trie>();
    private boolean hasKnownData;

    private List<QualityCheckerListener> qualityListeners = new ArrayList<QualityCheckerListener>();
    private List<SeeSuggestionsListener> suggestionsListeners = new ArrayList<SeeSuggestionsListener>();

    public void addListener(QualityCheckerListener listener){
        this.qualityListeners.add(listener);
    }

    public void addListener(SeeSuggestionsListener listener){
        this.suggestionsListeners.add(listener);
    }

    public void fieldChange(String knownData){
        if(knownData == null || knownData.equals("")){
            hasKnownData = false;
            return;
        }
        hasKnownData = true;
        if(tries.containsKey(knownData))
            dictionary = tries.get(knownData);
        else{
            dictionary = new Trie();
            try{
                Scanner in = new Scanner(new URL(knownData).openStream());
                in.useDelimiter(",");
                while (in.hasNext())
                    dictionary.add(in.next().toLowerCase());
                in.close();
                tries.put(knownData, dictionary);
            }catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void isInvalidEntry(int row, int col, String word){
        if(dictionary != null && hasKnownData){
            for(QualityCheckerListener l:qualityListeners){
                l.setInvalid(row,col,!dictionary.contains(word));
            }
        }
    }

    public void getSuggestions(int row, int col, String knownData, String word){
        dictionary = tries.get(knownData);
        word = word.toLowerCase();
        Set<String> possiblities = getEditWords(word);
        possiblities = getAllEditWords(possiblities);
        List<String> ret = new ArrayList<String>();
        ret.addAll(getValidWords(possiblities));
        Collections.sort(ret);
        for(SeeSuggestionsListener l:suggestionsListeners){
            l.seeSuggestions(row,col,ret);
        }
    }
    public ArrayList<String> getSuggestions(String word, String knownData){
        dictionary = tries.get(knownData);
        word = word.toLowerCase();
        Set<String> possiblities = getEditWords(word);
        possiblities = getAllEditWords(possiblities);
        ArrayList<String> ret = new ArrayList<String>();
        ret.addAll(getValidWords(possiblities));
        Collections.sort(ret);
        return ret;
    }

    private ArrayList<String> getValidWords(Set<String> possiblities) {
        Set<String> list = new HashSet<String>();
        ArrayList<String> retList = new ArrayList<String>();
        for(String s:possiblities){
            if(dictionary.contains(s) && !s.equals("")){
                list.add(s);
            }
        }
        retList.addAll(list);
        return retList;
    }

    private Set<String> getAllEditWords(Set<String> list) {
        Set<String> newList = new HashSet<String>();
        for (String word : list) {
            newList.addAll(getEditWords(word));
        }
        return newList;
    }



    private Set<String> getEditWords(String inputWord) {
        Set<String> list = new HashSet<String>();
        list.addAll(deleteWords(inputWord));
        list.addAll(transpositionWords(inputWord));
        list.addAll(insAltWords(inputWord, 1));
        list.addAll(insAltWords(inputWord, 0));
        return list;
    }

    private ArrayList<String> transpositionWords(String inputWord) {
        ArrayList<String> list = new ArrayList<String>();
        for(int i = 0;i<inputWord.length()-1;i++){
            list.add(inputWord.substring(0,i)+inputWord.charAt(i+1)+inputWord.charAt(i)+inputWord.substring(i+2));
        }
        return list;
    }

    private ArrayList<String> insAltWords(String inputWord, int ins) {
        ArrayList<String> list = new ArrayList<String>();
        for(int j = 0;j<26;j++){
            char c = (char) ('a' + j);
            for(int i = 0;i<inputWord.length();i++) {
                list.add((inputWord.substring(0, i) + c + inputWord.substring(i + ins)).trim());
            }
            if(ins == 0){
                list.add((inputWord + c).trim());
            }
        }
        return list;
    }

    private ArrayList<String> deleteWords(String inputWord) {
        ArrayList<String> list = new ArrayList<String>();
        for(int i = 0;i<inputWord.length();i++){
            list.add(inputWord.substring(0,i)+inputWord.substring(i+1));
        }
        return list;
    }

    private class Trie{
        private Node root = new Node();
        public Node searchNode;

        public void add(String word){root.add(word.trim().toLowerCase());}

        public boolean contains(String word){
            searchNode = root.contains(word.trim().toLowerCase());
            return searchNode != null;
        }
    }

    private class Node {

        private Node[] children = new Node[27];

        public void add(String s) {
            Node temp = this;
            for (char c : s.toLowerCase().toCharArray()) {
                int index = c == ' ' ? 26 : c - 'a';
                if (index < 0 || index > children.length)
                    return;
                if (temp.children[index] == null) {
                    temp.children[index] = new Node();
                }
                temp = temp.children[index];
            }
        }

        public Node contains(String s) {
            Node temp = this;
            for(char c : s.toLowerCase().toCharArray()){
                int index = c == ' ' ? 26 : c - 'a';
                if (index < 0 || index > children.length)
                    return null;
                if(temp.children[index] != null){
                    temp = temp.children[index];
                } else {
                    return null;
                }
            }
            return temp;
        }
    }

    public interface QualityCheckerListener{
        public void setInvalid(int row, int col, boolean invalid);
    }

    public interface SeeSuggestionsListener{
        public void seeSuggestions(int row, int col, List<String> similar);
    }

}
