package ru.tomsk.home.tva.frames;

import javax.swing.*;
import java.awt.*;

public class ButtonsPanel {

    private static final String OPTIONS_TITLE = "Options";
    private static final String PAUSE_TITLE = "Pause";
    private static final String START_TITLE = "Sort";

    private JButton options;
    private JButton pause;
    private JButton start;


    public ButtonsPanel(Component parent) {
        options = new JButton(OPTIONS_TITLE);
        pause = new JButton(PAUSE_TITLE);
        start = new JButton(START_TITLE);
    }

    public JPanel toJPanel() {
        JPanel panel = new JPanel(new BorderLayout(4, 4));
        panel.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
            JPanel panel1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
                panel1.add(options);
            panel.add(panel1, BorderLayout.WEST);
            JPanel panel2 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
                panel2.add(pause);
                panel2.add(start);
            panel.add(panel2, BorderLayout.CENTER);
        return panel;
    }
}
