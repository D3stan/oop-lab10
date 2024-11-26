package it.unibo.mvc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;


/**
 * Utility class for retrieving config values from file.
 */
public final class DrawNumberConfig {

    /**
     * private constructor.
     */
    private DrawNumberConfig() {
        // do nothing
    }

    /**
     * Loads config file from resources folder.
     * @param configFile file name
     * @return a Map of the configuration values
     */
    static Map<String, Integer> retrieveConfiguration(final String configFile) {
        final Map<String, Integer> configMap = new HashMap<>();
        try (
            BufferedReader r = new BufferedReader(
                new InputStreamReader(ClassLoader.getSystemResourceAsStream(configFile), StandardCharsets.UTF_8)
            )) {
            String lineRead;
            while ((lineRead = r.readLine()) != null) { // NOPMD needed for cleaner code
                configMap.put(lineRead.split(":")[0], Integer.parseInt(lineRead.split(":")[1].trim()));
            }
        } catch (IOException e) {
            e.printStackTrace(); //NOPMD allowed
        }
        return configMap;
    }
}
