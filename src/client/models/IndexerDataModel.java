package client.models;

import java.util.List;

/**
 * Created by Matt on 4/13/2015.
 */
public class IndexerDataModel {
    private List<IndexerDataListener> listeners;

    public void addListener(IndexerDataListener listener) {
        this.listeners.add(listener);
    }

    public void selectCell(int row, int col){
        for(IndexerDataListener listener:listeners){
            listener.selectData(row, col);
        }
    }

    public void dataChange(int row, int col, String data){
        for(IndexerDataListener listener:listeners){
            listener.dataChange(row, col,data);
        }
    }

    public interface IndexerDataListener{
        public void dataChange(int row, int col, String data);
        public void selectData(int row, int col);
    }
}

