package ru.tomsk.home.tva.frames;


import org.springframework.beans.factory.annotation.Autowired;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

@org.springframework.stereotype.Component
public class SourceDirectoryPanel {

    @Autowired
    private ButtonsPanel buttonsPanel;

    private static final String LABEL_TEXT = "Source directoryTextField:";
    private static final String CHOOSE_BUTTON_TEXT = "...";
    private static final String CHOOSE_DIALOG_TITLE = "Select source directoryTextField";

    private JTextField directoryTextField;
    private Component parent;
    private JButton chooseButton;


    public SourceDirectoryPanel(Component parent) {
        this.parent = parent;
        directoryTextField = new JTextField();
        directoryTextField.setColumns(10);
        directoryTextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if(!getDirectory().isEmpty()) buttonsPanel.getStartButton().setEnabled(true);
                else buttonsPanel.getStartButton().setEnabled(false);
            }
        });
    }

    public JPanel toJPanel() {
        JPanel panel = new JPanel(new BorderLayout(4, 4));
            panel.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
            panel.add(new JLabel(LABEL_TEXT), BorderLayout.WEST);
            panel.add(directoryTextField, BorderLayout.CENTER);
            chooseButton = new JButton(CHOOSE_BUTTON_TEXT);
            chooseButton.addActionListener(actionEvent -> {
                JFileChooser fileChooser = new JFileChooser(getDirectory());
                fileChooser.setDialogTitle(CHOOSE_DIALOG_TITLE);
                fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                if(JFileChooser.APPROVE_OPTION == fileChooser.showOpenDialog(parent)) {
                    setDirectory(fileChooser.getSelectedFile().getAbsolutePath());
                    buttonsPanel.getStartButton().setEnabled(true);
                }
            });
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
}
