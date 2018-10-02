package ru.tomsk.home.tva;

import org.slf4j.event.Level;


public interface Sortable {

    void addProgress();

    void addWithMetadata();

    void addSorted();

    void addError();

    void log(Level level, String message);

}
