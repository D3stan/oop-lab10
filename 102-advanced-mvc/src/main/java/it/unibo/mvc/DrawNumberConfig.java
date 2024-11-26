package it.unibo.mvc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class DrawNumberConfig {
    /**
     * Loads config file from resources folder
     * @param configFile file name
     * @return a Map of the configuration values
     */
    static final Map <String, Integer> retrieveConfiguration(final String configFile) {
        final Map<String, Integer> configMap = new HashMap<>();
        try (final BufferedReader r = new BufferedReader(new InputStreamReader(ClassLoader.getSystemResourceAsStream(configFile)))) {
            String lineRead;
            while ((lineRead = r.readLine()) != null) {
                configMap.put(lineRead.split(":")[0], Integer.parseInt(lineRead.split(":")[1].trim()));
            }
        } catch (IOException e) {
            e.printStackTrace(); //NOPMD allowed
        }
        return configMap;
    }
}
