package ru.tomsk.home.tva.core;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.lang.GeoLocation;
import com.drew.metadata.Metadata;
import com.drew.metadata.exif.ExifIFD0Directory;
import com.drew.metadata.exif.GpsDirectory;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class MetaFile {

    public static final String WITHOUT_GPS_FOLDER = "WithoutGPS";

    private String withoutGpsFolder = WITHOUT_GPS_FOLDER;

    private File file;

    private Date date;

    private Location location;

    private File metaFile;


    public class Location {

        private double latitude;
        private double longitude;

        public double getLatitude() {
            return latitude;
        }

        private void setLatitude(double latitude) {
            this.latitude = latitude;
        }

        public double getLongitude() {
            return longitude;
        }

        private void setLongitude(double longitude) {
            this.longitude = longitude;
        }
    }


    public MetaFile(String file) throws IOException, ImageProcessingException {
        this(new File(file));
    }

    public MetaFile(File file) throws IOException, ImageProcessingException {
        setFile(file);
    }

    public void setFile(File file) throws IOException, ImageProcessingException {
        if(null == file) throw new NullPointerException("Argument cannot be null!");
        this.file = file;
        this.date = null;
        this.location = null;
        this.metaFile = null;
        Metadata metadata = ImageMetadataReader.readMetadata(new FileInputStream(file));
        GpsDirectory gpsDirectory = metadata.getFirstDirectoryOfType(GpsDirectory.class);
        if(null != gpsDirectory) {
            date = gpsDirectory.getGpsDate();
            GeoLocation geoLocation = gpsDirectory.getGeoLocation();
            if(null != geoLocation) {
                location = new Location();
                location.setLatitude(geoLocation.getLatitude());
                location.setLongitude(geoLocation.getLongitude());
            }
        }
        if(null == date) {
            ExifIFD0Directory exif = metadata.getFirstDirectoryOfType(ExifIFD0Directory.class);
            date = exif.getDate(ExifIFD0Directory.TAG_DATETIME);
        }
    }

    public File getFile() {
        return file;
    }

    public Date getDate() {
        return date;
    }

    public Location getLocation() {
        return location;
    }

    public String getWithoutGpsFolder() {
        return withoutGpsFolder;
    }

    public void setWithoutGpsFolder(String withoutGpsFolder) {
        if(null == withoutGpsFolder) throw new NullPointerException("Argument cannot be null!");
        this.withoutGpsFolder = withoutGpsFolder;
    }

    public File getMetaFile(LatLongDecoder decoder) throws ParserConfigurationException, SAXException, XPathExpressionException, IOException {
        return getMetaFile(decoder, withoutGpsFolder);
    }

    public File getMetaFile(LatLongDecoder decoder, String missingGpsFolder) throws ParserConfigurationException, SAXException, XPathExpressionException, IOException {
        if(null == decoder) throw new NullPointerException("Argument cannot be null!");
        setWithoutGpsFolder(missingGpsFolder);
        if(null != metaFile) return metaFile;
        if((null == location) & (null == date)) return null;
        StringBuilder path = new StringBuilder(file.getAbsoluteFile().getParent());
        String decodedPath = "";
        String decodedDate;
        if(null != location) {
            decoder.setLatitude(location.getLatitude());
            decoder.setLongitude(location.getLongitude());
            decodedPath = decoder.getLocation();
            path.append(File.separator).append(decodedPath);
        } else if(!missingGpsFolder.isEmpty()) path.append(File.separator).append(missingGpsFolder);
        if(null != date) {
            String pattern;
            if(decodedPath.isEmpty()) pattern = String.format("YYYY%sMM", File.separator);
            else pattern = "YYYY-MM-dd";
            decodedDate = date.toInstant().atZone(ZoneId.systemDefault()).format(DateTimeFormatter.ofPattern(pattern));
            path.append(File.separator).append(decodedDate);
        }
        path.append(File.separator).append(file.getAbsoluteFile().getName());
        metaFile = new File(String.valueOf(path));
        return metaFile;
    }

    public boolean moveToMetaFile(LatLongDecoder decoder) throws ParserConfigurationException, SAXException, XPathExpressionException, IOException {
        return moveToMetaFile(decoder, withoutGpsFolder);
    }

    public boolean moveToMetaFile(LatLongDecoder decoder, String missingGpsFolder) throws ParserConfigurationException, SAXException, XPathExpressionException, IOException {
        File metaFile = getMetaFile(decoder, missingGpsFolder);
        if(null == metaFile) return false;
        if(!metaFile.getParentFile().mkdirs()) return false;
        return file.renameTo(metaFile);
    }
}
