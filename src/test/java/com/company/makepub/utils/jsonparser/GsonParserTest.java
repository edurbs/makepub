package com.company.makepub.utils.jsonparser;

import com.company.makepub.app.utils.jsonparser.GsonParser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class GsonParserTest {

    private record JsonRecord(String name) { }
    private static final GsonParser<JsonRecord> gsonParser = new GsonParser<>();

    @Test
    @DisplayName("Should parse JSON")
    void parse() {
        String jsonText = """
                [
                    {
                        "name" : "value1"
                    },
                    {
                        "name" : "value2"
                    },
                    {
                        "name" : "value3"
                    }
                ]
                """;
        List<JsonRecord> records = gsonParser.parse(jsonText, JsonRecord[].class);
        assertEquals(3, records.size());
        assertEquals("value1", records.getFirst().name);
        assertEquals("value3", records.getLast().name);
    }

    @Test
    @DisplayName("Should parse empty JSON")
    void parseEmpty() {
        String jsonText = """
                []
                """;
        List<JsonRecord> records = gsonParser.parse(jsonText, JsonRecord[].class);
        assertEquals(0, records.size());
    }

    @Test
    @DisplayName("Should parse invalid json")
    void parseInvalid() {
        List<JsonRecord> records = gsonParser.parse("%¨& ", JsonRecord[].class);
        assertEquals(0, records.size());
    }

    @Test
    @DisplayName("Should parse blank JSON")
    void parseEmpty2() {
        List<JsonRecord> records = gsonParser.parse(" ", JsonRecord[].class);
        assertEquals(0, records.size());
    }

    @Test
    @DisplayName("Should parse null JSON")
    void parseNull() {
        List<JsonRecord> records = gsonParser.parse(null, JsonRecord[].class);
        assertEquals(0, records.size());
    }
}