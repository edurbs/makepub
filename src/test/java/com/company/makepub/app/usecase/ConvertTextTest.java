package com.company.makepub.app.usecase;

import com.company.makepub.app.domain.Markup;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;



class ConvertTextTest {

    private ConvertText convertText;
    private static List<Markup> markups = new ArrayList<>();

    @BeforeAll
    static void beforeAll() {
        markups.add(new Markup("_", "<i>", "</i>", false, false, false));
        markups.add(new Markup("~", "<b>", "</b>", false, false, false));
        markups.add(new Markup("*", "<b><sup><a epub:type=\"noteref\" href=\"#{idFootNote}\">*</a></sup></b>", "", false, true, false));
        markups.add(new Markup("£", "<aside id=\"{idFootNote}\" epub:type=\"footnote\"><p>", "</p></aside>", true, false, true));

    }

    @BeforeEach
    void setUp() {
        convertText = new ConvertText(new myUUIDGeneratorTest(), this.markups);
    }

    @Test
    @DisplayName("Should not convert anything")
    void convert() {
        String text = "Hello, World!";
        assertEquals(text, convertText.convert(text));
    }

    @Test
    @DisplayName("Should convert Bold")
    void convertBold() {
        String text = "Hello, ~World!~ Hello";
        String expected = "Hello, <b>World!</b> Hello";
        assertEquals(expected, convertText.convert(text));
    }

    @Test
    @DisplayName("Should convert Bold two times")
    void convertBold2() {
        String text = "Hello, ~World!~ Hello Hello, ~World!~ Hello";
        String expected = "Hello, <b>World!</b> Hello Hello, <b>World!</b> Hello";
        assertEquals(expected, convertText.convert(text));
    }

    @Test
    @DisplayName("Should convert Bold three times")
    void convertBold3() {
        String text = "Hello, ~World!~ Hello Hello, ~World!~ Hello Hello, ~World!~ Hello";
        String expected = "Hello, <b>World!</b> Hello Hello, <b>World!</b> Hello Hello, <b>World!</b> Hello";
        assertEquals(expected, convertText.convert(text));
    }

    @Test
    @DisplayName("Should convert Italic")
    void convertItalic() {
        String text = "Hello, _World!_ Hello";
        String expected = "Hello, <i>World!</i> Hello";
        assertEquals(expected, convertText.convert(text));
    }

    @Test
    @DisplayName("Should convert Italic and Bold")
    void convertItalicAndBold() {
        String text = "Hello, _~World!~_ Hello";
        String expected = "Hello, <i><b>World!</b></i> Hello";
        assertEquals(expected, convertText.convert(text));
    }

    @Test
    @DisplayName("Should convert Footnote text")
    void convertFootnote() {
        String text = """
                Hello, World!* Hello
                £Text footnote.
                """;
        String expected = """
                Hello, World!<b><sup><a epub:type="noteref" href="#123">*</a></sup></b> Hello
                <aside id="123" epub:type="footnote"><p>Text footnote.</p></aside>
                """;
        assertEquals(expected.trim(), convertText.convert(text));
    }

    @Test
    @DisplayName("Should convert two Footnote texts")
    void convertTwoFootnote() {
        String text = """
                Hello, World!* Hello
                Hello,* World! Hello
                £Text footnote.
                £Another thing.
                """;
        String expected = """
                Hello, World!<b><sup><a epub:type="noteref" href="#123">*</a></sup></b> Hello
                Hello,<b><sup><a epub:type="noteref" href="#124">*</a></sup></b> World! Hello
                <aside id="123" epub:type="footnote"><p>Text footnote.</p></aside>
                <aside id="124" epub:type="footnote"><p>Another thing.</p></aside>
                """;
        assertEquals(expected.trim(), convertText.convert(text));
    }

    @Test
    @DisplayName("Should convert two Footnote texts in same line")
    void convertTwoFootnoteSameLine() {
        String text = """
                Hello, World!* Hello Hello,* World! Hello
                £Text footnote.
                £Another thing.
                """;
        String expected = """
                Hello, World!<b><sup><a epub:type="noteref" href="#123">*</a></sup></b> Hello Hello,<b><sup><a epub:type="noteref" href="#124">*</a></sup></b> World! Hello
                <aside id="123" epub:type="footnote"><p>Text footnote.</p></aside>
                <aside id="124" epub:type="footnote"><p>Another thing.</p></aside>
                """;
        assertEquals(expected.trim(), convertText.convert(text));
    }



}

