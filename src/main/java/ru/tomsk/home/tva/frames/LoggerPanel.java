package ru.tomsk.home.tva.frames;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.util.LinkedList;
import java.util.List;

public class LoggerPanel {

    private static final String LOGGER_TITLE = "Log";
    private static final String BODY_TEMPLATE = "<!DOCTYPE html><html lang=\"en\"><head><meta charset=\"UTF-8\"></head><body>%s</body></html>";

    private JEditorPane editor;

    private List<String> body = new LinkedList<>();


    public LoggerPanel() {
        editor = new JEditorPane();
        editor.setContentType("text/html");
        editor.setEditable(false);
    }

    public JPanel toJPanel() {
        JPanel panel = new JPanel(new BorderLayout(4, 4));
        Border border = BorderFactory.createEtchedBorder();
        panel.setBorder(BorderFactory.createTitledBorder(border, LOGGER_TITLE));
            JScrollPane scroll = new JScrollPane(editor);
            scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
            panel.add(scroll);
        return panel;
    }

    public void clear() {
        body = new LinkedList<>();
        editor.setText("");
    }

    public void addText(String text) {
        if(null == text) throw new NullPointerException("Argument cannot be null!");
        body.add(text);
        editor.setText(String.format(BODY_TEMPLATE, String.join("<br />", body)));
        editor.setCaretPosition(editor.getDocument().getLength());
    }

    public void setText(String text) {
        if(null == text) throw new NullPointerException("Argument cannot be null!");
    }
}
