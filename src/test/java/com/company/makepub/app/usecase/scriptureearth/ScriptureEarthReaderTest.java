package com.company.makepub.app.usecase.scriptureearth;

import com.company.makepub.app.domain.Book;
import com.company.makepub.app.domain.JsonBookRecord;
import com.company.makepub.app.domain.ScriptureAddress;
import com.company.makepub.app.usecase.types.BibleReader;
import com.company.makepub.utils.htmlparser.JsoupHtmlParser;
import com.company.makepub.utils.jsonparser.GsonParser;
import com.company.makepub.utils.linkreader.JavaUrlReader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ScriptureEarthReaderTest {

    private BibleReader sut;

    @BeforeEach
    void setUp() {
        var gson = new GsonParser<JsonBookRecord>();
        var urlReader = new JavaUrlReader();
        var readJson = new ReadJsonBooks(gson, urlReader);
        var htmlParser = new JsoupHtmlParser(urlReader);
        sut = new ScriptureEarthReader(
                htmlParser,
                readJson
        );
    }

    @Test
    @DisplayName("Should get Lucas 10:9")
    void getScripture() {
        String result = sut.getScripture("Lucas", 10, 9);
        String expected = """
                9 Tame 'ri wa'õtõ 'remhã, ma'ãpé ihâzé ré norĩ hã pese za'ra wa'aba mono. Duré dama rowasu'u za'ra wa'aba mono, ãne: “'Re ihâimana u'âsi mono da'ãma 're isib'a'uwẽ mono zé hã ma we atẽme tiwisi za'ra wa'wa.”
                """.trim();
        assertEquals(expected, result);
    }

    @Test
    @DisplayName("Should get 2 Samuel 18:32")
    void getScripture1() {
        String result = sut.getScripture("2 Samuel", 18, 32);
        String expected = """
                32 — E bété Absarõ, iprédupté hã. E rowẽ na te tihâiba. — Ãne te apito hã sadanha.
                Tawamhã romreme 'wapéi'wa, ma ãne ãma sada'â:
                — Wa hã ĩma iwẽ uptabi aré, õhõ zô marĩ iwatobro ne, uburé asitob'ru norĩ zô, duré uburé asada ihâimana za'ra norĩ zô zama, marĩ sô iwatobro za'ra da hã.
                """.trim();
        assertEquals(expected, result);
    }

    @Test
    @DisplayName("Should get Gênesis 2:4")
    void getScriptureGen2_4() {
        String result = sut.getScripture("Gênesis", 2, 4);
        String expected = """
                4 Ãne ma tô hâiwa duré uburé iwa 're isimasa mono da hã ti'ai me hã poto za'ra.
                — Uburé Danhib'apito 'Re ihâimana u'âsi mono, hâiwa duré uburé iwa 're isimasa mono da hã ti'ai me hã, te te poto za'ra wamhã,
                """.trim();
        assertEquals(expected, result);
    }

    @Test
    @DisplayName("Should get Gênesis 2:25")
    void getScriptureGen2_25() {
        String result = sut.getScripture("Gênesis", 2, 25);
        String expected = """
                25 Tawamhã aibâ norĩ, timro me hã ai'uréiwi 're nem nherẽ, sima 're siséb zahuré mono õ di.
                """.trim();
        assertEquals(expected, result);
    }

    @Test
    @DisplayName("Should get Gênesis 2:25 com record")
    void getScriptureGen2_25WithRecord() {
        String result = sut.getScripture(new ScriptureAddress(Book.BOOK_01_GEN, 2, 25, 0));
        String expected = """
                25 Tawamhã aibâ norĩ, timro me hã ai'uréiwi 're nem nherẽ, sima 're siséb zahuré mono õ di.
                """.trim();
        assertEquals(expected, result);
    }

    @Test
    @DisplayName("Should get Gênesis 2:1")
    void getScriptureGen2_1() {
        String result = sut.getScripture("Gênesis", 2, 1);
        String expected = """
                Ãne 'Re ihâimana u'âsi mono, ma tô te te apoto 'rãsutu, hâiwa duré ti'a hã, duré uburé marĩ iwab zahuré si'uiwa na 're ihâimana za'ra mono da hã.
                """.trim();
        assertEquals(expected, result);
    }

    @Test
    @DisplayName("Should get Rute 4:18")
    void getScriptureRu4_18() {
        String result = sut.getScripture("Rute", 4, 18);
        String expected = """
                18-22 Davihi hi'rata norĩ hã Perizihi hawimhã ãne isisi za'ra hã: Periz hã Hesirõhõ mama, Hezirõ hã Rãhã mama, Rã hã Aminadabihi mama, Aminadabi hã Nasõhõ mama, Nasõ hã Samohõ mama, Samo hã Boazihi mama, Boaz hã Obedihi mama, Obedi hã Zéséhé mama, duré Zésé hã Davihi mama.
                """.trim();
        assertEquals(expected, result);
    }

    @Test
    @DisplayName("Should get Romanos 2:24")
    void getScriptureRom2_24() {
        String result = sut.getScripture("Romanos", 2, 24);
        String expected = """
                24 Ãne 'Re ihâimana u'âsi mono mreme na Ibaihâ iwazé pese na rob'ui'éré hã:
                “A norĩ wa'wa hã Zudeu na 're aihâimana za'ra wa'aba mono, 'Re ihâimana u'âsi mono nhimiroti zarina si 're aihâimana za'ra wa'aba mono õ wa, te zudeu'õ norĩ hã 'Re ihâimana u'âsi mono ãma te te 're wasété za'ra.”
                """.trim();
        assertEquals(expected, result);
    }

    @Test
    @DisplayName("Should get Romanos 2:29")
    void getScriptureRom2_29() {
        String result = sut.getScripture("Romanos", 2, 29);
        String expected = """
                """.trim();
        assertEquals(expected, result);
    }

    @Test
    @DisplayName("Should get Salmo 119:137")
    void getScriptureSal119_137() {
        String result = sut.getScripture("Salmos", 119, 137);
        String expected = """
                """.trim();
        assertEquals(expected, result);
    }

    @Test
    @DisplayName("Should get Salmo 119:10-12 com record")
    void getScriptureSal119_10_12_withRecord() {
        String result = sut.getScripture(new ScriptureAddress(Book.BOOK_19_PSA, 119, 10, 12));
        String expected = """
                10 Wa hã wa ĩpẽ'ẽ na niha na ai'ãma si 're ĩmorĩ da hã ĩsima te 're rosa'rata. Ma'ãpé tahawa, ĩwi sawi na, aimreme te rẽme tõ da.
                11 Aimreme hã wa tô ĩsima te 're tété ĩpẽ'ẽ 'remhã, ĩwasédé te aiwi 're 'manharĩ tõ mono da.
                12 Uburé Danhib'apito, wa ai'ãma 're ĩwata!
                Asimiroti na, ma'ãpé ãma ĩma rowahutu.""".trim();
        assertEquals(expected, result);
    }


}