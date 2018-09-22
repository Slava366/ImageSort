package ru.tomsk.home.tva;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.Map;

public class GeoDecoder {

    private MetaFile metaFile;

    private GeoDecoderLanguage responseLanguage;

    private static final String GEO_SERVER_URL_TEMPLATE = "https://geocode-maps.yandex.ru/1.x/?geocode=%s&results=1&sco=latlong&lang=%s";

    private static final GeoDecoderLanguage DEFAULT_RESPONSE_LANGUAGE = GeoDecoderLanguage.US;


    public GeoDecoder(MetaFile metaFile, GeoDecoderLanguage language) {
        if(null == metaFile) throw new NullPointerException("Argument 'metaFile' cannot be null!");
        if(null == language) throw new NullPointerException("Argument 'responseLanguage' cannot be null!");
        this.metaFile = metaFile;
        responseLanguage = language;
    }

    public GeoDecoder(MetaFile metaFile) {
        this(metaFile, DEFAULT_RESPONSE_LANGUAGE);
    }

    private GeoServerResponse getResponse() throws ParserConfigurationException, IOException, SAXException, XPathExpressionException {
        Map<String, String> nodes = new LinkedHashMap<>();
        if(!metaFile.isContainsMetadata()) return new GeoServerResponse(nodes);
        URL url = new URL(String.format(GEO_SERVER_URL_TEMPLATE, metaFile.getGpsLocation(), responseLanguage));
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        Document document = documentBuilder.parse(url.openStream());
        XPathFactory xPathFactory = XPathFactory.newInstance();
        XPath xPath = xPathFactory.newXPath();
        NodeList kinds = (NodeList) xPath.compile("//Component/kind/text()").evaluate(document, XPathConstants.NODESET);
        NodeList names = (NodeList) xPath.compile("//Component/name/text()").evaluate(document, XPathConstants.NODESET);
        if((0 == kinds.getLength()) || (kinds.getLength() != names.getLength())) throw new IOException("Bad GPS server response!");
        for (int i = 0; i < kinds.getLength(); i++)
            nodes.put(
                    kinds.item(i).getNodeValue(),
                    names.item(i).getNodeValue()
            );
        return new GeoServerResponse(nodes);
    }

    public File getSortedFile() throws ParserConfigurationException, SAXException, XPathExpressionException, IOException {
        GeoServerResponse geoServerResponse = getResponse();
        StringBuilder sb = new StringBuilder(metaFile.getFile().getParent());
        if(!geoServerResponse.getLocality().equals("")) sb.append(File.separator).append(geoServerResponse.getLocality());
        if(!geoServerResponse.getChildLocality().equals("")) sb.append(File.separator).append(geoServerResponse.getChildLocality());
        if(metaFile.isContainsCreationDate())
            sb.append(File.separator)
                    .append(
                            metaFile.getCreationDate()
                                    .toInstant()
                                    .atZone(ZoneId.systemDefault())
                                    .format(DateTimeFormatter.ISO_LOCAL_DATE)
                    );
        return new File(sb.append(File.separator).append(metaFile.getFile().getName()).toString());
    }
}
