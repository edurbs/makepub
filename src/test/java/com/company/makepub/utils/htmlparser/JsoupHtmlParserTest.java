package com.company.makepub.utils.htmlparser;

import com.company.makepub.app.usecase.exceptions.UseCaseException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JsoupHtmlParserTest {

    @Test
    @DisplayName("Should parse the wol.jw.org page and return the tag content")
    void parse() {
        JsoupHtmlParser jsoupHtmlParser = new JsoupHtmlParser();
        String tagContent = jsoupHtmlParser.parse("https://wol.jw.org/xav/wol/d/r511/lp-xv/1102016802", "article");
        assertTrue(tagContent.contains("Jeová — Atsitsi ĩwẽ"));
    }

    @Test
    @DisplayName("Should throws exception when site is unreachable")
    void parseWithWrongSite() {
        JsoupHtmlParser jsoupHtmlParser = new JsoupHtmlParser();
        assertThrows(UseCaseException.class, () -> jsoupHtmlParser.parse("https://none.none", ""));
    }


}