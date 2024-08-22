package com.company.makepub.app.usecase.scripture;

import com.company.makepub.app.domain.ScriptureAddress;
import com.company.makepub.app.gateway.UUIDGenerator;
import com.company.makepub.app.usecase.types.BibleReader;

import java.util.List;
import java.util.regex.Matcher;

public class LinkScriptures {

    private final MakeRegex makeRegex;
    private final UUIDGenerator uuidGenerator;
    private final BibleReader scriptureEarthReader;
    private final BibleReader nwtpReader;
    private final BibleReader tnmReader;
    private final StringBuilder linkedHtml = new StringBuilder();

    public LinkScriptures(MakeRegex makeRegex, UUIDGenerator uuidGenerator, BibleReader scriptureEarthReader, BibleReader nwtpReader, BibleReader tnmReader) {
        this.makeRegex = makeRegex;
        this.uuidGenerator = uuidGenerator;
        this.scriptureEarthReader = scriptureEarthReader;
        this.nwtpReader = nwtpReader;
        this.tnmReader = tnmReader;
    }
    
    public String execute(String originalText) {
        Matcher matcher = makeRegex.getMatcher(originalText);
        String lastBookName="";
        StringBuilder generatedScriptureContents = new StringBuilder();
        boolean first = true;
        while (matcher.find()) {
            var extractor = new ScriptureAddressExtractor(matcher, lastBookName);
            String addressPrefix = extractor.getAddressPrefix();
            String allScriptures = extractor.getAllVerses();
            String uuid = uuidGenerator.generate();
            List<ScriptureAddress> scriptureAddresses = new DetectScripture(matcher, lastBookName).execute();
            String scriptureAddressText = addressPrefix + allScriptures;
            if(first) {
                first = false;
            }else{
                scriptureAddressText = " "+scriptureAddressText;
            }
            linkScripture(scriptureAddressText, matcher, uuid);
            generatedScriptureContents.append(generateScriptureContents(uuid, scriptureAddressText, scriptureAddresses));
            lastBookName = extractor.getBookName();
        }
        matcher.appendTail(linkedHtml);
        linkedHtml.append("\n<div class=\"groupExt\">\n<div class=\"groupExtScrpCite\">");
        linkedHtml.append(generatedScriptureContents);
        linkedHtml.append("\n</div>\n</div>");
        //linkedHtml.append("\n</body>\n</html>");
        return linkedHtml.toString();
    }

    private String getScriptureFromBible(ScriptureAddress address) {
        String scripture = scriptureEarthReader.getScripture(address);
        if(scripture.isBlank()) {
            scripture = nwtpReader.getScripture(address);
        }
        if(scripture.isBlank()) {
            scripture = tnmReader.getScripture(address);
        }
        return scripture;
    }

    private String generateScriptureContents(String uuid, String scriptureAddressText, List<ScriptureAddress> scriptureAddresses) {
        StringBuilder footnotes = new StringBuilder();
        StringBuilder contents = new StringBuilder();
        for(ScriptureAddress scriptureAddress : scriptureAddresses) {
            contents.append(getScriptureFromBible(scriptureAddress));
            contents.append(" ");
        }
        scriptureAddressText = scriptureAddressText.trim();
        footnotes.append("""
                <p class="extScrpCiteTxt">
                <strong>(%s)</strong>
                %s
                </p>""".formatted(scriptureAddressText, contents.toString().trim())
        );
        return """
                \n<aside epub:type="footnote">
                <div epub:type="footnote" class="extScrpCite" id="%s">
                %s
                </div>
                </aside>""".formatted(uuid, footnotes);
    }

    private void linkScripture(String scriptureAddress, Matcher matcher, final String uuid) {
        String optionsForTagA = "epub:type=\"noteref\"";
        String linkedScripture = """
                 <a %s href="#%s">%s</a>""".formatted(optionsForTagA, uuid, scriptureAddress);
        matcher.appendReplacement(linkedHtml, linkedScripture);

    }
}
