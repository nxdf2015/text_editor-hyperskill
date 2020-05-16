package editor;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextSelectorRegEx  extends  TextSelector{


    private final Matcher matcherSearch;
    private List<Integer> endIndex;

    public TextSelectorRegEx(JTextArea textArea, String search, String text) {
        super(textArea, search, text);
        Pattern patternSearch = Pattern.compile(search,Pattern.CASE_INSENSITIVE);
         matcherSearch = patternSearch.matcher(text);
         endIndex=new ArrayList<>();
    }

    @Override
    public int findNext() {
        if (matcherSearch.find()) {
            int i = matcherSearch.start();
            addIndex(i);
            endIndex.add(matcherSearch.end());
            return i;
        }
        return -1;
    }

    @Override
    public void showWord(Integer i) {
        textArea.setCaretPosition(index.get(i) + endIndex.get(i)+1);
        textArea.select(index.get(i), endIndex.get(i));
        textArea.grabFocus();
    }
}
