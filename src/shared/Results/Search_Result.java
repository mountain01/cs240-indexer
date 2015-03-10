package shared.Results;

import server.Models.SearchResult;

import java.util.ArrayList;

/**
 * Created by Matt on 3/10/2015.
 */
public class Search_Result extends Result {
    ArrayList<SearchResult> results;

    public ArrayList<SearchResult> getResults() {
        return results;
    }

    public void setResults(ArrayList<SearchResult> results) {
        this.results = results;
    }

    @Override
    public String toString(){
        if(isError()){
            return super.toString();
        } else {
            StringBuilder string = new StringBuilder();
            for(SearchResult sr:results){
                string.append(sr.toString());
            }
            return string.toString();
        }
    }
}
