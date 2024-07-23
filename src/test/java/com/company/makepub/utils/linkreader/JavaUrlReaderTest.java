package com.company.makepub.utils.linkreader;

import com.company.makepub.app.usecase.exceptions.UseCaseException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JavaUrlReaderTest {

    @Test
    @DisplayName("Should read raw text from site")
    void execute() {
        String text = new JavaUrlReader().execute("https://www.google.com/");
        assertNotNull(text);
    }

    @Test
    @DisplayName("Should throws if invalid url")
    void executeInvalidUrl() {
        assertThrows(UseCaseException.class, () -> new JavaUrlReader().execute("invalid"));
    }
}