package com.company.makepub.utils.htmlparser;

import com.company.makepub.app.gateway.UrlReader;
import com.company.makepub.app.usecase.exceptions.UseCaseException;
import com.company.makepub.app.utils.htmlparser.JsoupHtmlParser;
import com.company.makepub.app.utils.linkreader.JavaUrlReader;
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

    @Test
    @DisplayName("Should get text from sbi1")
    void getSbi1Text() {
        String actual = sut.query("https://wol.jw.org/xav/wol/b/r511/lp-xv/sbi1/58/2/", "span[id^=v58-2-6]");
        String expected = """
                6 Tazahã duréihã niꞌwa hã ma tô ãne na ibaihâ na ãma robꞌuiꞌéré, ꞌRe ihâimana uꞌâsi mono ma, te te inharĩzém na hã:<br>
                “ꞌRe ihâimana uꞌâsi mono, e dahâimana pire na aima ꞌre dahâimana zaꞌra mono bâ, te daꞌãma ꞌre aipoꞌre puꞌu uꞌâsi mo. E niha da, te bété daꞌãma ꞌre aiwamri uꞌâsi mo, aꞌuwẽ na si ꞌre dahâimana zaꞌra mono nherẽ.""";
        assertEquals(expected, actual);
    }


    @Test
    @DisplayName("Should get text from nwtp")
    void getNwtpText() {
        String actual = sut.query("https://wol.jw.org/xav/wol/b/r511/lp-xv/nwtp/18/2/", "span[id^=v18-2-2]");
        String expected = """
                2 Tawamhã Jeová te tsadanha Satanás: “E ma hawi te we aimo”. Satanás te tsadaꞌö Jeová: “Tiꞌai baba ꞌre ĩwara mono dzém hawi, duré Tiꞌai babamhã ꞌre ĩ̱morĩdzém hawi.”""";
        assertEquals(expected, actual);
    }


}