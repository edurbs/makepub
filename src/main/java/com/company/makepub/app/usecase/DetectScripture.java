package com.company.makepub.app.usecase;

import com.company.makepub.app.domain.Book;
import com.company.makepub.app.domain.ScriptureAddress;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DetectScripture {

    public List<ScriptureAddress> execute(String html) {
        List<ScriptureAddress> result = new ArrayList<>();
        String regex = "(%s)\\s+(\\d+)|(%s)\\s+(\\d+)|(%s)\\s+(\\d+)";
        for(Book book : Book.values()) {
            String regexFormatted = regex.formatted(book.getFullName(), book.getAbbreviation1(), book.getAbbreviation2());
            searchForName(html, regexFormatted, book, result);
        }
        return result;
    }

    private void searchForName(String html, String regex, Book book, List<ScriptureAddress> result) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(html);
        while (matcher.find()) {
            String found = matcher.group().trim();
            String[] foundSplitted = found.split(" ");
            int chapter = Integer.parseInt(foundSplitted[foundSplitted.length-1]);
            result.add(new ScriptureAddress(book, chapter, 0));
        }
    }
}
