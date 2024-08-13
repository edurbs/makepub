package com.company.makepub.app.usecase.scripture;

import com.company.makepub.app.domain.Book;
import com.company.makepub.app.domain.ScriptureAddress;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DetectScripture {

    private final String html;
    private String htmlWithScriptureAddress;
    private final List<ScriptureAddress> scriptureAddressList = new ArrayList<>();

    public DetectScripture(String html) {
        this.html = html;
    }

    public String getHtml() {
        return html;
    }

    public List<ScriptureAddress> execute() {

        final String regex = getRegex();
        Pattern pattern = Pattern.compile(regex);

        Matcher matcher = pattern.matcher(html);
        String lastBookName="";
        while (matcher.find()) {
            final String bookName;
            final String fullScriptureAddress;
            final String scriptureAddress;
            if(isAddressWithBookName(matcher)) {
                bookName = matcher.group(2).trim();
                lastBookName = bookName;
                fullScriptureAddress = matcher.group(0).trim();
                scriptureAddress = fullScriptureAddress.replace(bookName, "").trim();
            }else{
                bookName = lastBookName;
                fullScriptureAddress = matcher.group(0).trim();
                scriptureAddress = fullScriptureAddress;
            }
            final int chapter = Integer.parseInt(scriptureAddress.split(":")[0].trim());
            final String allVerses = scriptureAddress.split(":")[1].trim();
            detectScriptureAddress(scriptureAddressList, bookName, chapter, allVerses);
        }
        return scriptureAddressList;
    }

    private void detectScriptureAddress(List<ScriptureAddress> result, final String bookName, final int chapter, final String allVerses) {
        String[] versesBetweenCommas = allVerses.split(",");
        for (String verseString : versesBetweenCommas) {
            if(verseString.contains("-")) {
                String[] versesBetweenHyphens = verseString.split("-");
                final int startVerse = Integer.parseInt(versesBetweenHyphens[0].trim());
                final int endVerse = Integer.parseInt(versesBetweenHyphens[1].trim());
                result.add(new ScriptureAddress(getBook(bookName), chapter, startVerse, endVerse));
            }else{
                final int verse = Integer.parseInt(verseString.trim());
                result.add(new ScriptureAddress(getBook(bookName), chapter, verse, 0));
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

    private String getRegex(){
        StringBuilder booksForRegex = new StringBuilder();
        for(Book book : Book.values()) {
            booksForRegex.append(book.getFullName()).append("|");
            booksForRegex.append(book.getAbbreviation1()).append("|");
        }
        return "((%s)\\s)?\\b\\d{1,3}:\\d{1,3}(?:[-,]\\s*\\d{1,3})*(?:,\\s*\\d{1,3})?(?:-\\s*\\d{1,3})?\\b"
                .formatted(booksForRegex.toString().trim());
    }
}
