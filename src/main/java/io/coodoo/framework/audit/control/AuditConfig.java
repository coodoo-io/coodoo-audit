package io.coodoo.framework.audit.control;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.coodoo.framework.audit.entity.AuditChange;

/**
 * Audit configuration
 * 
 * @author coodoo GmbH (coodoo.io)
 */
@ApplicationScoped
public class AuditConfig {

    private static Logger log = LoggerFactory.getLogger(AuditConfig.class);

    /**
     * Default user name
     */
    public static String DEFAULT_USER = "Unknown";

    /**
     * ZoneId Object time zone for LocalDateTime instance creation.
     */
    public static String LOCAL_DATE_TIME_ZONE = "UTC";

    /**
     * Maximal possible characters to use as a value in {@link AuditChange}
     */
    public static int MAX_CHARACTERS = 4000;

    /**
     * Default pattern for date values in {@link AuditChange}
     */
    public static String TIMESTAMP_PATTERN = "dd.MM.yyyy HH:mm:ss";

    /**
     * Separator to join list elements
     */
    public static String LIST_SEPARATOR = ", ";

    /**
     * Name of the (optional) audit property file
     */
    private static final String auditPropertiesFilename = "coodoo.audit.properties";

    Properties properties = new Properties();

    public void init(@Observes @Initialized(ApplicationScoped.class) Object init) {

        InputStream inputStream = null;
        try {
            inputStream = getClass().getClassLoader().getResourceAsStream(auditPropertiesFilename);

            if (inputStream != null) {

                properties.load(inputStream);
                log.info("Reading {}", auditPropertiesFilename);

                DEFAULT_USER = loadProperty(DEFAULT_USER, "coodoo.audit.default.user");
                LOCAL_DATE_TIME_ZONE = loadProperty(LOCAL_DATE_TIME_ZONE, "coodoo.audit.timezone");
                MAX_CHARACTERS = loadProperty(MAX_CHARACTERS, "coodoo.audit.max.character");
                TIMESTAMP_PATTERN = loadProperty(TIMESTAMP_PATTERN, "coodoo.audit.timestamp.pattern");
                LIST_SEPARATOR = loadProperty(LIST_SEPARATOR, "coodoo.audit.list.separator");
            }
        } catch (IOException e) {
            log.info("Couldn't read {}!", auditPropertiesFilename, e);
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                log.warn("Couldn't close {}!", auditPropertiesFilename, e);
            }
        }
    }

    private String loadProperty(String value, String key) {

        String property = properties.getProperty(key);
        if (property == null) {
            return value;
        }
        log.info("Audit Property {} loaded: {}", key, property);
        return property;
    }

    private int loadProperty(int value, String key) {
        String property = properties.getProperty(key);
        if (property != null) {
            try {
                log.info("Audit Property {} loaded: {}", key, property);
                return Integer.valueOf(property).intValue();
            } catch (NumberFormatException e) {
                log.warn("Audit Property {} value invalid: {}", key, property);
            }
        }
        return value;
    }

}
