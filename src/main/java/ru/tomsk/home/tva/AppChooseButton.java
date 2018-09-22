package ru.tomsk.home.tva;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@Service
public class AppChooseButton extends JButton implements ActionListener {

    private SourceDirectory sourceDirectory;

    @Autowired
    AppProgressBar appProgressBar;

    @Autowired
    AppStartButton appStartButton;

    @Autowired
    AppFrame appFrame;

    @Autowired
    AppTextArea appTextArea;

    private AppChooseButton() {
    }

    public AppChooseButton(String text) {
        super(text);
        addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Choose source directory");
        chooser.setMultiSelectionEnabled(false);
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        if(JFileChooser.APPROVE_OPTION == chooser.showOpenDialog(null)) {
            String directory = chooser.getSelectedFile().getAbsolutePath();
            try {
                sourceDirectory = new SourceDirectory(directory);
                if(0 < sourceDirectory.getMetaFilesAmount()) {
                    appFrame.getSourceDirectoryTextField().setText(directory);
                    appProgressBar.setMaximum(sourceDirectory.getMetaFilesAmount());
                    appProgressBar.setValue(0);
                    appStartButton.setEnabled(true);
                    appTextArea.setText(
                            String.format(
                                    "The source directory contains:%n    Files: %d%n    Files with metadata: %d%n",
                                    sourceDirectory.getFilesAmount(),
                                    sourceDirectory.getMetaFilesAmount()
                            )
                    );
                    appStartButton.setPosition(0);
                } else {
                    JOptionPane.showMessageDialog(appFrame, "Source directory does not contain files with a metadata!", "Source directory warning", JOptionPane.WARNING_MESSAGE);
                }
            } catch (Exception e1) {
                JOptionPane.showMessageDialog(appFrame, e1.getMessage(), "Source directory error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public SourceDirectory getSourceDirectory() {
        return sourceDirectory;
    }
}
