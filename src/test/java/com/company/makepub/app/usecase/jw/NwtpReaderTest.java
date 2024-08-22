package com.company.makepub.app.usecase.jw;

import com.company.makepub.app.domain.Book;
import com.company.makepub.app.domain.ScriptureAddress;
import com.company.makepub.app.gateway.HtmlParser;
import com.company.makepub.app.usecase.scripture.ConvertScripture;
import com.company.makepub.utils.linkreader.JavaUrlReader;
import com.company.makepub.utils.requestapi.OkHttpRequestApi;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
class NwtpReaderTest {

    @Test
    @DisplayName("Should get the text from the mocked nwtp site")
    void getScripture() {
        HtmlParser htmlParser = Mockito.mock(HtmlParser.class);
        ConvertScripture convertScripture = Mockito.mock(ConvertScripture.class);
        String site = "https://wol.jw.org/xav/wol/b/r511/lp-xv/nwtp/58/2";
        String query = "span[id^=v58-2-6]";
        Mockito.when(htmlParser.query(site, query)).thenReturn("some text");
        NwtpReader nwtpReader = new NwtpReader(htmlParser);
        ScriptureAddress scriptureAddress = new ScriptureAddress(Book.BOOK_58_HEB, 2, 6, 0);
        String result = nwtpReader.getScripture(scriptureAddress);
        assertEquals("some text", result);
    }

}