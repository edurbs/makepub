package com.company.makepub.app.usecase;

import com.company.makepub.app.domain.EpubFile;
import com.company.makepub.app.gateway.HtmlParser;
import com.company.makepub.app.gateway.UUIDGenerator;
import com.company.makepub.app.usecase.types.EpubMap;
import com.company.makepub.app.usecase.types.LinkReferencePage;
import com.company.makepub.app.usecase.types.StringConversor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class EpubCreatorTest {

    @Mock
    private UUIDGenerator mockUuidGenerator;

    @Mock
    private HtmlParser mockHtmlParser;

    @Mock
    private StringConversor mockMarkupConversor;

    @Mock
    private LinkReferencePage mockLinkMusic;

    @Mock
    private LinkReferencePage mockLinkScriptures;

    private EpubCreator epubCreator;

    @Test
    @DisplayName("Should create epub file")
    void getEpubFile() {
        String fakeMainText = "some main text";
        byte[] fakeCoverImage = new byte[10];
        this.epubCreator = new EpubCreator (mockUuidGenerator, mockHtmlParser, mockMarkupConversor, mockLinkMusic, mockLinkScriptures, fakeMainText, fakeCoverImage);

        Mockito.when(mockMarkupConversor.convert(ArgumentMatchers.any())).thenReturn("some text");
        Mockito.when(mockLinkMusic.execute(ArgumentMatchers.any())).thenReturn(Map.of(EpubMap.MUSIC, "some text", EpubMap.TEXT, "some text"));
        Mockito.when(mockLinkScriptures.execute(ArgumentMatchers.any())).thenReturn(Map.of(EpubMap.SCRIPTURES, "some text", EpubMap.TEXT, "some text"));

        EpubFile actual = epubCreator.execute();

        assertDoesNotThrow(() -> epubCreator.execute());
        assertEquals("file.epub", actual.filename());
        assertNotNull(actual.content());
    }
}