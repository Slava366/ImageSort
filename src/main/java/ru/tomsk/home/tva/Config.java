package ru.tomsk.home.tva;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import ru.tomsk.home.tva.frames.*;

@Configuration
@PropertySource("classpath:config.properties")
public class Config {

    @Value("${app.title}")
    private String appTitle;

    @Bean
    public MainFrame mainFrame() {
        return new MainFrame(appTitle);
    }

    @Bean
    public SourceDirectoryPanel sourceDirectoryPanel() {
        return new SourceDirectoryPanel();
    }

    @Bean
    public InformationPanel informationPanel() {
        return new InformationPanel();
    }

    @Bean
    public LoggerPanel loggerPanel() {
        return new LoggerPanel();
    }

    @Bean
    public ProgressPanel progressPanel() {
        return new ProgressPanel();
    }

    @Bean
    public ButtonsPanel buttonsPanel(MainFrame mainFrame) {
        return new ButtonsPanel(mainFrame);
    }
}
