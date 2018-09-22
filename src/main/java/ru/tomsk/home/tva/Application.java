package ru.tomsk.home.tva;

import org.springframework.boot.Banner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import java.io.File;
import java.io.IOException;

@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        new SpringApplicationBuilder(Application.class)
                .headless(false)
                .bannerMode(Banner.Mode.OFF)
                .run(args);
        /*SourceDirectory sourceDirectory = new SourceDirectory("d:\\FOTO\\EuroKate");
        System.out.println(sourceDirectory.getFilesAmount());
        System.out.println(sourceDirectory.getMetaFilesAmount());
        for (MetaFile metaFile : sourceDirectory.getMetaFiles()) {
            GeoDecoder geoDecoder = new GeoDecoder(metaFile);
            try {
                File sortedFile = geoDecoder.getSortedFile();
                metaFile.sortFile(sortedFile);
                System.out.println(sortedFile.getAbsolutePath());
            } catch (ParserConfigurationException | XPathExpressionException | IOException | SAXException e) {
                System.out.println(e.getMessage());
            }
        }*/
    }
}
