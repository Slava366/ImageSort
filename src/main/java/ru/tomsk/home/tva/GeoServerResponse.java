package ru.tomsk.home.tva;

import java.util.Map;

public class GeoServerResponse {

    private String locality;

    private String childLocality;


    public GeoServerResponse(Map<String, String> nodes) {
        if(nodes.size() == 0) {
            locality = childLocality = "";
            return;
        }
        if(nodes.containsKey("country")) locality = nodes.get("country");
        else locality = nodes.values().iterator().next();
        if(nodes.containsKey("locality")) childLocality = nodes.get("locality");
        else if(nodes.containsKey("district")) childLocality = nodes.get("district");
        else if(nodes.containsKey("area")) childLocality = nodes.get("area");
        else childLocality = nodes.getOrDefault("province", "");
    }

    public String getLocality() {
        return locality;
    }

    public String getChildLocality() {
        return childLocality;
    }
}
