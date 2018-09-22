package ru.tomsk.home.tva;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class SourceDirectory implements FileFilter {

    private File sourceDirectory;

    private int filesAmount;

    private int metaFilesAmount;

    private List<MetaFile> metaFiles = new LinkedList<>();


    public SourceDirectory(String pathname) {
        this(new File(pathname));
    }

    public SourceDirectory(File file) {
        if(null == file) throw new NullPointerException("The source directory path cannot be null!");
        if(!file.exists()) throw new IllegalArgumentException(String.format("The source directory '%s' does not exist!", file.getAbsolutePath()));
        if(!file.isDirectory()) throw new IllegalArgumentException(String.format("The file '%s' is not a source directory!", file.getAbsolutePath()));
        try {
            sourceDirectory = file.getCanonicalFile();
        } catch (IOException e) {
            sourceDirectory = file;
        }
        sourceDirectory.listFiles(this);
    }

    @Override
    public boolean accept(File pathname) {
        if(!pathname.isFile()) return false;
        filesAmount++;
        try {
            MetaFile metaFile = new MetaFile(pathname);
            if (metaFile.isContainsCreationDate() || metaFile.isContainsMetadata()) {
                metaFilesAmount++;
                metaFiles.add(metaFile);
            }
        } catch (Exception e) { return false; }
        return false;
    }

    public File getSourceDirectory() {
        return sourceDirectory;
    }

    public int getFilesAmount() {
        return filesAmount;
    }

    public int getMetaFilesAmount() {
        return metaFilesAmount;
    }

    public List<MetaFile> getMetaFiles() {
        return metaFiles;
    }
}
