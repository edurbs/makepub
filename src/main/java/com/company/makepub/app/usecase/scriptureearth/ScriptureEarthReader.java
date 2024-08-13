package com.company.makepub.app.usecase.scriptureearth;

import com.company.makepub.app.domain.BookAddress;
import com.company.makepub.app.domain.Book;
import com.company.makepub.app.domain.ScriptureAddress;
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
    public String getScripture(final String bookName, final int chapter, final int startVerse, final int endVerse) {
        int checkedEndVerse = Math.max(startVerse, endVerse);
        Book book = Book.getBookNameFromFullName(bookName);
        if (book == null) {
            return "";
        }
        return getScripture(new ScriptureAddress(book, chapter, startVerse, checkedEndVerse));
    }

    @Override
    public String getScripture(ScriptureAddress scriptureAddress){
        List<BookAddress> bookAddresses = readJsonBooks.execute();
        Book book = scriptureAddress.book();
        int chapter = scriptureAddress.chapter();
        int startVerse = scriptureAddress.verse();
        int endVerse = scriptureAddress.endVerse();
        int checkedEndVerse = Math.max(startVerse, endVerse);
        Optional<BookAddress> bookAddress = bookAddresses.stream()
                .filter(b -> b.book().equals(book))
                .findFirst();
        if (bookAddress.isEmpty()) {
            throw new IllegalArgumentException("Book not found: " + book.getFullName());
        }
        String url = bookAddress.get().url();
        return getScriptureFromSite(url, chapter, startVerse, checkedEndVerse, book);
    }

    private String getScriptureFromSite(final String url, final int chapter, final int startVerse, final int endVerse, final Book book) {
        List<String> tagsToRemove = List.of("div.s", "div.r", "div.video-block", "div.footer", "div.c-drop");
        String tagIdStart = "<a id=\"v" + startVerse + "\"></a>";
        String tagIdEnd = "<a id=\"v" + (endVerse+1) + "\"></a>";
        String chapterNumberWithZeros = String.format("%03d", chapter);
        String chapterUrl = url.replaceAll("(\\d{3})(\\.html)$", chapterNumberWithZeros+"$2");
        if(isLastVerse(chapter, endVerse, book)){
            tagIdEnd="<span id=\"bookmarks";
        }
        String result = htmlParser.getTextBetweenTagId(
                chapterUrl,
                tagIdStart,
                tagIdEnd,
                tagsToRemove
        ).trim();
        if(result.isBlank()){
            result = htmlParser.getTextBetweenTagId(
                    chapterUrl,
                    tagIdStart,
                    "<span id=\"bookmarks",
                    tagsToRemove
            ).trim();
        }
        return result;
    }

    private boolean isLastVerse(int chapter, int endVerse, Book book) {
        return endVerse == book.getNumberOfScriptures(chapter);
    }

}
