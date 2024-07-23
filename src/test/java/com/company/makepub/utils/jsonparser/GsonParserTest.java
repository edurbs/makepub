package com.company.makepub.utils.jsonparser;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class GsonParserTest {

    private record JsonRecord(String name) { }

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
        GsonParser<JsonRecord> gsonParser = new GsonParser<>();
        List<JsonRecord> records = gsonParser.parse(jsonText, JsonRecord[].class);
        assertEquals(3, records.size());
        assertEquals("value1", records.getFirst().name);
        assertEquals("value3", records.getLast().name);
    }
}