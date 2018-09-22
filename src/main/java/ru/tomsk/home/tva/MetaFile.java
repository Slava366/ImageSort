package ru.tomsk.home.tva;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.lang.GeoLocation;
import com.drew.metadata.Metadata;
import com.drew.metadata.exif.ExifIFD0Directory;
import com.drew.metadata.exif.GpsDirectory;

import java.io.File;
import java.io.IOException;
import java.util.Date;

public class MetaFile {

    private File file;

    private Date creationDate;

    private LatLong gpsLocation;

    private boolean containsMetadata;


    public MetaFile(String pathname) {
        this(new File(pathname));
    }

    public MetaFile(File file) {
        if(null == file) throw new NullPointerException("Argument 'file' cannot be null!");
        if(!file.exists()) throw new IllegalArgumentException(String.format("The file '%s' does not exist!", file.getAbsolutePath()));
        if(!file.isFile()) throw new IllegalArgumentException(String.format("The file '%s' is not a file!", file.getAbsolutePath()));
        this.file = file;
        containsMetadata = false;
        Metadata metadata;
        try {
            metadata = ImageMetadataReader.readMetadata(file);
        } catch (ImageProcessingException | IOException e) {
            return;
        }
        GpsDirectory gpsDirectory = metadata.getFirstDirectoryOfType(GpsDirectory.class);
        GeoLocation geoLocation;
        if(null != gpsDirectory) {
            geoLocation = gpsDirectory.getGeoLocation();
            if(null != geoLocation) {
                gpsLocation = new LatLong();
                gpsLocation.setLatitude(geoLocation.getLatitude());
                gpsLocation.setLongitude(geoLocation.getLongitude());
                containsMetadata = true;
            }
            creationDate = gpsDirectory.getGpsDate();
        }
        if(null == creationDate) {
            ExifIFD0Directory exif = metadata.getFirstDirectoryOfType(ExifIFD0Directory.class);
            if(null != exif) {
                creationDate = exif.getDate(ExifIFD0Directory.TAG_DATETIME);
            }
        }
    }

    public void sortFile(File sortedFile) throws IOException {
        if(null == sortedFile) throw new NullPointerException("Argument 'sortedFile' cannot be null!");
        File sortedDirectory = new File(sortedFile.getParent());
        if(sortedDirectory.mkdirs() || sortedDirectory.exists()) {
            //if(!file.renameTo(sortedFile)) throw new IOException(String.format("Cannot move file '%s'", file.getAbsolutePath()));
        }
    }

    public File getFile() {
        return file;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public boolean isContainsCreationDate() {
        return null != creationDate;
    }

    public LatLong getGpsLocation() {
        return gpsLocation;
    }

    public boolean isContainsMetadata() {
        return containsMetadata;
    }
}
