package com.retail.experience.io;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Use NIO to read data from system resource
 */
public class CsvLoader implements Loader {

    private final String filepath;
    private Map<Integer, String> headerIndexMap;
    private final Logger logger = LogManager.getLogger(CsvLoader.class.getName());

    public CsvLoader(String filepath) {
        this.filepath = filepath;
    }

    @Override
    public List<Map<String, String>> loadResourceData() {
        try {
            URI uri = ClassLoader.getSystemResource(filepath).toURI();
            List<String> lines = Files.readAllLines(Paths.get(uri));
            loadHeaderIndex(lines.get(0));
            return processAllLines(lines);
        } catch (Exception exception) {
            logger.info("Can not load resource with filepath: " + filepath);
            return new ArrayList<>();
        }
    }

    public Map<Integer, String> loadHeaderIndex(String lineHeader) {
        headerIndexMap = new HashMap<>();
        String[] values = lineHeader.split(",");
        for (int index = 0; index < values.length; index++) {
            headerIndexMap.put(index, values[index].trim());
        }
        return headerIndexMap;
    }

    public List<Map<String, String>> processAllLines(List<String> lines) {
        List<Map<String, String>> listItems = new ArrayList<>();
        for (int index = 1; index < lines.size(); index++) {
            Map<String, String> items = new HashMap<>();
            String[] values = lines.get(index).split(",");
            for (int col = 0; col < values.length; col++) {
                items.put(headerIndexMap.get(col), values[col].trim());
            }
            listItems.add(items);
        }
        return listItems;
    }
}
