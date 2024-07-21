package com.company.makepub.app.usecase.types;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;

class EpubMapTest {


    @Test
    @DisplayName("Should get content with a variable to put UUID")
    void getContent() {
        String content = EpubMap.CONTENT.getDefaultText().formatted(UUID.randomUUID().toString());
        String uuidRegex = "\\b[0-9a-fA-F]{8}\\b-[0-9a-fA-F]{4}\\b-[0-9a-fA-F]{4}\\b-[0-9a-fA-F]{4}\\b-[0-9a-fA-F]{12}\\b";
        Pattern pattern = Pattern.compile(uuidRegex);
        Matcher matcher = pattern.matcher(content);
        assertTrue(matcher.find());
    }
}