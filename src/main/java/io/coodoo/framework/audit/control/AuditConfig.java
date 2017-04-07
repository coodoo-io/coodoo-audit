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
     * Maximal possible characters to use as a value in {@link AuditChange}
     */
    public static int MAX_CHARACTERS = 4000;

    /**
     * Default pattern for date values in {@link AuditChange}
     */
    public static String TIMESTAMP_PATTERN = "dd.MM.yyyy HH:mm:ss";

    /**
     * ZoneId Object time zone for LocalDateTime instance creation.
     */
    public static String LOCAL_DATE_TIME_ZONE = "UTC";

    /**
     * Name of the (optional) audit property file
     */
    private static final String auditPropertiesFilename = "coodoo.audit.properties";

    static Properties properties = new Properties();

    public void init(@Observes @Initialized(ApplicationScoped.class) Object init) {

        AuditConfig.loadProperties();
    }

    private static void loadProperties() {
        InputStream inputStream = null;
        try {
            inputStream = AuditConfig.class.getClassLoader().getResourceAsStream(auditPropertiesFilename);

            if (inputStream != null) {

                properties.load(inputStream);
                log.info("Reading {}", auditPropertiesFilename);

                DEFAULT_USER = loadProperty(DEFAULT_USER, "coodoo.audit.default.user");
                MAX_CHARACTERS = loadProperty(MAX_CHARACTERS, "coodoo.audit.max.character");
                LOCAL_DATE_TIME_ZONE = loadProperty(LOCAL_DATE_TIME_ZONE, "coodoo.audit.timezone");
                TIMESTAMP_PATTERN = loadProperty(TIMESTAMP_PATTERN, "coodoo.audit.timestamp.pattern");
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

    private static String loadProperty(String value, String key) {

        String property = properties.getProperty(key);
        if (property == null) {
            return value;
        }
        log.info("Audit Property {} loaded: {}", key, property);
        return property;
    }

    private static int loadProperty(int value, String key) {
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
