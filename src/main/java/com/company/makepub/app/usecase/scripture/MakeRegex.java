package com.company.makepub.app.usecase.scripture;

import com.company.makepub.app.domain.Book;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MakeRegex {
    private final String text;
    private Matcher matcher;
    private static final Pattern pattern = Pattern.compile(getRegex());

    public MakeRegex (String text) {
        this.text = text;
        execute();
    }

    public Matcher getMatcher() {
        return matcher;
    }

    private void execute(){
        String cleanedText = cleanText(text);
        matcher = pattern.matcher(cleanedText);
    }

    private String cleanText(String text) {
        return text.replace("\u00A0", " "); // non-breaking space
    }

    private static String getRegex(){
        StringBuilder booksForRegex = new StringBuilder();
        for(Book book : Book.values()) {
            booksForRegex.append(book.getFullName()).append("|");
            booksForRegex.append(book.getAbbreviation1()).append("|");
        }
        return "((%s)\\s)?\\d+:\\d+(?:[-,]\\s*\\d+)*"
                .formatted(booksForRegex.toString().trim());
    }
}
