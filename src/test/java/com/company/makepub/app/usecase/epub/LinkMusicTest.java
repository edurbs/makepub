package com.company.makepub.app.usecase.epub;

import com.company.makepub.app.gateway.HtmlParser;
import com.company.makepub.app.gateway.UUIDGenerator;
import com.company.makepub.app.usecase.exceptions.UseCaseException;
import com.company.makepub.app.usecase.types.EpubMap;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class LinkMusicTest {
    public static final String CORRECT_TEXT_TO_TEST = """
            <body lang="PT-BR" style='tab-interval:35.4pt;word-wrap:break-word'>
            <h4 style="text-align: center;">ROMNHORÉ 16</h4>
            <h4>DANHOꞌRE 58 Wei witsi Höimanaꞌuꞌö Nhimirobꞌrudzé hã!</h4>
            <h1 style="text-align: center;">Rowẽ tsina dama roꞌmahörö dzaꞌra na</h1>
            <p align="center"><i>‘Uburé Danhibꞌapito ãma, ato dzaꞌra waꞌaba tsina ãma aiwata dzaꞌra waꞌaba.’</i> — <a epub:type="noteref" href="Section0002.xhtml#Salmo 100:2">SAL. 100:2</a></p>
            """;


    @Mock
    private HtmlParser mockParser;
    @Mock
    private UUIDGenerator mockUuidGenTest;

    private final String musicTitle = "DANHOꞌRE";


    @Test
    @DisplayName("Should link music")
    void shouldLinkMusic() {
        Mockito.when(mockUuidGenTest.generate()).thenReturn("123");
        Mockito.when(mockParser.parse(ArgumentMatchers.any(), ArgumentMatchers.any())).thenReturn("fake music");

        var linkMusic = new LinkMusic(mockParser, mockUuidGenTest);

        String expected = """
                <body lang="PT-BR" style='tab-interval:35.4pt;word-wrap:break-word'>
                <h4 style="text-align: center;">ROMNHORÉ 16</h4>
                <a href="Section0002.xhtml#123"><h4>DANHOꞌRE 58 Wei witsi Höimanaꞌuꞌö Nhimirobꞌrudzé hã!</h4></a>
                <h1 style="text-align: center;">Rowẽ tsina dama roꞌmahörö dzaꞌra na</h1>
                <p align="center"><i>‘Uburé Danhibꞌapito ãma, ato dzaꞌra waꞌaba tsina ãma aiwata dzaꞌra waꞌaba.’</i> — <a epub:type="noteref" href="Section0002.xhtml#Salmo 100:2">SAL. 100:2</a></p>
                """;
        String actual = linkMusic.execute(CORRECT_TEXT_TO_TEST).get(EpubMap.TEXT);
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Should throw exception if not found music number")
    void shouldThrowExceptionWithoutMusicNumber() {

        var linkMusic = new LinkMusic(mockParser, mockUuidGenTest);
        String textGivenWithoutMusicNumber = """
            <body lang="PT-BR" style='tab-interval:35.4pt;word-wrap:break-word'>
            <h4 style="text-align: center;">ROMNHORÉ 16</h4>
            <h4>DANHOꞌRE         Wei witsi Höimanaꞌuꞌö Nhimirobꞌrudzé hã!</h4>
            <h1 style="text-align: center;">Rowẽ tsina dama roꞌmahörö dzaꞌra na</h1>
            <p align="center"><i>‘Uburé Danhibꞌapito ãma, ato dzaꞌra waꞌaba tsina ãma aiwata dzaꞌra waꞌaba.’</i> — <a epub:type="noteref" href="Section0002.xhtml#Salmo 100:2">SAL. 100:2</a></p>
            """;

        assertThrows(UseCaseException.class, () -> linkMusic.execute(textGivenWithoutMusicNumber));
    }

    @Test
    @DisplayName("Should get the right music number")
    void musicNumberTest(){
        Mockito.when(mockParser.parse(ArgumentMatchers.any(), ArgumentMatchers.any())).thenReturn("DANHOꞌRE 58 fake music");
        var linkMusic = new LinkMusic(mockParser, mockUuidGenTest);

        String expected = "58";
        String linkedText = linkMusic.execute(CORRECT_TEXT_TO_TEST).get(EpubMap.TEXT);
        String regex = "DANHOꞌRE\\s\\d{1,3}\\s";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(linkedText);
        String actualMusicNumber = "";
        if(matcher.find()) {
            actualMusicNumber = matcher.group();
            actualMusicNumber = actualMusicNumber.replaceAll("\\D+","");
        }
        assertEquals(expected, actualMusicNumber);
    }

    @Test
    @DisplayName("Should get the music text from the mock site")
    void shouldGetMusicText(){
        String fakeMusic = "music text comes here";
        Mockito.when(mockParser.parse(ArgumentMatchers.any(), ArgumentMatchers.any()))
                .thenReturn(fakeMusic);
        var linkMusic = new LinkMusic(mockParser, mockUuidGenTest);
        String musicText = linkMusic.execute(CORRECT_TEXT_TO_TEST).get(EpubMap.MUSIC).trim();
        String expected = """
                <?xml version="1.0" encoding="utf-8"?>
                <!DOCTYPE html>
                <html xmlns="http://www.w3.org/1999/xhtml" xmlns:epub="http://www.idpf.org/2007/ops">
                <head><title></title></head>
                <body>
                <aside id="null" epub:type="footnote"><h4>%s</h4>
                </aside><br/><hr></hr><br/></body></html>
                """.formatted(fakeMusic);
        assertEquals(expected.trim(), musicText);

    }

}