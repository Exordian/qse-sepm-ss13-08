package at.ac.tuwien.sepm.service;

public interface PropertyService {

    /**
     * set program wide property
     * @param key property key
     * @param value property value
     */
    void setProperty(String key, String value);

    /**
     * gets program wide property
     * @param key property key
     * @return property value
     */
    String getProperty(String key);

    /**
     * gets program wide property
     * @param key property key
     * @param defaultValue default value
     * @return property value
     */
    String getProperty(String key, String defaultValue);

    /**
     * reload property file
     */
    void load();

    /**
     * save property file
     */
    void save();

}
