package com.company.makepub.app.usecase.scripture;

import com.company.makepub.app.gateway.UUIDGenerator;
import com.company.makepub.app.usecase.types.BibleReader;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class LinkScriptureTest {
    private final String TAG_A_PREFIX = "<a epub:type=\"noteref\" href=\"#uuid\">";

    @Mock
    private UUIDGenerator mockUUIDGenerator;

    @Mock
    private BibleReader bibleReader;

    LinkScriptures init() {
        return new LinkScriptures(new MakeRegex(), mockUUIDGenerator, bibleReader);
    }

    void setupMockUUIDGenerator() {
        Mockito.when(mockUUIDGenerator.generate())
                .thenReturn("uuid");
        Mockito.when(bibleReader.getScripture(Mockito.any()))
                .thenReturn("Some fake content.");
    }


    @Test
    @DisplayName("Link Salmo 10:1")
    void linkSalmo10_1(TestInfo testInfo) {
        setupMockUUIDGenerator();
        var sut = init();
        String expectedHtml = """
            Link %sSalmo 10:1</a>
            <div class="groupExt">
            <div class="groupExtScrpCite">
            <aside epub:type="footnote">
            <div epub:type="footnote" class="extScrpCite" id="uuid">
            <p class="extScrpCiteTxt">
            <strong>(Salmo 10:1)</strong>
            Some fake content.
            </p>
            </div>
            </aside>
            </div>
            </div>""".formatted(TAG_A_PREFIX).trim();
        String actualHtml = sut.execute(testInfo.getDisplayName());
        assertEquals(expectedHtml, actualHtml);
    }


    @Test
    @DisplayName("Link Salmo 10:1 some text after")
    void linkSalmo10_1WithTextAfter(TestInfo testInfo) {
        setupMockUUIDGenerator();
        var sut = init();
        String expectedHtml = """
            Link %sSalmo 10:1</a> some text after
            <div class="groupExt">
            <div class="groupExtScrpCite">
            <aside epub:type="footnote">
            <div epub:type="footnote" class="extScrpCite" id="uuid">
            <p class="extScrpCiteTxt">
            <strong>(Salmo 10:1)</strong>
            Some fake content.
            </p>
            </div>
            </aside>
            </div>
            </div>""".formatted(TAG_A_PREFIX).trim();
        String actualHtml = sut.execute(testInfo.getDisplayName());
        assertEquals(expectedHtml, actualHtml);
    }

    @Test
    @DisplayName("Link Salmo 10:1, 2")
    void linkSalmo10_1_2(TestInfo testInfo) {
        setupMockUUIDGenerator();
        var sut = init();
        String expectedHtml = """
            Link %sSalmo 10:1, 2</a>
            <div class="groupExt">
            <div class="groupExtScrpCite">
            <aside epub:type="footnote">
            <div epub:type="footnote" class="extScrpCite" id="uuid">
            <p class="extScrpCiteTxt">
            <strong>(Salmo 10:1, 2)</strong>
            Some fake content. Some fake content.
            </p>
            </div>
            </aside>
            </div>
            </div>""".formatted(TAG_A_PREFIX).trim();
        String actualHtml = sut.execute(testInfo.getDisplayName());
        assertEquals(expectedHtml, actualHtml);
    }

    @Test
    @DisplayName("Link Salmo 10:1-3")
    void linkSamo10_1_3(TestInfo testInfo) {
        setupMockUUIDGenerator();
        var sut = init();
        String expectedHtml = """
            Link %sSalmo 10:1-3</a>
            <div class="groupExt">
            <div class="groupExtScrpCite">
            <aside epub:type="footnote">
            <div epub:type="footnote" class="extScrpCite" id="uuid">
            <p class="extScrpCiteTxt">
            <strong>(Salmo 10:1-3)</strong>
            Some fake content.
            </p>
            </div>
            </aside>
            </div>
            </div>""".formatted(TAG_A_PREFIX).trim();
        String actualHtml = sut.execute(testInfo.getDisplayName());
        assertEquals(expectedHtml, actualHtml);
    }

    @Test
    @DisplayName("Link Salmo 10:1-3, 5-8, 10, 12")
    void Salmo10_1_3_5_8_10_12(TestInfo testInfo) {
        setupMockUUIDGenerator();
        var sut = init();
        String expectedHtml = """
            Link %sSalmo 10:1-3, 5-8, 10, 12</a>
            <div class="groupExt">
            <div class="groupExtScrpCite">
            <aside epub:type="footnote">
            <div epub:type="footnote" class="extScrpCite" id="uuid">
            <p class="extScrpCiteTxt">
            <strong>(Salmo 10:1-3, 5-8, 10, 12)</strong>
            Some fake content. Some fake content. Some fake content. Some fake content.
            </p>
            </div>
            </aside>
            </div>
            </div>""".formatted(TAG_A_PREFIX).trim();
        String actualHtml = sut.execute(testInfo.getDisplayName());
        assertEquals(expectedHtml, actualHtml);
    }

    @Test
    @DisplayName("Link Salmo 10:1; 11:1")
    void testSalmo10_1_11_1(TestInfo testInfo) {
        setupMockUUIDGenerator();
        var sut = init();
        String expectedHtml = """
            Link %sSalmo 10:1</a>;%<s Salmo 11:1</a>
            <div class="groupExt">
            <div class="groupExtScrpCite">
            <aside epub:type="footnote">
            <div epub:type="footnote" class="extScrpCite" id="uuid">
            <p class="extScrpCiteTxt">
            <strong>(Salmo 10:1)</strong>
            Some fake content.
            </p>
            </div>
            </aside>
            <aside epub:type="footnote">
            <div epub:type="footnote" class="extScrpCite" id="uuid">
            <p class="extScrpCiteTxt">
            <strong>(Salmo 11:1)</strong>
            Some fake content.
            </p>
            </div>
            </aside>
            </div>
            </div>""".formatted(TAG_A_PREFIX);
        String actualHtml = sut.execute(testInfo.getDisplayName());
        assertEquals(expectedHtml, actualHtml);
    }

    @Test
    @DisplayName("Link Salmo 10:1; 11:1 some text after")
    void testSalmo10_1_11_1SomeTextAfter(TestInfo testInfo) {
        setupMockUUIDGenerator();
        var sut = init();
        String expectedHtml = """
            Link %sSalmo 10:1</a>;%<s Salmo 11:1</a> some text after
            <div class="groupExt">
            <div class="groupExtScrpCite">
            <aside epub:type="footnote">
            <div epub:type="footnote" class="extScrpCite" id="uuid">
            <p class="extScrpCiteTxt">
            <strong>(Salmo 10:1)</strong>
            Some fake content.
            </p>
            </div>
            </aside>
            <aside epub:type="footnote">
            <div epub:type="footnote" class="extScrpCite" id="uuid">
            <p class="extScrpCiteTxt">
            <strong>(Salmo 11:1)</strong>
            Some fake content.
            </p>
            </div>
            </aside>
            </div>
            </div>""".formatted(TAG_A_PREFIX);
        String actualHtml = sut.execute(testInfo.getDisplayName());
        assertEquals(expectedHtml, actualHtml);
    }

    @Test
    @DisplayName("Link Salmo 10:1, 3-6, 10, 15-19; 11:10; 12:1-4, 6; 13:1-3")
    void test7l(TestInfo testInfo) {
        setupMockUUIDGenerator();
        var sut = init();
        String expectedHtml = """
                Link <a epub:type="noteref" href="#uuid">Salmo 10:1, 3-6, 10, 15-19</a>;<a epub:type="noteref" href="#uuid"> Salmo 11:10</a>;<a epub:type="noteref" href="#uuid"> Salmo 12:1-4, 6</a>;<a epub:type="noteref" href="#uuid"> Salmo 13:1-3</a>
                <div class="groupExt">
                <div class="groupExtScrpCite">
                <aside epub:type="footnote">
                <div epub:type="footnote" class="extScrpCite" id="uuid">
                <p class="extScrpCiteTxt">
                <strong>(Salmo 10:1, 3-6, 10, 15-19)</strong>
                Some fake content. Some fake content. Some fake content. Some fake content.
                </p>
                </div>
                </aside>
                <aside epub:type="footnote">
                <div epub:type="footnote" class="extScrpCite" id="uuid">
                <p class="extScrpCiteTxt">
                <strong>(Salmo 11:10)</strong>
                Some fake content.
                </p>
                </div>
                </aside>
                <aside epub:type="footnote">
                <div epub:type="footnote" class="extScrpCite" id="uuid">
                <p class="extScrpCiteTxt">
                <strong>(Salmo 12:1-4, 6)</strong>
                Some fake content. Some fake content.
                </p>
                </div>
                </aside>
                <aside epub:type="footnote">
                <div epub:type="footnote" class="extScrpCite" id="uuid">
                <p class="extScrpCiteTxt">
                <strong>(Salmo 13:1-3)</strong>
                Some fake content.
                </p>
                </div>
                </aside>
                </div>
                </div>""".trim();
        String actualHtml = sut.execute(testInfo.getDisplayName());
        assertEquals(expectedHtml, actualHtml);
    }


    @Test
    @DisplayName("Link Salmo 10:1, 3-6, 10, 15-19; 11:10; 12:1-4, 6; 13:1-3; Mateus 24:14; 25:1-3")
    void test9l(TestInfo testInfo) {
        setupMockUUIDGenerator();
        var sut = init();
        String expectedHtml = """
                Link <a epub:type="noteref" href="#uuid">Salmo 10:1, 3-6, 10, 15-19</a>;<a epub:type="noteref" href="#uuid"> Salmo 11:10</a>;<a epub:type="noteref" href="#uuid"> Salmo 12:1-4, 6</a>;<a epub:type="noteref" href="#uuid"> Salmo 13:1-3</a>; <a epub:type="noteref" href="#uuid"> Mateus 24:14</a>;<a epub:type="noteref" href="#uuid"> Mateus 25:1-3</a>
                <div class="groupExt">
                <div class="groupExtScrpCite">
                <aside epub:type="footnote">
                <div epub:type="footnote" class="extScrpCite" id="uuid">
                <p class="extScrpCiteTxt">
                <strong>(Salmo 10:1, 3-6, 10, 15-19)</strong>
                Some fake content. Some fake content. Some fake content. Some fake content.
                </p>
                </div>
                </aside>
                <aside epub:type="footnote">
                <div epub:type="footnote" class="extScrpCite" id="uuid">
                <p class="extScrpCiteTxt">
                <strong>(Salmo 11:10)</strong>
                Some fake content.
                </p>
                </div>
                </aside>
                <aside epub:type="footnote">
                <div epub:type="footnote" class="extScrpCite" id="uuid">
                <p class="extScrpCiteTxt">
                <strong>(Salmo 12:1-4, 6)</strong>
                Some fake content. Some fake content.
                </p>
                </div>
                </aside>
                <aside epub:type="footnote">
                <div epub:type="footnote" class="extScrpCite" id="uuid">
                <p class="extScrpCiteTxt">
                <strong>(Salmo 13:1-3)</strong>
                Some fake content.
                </p>
                </div>
                </aside>
                <aside epub:type="footnote">
                <div epub:type="footnote" class="extScrpCite" id="uuid">
                <p class="extScrpCiteTxt">
                <strong>(Mateus 24:14)</strong>
                Some fake content.
                </p>
                </div>
                </aside>
                <aside epub:type="footnote">
                <div epub:type="footnote" class="extScrpCite" id="uuid">
                <p class="extScrpCiteTxt">
                <strong>(Mateus 25:1-3)</strong>
                Some fake content.
                </p>
                </div>
                </aside>
                </div>
                </div>""".trim();
        String actualHtml = sut.execute(testInfo.getDisplayName());
        assertEquals(expectedHtml, actualHtml);
    }


}
