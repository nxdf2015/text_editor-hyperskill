package editor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.*;


public class TextEditor extends JFrame {
    public static String path ="C://Users//nique//IdeaProjects//Text Editor//Text Editor//task//src//editor";
    private final JTextField searchField;

    private final JFileChooser jfc;
    private JCheckBox regexCheckBox =null;
    private boolean useRegex;
    private TextSelector textSelector;
    JTextArea textArea;
    String nameFile;



    ActionListener ActionCheckBox = e -> {

        useRegex = !useRegex;
        regexCheckBox.setSelected(useRegex);

    };

    class ActionSave implements  ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {

            jfc.setVisible(true);
            String text = textArea.getText();


            int response = jfc.showSaveDialog(null);
            if (response == JFileChooser.APPROVE_OPTION) {
                File file = jfc.getSelectedFile();

                try (PrintWriter writer = new PrintWriter(file)){
                    for(String line: text.split(System.lineSeparator())){
                        writer.println(line);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            jfc.setVisible(false);

        }
    }



    class ActionLoad implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            jfc.setVisible(true);

            int response = jfc.showOpenDialog(null);
                    try {
                        if (response == JFileChooser.APPROVE_OPTION) {
                            File file = jfc.getSelectedFile();
                            textArea.setText("");

                            BufferedReader  reader = new BufferedReader(new FileReader(file));
                           final  StringBuilder t= new StringBuilder("");
                           reader.lines().forEach(line -> t.append(line+"\n"));

                           textArea.append(t.toString().substring(0,t.length()-1));

                           nameFile = file.getName();
                    } }
                    catch (IOException ioException) {
                        ioException.printStackTrace();
                        textArea.setText("");
                    }

                    jfc.setVisible(false);
                }

        }


    class ActionStartSearch    implements  ActionListener{
        @Override
        public void actionPerformed(ActionEvent actionEvent) {

            String text = textArea.getText();
            String searchText = searchField.getText();
             textSelector= TextSelector.create(useRegex,textArea,searchText,text);
            new SearchWorker(textSelector).execute();

        }
    }

