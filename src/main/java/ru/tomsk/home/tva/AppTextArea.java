package ru.tomsk.home.tva;

import javax.swing.*;
import java.awt.*;

public class AppTextArea extends JTextArea {

    public AppTextArea() {
        setFont(new Font(null, Font.PLAIN, 12));
    }

    public void addText(String text) {
        setText(getText() + text);
        setCaretPosition(getDocument().getLength());
    }
}
