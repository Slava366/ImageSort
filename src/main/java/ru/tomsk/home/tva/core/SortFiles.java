package ru.tomsk.home.tva.core;

import com.drew.imaging.ImageProcessingException;
import org.slf4j.event.Level;
import org.xml.sax.SAXException;
import ru.tomsk.home.tva.Sortable;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class SortFiles extends Thread {

    private Sortable frame;
    private List<File> files;
    private int position;
    private boolean inProgress;
    private boolean exit;
    private LatLongDecoder.Language language;
    private String withoutGpsFolder;


    public static class Builder {

        private static final String ARGUMENT_NULL = "Argument cannot be null!";
        private static final String ARGUMENT_EMPTY = "Argument cannot be empty!";
        private Sortable frame;
        private List<File> files;
        private LatLongDecoder.Language language;
        private String withoutGpsFolder;

        private Builder(Sortable frame) {
            if(null == frame) throw new NullPointerException(ARGUMENT_NULL);
            this.frame = frame;
        }

        public Builder setFiles(List<File> files) {
            if(null == files) throw new NullPointerException(ARGUMENT_NULL);
            if(files.isEmpty()) throw new  IllegalArgumentException(ARGUMENT_EMPTY);
            this.files = files;
            return this;
        }

        public Builder setLanguage(LatLongDecoder.Language language) {
            if(null == language) throw new NullPointerException(ARGUMENT_NULL);
            this.language = language;
            return this;
        }

        public Builder setWithoutGpsFolder(String withoutGpsFolder) {
            if(null == withoutGpsFolder) throw new NullPointerException(ARGUMENT_NULL);
            this.withoutGpsFolder = withoutGpsFolder;
            return this;
        }

        public SortFiles build() {
            if(null == frame) throw new NullPointerException(ARGUMENT_NULL);
            if(null == files) throw new NullPointerException(ARGUMENT_NULL);
            if(null == language) throw new NullPointerException(ARGUMENT_NULL);
            if(null == withoutGpsFolder) throw new NullPointerException(ARGUMENT_NULL);
            SortFiles sortFiles = new SortFiles();
            sortFiles.frame = frame;
            sortFiles.files = files;
            sortFiles.language = language;
            sortFiles.withoutGpsFolder = withoutGpsFolder;
            sortFiles.position = 0;
            sortFiles.inProgress = false;
            sortFiles.exit = false;
            return sortFiles;
        }
    }


    public static Builder withFrame(Sortable frame) {
        return new Builder(frame);
    }

    public void setInProgress(boolean inProgress) {
        this.inProgress = inProgress;
    }

    public void setExit(boolean exit) {
        this.exit = exit;
    }

    @Override
    public void run() {
        int amount = files.size();
        while(!inProgress) yield();
        for(int i = 0; i < amount; i++, position++) {
            File file = files.get(i);
            MetaFile metaFile;
            File newFile;
            frame.addProgress();
/*
            try {
                metaFile = new MetaFile(file);
                try {
                    newFile = metaFile.getMetaFile(new LatLongDecoder(language), withoutGpsFolder);
                    if(null != newFile) {
                        frame.addWithMetadata();
                        newFile.getParentFile().mkdirs();
                        if(file.renameTo(newFile)) {
                            frame.addSorted();
                            frame.log(
                                    Level.INFO,
                                    String.format("[%d/%d][INFO] - The file '%s' successfully moved to '%s'", position + 1, amount, file.getAbsolutePath(), newFile.getAbsolutePath())
                            );
                        } else {
                            frame.addError();
                            frame.log(
                                    Level.ERROR,
                                    String.format("[%d/%d][ERROR] - The file '%s' cannot be moved to '%s'", position + 1, amount, file.getAbsolutePath(), newFile.getAbsolutePath())
                            );
                        }
                    } else {
                        frame.log(
                                Level.WARN,
                                String.format("[%d/%d][WARNING] - The file '%s' does not contain metadata", position + 1, amount, file.getAbsolutePath())
                        );
                    }
                } catch (ParserConfigurationException | SAXException | XPathExpressionException e) {
                    frame.addError();
                    frame.addWithMetadata();
                    frame.log(
                            Level.ERROR,
                            String.format("[%d/%d][ERROR] - Bad server response for file '%s'", position + 1, amount, file.getAbsolutePath())
                    );
                }
            } catch (IOException | ImageProcessingException e) {
                frame.log(
                        Level.WARN,
                        String.format("[%d/%d][WARNING] - The file '%s' does not contain metadata", position + 1, amount, file.getAbsolutePath())
                );
            }
*/
            while(!inProgress) yield();
            if(exit) break;
        }
    }
}
