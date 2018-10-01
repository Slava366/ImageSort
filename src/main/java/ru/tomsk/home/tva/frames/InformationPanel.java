package ru.tomsk.home.tva.frames;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class InformationPanel {

    private static final String TITLE = "Information";

    private static final String TOTAL_TEXT = "Total files:";
    private static final String METADATA_TEXT = "With metadata:";
    private static final String SORTED_TEXT = "Sorted:";
    private static final String ERRORS_TEXT = "Errors:";

    private JLabel total;
    private JLabel metadata;
    private JLabel sorted;
    private JLabel errors;


    public InformationPanel() {
        this.total = new JLabel("0");
        this.metadata = new JLabel("0");
        this.sorted = new JLabel("0");
        this.errors = new JLabel("0");
    }

    public JPanel toJPanel() {
        JPanel panel = new JPanel(new BorderLayout(4, 4));
        Border border = BorderFactory.createEtchedBorder();
        panel.setBorder(BorderFactory.createTitledBorder(border, TITLE));
            JPanel panel1 = new JPanel(new GridLayout(1, 2));
                JPanel panel2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
                    JPanel panel4 = new JPanel(new GridLayout(2, 1, 8, 8));
                        panel4.add(new JLabel(TOTAL_TEXT));
                        panel4.add(new JLabel(METADATA_TEXT));
                    panel2.add(panel4);
                    JPanel panel5 = new JPanel(new GridLayout(2, 1, 8, 8));
                        panel5.add(total);
                        panel5.add(metadata);
                    panel2.add(panel5);
                panel1.add(panel2);
                JPanel panel3 = new JPanel(new FlowLayout(FlowLayout.LEFT));
                    JPanel panel6 = new JPanel(new GridLayout(2, 1, 8, 8));
                        panel6.add(new JLabel(SORTED_TEXT));
                        panel6.add(new JLabel(ERRORS_TEXT));
                    panel3.add(panel6);
                    JPanel panel7 = new JPanel(new GridLayout(2, 1, 8, 8));
                        panel7.add(sorted);
                        panel7.add(errors);
                    panel3.add(panel7);
                panel1.add(panel3);
            panel.add(panel1, BorderLayout.CENTER);
        return panel;
    }

    public int getTotal() {
        return Integer.parseInt(total.getText());
    }

    public void setTotal(int value) {
        total.setText(String.valueOf(value));
    }

    public int getMetadata() {
        return Integer.parseInt(metadata.getText());
    }

    public void setMetadata(int value) {
        metadata.setText(String.valueOf(value));
    }

    public int getSorted() {
        return Integer.parseInt(sorted.getText());
    }

    public void setSorted(int value) {
        sorted.setText(String.valueOf(value));
    }

    public int getErrors() {
        return Integer.parseInt(errors.getText());
    }

    public void setErrors(int value) {
        errors.setText(String.valueOf(value));
    }

    public void clear() {
        total.setText("0");
        metadata.setText("0");
        sorted.setText("0");
        errors.setText("0");
    }
}
