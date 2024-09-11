package com.company.makepub.app.usecase.scripture;

import com.company.makepub.app.domain.Book;
import com.company.makepub.app.domain.ScriptureAddress;
import jakarta.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

public class DetectScripture {

    private final List<ScriptureAddress> scriptureAddressList = new ArrayList<>();
    private final Matcher matcher;
    private final String lastBookName;

    public DetectScripture(Matcher matcher, String lastBookName) {
        this.matcher = matcher;
        this.lastBookName = lastBookName;
    }

    public List<ScriptureAddress> execute() {
        var extractor = new ScriptureAddressExtractor(matcher, lastBookName);
        final String bookName = extractor.getBookName();
        final int chapter = extractor.getChapter();
        final String allVerses = extractor.getAllVerses();
        String[] versesBetweenCommas = allVerses.split(",");
        for (String verseString : versesBetweenCommas) {
            addToList(verseString, bookName, chapter);
        }
        return scriptureAddressList;
    }

    private void addToList(String verseString, String bookName, int chapter) {
        if(verseString.contains("-")) {
            String[] versesBetweenHyphens = verseString.split("-");
            final int startVerse = Integer.parseInt(versesBetweenHyphens[0].trim());
            final int endVerse = Integer.parseInt(versesBetweenHyphens[1].trim());
            scriptureAddressList.add(new ScriptureAddress(getBook(bookName), chapter, startVerse, endVerse));
        }else{
            final int verse = Integer.parseInt(verseString.trim());
            var address = new ScriptureAddress(getBook(bookName), chapter, verse, 0);
            scriptureAddressList.add(address);
        }
    }

    private @Nullable  Book getBook(String bookName) {
        for (Book book : Book.values()) {
            if (book.getFullName().equalsIgnoreCase(bookName)) {
                return book;
            }
            if(book.getAbbreviation1().equalsIgnoreCase(bookName)) {
                return book;
            }
        }
        return null;
    }
}
