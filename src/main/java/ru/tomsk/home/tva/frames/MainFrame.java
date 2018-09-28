package ru.tomsk.home.tva.frames;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.swing.*;
import java.awt.*;

@Component
public class MainFrame extends JFrame {

    @Autowired private SourceDirectoryPanel sourceDirectoryPanel;
    @Autowired private InformationPanel informationPanel;
    @Autowired private LoggerPanel loggerPanel;
    @Autowired private ProgressPanel progressPanel;
    @Autowired private ButtonsPanel buttonsPanel;

    private MainFrame() throws HeadlessException {
    }

    public MainFrame(String title) throws HeadlessException {
        super(title);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(640, 480));
        setLocationRelativeTo(null);
        getContentPane().setLayout(new BorderLayout(8,8));
    }

    private void addPanels() {
        getContentPane().add(sourceDirectoryPanel.toJPanel(), BorderLayout.NORTH);
        JPanel panel = new JPanel(new BorderLayout(8, 8));
            panel.add(informationPanel.toJPanel(), BorderLayout.NORTH);
            panel.add(loggerPanel.toJPanel(), BorderLayout.CENTER);
            JPanel panel1 = new JPanel(new BorderLayout(8, 8));
                panel1.add(progressPanel.toJPanel(), BorderLayout.CENTER);
                panel1.add(buttonsPanel.toJPanel(), BorderLayout.SOUTH);
            panel.add(panel1, BorderLayout.SOUTH);
        getContentPane().add(panel, BorderLayout.CENTER);
    }

    @PostConstruct
    private void showFrame() {
        addPanels();
        doStart();
        setVisible(true);
    }

    private void doStart() {
        buttonsPanel.getStartButton().setEnabled(false);
        buttonsPanel.getPauseButton().setEnabled(false);
    }
}
