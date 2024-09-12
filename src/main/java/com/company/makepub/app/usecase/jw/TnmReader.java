package com.company.makepub.app.usecase.jw;

import com.company.makepub.app.domain.Book;
import com.company.makepub.app.domain.ScriptureAddress;
import com.company.makepub.app.gateway.HtmlParser;
import com.company.makepub.app.usecase.types.BibleReader;
import jakarta.annotation.Nonnull;

public class TnmReader implements BibleReader {

    private final HtmlParser htmlParser;

    public TnmReader(HtmlParser htmlParser) {
        this.htmlParser = htmlParser;
    }

    @Nonnull
    @Override
    public String getScripture(String book, int chapter, int verse) {
        throw new UnsupportedOperationException();
    }

    @Nonnull
    @Override
    public String getScripture(String book, int chapter, int startVerse, int endVerse) {
        throw new UnsupportedOperationException();
    }

    @Nonnull
    @Override
    public String getScripture(@Nonnull ScriptureAddress scriptureAddress) {
        // id="v58-2-6-1"
        // id="v58-2-6-2" próximo §
        // v58 número do livro
        // v58-2 numero do capítulo
        // v58-2-6 numero do versículo
        // v58-2-6-1 número do §
        // css selector span[id^=v58-2-6]
        if(scriptureAddress.book()==null){
            return "";
        }
        int bookNumber = scriptureAddress.book().getOrdinalValue();
        int chapterNumber = scriptureAddress.chapter();
        int verseNumber = scriptureAddress.verse();
        int endVerse = scriptureAddress.endVerse();
        String site = getChapterSite(scriptureAddress);
        StringBuilder result = new StringBuilder();
        if(endVerse<verseNumber){
            endVerse = verseNumber;
        }
        for (int i = verseNumber; i <= endVerse; i++) {
            String query = "span[id^=v" + bookNumber + "-" + chapterNumber + "-" + i + "-]";
            result.append(htmlParser.query(site, query));
        }
        String resultText = result.toString();
        resultText = resultText.replace("*", "");
        resultText = resultText.replace("+", "");
        return resultText;
    }

    @Nonnull
    private String getChapterSite(@Nonnull ScriptureAddress scriptureAddress){
        // https://wol.jw.org/xav/wol/b/r511/lp-xv/nwtp/18/2
        // job book 18 chapter 2
        Book book = scriptureAddress.book();
        if(book==null){
            return "";
        }
        int chapter = scriptureAddress.chapter();
        return "https://wol.jw.org/pt/wol/b/r5/lp-t/nwtsty/"+book.getOrdinalValue()+"/"+chapter;
    }
}
