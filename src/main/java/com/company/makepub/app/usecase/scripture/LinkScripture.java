package com.company.makepub.app.usecase.scripture;

import com.company.makepub.app.gateway.UUIDGenerator;

import java.util.regex.Matcher;

public class LinkScripture {

    private final Matcher matcher;
    private final UUIDGenerator uuidGenerator;
    private final StringBuilder linkedHtml = new StringBuilder();

    public LinkScripture(Matcher matcher, UUIDGenerator uuidGenerator) {
        this.matcher = matcher;
        this.uuidGenerator = uuidGenerator;
    }   
    
    public String execute() {
        String lastBookName="";
        while (matcher.find()) {
            var extractor = new ScriptureAddressExtractor(matcher, lastBookName);
            String addressPrefix = extractor.getAddressPrefix();
            String allVerses = extractor.getAllVerses();
            linkVerse(addressPrefix + allVerses, matcher );
        }
        return linkedHtml.toString();
    }

    private void linkVerse(String textAddress, Matcher verseMatcher) {
        String optionsForTagA = "epub:type=\"noteref\"";
        String uuid = uuidGenerator.generate();
        String linkedVerse = """
                 <a %s href="#%s">%s</a>""".formatted(optionsForTagA, uuid, textAddress);
        verseMatcher.appendReplacement(linkedHtml, linkedVerse);
    }

}
