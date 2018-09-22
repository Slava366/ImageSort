package ru.tomsk.home.tva;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@Service
public class AppFrame extends JFrame {

    private JTextField sourceDirectoryTextField = new JTextField();

    @Autowired
    AppChooseButton appChooseButton;

    @Autowired
    AppStartButton appStartButton;

    @Autowired
    AppProgressBar appProgressBar;

    private AppFrame() throws HeadlessException {
    }

    public AppFrame(String title) throws HeadlessException {
        super(title);
    }

    @PostConstruct
    private void doVisible() {
        setDefaultLookAndFeelDecorated(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setBounds(new Rectangle(640, 480));
        setMinimumSize(new Dimension(640, 480));
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel appNorthPanel = new JPanel();
        appNorthPanel.setLayout(new BoxLayout(appNorthPanel, BoxLayout.X_AXIS));
        appNorthPanel.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
        appNorthPanel.add(new JLabel("Source directory: "));
        appNorthPanel.add(sourceDirectoryTextField);
        sourceDirectoryTextField.setEditable(false);
        appNorthPanel.add(appChooseButton);
        add(appNorthPanel, BorderLayout.NORTH);

        JPanel appSouthPanel = new JPanel();
        appSouthPanel.setLayout(new BoxLayout(appSouthPanel, BoxLayout.X_AXIS));
        appSouthPanel.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
        appSouthPanel.add(new JLabel("Progress: "));
        appSouthPanel.add(appProgressBar);
        appSouthPanel.add(appStartButton);
        add(appSouthPanel, BorderLayout.SOUTH);

        this.setVisible(true);
    }

    public String getSourceDirectory() {
        return sourceDirectoryTextField.getText();
    }

    public void setSourceDirectory(String sourceDirectory) {
        sourceDirectoryTextField.setText(sourceDirectory);
    }

    public JTextField getSourceDirectoryTextField() {
        return sourceDirectoryTextField;
    }
}
