package editor;

import javax.swing.*;
import java.awt.*;

public class IconsMenu {


    public static Icon getIcon(String name){


        Image   img =  new ImageIcon(TextEditor.path + "//icons//"+name+".png").getImage();
        Image newImg = img.getScaledInstance(15,15,Image.SCALE_SMOOTH);
        return new ImageIcon(newImg);
    }
}
