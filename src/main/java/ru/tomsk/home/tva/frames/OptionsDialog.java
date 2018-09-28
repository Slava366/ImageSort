package ru.tomsk.home.tva.frames;

import ru.tomsk.home.tva.core.LatLongDecoder;

import javax.swing.*;
import java.awt.*;

public class OptionsDialog extends JDialog {

    private static final String DIALOG_TITLE = "Options";
    private static final String LANGUAGE_TEXT = "Language:";
    private static final String FOLDER_TEXT = "The folder for files without metadata:";
    private static final String BUTTON_TEXT = "OK";

    private static final LatLongDecoder.Language defaultLanguage = LatLongDecoder.DEFAULT_LANGUAGE;
    private static final String DEFAULT_FOLDER = "WithoutGPS";

    private LatLongDecoder.Language language;

    private JTextField folder;


    public OptionsDialog(Component parent) {
        language = defaultLanguage;
        folder = new JTextField(DEFAULT_FOLDER);
        folder.setColumns(10);
        setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        setLocationRelativeTo(parent);
        setTitle(DIALOG_TITLE);
        JPanel panel = new JPanel(new BorderLayout(4, 4));
        panel.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
            JPanel panel1 = new JPanel(new FlowLayout(FlowLayout.CENTER));
                panel1.add(new Label(LANGUAGE_TEXT));
                JComboBox<LatLongDecoder.Language> languages = new JComboBox<>(LatLongDecoder.Language.values());
                languages.setSelectedItem(language.toString());
                panel1.add(languages);
            panel.add(panel1, BorderLayout.NORTH);
            JPanel panel2 = new JPanel();
            panel2.setLayout(new BoxLayout(panel2, BoxLayout.Y_AXIS));
                panel2.add(new Label(FOLDER_TEXT));
                panel2.add(folder);
            panel.add(panel2, BorderLayout.CENTER);
            JPanel panel3 = new JPanel(new FlowLayout(FlowLayout.CENTER));
                JButton button = new JButton(BUTTON_TEXT);
                button.addActionListener(actionEvent -> OptionsDialog.this.setVisible(false));
                panel3.add(button);
            panel.add(panel3, BorderLayout.SOUTH);
        getContentPane().add(panel);
        pack();
        setResizable(false);
    }

    public LatLongDecoder.Language getLanguage() {
        return language;
    }

    public void setLanguage(LatLongDecoder.Language language) {
        if(null == language) throw new NullPointerException("Argument cannot be null!");
        this.language = language;
    }

    public String getFolder() {
        return folder.getText().trim();
    }

    public void setFolder(String name) {
        if(null == name) throw new NullPointerException("Argument cannot be null!");
        folder.setText(name.trim());
    }
}
