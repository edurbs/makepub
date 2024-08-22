package com.company.makepub.utils.htmlparser;

import com.company.makepub.app.gateway.HtmlParser;
import com.company.makepub.app.gateway.UrlReader;
import com.company.makepub.app.usecase.exceptions.UseCaseException;
import jakarta.validation.constraints.NotNull;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class JsoupHtmlParser implements HtmlParser {

    private UrlReader urlReader;

    public JsoupHtmlParser(UrlReader urlReader) {
        this.urlReader = urlReader;
    }

    @Override
    public String parse(@NotNull String site, @NotNull String tag) throws UseCaseException {
        Document doc = getDocument(site);
        Element tagElement = doc.getElementById(tag);
        if(tagElement == null) {
            return "";
        }
        return getTagContent(tagElement.wholeText());
    }

    @Override
    public String query(String site, String query){
        Document doc = getDocument(site);
        Elements elements = doc.select(query);
        if(elements.isEmpty()) {
            return "";
        }
        StringBuilder result = new StringBuilder();
        for(Element element : elements) {
            result.append(element.text());
            if(!Objects.equals(elements.last(), element)) {
                result.append("<br>\n");
            }
        }
        String resultText = result.toString();
        resultText = resultText.replace(" ", " ");
        resultText = resultText.replace("\n\n\n", "\n");
        resultText = resultText.replace("\n\n", "\n");
        resultText = resultText.replace("\n\n", "\n");
        return resultText.trim();
    }



    private  String getTagContent(String tagContent) {
        if(tagContent.isBlank()) {
            return "";
        }
        String surroundInTag = "p";
        String startSurround = "<" + surroundInTag + ">";
        String endSurround = "</" + surroundInTag + ">";
        StringBuilder sb = new StringBuilder();
        String[] lines = tagContent.split("\n");
        for(String line : lines) {
            if(!line.isBlank()){
                sb.append(startSurround);
                sb.append(line.trim());
                sb.append(endSurround);
                sb.append("\n");
            }
        }
        return sb.toString();
    }

    private @NotNull Document getDocument(String site) {
        try {
            return Jsoup.connect(site).get();
        } catch (IOException e) {
            throw new UseCaseException("Erro ao conectar ao site " + site, e);
        }
    }

    @Override
    public String getTextBetweenTagId(String site, String tagIdStart, String tagIdEnd, List<String> tagsToRemove) {
        String scriptureText = "";
        String siteText = urlReader.execute(site);
        int startIndex = siteText.indexOf(tagIdStart);
        int endIndex = siteText.indexOf(tagIdEnd, startIndex);
        if(startIndex != -1 && endIndex != -1) {
            scriptureText = siteText.substring(startIndex+tagIdStart.length(), endIndex);
        }
        if(scriptureText.isBlank()) {
            return "";
        }
        return removeHtmlTags(scriptureText, tagsToRemove);
    }

    private String removeHtmlTags(String scriptureText, List<String> tagsToRemove) {
        Document document = Jsoup.parse(scriptureText);
        String cssQuery = String.join(", ", tagsToRemove);
        document.select(cssQuery).remove();
        return document.wholeText()
                .replace(" ", " ")
                .replace("\n\n\n", "\n")
                .replace("\n\n", "\n")
                .replace("\n\n", "\n")
                .trim();
    }
}
