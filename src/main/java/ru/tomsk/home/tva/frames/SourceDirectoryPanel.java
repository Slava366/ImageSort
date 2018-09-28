package ru.tomsk.home.tva.frames;

import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;

@Component
public class SourceDirectoryPanel {

    private static final String LABEL_TEXT = "Source directoryTextField:";
    private static final String CHOOSE_BUTTON_TEXT = "...";
    private static final String CHOOSE_DIALOG_TITLE = "Select source directoryTextField";

    private JTextField directoryTextField;
    private JButton chooseButton;


    public SourceDirectoryPanel() {
        directoryTextField = new JTextField();
        directoryTextField.setColumns(10);
    }

    public JPanel toJPanel() {
        JPanel panel = new JPanel(new BorderLayout(4, 4));
            panel.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
            panel.add(new JLabel(LABEL_TEXT), BorderLayout.WEST);
            panel.add(directoryTextField, BorderLayout.CENTER);
            chooseButton = new JButton(CHOOSE_BUTTON_TEXT);
            panel.add(chooseButton, BorderLayout.EAST);
        return panel;
    }

    public JTextField getDirectoryTextField() {
        return directoryTextField;
    }

    public JButton getChooseButton() {
        return chooseButton;
    }

    public String getDirectory() {
        return directoryTextField.getText();
    }

    public void setDirectory(String path) {
        if(null == path) throw new NullPointerException("Argument cannot be null!");
        directoryTextField.setText(path);
    }

    public String getChooseDialogTitle() {
        return CHOOSE_DIALOG_TITLE;
    }
}
