package com.retail.experience.io;

import com.retail.experience.factory.ComputerComponentFactory;
import com.retail.experience.model.Component;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

class ParserTest {

    @Test
    void parse_components() {
        Loader loader = new CsvLoader("Inventory.csv");
        Parser parser = new Parser(loader, new ComputerComponentFactory());
        List<Component> components = parser.parseComponents();
        Assertions.assertEquals(100, components.size());
    }
}