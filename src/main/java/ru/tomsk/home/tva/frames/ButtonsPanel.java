package ru.tomsk.home.tva.frames;

import ru.tomsk.home.tva.core.LatLongDecoder;

import javax.swing.*;
import java.awt.*;

public class ButtonsPanel {

    private static final String LANGUAGE_TITLE = "Language";
    private static final String FOLDER_TITLE = "Without GPS folder";
    private static final String DEFAULT_FOLDER = "WithoutGPS";
    private static final String PAUSE_TITLE = "Pause";
    private static final String START_TITLE = "Sort";

    private JButton languageButton;
    private JButton folderButton;
    private JButton pauseButton;
    private JButton startButton;

    private LatLongDecoder.Language language = LatLongDecoder.DEFAULT_LANGUAGE;
    private String folder = DEFAULT_FOLDER;


    public ButtonsPanel(Component parent) {
        languageButton = new JButton(LANGUAGE_TITLE);
        languageButton.setToolTipText(language.toString());
        languageButton.addActionListener(e -> {
            LatLongDecoder.Language selectedLanguage;
            selectedLanguage = (LatLongDecoder.Language) JOptionPane.showInputDialog(
                    parent,
                    "Select language",
                    "Language options",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    LatLongDecoder.Language.values(),
                    language
            );
            if(null != selectedLanguage) language = selectedLanguage;
            languageButton.setToolTipText(language.toString());
        });
        folderButton = new JButton(FOLDER_TITLE);
        folderButton.setToolTipText(folder);
        folderButton.addActionListener(e -> {
            String inputFolder;
            inputFolder = (String) JOptionPane.showInputDialog(
                    parent,
                    "Set folder for files without GPS metadata",
                    "Folder options",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    null,
                    folder);
            if(null != inputFolder) folder = inputFolder.trim();
            folderButton.setToolTipText(folder);
        });
        pauseButton = new JButton(PAUSE_TITLE);
        startButton = new JButton(START_TITLE);
    }

    public JPanel toJPanel() {
        JPanel panel = new JPanel(new BorderLayout(4, 4));
        panel.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
            JPanel panel1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
                panel1.add(languageButton);
                panel1.add(folderButton);
            panel.add(panel1, BorderLayout.WEST);
            JPanel panel2 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
                panel2.add(pauseButton);
                panel2.add(startButton);
            panel.add(panel2, BorderLayout.CENTER);
        return panel;
    }

    public JButton getPauseButton() {
        return pauseButton;
    }

    public JButton getStartButton() {
        return startButton;
    }

    public LatLongDecoder.Language getLanguage() {
        return language;
    }

    public String getFolder() {
        return folder;
    }
}
