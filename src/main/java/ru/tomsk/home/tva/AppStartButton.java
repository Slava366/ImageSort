package ru.tomsk.home.tva;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

@Service
public class AppStartButton extends JButton implements ActionListener {

    @Autowired
    AppFrame appFrame;

    private AppStartButton() {
    }

    public AppStartButton(String text) {
        super(text);
        setEnabled(false);
        addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if((null == appFrame.getSourceDirectory()) || appFrame.getSourceDirectory().isEmpty()) {
            JOptionPane.showMessageDialog(appFrame, "You must choose source directory!", "Source directory error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        SourceDirectory sourceDirectory;
        try {
            sourceDirectory = new SourceDirectory(appFrame.getSourceDirectory());
            System.out.println(sourceDirectory.getFilesAmount());
            System.out.println(sourceDirectory.getMetaFilesAmount());
            for (MetaFile metaFile : sourceDirectory.getMetaFiles()) {
                GeoDecoder geoDecoder = new GeoDecoder(metaFile);
                try {
                    File sortedFile = geoDecoder.getSortedFile();
                    metaFile.sortFile(sortedFile);
                    System.out.println(sortedFile.getAbsolutePath());
                } catch (Exception e1) {
                    System.out.println(e1.getMessage());
                }
            }
        } catch (Exception e1) {
            JOptionPane.showMessageDialog(appFrame, e1.getMessage(), "Source directory error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
