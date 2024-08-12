package com.company.makepub.app.usecase.epub;

import com.company.makepub.app.gateway.HtmlParser;
import com.company.makepub.app.gateway.UUIDGenerator;
import com.company.makepub.app.usecase.types.EpubMap;
import com.company.makepub.app.usecase.types.LinkReferencePage;

import java.util.HashMap;
import java.util.Map;

public class LinkScriptures implements LinkReferencePage {

    private final HtmlParser htmlParser;
    private final UUIDGenerator uuidGenerator;

    public LinkScriptures(HtmlParser htmlParser, UUIDGenerator uuidGenerator) {
        this.uuidGenerator = uuidGenerator;
        this.htmlParser = htmlParser;
    }

    @Override
    public Map<EpubMap, String> execute(final String text) {
        String scriptures = getScriptures(text);
        String linkedText = linkScriptures(text, scriptures);
        Map<EpubMap, String> resultMap = new HashMap<>();
        resultMap.put(EpubMap.TEXT, linkedText);
        resultMap.put(EpubMap.SCRIPTURES, scriptures);
        return resultMap;
    }

    private String linkScriptures(final String text, final String scriptures) {
        return text;
    }

    private String getScriptures(final String text) {
        return "";
    }


}
