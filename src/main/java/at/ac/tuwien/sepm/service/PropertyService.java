package at.ac.tuwien.sepm.service;

public interface PropertyService {
    public static final String FIRST_YEAR = "user.firstYear";
    public static final String FIRST_SEMESTER = "user.firstSemester";
    public static final String TISS_USER = "tiss.user";
    public static final String TISS_PASSWORD = "tiss.password";
    public static final String FACEBOOK_KEY = "facebook.user";
    public static final String FACEBOOK_PASSWORD = "facebook.password";
    public static final String MAJOR = "user.majorName";
    public static final String FIRST_RUN = "firstRun";

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

    void removeProperty(String key);
}
