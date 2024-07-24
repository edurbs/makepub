package com.company.makepub.app.usecase;

import com.company.makepub.app.domain.Book;
import com.company.makepub.app.domain.ScriptureAddress;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DetectScripture {

    private String book;
    private int chapter;
    private int startVerse;
    //private int endVerse;

    public List<ScriptureAddress> execute(String html) {
        List<ScriptureAddress> result = new ArrayList<>();
        String regex = "(%s)\\s+(\\d+)";
        for(Book book : Book.values()) {
            String bookName = book.getFullName();
            String fullNameRegex = String.format(regex, bookName);
            Pattern pattern = Pattern.compile(fullNameRegex);
            Matcher matcher = pattern.matcher(html);
            if(matcher==null) {
                continue;
            }
            while (matcher.find()) {
                this.book = matcher.group().replaceAll("\\d+", "").trim();
                this.chapter = Integer.parseInt(matcher.group().replaceAll("\\D+", ""));
                //this.startVerse = Integer.parseInt(matcherBook.group().replaceAll("\\D+", ""));
                result.add(new ScriptureAddress(this.book, this.chapter, this.startVerse));
            }
        }
        return result;
    }

    private Matcher detectBook(String html) {

        return null;
    }

    /*
    private int detectChaper(Matcher matcherBook, String html) {
        int chapter= 0;
        String collon = ";";
        int bookEnd = matcherBook.end();
        // read html until collon
        int endIndex = bookEnd - collon.length() + 1;
        if(endIndex> matcherBook.group().length()) {
            return 0;
        }
        String htmlUntilCollon = matcherBook.group().substring(0, endIndex);
        String regex = "(\\d+)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcherChapter = pattern.matcher(htmlUntilCollon);
        if(matcherChapter.find()) {
            this.chapter = Integer.parseInt(matcherChapter.group());
        }
        return matcherChapter.end();
    }

    private int detectStartVerse(int chapterEnd, String html) {
        String endVerse = ")";
        int indexEndVerse = html.indexOf(endVerse, chapterEnd);
        // get last number using regex
        String regex = "(\\d+)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(html.substring(chapterEnd, indexEndVerse));
        if(matcher.find()) {
            this.startVerse = Integer.parseInt(matcher.group(0));
        }
        //this.startVerse = Integer.parseInt(html.substring(chapterEnd, indexEndVerse));
        return indexEndVerse;
    }*/




}
