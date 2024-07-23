package com.company.makepub.app.usecase;

import com.company.makepub.app.domain.BookAddress;
import com.company.makepub.app.domain.BookName;
import com.company.makepub.app.domain.JsonBookRecord;
import com.company.makepub.app.domain.ScriptureEarthBookName;
import com.company.makepub.app.gateway.JsonParser;
import com.company.makepub.app.gateway.UrlReader;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.awt.print.Book;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ReadJsonBooksScriptureEarthTest {

    @Mock
    UrlReader urlReader;

    @Mock
    JsonParser<JsonBookRecord> jsonParser;

    @Test
    @DisplayName("Should read books from fake site")
    void execute() {
        String jsonFromSite = """
                var books = [
                  { name: "Lucas", ref: "xav-19-LUK-001.html" },
                  { name: "Leia o Novo Testamento", ref: "xav-44-XXD-001.html" },
                  { name: "Videos", ref: "xav-45-XXF-001.html" },
                ];
                """;
        String finalRef = "https://www.scriptureearth.org/data/xav/sab/xav/xav-19-LUK-001.html";
        BookName finalBook = BookName.BOOK_42_LUK;

        Mockito.when(urlReader.execute(ArgumentMatchers.any())).thenReturn(jsonFromSite);
        List<JsonBookRecord> jsonBookRecords = List.of(new JsonBookRecord("Lucas", "xav-19-LUK-001.html"));
        Mockito.when(jsonParser.parse(ArgumentMatchers.any(), ArgumentMatchers.any())).thenReturn(jsonBookRecords);

        ReadJsonBooksScriptureEarth sut = new ReadJsonBooksScriptureEarth(jsonParser, urlReader);
        List<BookAddress> bookAddresses = sut.execute();

        assertEquals(1, bookAddresses.size());
        assertEquals( bookAddresses.getFirst(), new BookAddress(finalBook, finalRef));
    }
}