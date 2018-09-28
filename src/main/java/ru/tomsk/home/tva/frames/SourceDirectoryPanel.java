package ru.tomsk.home.tva.frames;


import javax.swing.*;
import java.awt.*;

public class SourceDirectoryPanel {

    private static final String LABEL_TEXT = "Source directory:";
    private static final String CHOOSE_BUTTON_TEXT = "...";
    private static final String CHOOSE_DIALOG_TITLE = "Select source directory";

    private JTextField directory;
    private Component parent;


    public SourceDirectoryPanel(Component parent) {
        directory = new JTextField();
        directory.setColumns(10);
        this.parent = parent;
    }

    public JPanel toJPanel() {
        JPanel panel = new JPanel(new BorderLayout(4, 4));
            panel.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
            panel.add(new JLabel(LABEL_TEXT), BorderLayout.WEST);
            panel.add(directory, BorderLayout.CENTER);
            JButton button = new JButton(CHOOSE_BUTTON_TEXT);
            button.addActionListener(actionEvent -> {
                JFileChooser fileChooser = new JFileChooser(getDirectory());
                fileChooser.setDialogTitle(CHOOSE_DIALOG_TITLE);
                fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                if(JFileChooser.APPROVE_OPTION == fileChooser.showOpenDialog(parent)) {
                    setDirectory(fileChooser.getSelectedFile().getAbsolutePath());
                }
            });
            panel.add(button, BorderLayout.EAST);
        return panel;
    }

    public String getDirectory() {
        return directory.getText();
    }

    public void setDirectory(String path) {
        if(null == path) throw new NullPointerException("Argument cannot be null!");
        directory.setText(path);
    }
}
