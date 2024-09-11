package com.company.makepub.app.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BookNameTest {

    @Test
    @DisplayName("Should get meps format")
    void getMepsFormat() {
        String actual = Book.BOOK_01_GEN.getMepsFormat();
        assertEquals("01_GEN", actual);
    }

    @Test
    @DisplayName("Should get number of chapters for given book")
    void getNumberOfChapters() {
        int actual = Book.BOOK_56_TIT.getNumberOfChapters();
        assertEquals(3, actual);
    }

    @Test
    @DisplayName("Should get number of scriptures for given chapter")
    void getNumberOfScriptures() {
        int actual = Book.BOOK_65_JUD.getNumberOfScriptures(1);
        assertEquals(25, actual);
    }

    @Test
    @DisplayName("Given a string should get ordinal value")
    void getOrdinalValue() {
        int actual = Book.getOrdinalValue("book_66_rev");
        assertEquals(66, actual);
    }

    @Test
    @DisplayName("Given a null string should get 0")
    void getOrdinalValueNull() {
        int actual = Book.getOrdinalValue(null);
        assertEquals(0, actual);
    }

    @Test
    @DisplayName("Given a blank string should get 0")
    void getOrdinalValueBlank() {
        int actual = Book.getOrdinalValue("");
        assertEquals(0, actual);
    }

    @Test
    @DisplayName("Given a invalid string should get 0")
    void getOrdinalValueInvalid() {
        int actual = Book.getOrdinalValue("asd");
        assertEquals(0, actual);
    }

    @Test
    @DisplayName("Given a book should ordinal value")
    void testGetOrdinalValue() {
        int actual = Book.BOOK_66_REV.getOrdinalValue();
        assertEquals(66, actual);
    }

    @Test
    @DisplayName("Should return 66 values")
    void values() {
        int actual = Book.values().length;
        assertEquals(66, actual);
    }
}