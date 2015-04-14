package client.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Matt on 4/13/2015.
 */
public class IndexerDataModel {
    private List<IndexerDataListener> listeners;

    public IndexerDataModel(){
        listeners = new ArrayList<IndexerDataListener>();
    }

    public void addListener(IndexerDataListener listener) {
        this.listeners.add(listener);
    }

    public void selectCell(int row, int col){
        if(row < 0 || col < 0)
            return;
        for(IndexerDataListener listener:listeners){
            listener.selectData(row, col);
        }
    }

    public void dataChange(int row, int col, String data){
        if(row < 0 || col < 0)
            return;
        for(IndexerDataListener listener:listeners){
            listener.dataChange(row, col,data);
        }
    }

    public interface IndexerDataListener{
        public void dataChange(int row, int col, String data);
        public void selectData(int row, int col);
    }
}

