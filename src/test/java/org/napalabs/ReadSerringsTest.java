package org.napalabs;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class ReadSerringsTest {
    static Settings settings;
    @ParameterizedTest
    @CsvSource(value = {
            "settings.txt, 42000",
            "settings, 42000",
            "'' ,42000",
            "null, 42000"
    })
    void getCorrectPort(String filePath, int port) {
        settings = new Settings(filePath);
        Assertions.assertEquals(port,settings.getPort());
    }
}