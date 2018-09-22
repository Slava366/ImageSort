package ru.tomsk.home.tva;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

@Service
public class AppStartButton extends JButton implements ActionListener {

    private boolean sorting = false;

    private int position;

    @Autowired
    AppFrame appFrame;

    @Autowired
    AppTextArea appTextArea;

    @Autowired
    AppChooseButton appChooseButton;

    @Autowired
    AppProgressBar appProgressBar;

    private AppStartButton() {
    }

    public AppStartButton(String text) {
        super(text);
        setEnabled(false);
        addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(sorting) {
            sorting = false;
            setText("Sort");
            appFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            appChooseButton.setEnabled(true);
        } else {
            sorting = true;
            setText("Stop");
            appFrame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
            appChooseButton.setEnabled(false);
        }
    }

    @Scheduled(fixedDelay = 1000)
    private void sortFiles() {
        while(sorting && (position < appChooseButton.getSourceDirectory().getMetaFilesAmount())) {
            MetaFile metaFile = appChooseButton.getSourceDirectory().getMetaFiles().get(position);
            GeoDecoder geoDecoder = new GeoDecoder(metaFile);
            File sortedFile;
            try {
                sortedFile = geoDecoder.getSortedFile();
                metaFile.sortFile(sortedFile);
                appTextArea.addText(
                        String.format(
                                "[%d/%d] Successfully moved the file - %s%n",
                                ++position,
                                appChooseButton.getSourceDirectory().getMetaFilesAmount(),
                                sortedFile.getAbsolutePath()
                        )
                );
            } catch (Exception e1) {
                appTextArea.addText(
                        String.format(
                                "[%d/%d] [ERROR] Unable to move the file - %s%n",
                                ++position,
                                appChooseButton.getSourceDirectory().getMetaFilesAmount(),
                                metaFile.getFile().getAbsolutePath()
                        )
                );
            }
            appProgressBar.setValue(position);
        }
        if((null != appChooseButton.getSourceDirectory()) && (position == appChooseButton.getSourceDirectory().getMetaFilesAmount())) {
            sorting = false;
            setText("Sort");
            setEnabled(false);
            appFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            appChooseButton.setEnabled(true);
        }
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
