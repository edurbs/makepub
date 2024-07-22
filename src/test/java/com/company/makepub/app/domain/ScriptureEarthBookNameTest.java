package com.company.makepub.app.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ScriptureEarthBookNameTest {

    @Test
    void getName() {
        assertEquals("3JN", ScriptureEarthBookName.BOOK_3JN.getName());
    }

    @Test
    @DisplayName("Should get correct meps format from String")
    void fromString() {
        String mepsFormat = "BOOK_64_3JO";
        assertEquals(
                ScriptureEarthBookName.BOOK_3JN,
                ScriptureEarthBookName.fromString(mepsFormat));
    }

    @Test
    @DisplayName("Should get correct meps format from Enum")
    void fromMepsFormat() {
        assertEquals(
                ScriptureEarthBookName.BOOK_3JN,
                ScriptureEarthBookName.fromMepsFormatEnum(BookName.BOOK_64_3JO));
    }

    @Test
    @DisplayName("Should get 66 values")
    void values() {
        int size = ScriptureEarthBookName.values().length;
        assertEquals(66, size);
    }
}