    class ActionNextWord implements  ActionListener{
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            textSelector.showNext();
        }
    }

    class ActionPreviousWord implements  ActionListener{
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            textSelector.showPrevious();
        }
    }

    public TextEditor() {
        Dimension d = new Dimension(100, 50);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 300);
        setTitle("The first stage");
        setVisible(true);

        this.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        createMenu();

        textArea = new JTextArea();
        textArea.setName("TextArea");
        textArea.setBounds(60, 60, 300, 200);
        textArea.setVisible(true);


        JButton saveBtn = new JButton(IconsMenu.getIcon("save"));
        saveBtn.setName("SaveButton");
        saveBtn.setVisible(true);
        saveBtn.setBounds(100, 0, 100, 50);
        c.gridx=0;
        c.gridy=0;
        c.fill=GridBagConstraints.VERTICAL;
        add(saveBtn,c);



        JButton loadButton = new JButton(IconsMenu.getIcon("load"));
        loadButton.setName("OpenButton");
        loadButton.setVisible(true);
        loadButton.setBounds(200, 0, 100, 50);
        loadButton.setPreferredSize(d);
        loadButton.setMaximumSize(d);
        c.gridx=1;
        c.gridy=0;
        c.fill=GridBagConstraints.VERTICAL;
        add(loadButton,c);


        searchField =new JTextField();
        searchField.setMinimumSize(new Dimension(10,5) );
        searchField.setName("SearchField");
        c.gridx=2;
        c.gridy=0;
        c.fill=GridBagConstraints.BOTH;
        c.weightx=1;
        c.weightx=2;
        add(searchField,c);


        JButton previousButton = new JButton(IconsMenu.getIcon("previous"));
        previousButton.setName("PreviousMatchButton");
        previousButton.setVisible(true);
        previousButton.addActionListener(new ActionPreviousWord());
        c.gridx=3;
        c.gridy=0;
        c.fill=GridBagConstraints.BOTH;
        add(previousButton,c);


        JButton nextButton = new JButton(IconsMenu.getIcon("next"));
        nextButton.setName("NextMatchButton");
        nextButton.setVisible(true);
        nextButton.addActionListener(new ActionNextWord());
        c.gridx=4;
        c.gridy=0;
        c.fill=GridBagConstraints.BOTH;
        add(nextButton,c);


        JButton searchButton = new JButton(IconsMenu.getIcon("search"));
        searchButton.setName("StartSearchButton");
        searchButton.setVisible(true);
        searchButton.addActionListener(new ActionStartSearch());
        c.gridx=5;
        c.gridy=0;
        c.fill=GridBagConstraints.BOTH;
        add(searchButton,c);


        regexCheckBox = new JCheckBox("Use regex");
        regexCheckBox.setName("UseRegExCheckbox");
        regexCheckBox.addActionListener(ActionCheckBox);
        c.gridx=6;
        c.gridy=0;
        c.fill=GridBagConstraints.VERTICAL;
        add(regexCheckBox);


        JScrollPane scroll = new JScrollPane(textArea);
        scroll.createVerticalScrollBar();
        scroll.setName("ScrollPane");
        c.gridx=0;
        c.gridy=1;
        c.gridwidth=6;
        c.gridheight=4;
        c.weighty=0.5;
        c.fill=GridBagConstraints.BOTH;
        add(scroll,c);


        jfc = new JFileChooser(TextEditor.path);
        jfc.setName("FileChooser");
        jfc.setVisible(false);
        add(jfc);
        loadButton.addActionListener(new ActionLoad());
        saveBtn.addActionListener(new ActionSave());
    }



    public void createMenu(){
        JMenu FileMenu = new JMenu("File");
        FileMenu.setName("MenuFile");
        JMenuItem loadMenu = new JMenuItem("Load");
        loadMenu.setMnemonic(KeyEvent.VK_L);
        loadMenu.addActionListener(new ActionLoad());
        loadMenu.setName("MenuOpen");

        JMenuItem saveMenu = new JMenuItem("Save");
        saveMenu.setMnemonic(KeyEvent.VK_S);
        saveMenu.addActionListener(new ActionSave());
        saveMenu.setName("MenuSave");

        JMenuItem saveAsMenu = new JMenuItem("SaveAs");
        saveAsMenu.setMnemonic(KeyEvent.VK_A);
        saveAsMenu.addActionListener(new ActionSave());
        saveAsMenu.setName("MenuSaveAs");


        JMenuItem exitMenu = new JMenuItem("MenuExit");
        exitMenu.setMnemonic(KeyEvent.VK_E);
        exitMenu.addActionListener(e -> this.dispose());
        exitMenu.setName("MenuExit");

        FileMenu.add(loadMenu);
        FileMenu.add(saveMenu);
        FileMenu.add(saveAsMenu);
        FileMenu.addSeparator();
        FileMenu.add(exitMenu);


        JMenu searchMenu = new JMenu("Search");
        searchMenu.setName("MenuSearch");

        JMenuItem startSearchMenu=new JMenuItem("Start search");
        startSearchMenu.setName("MenuStartSearch");
        startSearchMenu.addActionListener(new ActionStartSearch());

        JMenuItem previousMenu = new JMenuItem("previous");
        previousMenu.setName("MenuPreviousMatch");
        previousMenu.addActionListener(new ActionPreviousWord());

        JMenuItem nextMenu = new JMenuItem("next");
        nextMenu.setName("MenuNextMatch");
        nextMenu.addActionListener(new ActionNextWord());

        JMenuItem regexMenu = new JMenuItem("Use regular expressions");
        regexMenu.setName("MenuUseRegExp");
        regexMenu.addActionListener(  ActionCheckBox );

        searchMenu.add(startSearchMenu);
        searchMenu.add(previousMenu);
        searchMenu.add(nextMenu);
        searchMenu.add(regexMenu);

        JMenuBar menu = new JMenuBar();
        menu.add(FileMenu);
        menu.add(searchMenu);
        setJMenuBar(menu);
    }

    public static void main(String[] args) {
        new TextEditor();
    }


}
