package com.company.makepub.app.usecase.scripture;

import com.company.makepub.app.domain.Book;
import com.company.makepub.app.domain.ScriptureAddress;
import com.company.makepub.app.gateway.UUIDGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DetectScripture {

    private final UUIDGenerator uuidGenerator;
    private final String html;
    private final StringBuilder linkedHtml = new StringBuilder();
    private final StringBuilder scripturesHtml = new StringBuilder();
    private final List<ScriptureAddress> scriptureAddressList = new ArrayList<>();

    public DetectScripture(String html, UUIDGenerator uuidGenerator) {
        this.html = html;
        this.uuidGenerator = uuidGenerator;
    }

    public String getLinkedHtml() {
        return linkedHtml.toString();
    }

    public String getScripturesHtml() {
        return scripturesHtml.toString();
    }

    public List<ScriptureAddress> execute() {

        final Matcher matcher = new MakeRegex(html).execute();
        String lastBookName="";
        while (matcher.find()) {
            final String bookName;
            final String fullScriptureAddress;
            final String scriptureAddress;
            if(isAddressWithBookName(matcher)) {
                bookName = matcher.group(2).trim();
                lastBookName = bookName;
                fullScriptureAddress = matcher.group(0).trim();
                scriptureAddress = fullScriptureAddress.replace(bookName, "");
            }else{
                bookName = lastBookName;
                fullScriptureAddress = matcher.group(0).trim();
                scriptureAddress = fullScriptureAddress;
            }
            final int chapter = Integer.parseInt(scriptureAddress.split(":")[0].trim());
            String addressPrefix = bookName + " " + chapter + ":";
            if(bookName.isBlank()){
                addressPrefix = " " + chapter + ":";
            }
            final String allVerses = scriptureAddress.split(":")[1];
            detectScriptureAddress(scriptureAddressList, bookName, chapter, allVerses, matcher);
            linkVerse(addressPrefix + allVerses, matcher );
        }
        return scriptureAddressList;
    }

    private void detectScriptureAddress(List<ScriptureAddress> result, final String bookName, final int chapter, final String allVerses, Matcher matcher) {
        String[] versesBetweenCommas = allVerses.split(",");
        for (String verseString : versesBetweenCommas) {
            if(verseString.contains("-")) {
                String[] versesBetweenHyphens = verseString.split("-");
                final int startVerse = Integer.parseInt(versesBetweenHyphens[0].trim());
                final int endVerse = Integer.parseInt(versesBetweenHyphens[1].trim());
                result.add(new ScriptureAddress(getBook(bookName), chapter, startVerse, endVerse));
            }else{
                final int verse = Integer.parseInt(verseString.trim());
                var address = new ScriptureAddress(getBook(bookName), chapter, verse, 0);
                result.add(address);
            }
        }
    }

    /**
     * @param matcher the regex matcher
     * @return true if the address contains a book name, for example, John 1:1
     */
    private boolean isAddressWithBookName(Matcher matcher) {
        return matcher.groupCount() == 2;
    }

    private Book getBook(String bookName) {
        for (Book book : Book.values()) {
            if (book.getFullName().equals(bookName)) {
                return book;
            }
            if(book.getAbbreviation1().equals(bookName)) {
                return book;
            }
        }
        return null;
    }



    private void linkVerse(String textAddress, Matcher verseMatcher) {
        String optionsForTagA = "epub:type=\"noteref\"";
        String uuid = uuidGenerator.generate();
        String linkedVerse = """
                 <a %s href="#%s">%s</a>""".formatted(optionsForTagA, uuid, textAddress);
        verseMatcher.appendReplacement(linkedHtml, linkedVerse);
        //verseMatcher.appendTail(linkedHtml);
    }
}
