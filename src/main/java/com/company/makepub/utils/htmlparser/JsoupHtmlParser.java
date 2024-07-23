package com.company.makepub.utils.htmlparser;

import com.company.makepub.app.gateway.HtmlParser;
import com.company.makepub.app.usecase.exceptions.UseCaseException;
import jakarta.validation.constraints.NotNull;
import org.jsoup.Jsoup;
import org.jsoup.nodes.*;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.List;

public class JsoupHtmlParser implements HtmlParser {

    @Override
    public String parse(@NotNull String site, @NotNull String tag) throws UseCaseException {
        Document doc = getDocument(site);
        Element tagElement = doc.getElementById(tag);
        if(tagElement == null) {
            return "";
        }
        return getTagContent(tagElement.wholeText());

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

    private static @NotNull Document getDocument(String site) {
        Document doc;
        try {
            doc = Jsoup.connect(site).get();
        } catch (IOException e) {
            throw new UseCaseException("Erro ao conectar ao site " + site, e);
        }
        return doc;
    }

    // TODO test
    @Override
    public String getTextBetweenTagId(String site, String tagIdStart, String tagIdEnd) {
        Document doc = getDocument(site);
        StringBuilder extractedText = new StringBuilder();
        Element content = doc.getElementById("content");
        if(content == null) {
            return "";
        }
        List<Node> allNodes = content.childNodes();
        boolean isTextExtractionActive = false;
        for (Node node : allNodes) {
            if (node instanceof Element) {
                isTextExtractionActive =analyzeNode(tagIdStart, tagIdEnd, node, isTextExtractionActive, extractedText);
                if(node.childNodeSize()>0){
                    List<Node> childNodes = node.childNodes();
                    for (Node childNode : childNodes) {
                         isTextExtractionActive = analyzeNode(tagIdStart, tagIdEnd, childNode, isTextExtractionActive, extractedText);
                    }
                }
            }
        }
        return extractedText.toString().replaceAll("\\s+", " ").trim();
    }

    private boolean analyzeNode(String tagIdStart, String tagIdEnd, Node node, boolean isTextExtractionActive, StringBuilder extractedText) {
        isTextExtractionActive = checkIsTextExtractionActive(node, tagIdStart, tagIdEnd, isTextExtractionActive);
        String textFromNode = readNode(node, isTextExtractionActive);
        extractedText.append(textFromNode);
       return isTextExtractionActive;
    }

    private String readNode(Node node, boolean isTextExtractionActive) {
        return switch (node) {
            case Element element when isTextExtractionActive && isScope(element) -> element.outerHtml();
            case TextNode textNode when isTextExtractionActive -> textNode.outerHtml();
            case null, default -> " ";
        };
    }

    private boolean isScope(Element element){
        return !element.tagName().equals("a");
    }

    private boolean checkIsTextExtractionActive(Node node, String tagIdStart, String tagIdEnd, boolean isTextExtractionActive) {
        return switch (node) {
          case Element element ->  {
              String nodeId = element.id();
              if(nodeId.equals(tagIdStart)) {
                  yield true;
              } else if(nodeId.equals(tagIdEnd)) {
                  yield false;
              }
              yield isTextExtractionActive;
          }
          case null, default -> isTextExtractionActive;
        };
    }




}
