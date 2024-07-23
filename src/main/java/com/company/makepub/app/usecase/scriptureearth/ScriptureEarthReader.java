package com.company.makepub.app.usecase.scriptureearth;

import com.company.makepub.app.domain.BookAddress;
import com.company.makepub.app.domain.BookName;
import com.company.makepub.app.gateway.HtmlParser;
import com.company.makepub.app.usecase.types.BibleReader;

import java.util.List;
import java.util.Optional;

public class ScriptureEarthReader implements BibleReader {


    private final HtmlParser htmlParser;
    private final ReadJsonBooks readJsonBooks;

    public ScriptureEarthReader(HtmlParser htmlParser, ReadJsonBooks readJsonBooks) {
        this.readJsonBooks = readJsonBooks;
        this.htmlParser = htmlParser;
    }

    @Override
    public String getScripture(final String book, final int chapter, final int verse) {
        return getScripture(book, chapter, verse, verse);
    }

    @Override
    public String getScripture(final String book, final int chapter, final int startVerse, final int endVerse) {
        int checkedEndVerse = Math.max(startVerse, endVerse);
        List<BookAddress> bookAddresses = readJsonBooks.execute();
        BookName bookName = BookName.getBookName(book);
        Optional<BookAddress> bookAddress = bookAddresses.stream()
                .filter(b -> b.bookName().equals(bookName))
                .findFirst();
        if (bookAddress.isEmpty()) {
            throw new IllegalArgumentException("Book not found: " + book);
        }
        String url = bookAddress.get().url();
        return getScriptureFromSite(url, chapter, startVerse, checkedEndVerse);
    }

    private String getScriptureFromSite(final String url, final int chapter, final int startVerse, final int endVerse) {
        String chapterNumberWithZeros = String.format("%03d", chapter);
        String chapterUrl = url.replaceAll("(\\d{3})(\\.html)$", chapterNumberWithZeros+"$2");
        return htmlParser.getTextBetweenTagId(
                chapterUrl,
                "v"+startVerse,
                "v"+(endVerse+1)
        );
    }

}
