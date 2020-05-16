package editor;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;



public abstract class TextSelector {
    protected final String text;
    protected JTextArea textArea;
    protected List<Integer> index;
    protected String search;
    protected  int current;

   public static TextSelector create(boolean useRegex, JTextArea textArea, String search,String text){

       if (useRegex){
            return new TextSelectorRegEx(textArea, search, text);
       }
       else {
           return new TextSelectorString( textArea,  search, text);
       }

   }

    public TextSelector(JTextArea textArea, String search,String text) {
        this.textArea = textArea;
        this.search = search;
        this.text=text;
        index=new ArrayList<>();
        current=0;

    }


    public  abstract  int findNext();
    public abstract  void showWord(Integer index) ;


    public int length() {
        return text.length();
    }



    public   void addIndex(int i){
        index.add(i);
    }

    public List<Integer> getIndex() {
        return index;
    }

    public void showFind() {
        index.stream().forEach(n -> showWord(n));
    }

    public int countFind(){
        return index.size();
    }




    public void showNext() {
        int next;
        if (current == countFind() - 1 ){
            current=0;
        }
        else {
            current++;
        }
        showWord(current);
    }

    public void showPrevious() {

       if (current == 0){
           current = countFind() - 1;
       }
       else {
           current--;
       }
        showWord(current);


    }
}
