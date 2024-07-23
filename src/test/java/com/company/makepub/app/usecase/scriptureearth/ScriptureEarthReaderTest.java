package com.company.makepub.app.usecase.scriptureearth;

import com.company.makepub.app.domain.JsonBookRecord;
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
                <span class="v">9</span><span class="vsp">&nbsp;</span>Tame 'ri wa'õtõ 'remhã, ma'ãpé ihâzé ré norĩ hã pese za'ra wa'aba mono. Duré dama rowasu'u za'ra wa'aba mono, ãne: “'Re ihâimana u'âsi mono da'ãma 're isib'a'uwẽ mono zé hã ma we atẽme tiwisi za'ra wa'wa.”
                """.trim();
        assertEquals(expected, result);
    }

    @Test
    @DisplayName("Should get 2 Samuel 18:32")
    void getScripture1() {
        String result = sut.getScripture("2 Samuel", 18, 32);
        String expected = """
                <div class="p"><span class="v">32</span><span class="vsp">&nbsp;</span>— E bété Absarõ, iprédupté hã. E rowẽ na te tihâiba. — Ãne te apito hã sadanha.</div>
                <div class="p">Tawamhã romreme 'wapéi'wa, ma ãne ãma sada'â:</div>
                <div class="p">— Wa hã ĩma iwẽ uptabi aré, õhõ zô marĩ iwatobro ne, uburé asitob'ru norĩ zô, duré uburé asada ihâimana za'ra norĩ zô zama, marĩ sô iwatobro za'ra da hã.
                """.trim();
        assertEquals(expected, result);
    }


}