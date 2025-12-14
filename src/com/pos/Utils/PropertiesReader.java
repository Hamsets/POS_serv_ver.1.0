package com.pos.Utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;
import lombok.Getter;


public class PropertiesReader {

    public static final String CONFIG_PROPERTIES_PATH = "src/resources/config.properties";
    @Getter
    private HashMap<String,String> mapProperties = new HashMap<>();

    public PropertiesReader(){
        FileInputStream fileInputStream;
        Properties property = new Properties();
        try {
            fileInputStream = new FileInputStream (CONFIG_PROPERTIES_PATH);
            property.load(fileInputStream);
            mapProperties.put("db.url", property.getProperty("db.url"));
            mapProperties.put("db.user", property.getProperty("db.user"));
            mapProperties.put("db.password", property.getProperty("db.password"));
            mapProperties.put("dbText.path", property.getProperty("dbText.path"));
            mapProperties.put("portServer", property.getProperty("portServer"));
            mapProperties.put("timeZoneMilliSec", property.getProperty("timeZoneMilliSec"));
            fileInputStream.close();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

}
