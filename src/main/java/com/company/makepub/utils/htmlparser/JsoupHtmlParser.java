package com.company.makepub.utils.htmlparser;

import com.company.makepub.app.gateway.HtmlParser;
import com.company.makepub.app.usecase.exceptions.UseCaseException;
import jakarta.validation.constraints.NotNull;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;

public class JsoupHtmlParser implements HtmlParser {

    @Override
    public String parse(@NotNull String site, @NotNull String tag) throws UseCaseException {
        String tagContent = "";
        Document doc;
        try {
            doc = Jsoup.connect(site).get();
        } catch (IOException e) {
            throw new UseCaseException("Erro ao conectar ao site " + site, e);
        }
        Element tagElement = doc.getElementById(tag);
        if(tagElement != null) {
            tagContent = tagElement.html();
        }
        return tagContent;
    }
}
