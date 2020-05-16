package editor;

import javax.swing.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextSelectorString extends  TextSelector{

    private final Matcher matcher;

    public TextSelectorString(JTextArea textArea, String search, String text)
    {
        super(textArea, search, text);
        Pattern patternSearch = Pattern.compile(search,Pattern.CASE_INSENSITIVE);
         matcher = patternSearch.matcher(text);
    }

    public int findNext() {
        if (matcher.find()){

            int i =  matcher.start();
            addIndex(i);
            return i;
        }
        return -1;
    }

    public void showWord(Integer i) {
        textArea.setCaretPosition(index.get(i) + search.length());
        textArea.select(index.get(i), index.get(i) + search.length());
        textArea.grabFocus();
    }

}
