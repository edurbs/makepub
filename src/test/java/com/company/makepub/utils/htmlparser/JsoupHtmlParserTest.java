package com.company.makepub.utils.htmlparser;

import com.company.makepub.app.gateway.UrlReader;
import com.company.makepub.app.usecase.exceptions.UseCaseException;
import com.company.makepub.utils.linkreader.JavaUrlReader;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JsoupHtmlParserTest {

    private final UrlReader javaUrlReader = new JavaUrlReader();
    private final JsoupHtmlParser sut = new JsoupHtmlParser(javaUrlReader);

    @Test
    @DisplayName("Should parse the wol.jw.org page and return the tag content")
    void parse() {
        String tagContent = sut.parse("https://wol.jw.org/xav/wol/d/r511/lp-xv/1102016802", "article");
        assertTrue(tagContent.contains("Jeová — Atsitsi ĩwẽ"));
    }

    @Test
    @DisplayName("Should throws exception when site is unreachable")
    void parseWithWrongSite() {
        assertThrows(UseCaseException.class, () -> sut.parse("https://none.none", ""));
    }

//    @Test
//    @DisplayName("Should read text between two tags")
//    void getContentBetweenTags() {
//        String url = "https://www.scriptureearth.org/data/xav/sab/xav/xav-19-LUK-001.html";
//        String expected = """
//                <span class="v">10</span><span class="vsp">&nbsp;</span>Wedewati isadaze te te zata ré, te rowi idasi'ubumro norĩ hã rowaptẽrẽ za'ra ni.<span id="bookmarks10"></span>
//                """.trim();
//        String tagContent = sut.getTextBetweenTagId(url, "v10", "v11", null);
//        assertEquals(expected, tagContent);
//    }


}