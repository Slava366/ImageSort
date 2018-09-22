package ru.tomsk.home.tva;

import org.springframework.boot.Banner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

import javax.swing.*;
import java.io.IOException;


@SpringBootApplication
public class Application {
    public static void main(String[] args) throws IOException {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {}
        new SpringApplicationBuilder(Application.class)
                .headless(false)
                .bannerMode(Banner.Mode.OFF)
                .run(args);
    }
}
