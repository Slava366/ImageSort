package ru.tomsk.home.tva.frames;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;

@Component
public class MainFrame extends JFrame {

    @Autowired private SourceDirectoryPanel sourceDirectoryPanel;
    @Autowired private InformationPanel informationPanel;
    @Autowired private LoggerPanel loggerPanel;
    @Autowired private ProgressPanel progressPanel;
    @Autowired private ButtonsPanel buttonsPanel;

    private boolean sorting = false;


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
        buttonsPanel.getStartButton().addActionListener(e -> doStart());
        buttonsPanel.getPauseButton().addActionListener(e -> doPause());
        sourceDirectoryPanel.getChooseButton().addActionListener(e -> doChoose());
        sourceDirectoryPanel.getDirectoryTextField().addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if(!sourceDirectoryPanel.getDirectory().isEmpty()) {
                    int amount = directoryFilesAmount(new File(sourceDirectoryPanel.getDirectory()));
                    if(0 < amount) {
                        informationPanel.setTotal(amount);
                        buttonsPanel.getStartButton().setEnabled(true);
                    } else {
                        informationPanel.setTotal(0);
                        buttonsPanel.getStartButton().setEnabled(false);
                    }
                } else {
                    informationPanel.setTotal(0);
                    buttonsPanel.getStartButton().setEnabled(false);
                }
            }
        });
    }

    @PostConstruct
    private void showFrame() {
        addPanels();
        buttonsPanel.getStartButton().setEnabled(false);
        buttonsPanel.getPauseButton().setEnabled(false);
        setVisible(true);
    }

    private void doStart() {
        String withoutGpsFolder = sourceDirectoryPanel.getDirectory().concat(File.separator).concat(buttonsPanel.getFolder());
        File withoutGpsFolderFile = new File(withoutGpsFolder);
        if(!withoutGpsFolderFile.exists() & !withoutGpsFolderFile.mkdirs()) {
            JOptionPane.showMessageDialog(
                    this,
                    String.format("Cannot create folder '%s'!", withoutGpsFolder),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            doPause();
            return;
        }
        sorting = true;
        buttonsPanel.getStartButton().setEnabled(false);
        buttonsPanel.getPauseButton().setEnabled(true);
        sourceDirectoryPanel.getDirectoryTextField().setEditable(false);
        sourceDirectoryPanel.getChooseButton().setEnabled(false);
        buttonsPanel.getLanguageButton().setEnabled(false);
        buttonsPanel.getFolderButton().setEnabled(false);
        //todo
    }

    private void doPause() {
        sorting = false;
        buttonsPanel.getStartButton().setEnabled(true);
        buttonsPanel.getPauseButton().setEnabled(false);
        sourceDirectoryPanel.getDirectoryTextField().setEditable(true);
        sourceDirectoryPanel.getChooseButton().setEnabled(true);
        buttonsPanel.getLanguageButton().setEnabled(true);
        buttonsPanel.getFolderButton().setEnabled(true);
    }

    private void doChoose() {
        JFileChooser fileChooser = new JFileChooser(sourceDirectoryPanel.getDirectory());
        fileChooser.setDialogTitle(sourceDirectoryPanel.getChooseDialogTitle());
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        if(JFileChooser.APPROVE_OPTION == fileChooser.showOpenDialog(this)) {
            int amount = directoryFilesAmount(fileChooser.getSelectedFile());
            if(0 < amount) {
                informationPanel.setTotal(amount);
                sourceDirectoryPanel.setDirectory(fileChooser.getSelectedFile().getAbsolutePath());
                buttonsPanel.getStartButton().setEnabled(true);
            } else {
                JOptionPane.showMessageDialog(
                        this,
                        "Source directory does not contain any files!",
                        "Warning",
                        JOptionPane.WARNING_MESSAGE
                );
            }
        }
    }

    private int directoryFilesAmount(File source) {
        File[] files = source.listFiles(File::isFile);
        if((null == files) || (0 == files.length)) return 0;
        return files.length;
    }
}
