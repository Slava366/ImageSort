package ru.tomsk.home.tva.core;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class LatLongDecoder {

    private static final String URL_TEMPLATE = "https://geocode-maps.yandex.ru/1.x/?geocode=%s,%s&results=1&sco=latlong&lang=%s";

    public static final Language DEFAULT_LANGUAGE = Language.EN;

    private Language language;

    private double latitude;

    private double longitude;


    public enum Language {
        RU("ru_RU", "русский"),
        UK("uk_UA", "украинский"),
        BE("be_BY", "белорусский"),
        EN("en_US", "американский"),
        TR("tr_TR", "турецкий");

        private String name;
        private String description;

        Language(String name, String description) {
            this.name = name;
            this.description = description;
        }

        public String getDescription() {
            return description;
        }


        @Override
        public String toString() {
            return name;
        }
    }


    public LatLongDecoder() {
        this(0, 0, DEFAULT_LANGUAGE);
    }

    public LatLongDecoder(Language language) {
        this(0, 0, language);
    }

    public LatLongDecoder(double latitude, double longitude) {
        this(latitude, longitude, DEFAULT_LANGUAGE);
    }

    public LatLongDecoder(double latitude, double longitude, Language language) {
        this.latitude = latitude;
        this.longitude = longitude;
        setLanguage(language);
    }

    public Language getLanguage() {
        return language;
    }

    public String getLocation(Language language) throws ParserConfigurationException, SAXException, XPathExpressionException, IOException {
        setLanguage(language);
        return getLocation();
    }

    public String getLocation() throws IOException, ParserConfigurationException, SAXException, XPathExpressionException {
        if((0 == latitude) & (0 == longitude)) return "";
        URL url = new URL(
                String.format(
                        URL_TEMPLATE,
                        String.valueOf(latitude),
                        String.valueOf(longitude),
                        language
                )
        );
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(url.openStream());
        XPath xPath = XPathFactory.newInstance().newXPath();
        String locationParent;
        String locationChild;
        if((locationParent = String.valueOf(xPath.compile("//Component[kind = 'country']/name/text()").evaluate(document, XPathConstants.STRING))).isEmpty())
            locationParent = String.valueOf(xPath.compile("//Component[1]/name/text()").evaluate(document, XPathConstants.STRING));
        String location;
        if(!(location = String.valueOf(xPath.compile("//Component[kind = 'locality']/name/text()").evaluate(document, XPathConstants.STRING))).isEmpty()) locationChild = location;
        else if(!(location = String.valueOf(xPath.compile("//Component[kind = 'district']/name/text()").evaluate(document, XPathConstants.STRING))).isEmpty()) locationChild = location;
        else if(!(location = String.valueOf(xPath.compile("//Component[kind = 'area']/name/text()").evaluate(document, XPathConstants.STRING))).isEmpty()) locationChild = location;
        else if(!(location = String.valueOf(xPath.compile("//Component[kind = 'province']/name/text()").evaluate(document, XPathConstants.STRING))).isEmpty()) locationChild = location;
        else locationChild = "";
        if(locationParent.isEmpty() || locationChild.isEmpty()) return locationParent;
        return String.join(File.separator, locationParent, locationChild);
    }

    public void setLanguage(Language language) {
        if(null == language) throw new NullPointerException("Argument cannot be null!");
        this.language = language;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
