package com.company.makepub.app.usecase;

import com.company.makepub.app.domain.EpubFile;
import com.company.makepub.app.gateway.HtmlParser;
import com.company.makepub.app.gateway.UUIDGenerator;
import com.company.makepub.app.usecase.types.EpubMap;
import com.company.makepub.app.usecase.types.LinkReferencePage;
import com.company.makepub.app.usecase.types.StringConversor;
import com.company.makepub.app.usecase.types.UseCase;

import java.util.HashMap;
import java.util.Map;

public class EpubCreator implements UseCase {

    private final UUIDGenerator uuidGenerator;
    private final HtmlParser htmlParser;
    private final StringConversor markupConversor;
    private final LinkReferencePage linkMusic;
    private final LinkReferencePage linkScriptures;
    private final String mainText;
    private final Map<EpubMap, String> epubMap = new HashMap<>();

    public EpubCreator(UUIDGenerator uuidGenerator, HtmlParser htmlParser, StringConversor markupConversor, LinkReferencePage linkMusic, LinkReferencePage linkScriptures, String mainText) {
        this.uuidGenerator = uuidGenerator;
        this.htmlParser = htmlParser;
        this.markupConversor = markupConversor;
        this.linkMusic = linkMusic;
        this.linkScriptures = linkScriptures;
        this.mainText = mainText;
    }

    @Override
    public void execute(){
        String convertedText = markupConversor.convert(mainText);
        Map<EpubMap, String> musicMap = linkMusic.execute(convertedText);
        convertedText = musicMap.get(EpubMap.TEXT);
        Map<EpubMap, String> scripturesMap = linkScriptures.execute(convertedText);
        epubMap.put(EpubMap.TEXT, scripturesMap.get(EpubMap.TEXT));
        epubMap.put(EpubMap.MUSIC, musicMap.get(EpubMap.MUSIC));
        epubMap.put(EpubMap.SCRIPTURES, scripturesMap.get(EpubMap.SCRIPTURES));

    }

    public EpubFile getEpubFile() {
        // TODO make zipfile and rename to epub
        // TODO add cover, nav, etc.
        String filename = "";
        byte[] content = new byte[0];
        return new EpubFile(filename, content);
    }
}
