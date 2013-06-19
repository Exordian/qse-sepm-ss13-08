package at.ac.tuwien.sepm.service.impl;


import at.ac.tuwien.sepm.service.PropertyService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

@Service
public class PropertyServiceImpl implements PropertyService {
    private static Logger log = LogManager.getLogger(PropertyServiceImpl.class);

    private Properties properties;

    @PostConstruct
    @Override
    public void load() {
        properties = new Properties();
        try {
            // TODO: store production and test property file seperately
            FileInputStream in = new FileInputStream("./studentmanager.properties");
            properties.load(in);
        } catch (IOException e) {
            log.error("property file not found, using empty properties", e);
        }
    }

    @Override
    public void save() {
        try {
            FileOutputStream out = new FileOutputStream("./studentmanager.properties");
            properties.store(out, "");
            out.close();
        } catch (IOException e) {
            log.error("could not write property file", e);
        }

    }

    @Override
    public void setProperty(String key, String value) {
        properties.setProperty(key, value);
        save();
    }

    @Override
    public String getProperty(String key) {
        return properties.getProperty(key);
    }

    @Override
    public String getProperty(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }

    @Override
    public void removeProperty(String key) {
        properties.remove(key);
        save();
    }
}
