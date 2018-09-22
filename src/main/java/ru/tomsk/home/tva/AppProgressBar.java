package ru.tomsk.home.tva;

import org.springframework.stereotype.Service;

import javax.swing.*;

@Service
public class AppProgressBar extends JProgressBar {
    public AppProgressBar() {
        setMinimum(0);
    }
}
