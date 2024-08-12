package com.company.makepub.app.usecase.scripture;

import com.company.makepub.app.domain.Book;
import com.company.makepub.app.domain.ScriptureAddress;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DetectScripture {

    private String html;

    public DetectScripture(String html) {
        this.html = html;
    }

    public String getHtml() {
        return html;
    }

    public List<ScriptureAddress> execute() {
        List<ScriptureAddress> result = new ArrayList<>();
        String regex = "(%s)\\s+(\\d+)|(%s)\\s+(\\d+)|(%s)\\s+(\\d+)";
        for(Book book : Book.values()) {
            String regexFormatted = regex.formatted(book.getFullName(), book.getAbbreviation1(), book.getAbbreviation2());
            searchForName(regexFormatted, book, result);
        }
        return result;
    }

    private void searchForName(String regex, Book book, List<ScriptureAddress> result) {

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(html);
        while (matcher.find()) {
            String found = matcher.group().trim();
            String[] foundSplitted = found.split(" ");
            int chapter = Integer.parseInt(foundSplitted[foundSplitted.length-1]);
            int indexEndChapter = matcher.end();
            ScriptureAddress tempScriptureAddress = new ScriptureAddress(book, chapter, 0,0);
            findVerse(indexEndChapter, result, tempScriptureAddress);
        }
    }

    private void findVerse(int indexEndChapter, List<ScriptureAddress> result, ScriptureAddress tempScriptureAddress) {
        StringBuilder sb = new StringBuilder();
        String verseNumber = "";
        if(html.charAt(indexEndChapter) == ':') {
            String verseRegex = "(\\d+-\\d+)|(\\d+,*)";
            Pattern versePattern = Pattern.compile(verseRegex);
            Matcher verseMatcher = versePattern.matcher(html);
            verseMatcher.region(indexEndChapter, html.length());
            if(verseMatcher.find()) {
                verseNumber = verseMatcher.group();
                if(verseNumber.contains(",")) {
                    verseNumber = verseNumber.replaceAll("\\D+", "");
                    int verse = Integer.parseInt(verseNumber);
                    linkVerse(tempScriptureAddress, verse, verseMatcher, sb, "");
                    result.add(new ScriptureAddress(tempScriptureAddress.book(), tempScriptureAddress.chapter(), verse, 0));
                    while(verseMatcher.find()) {
                        verseNumber = verseMatcher.group();
                        verseNumber = verseNumber.replaceAll("\\D+", "");
                        verse = Integer.parseInt(verseNumber);
                        linkVerse(tempScriptureAddress, verse, verseMatcher, sb, "");
                        result.add(new ScriptureAddress(tempScriptureAddress.book(), tempScriptureAddress.chapter(), verse,0));
                    }
                } else if(verseNumber.contains("-")) {
                    String[] verseNumberSplitted = verseNumber.split("-");
                    verseNumber = verseNumberSplitted[0];
                    verseNumber = verseNumber.replaceAll("\\D+", "");
                    int verse = Integer.parseInt(verseNumber);
                    String endVerseNumber = verseNumberSplitted[1];
                    endVerseNumber = endVerseNumber.replaceAll("\\D+", "");
                    int endVerse = Integer.parseInt(endVerseNumber);
                    linkVerse(tempScriptureAddress, verse, verseMatcher, sb, "");
                    result.add(new ScriptureAddress(tempScriptureAddress.book(), tempScriptureAddress.chapter(), verse, endVerse));

                }else {
                    int verse = Integer.parseInt(verseNumber);
                    linkVerse(tempScriptureAddress, verse, verseMatcher, sb, "");
                    result.add(new ScriptureAddress(tempScriptureAddress.book(), tempScriptureAddress.chapter(), verse, 0));
                }
            }
        }
        html = sb.toString();
    }

    private void linkVerse(ScriptureAddress tempScriptureAddress, int verse, Matcher verseMatcher, StringBuilder sb, String postfix) {
        String linkedVerse = """
                <a href="#%s %d:%d">%d</a>%s
                """.formatted(tempScriptureAddress.book().getFullName(), tempScriptureAddress.chapter(), verse, verse, postfix).trim();
        verseMatcher.appendReplacement(sb, linkedVerse);
        verseMatcher.appendTail(sb);
    }
}
