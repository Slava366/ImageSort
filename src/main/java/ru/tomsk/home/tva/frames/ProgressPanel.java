package ru.tomsk.home.tva.frames;

import javax.swing.*;
import java.awt.*;

public class ProgressPanel {

    private static final String PROGRESS_TEXT = "Progress:";

    private JProgressBar progressBar;


    public ProgressPanel() {
        progressBar = new JProgressBar(0, 100);
    }

    public ProgressPanel(int maxValue) {
        progressBar = new JProgressBar();
        progressBar.setMinimum(0);
        setMaxValue(maxValue);
    }

    public JPanel toJPanel() {
        JPanel panel = new JPanel(new BorderLayout(4, 4));
        panel.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
            panel.add(new JLabel(PROGRESS_TEXT), BorderLayout.WEST);
            panel.add(progressBar, BorderLayout.CENTER);
        return panel;
    }

    public void setMaxValue(int value) {
        if(value <= 0) throw new IllegalArgumentException("The value cannot be 0 or less than 0!");
        progressBar.setMaximum(value);
    }

    public int getMaxValue() {
        return progressBar.getMaximum();
    }

    public void setCurrentValue(int value) {
        if(value < 0) throw new IllegalArgumentException("The value cannot be less than 0!");
        if(value > getMaxValue()) throw new IllegalArgumentException("The value cannot be bigger than max value!");
        progressBar.setValue(value);
    }

    public int getCurrentValue() {
        return progressBar.getValue();
    }

    public boolean isEnd() {
        return getMaxValue() == getCurrentValue();
    }
}
