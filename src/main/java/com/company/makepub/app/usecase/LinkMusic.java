package com.company.makepub.app.usecase;

import com.company.makepub.app.gateway.HtmlParser;
import com.company.makepub.app.gateway.UUIDGenerator;
import com.company.makepub.app.usecase.exceptions.UseCaseException;
import com.company.makepub.app.usecase.types.LinkReferencePage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LinkMusic implements LinkReferencePage {

    private final HtmlParser htmlParser;
    private final UUIDGenerator uuidGenerator;
    private final String musicTitle;
    private String musicPage = "";

    public LinkMusic(HtmlParser htmlParser, UUIDGenerator uuidGenerator1, String musicTitle) {
        this.htmlParser = htmlParser;
        this.uuidGenerator = uuidGenerator1;
        this.musicTitle = musicTitle;
    }

    @Override
    public Map<String, String> execute(final String text) {
        StringBuilder linkedText = new StringBuilder();
        List<String> lines = List.of(text.split("\n"));
        for(String line : lines) {
            if(line.indexOf(musicTitle)>0){
                line = linkLine(line);
            }
            linkedText.append(line).append("\n");
        }
        Map<String, String> result = new HashMap<>();
        result.put("text", linkedText.toString());
        result.put("musics", finalizeMusicPage());
        return result;
    }

    private String finalizeMusicPage() {
        String start = """
                <?xml version="1.0" encoding="utf-8"?>
                <!DOCTYPE html>
                <html xmlns="http://www.w3.org/1999/xhtml" xmlns:epub="http://www.idpf.org/2007/ops">
                <body>
                """;
        String end = "</body></html>";
        return start + musicPage + end;
    }

    private String linkLine(final String text) {
        String musicId = uuidGenerator.generate();
        String regex = "\\s\\d{1,3}\\s";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(text);
        String musicNumber = "";
        if(matcher.find()) {
            musicNumber = matcher.group().trim();
        }else{
            throw new UseCaseException("Número do cântico não foi encontrado!");
        }
        String musicText = getMusicText(musicNumber, musicId);
        musicPage += musicText;
        return "<a href=\"Section0002.xhtml#" + musicId + "\">" + text + "</a>";
    }

    private String getMusicText(String musicNumber, String musicId) {
        String start = "<aside id=\""+musicId+"\" epub:type=\"footnote\">";
        String end = "</aside>";
        return start + parseMusicPage(musicNumber) + end;
    }

    private String parseMusicPage(String musicNumber) {
        // https://wol.jw.org/xav/wol/d/r511/lp-xv/1102016814 14
        // https://wol.jw.org/xav/wol/d/r511/lp-xv/1102016860 60
        // https://wol.jw.org/xav/wol/d/r511/lp-xv/1102016951 151
        int linkNumber = 1102016800 + Integer.parseInt(musicNumber);
        String site = "https://wol.jw.org/xav/wol/d/r511/lp-xv/" + linkNumber;
        return htmlParser.parse(site, "article");
    }
}

