package editor;

import javax.swing.*;
import java.util.List;

public class SearchWorker  extends SwingWorker<List<Integer>,Integer> {

    private TextSelector text;


    public SearchWorker(TextSelector text) {
        this.text=text;
    }


    @Override
    protected List<Integer> doInBackground() throws Exception {
        int i =0;


        while( i < text.length() && ( i  = text.findNext()) != -1){
            publish(i);
            i++;

        }
        return text.getIndex();

    }

     

    @Override
    protected void process(List<Integer> chunks) {
       text.showWord(chunks.size() -1);
    }

    @Override
    protected void done() {

        text.showWord(0);


    }
}
