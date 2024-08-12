package com.company.makepub.app.usecase.epub;

import com.company.makepub.app.domain.MarkupRecord;
import com.company.makepub.app.gateway.UUIDGenerator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class ConvertMarkupToHtmlTest {

    @Mock
    private UUIDGenerator mockUUIDGenerator;

    private ConvertMarkupToHtml convertMarkupToHtml;
    private static final List<MarkupRecord> MY_MARKUPS = new ArrayList<>();

    @BeforeAll
    static void beforeAll() {
        MY_MARKUPS.add(new MarkupRecord("_", "<i>", "</i>", false, false, false, false, null));
        MY_MARKUPS.add(new MarkupRecord("~", "<b>", "</b>", false, false, false, false, null));
        MY_MARKUPS.add(new MarkupRecord("*", "<b><sup><a epub:type=\"noteref\" href=\"#{idFootNote}\">*</a></sup></b>", "", false, true, false, false, null));
        MY_MARKUPS.add(new MarkupRecord("£", "<aside id=\"{idFootNote}\" epub:type=\"footnote\"><p>", "</p></aside>", true, false, true, false, null));
        MY_MARKUPS.add(new MarkupRecord("=", "<h5>", "</h5>", true, false, false, true, null));

    }

    @BeforeEach
    void setUp() {
        convertMarkupToHtml = new ConvertMarkupToHtml(mockUUIDGenerator, MY_MARKUPS);
    }

    @Test
    @DisplayName("Should convert question with a box")
    void shouldConvertQuestion(){
        Mockito.when(mockUUIDGenerator.generate())
                .thenReturn("123");
        String text = "=Hello, ~World!~ Hello";
        String boxQuestion = "<textarea id=\"123\" rows=\"3\" cols=\"40\"></textarea>";
        String expected = "<h5>Hello, <b>World!</b> Hello</h5>"+boxQuestion;
        assertEquals(expected, convertMarkupToHtml.convert(text));
    }

    @Test
    @DisplayName("Should convert question with a box one time only")
    void shouldConvertQuestionOneTime(){
        Mockito.when(mockUUIDGenerator.generate())
                .thenReturn("123");
        String text = "=Hello, =~World!~ Hello";
        String boxQuestion = "<textarea id=\"123\" rows=\"3\" cols=\"40\"></textarea>";
        String expected = "<h5>Hello, =<b>World!</b> Hello</h5>"+boxQuestion;
        assertEquals(expected, convertMarkupToHtml.convert(text));
    }

    @Test
    @DisplayName("Should not convert anything")
    void convert() {
        String text = "Hello, World!";
        assertEquals(text, convertMarkupToHtml.convert(text));
    }

    @Test
    @DisplayName("Should convert Bold")
    void convertBold() {
        String text = "Hello, ~World!~ Hello";
        String expected = "Hello, <b>World!</b> Hello";
        assertEquals(expected, convertMarkupToHtml.convert(text));
    }

    @Test
    @DisplayName("Should convert Bold two times")
    void convertBold2() {
        String text = "Hello, ~World!~ Hello Hello, ~World!~ Hello";
        String expected = "Hello, <b>World!</b> Hello Hello, <b>World!</b> Hello";
        assertEquals(expected, convertMarkupToHtml.convert(text));
    }

    @Test
    @DisplayName("Should convert Bold three times")
    void convertBold3() {
        String text = "Hello, ~World!~ Hello Hello, ~World!~ Hello Hello, ~World!~ Hello";
        String expected = "Hello, <b>World!</b> Hello Hello, <b>World!</b> Hello Hello, <b>World!</b> Hello";
        assertEquals(expected, convertMarkupToHtml.convert(text));
    }

    @Test
    @DisplayName("Should convert Italic")
    void convertItalic() {
        String text = "Hello, _World!_ Hello";
        String expected = "Hello, <i>World!</i> Hello";
        assertEquals(expected, convertMarkupToHtml.convert(text));
    }

    @Test
    @DisplayName("Should convert Italic and Bold")
    void convertItalicAndBold() {
        String text = "Hello, _~World!~_ Hello";
        String expected = "Hello, <i><b>World!</b></i> Hello";
        assertEquals(expected, convertMarkupToHtml.convert(text));
    }

    @Test
    @DisplayName("Should convert Footnote text")
    void convertFootnote() {
        Mockito.when(mockUUIDGenerator.generate())
                .thenReturn("123");
        String text = """
                Hello, World!* Hello
                £Text footnote.
                """;
        String expected = """
                Hello, World!<b><sup><a epub:type="noteref" href="#123">*</a></sup></b> Hello
                <aside id="123" epub:type="footnote"><p>Text footnote.</p></aside>
                """;
        assertEquals(expected.trim(), convertMarkupToHtml.convert(text));
    }

    @Test
    @DisplayName("Should convert two Footnote texts")
    void convertTwoFootnote() {
        Mockito.when(mockUUIDGenerator.generate())
                .thenReturn("123")
                .thenReturn("124");
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
        assertEquals(expected.trim(), convertMarkupToHtml.convert(text));
    }

    @Test
    @DisplayName("Should convert two Footnote texts in same line")
    void convertTwoFootnoteSameLine() {
        Mockito.when(mockUUIDGenerator.generate())
                .thenReturn("123")
                .thenReturn("124");
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
        assertEquals(expected.trim(), convertMarkupToHtml.convert(text));
    }



}
