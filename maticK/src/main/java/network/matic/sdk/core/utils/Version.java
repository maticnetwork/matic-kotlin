package network.matic.sdk.core.utils;

import java.io.IOException;
import java.util.Properties;

/**
 * Build version utility method.
 */
public class Version {

    public static final String DEFAULT = "none";
    private static final String TIMESTAMP = "timestamp";
    private static final String VERSION = "version";
    private Version() {
    }

    public static String getVersion() throws IOException {
        return loadProperties().getProperty(VERSION);
    }

    public static String getTimestamp() throws IOException {
        return loadProperties().getProperty(TIMESTAMP);
    }

    private static Properties loadProperties() throws IOException {
        Properties properties = new Properties();
        properties.load(Version.class.getResourceAsStream("/web3j-version.properties"));
        return properties;
    }
}
