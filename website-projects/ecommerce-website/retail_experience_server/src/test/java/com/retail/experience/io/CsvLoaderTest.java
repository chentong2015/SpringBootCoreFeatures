package com.retail.experience.io;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

class CsvLoaderTest {

    private static final String SOURCE_FILEPATH = "Inventory.csv";
    private static final String SOURCE_FILEPATH_TEST = "Inventory_test.csv";
    private static final String SOURCE_FILEPATH_HEADER = "Inventory_header.csv";
    private static final String SOURCE_FILEPATH_EXTRA = "Inventory_extra.csv";

    @Test
    void load_resource_data() {
        CsvLoader loader = new CsvLoader(SOURCE_FILEPATH);
        List<Map<String, String>> mapList = loader.loadResourceData();
        Assertions.assertEquals(100, mapList.size());
    }

    @Test
    void load_resource_data_with_exception() {
        CsvLoader loader = new CsvLoader(SOURCE_FILEPATH_TEST);
        List<Map<String, String>> mapList = loader.loadResourceData();
        Assertions.assertEquals(0, mapList.size());
    }

    @Test
    void load_header_index() throws URISyntaxException, IOException {
        CsvLoader loader = new CsvLoader(SOURCE_FILEPATH_HEADER);
        List<String> lines = loadLinesFromSystemResource(SOURCE_FILEPATH_HEADER);
        Map<Integer, String> mapHeader = loader.loadHeaderIndex(lines.get(0));
        Assertions.assertEquals("ID", mapHeader.get(0));
    }

    @Test
    void process_all_lines() throws URISyntaxException, IOException {
        CsvLoader loader = new CsvLoader(SOURCE_FILEPATH_EXTRA);
        List<String> lines = loadLinesFromSystemResource(SOURCE_FILEPATH_EXTRA);
        loader.loadHeaderIndex(lines.get(0));
        List<Map<String, String>> mapLines = loader.processAllLines(lines);
        Assertions.assertEquals(1, mapLines.size());
        Assertions.assertEquals("70d0c37e-634e-4a68-8862-0ba44f216f3b", mapLines.get(0).get("ID"));
    }

    private List<String> loadLinesFromSystemResource(String name) throws IOException, URISyntaxException {
        URI uri = ClassLoader.getSystemResource(name).toURI();
        return Files.readAllLines(Paths.get(uri));
    }
}