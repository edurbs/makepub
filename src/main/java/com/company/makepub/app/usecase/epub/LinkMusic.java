package com.company.makepub.app.usecase.epub;

import com.company.makepub.app.gateway.HtmlParser;
import com.company.makepub.app.gateway.UUIDGenerator;
import com.company.makepub.app.usecase.exceptions.UseCaseException;
import com.company.makepub.app.usecase.types.EpubMap;
import com.company.makepub.app.usecase.types.LinkReferencePage;
import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@RequiredArgsConstructor
public class LinkMusic implements LinkReferencePage {

    private final HtmlParser htmlParser;
    private final UUIDGenerator uuidGenerator;

    private String musicPage = "";

    @Override
    @Nonnull
    public Map<EpubMap, String> execute(@Nonnull final String text) {
        StringBuilder linkedText = new StringBuilder();
        List<String> lines = List.of(text.split("\n"));
        for(String line : lines) {
            String musicTitle = "DANHOꞌRE";
            if(line.indexOf(musicTitle)>0){
                line = linkLine(line);
            }
            linkedText.append(line).append("\n");
        }
        Map<EpubMap, String> result = new HashMap<>();
        result.put(EpubMap.TEXT, linkedText.toString());
        result.put(EpubMap.MUSIC, finalizeMusicPage());
        return result;
    }

    @Nonnull
    private String finalizeMusicPage() {
        String start = """
                <?xml version="1.0" encoding="utf-8"?>
                <!DOCTYPE html>
                <html xmlns="http://www.w3.org/1999/xhtml" xmlns:epub="http://www.idpf.org/2007/ops">
                <head><title></title></head>
                <body>
                """;
        String end = "</body></html>";
        return start + musicPage + end;
    }

    @Nonnull
    private String linkLine(@Nonnull final String text) {
        String musicId = uuidGenerator.generate();
        String regex = "\\s\\d{1,3}\\s";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(text);
        String musicNumber;
        if(matcher.find()) {
            musicNumber = matcher.group().trim();
        }else{
            throw new UseCaseException("Número do cântico não foi encontrado!");
        }
        String musicText = getMusicText(musicNumber, musicId);
        musicPage += musicText;
        return "<a href=\"Section0002.xhtml#" + musicId + "\">" + text + "</a>";
    }

    @Nonnull
    private String getMusicText(@Nonnull String musicNumber, @Nonnull String musicId) {
        String start = "<aside id=\""+musicId+"\" epub:type=\"footnote\">";
        String end = "</aside><br/><hr/><br/>";
        StringBuilder sb = new StringBuilder();
        String musicPage = parseMusicPage(musicNumber);
        String[] lines = musicPage.split("\n");
        for(int i = 0; i < lines.length; i++) {
            switch (i) {
                case 0 -> sb.append("<h4>").append(lines[i]).append("</h4>");
                case 1 -> sb.append("<h3>").append(lines[i]).append("</h3>");
                default -> sb.append(lines[i]);
            }
            sb.append("\n");
        }
        String musicText = sb.toString();
        return start + musicText + end;
    }

    @Nonnull
    private String parseMusicPage(@Nonnull String musicNumber) {
        // https://wol.jw.org/xav/wol/d/r511/lp-xv/1102016814 14
        // https://wol.jw.org/xav/wol/d/r511/lp-xv/1102016860 60
        // https://wol.jw.org/xav/wol/d/r511/lp-xv/1102016951 151
        int linkNumber = 1102016800 + Integer.parseInt(musicNumber);
        String site = "https://wol.jw.org/xav/wol/d/r511/lp-xv/" + linkNumber;
        return htmlParser.parse(site, "article");
    }
}

