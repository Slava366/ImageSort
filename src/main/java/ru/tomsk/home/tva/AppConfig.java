package ru.tomsk.home.tva;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:config.properties")
public class AppConfig {

    @Value("${app.title}")
    private String appTitle;

    @Value("${app.button.choose}")
    private String appChooseButtonText;

    @Value("${app.button.start}")
    private String appStartButtonText;

    @Bean
    public AppTextArea appTextArea() {
        return new AppTextArea();
    }

    @Bean
    public AppStartButton appStartButton() {
        return new AppStartButton(appStartButtonText);
    }

    @Bean
    public AppProgressBar appProgressBar() {
        return new AppProgressBar();
    }
    @Bean
    public AppChooseButton appChooseButton() {
        return new AppChooseButton(appChooseButtonText);
    }

    @Bean
    public AppFrame appFrame() {
        return new AppFrame(appTitle);
    }
}
