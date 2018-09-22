package ru.tomsk.home.tva;

public enum  GeoDecoderLanguage {
    RU("ru_RU", "русский"),
    UA("uk_UA", "украинский"),
    BY("be_BY", "белорусский"),
    US("en_US", "американский"),
    TR("tr_TR", "турецкий");

    private String name;

    private String description;

    GeoDecoderLanguage(String name, String description) {
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
