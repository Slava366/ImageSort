package ru.tomsk.home.tva.frames;

import org.slf4j.event.Level;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.tomsk.home.tva.Sortable;
import ru.tomsk.home.tva.core.SortFiles;

import javax.annotation.PostConstruct;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

@Component
public class MainFrame extends JFrame implements Sortable {

    @Autowired private SourceDirectoryPanel sourceDirectoryPanel;
    @Autowired private InformationPanel informationPanel;
    @Autowired private LoggerPanel loggerPanel;
    @Autowired private ProgressPanel progressPanel;
    @Autowired private ButtonsPanel buttonsPanel;

    private SortFiles sortFiles;


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
        buttonsPanel.getStartButton().addActionListener(e -> {
            MainFrame.this.doStart();
            sortFiles.setInProgress(true);
        });
        buttonsPanel.getPauseButton().addActionListener(e -> {
            MainFrame.this.pauseSort();
            sortFiles.setInProgress(false);
        });
        buttonsPanel.getCancelButton().addActionListener(e -> {
            MainFrame.this.afterSort();
            sortFiles.setExit(true);
        });
        sourceDirectoryPanel.getChooseButton().addActionListener(e -> doChoose());
        sourceDirectoryPanel.getDirectoryTextField().addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if(!sourceDirectoryPanel.getDirectory().isEmpty()) {
                    List<File> files = directoryFiles(new File(sourceDirectoryPanel.getDirectory()));
                    int amount = files.size();
                    if(0 < amount) {
                        sortFiles = SortFiles.withFrame(MainFrame.this)
                                .setFiles(files)
                                .setLanguage(buttonsPanel.getLanguage())
                                .setWithoutGpsFolder(buttonsPanel.getFolder())
                                .build();
                        sortFiles.start();
                        init();
                        informationPanel.setTotal(amount);
                        progressPanel.setMaxValue(amount);
                        buttonsPanel.getStartButton().setEnabled(true);
                    } else {
                        init();
                    }
                } else {
                    init();
                }
            }
        });
    }

    private void doChoose() {
        JFileChooser fileChooser = new JFileChooser(sourceDirectoryPanel.getDirectory());
        fileChooser.setDialogTitle(sourceDirectoryPanel.getChooseDialogTitle());
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        if(JFileChooser.APPROVE_OPTION == fileChooser.showOpenDialog(this)) {
            List<File> files = directoryFiles(fileChooser.getSelectedFile());
            int amount = files.size();
            if(0 < amount) {
                sortFiles = SortFiles.withFrame(MainFrame.this)
                        .setFiles(files)
                        .setLanguage(buttonsPanel.getLanguage())
                        .setWithoutGpsFolder(buttonsPanel.getFolder())
                        .build();
                sortFiles.start();
                init();
                informationPanel.setTotal(amount);
                progressPanel.setMaxValue(amount);
                buttonsPanel.getStartButton().setEnabled(true);
                sourceDirectoryPanel.setDirectory(fileChooser.getSelectedFile().getAbsolutePath());
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

    @PostConstruct
    private void showFrame() {
        addPanels();
        init();
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
            pauseSort();
            return;
        }
        beforeSort();
    }

    private List<File> directoryFiles(File source) {
        File[] files = source.listFiles(File::isFile);
        if(null == files) return new LinkedList<>();
        return new LinkedList<>(Arrays.asList(files));
    }

    public void init() {
        sourceDirectoryPanel.getDirectoryTextField().setEnabled(true);
        sourceDirectoryPanel.getChooseButton().setEnabled(true);
        informationPanel.clear();
        loggerPanel.clear();
        progressPanel.setCurrentValue(0);
        buttonsPanel.getLanguageButton().setEnabled(true);
        buttonsPanel.getFolderButton().setEnabled(true);
        buttonsPanel.getCancelButton().setEnabled(false);
        buttonsPanel.getPauseButton().setEnabled(false);
        buttonsPanel.getStartButton().setEnabled(false);
    }

    public void beforeSort() {
        sourceDirectoryPanel.getDirectoryTextField().setEnabled(false);
        sourceDirectoryPanel.getChooseButton().setEnabled(false);
        buttonsPanel.getLanguageButton().setEnabled(false);
        buttonsPanel.getFolderButton().setEnabled(false);
        buttonsPanel.getCancelButton().setEnabled(true);
        buttonsPanel.getPauseButton().setEnabled(true);
        buttonsPanel.getStartButton().setEnabled(false);
    }

    public void pauseSort() {
        sourceDirectoryPanel.getDirectoryTextField().setEnabled(false);
        sourceDirectoryPanel.getChooseButton().setEnabled(false);
        buttonsPanel.getLanguageButton().setEnabled(false);
        buttonsPanel.getFolderButton().setEnabled(false);
        buttonsPanel.getCancelButton().setEnabled(false);
        buttonsPanel.getPauseButton().setEnabled(false);
        buttonsPanel.getStartButton().setEnabled(true);
    }

    public void afterSort() {
        sourceDirectoryPanel.getDirectoryTextField().setEnabled(true);
        sourceDirectoryPanel.getChooseButton().setEnabled(true);
        buttonsPanel.getLanguageButton().setEnabled(true);
        buttonsPanel.getFolderButton().setEnabled(true);
        buttonsPanel.getCancelButton().setEnabled(false);
        buttonsPanel.getPauseButton().setEnabled(false);
        buttonsPanel.getStartButton().setEnabled(false);
    }

    @Override
    public void addProgress() {
        progressPanel.setCurrentValue(progressPanel.getCurrentValue() + 1);
    }

    @Override
    public void addWithMetadata() {
        informationPanel.setMetadata(informationPanel.getMetadata() + 1);
    }

    @Override
    public void addSorted() {
        informationPanel.setSorted(informationPanel.getSorted() + 1);
    }

    @Override
    public void addError() {
        informationPanel.setErrors(informationPanel.getErrors() + 1);
    }

    @Override
    public void log(Level level, String message) {
        switch(level) {
            case ERROR: loggerPanel.addText(String.format("<font color=\"red\"><b>%s</b></font>", message)); break;
            case WARN: loggerPanel.addText(String.format("<b>%s</b>", message)); break;
            default: loggerPanel.addText(message);
        }
    }

    @Override
    public void endSort() {
        JOptionPane.showMessageDialog(this, "The sorting was finished", "Information", JOptionPane.INFORMATION_MESSAGE);
        afterSort();
    }
}
