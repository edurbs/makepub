package com.company.makepub.app.usecase.scripture;

import com.company.makepub.app.domain.Book;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MakeRegex {
    private final String text;

    public MakeRegex (String text) {
        this.text = text;
    }

    public Matcher execute(){
        Pattern pattern = Pattern.compile(getRegex());
        return pattern.matcher(text);
    }

    private String getRegex(){
        StringBuilder booksForRegex = new StringBuilder();
        for(Book book : Book.values()) {
            booksForRegex.append(book.getFullName()).append("|");
            booksForRegex.append(book.getAbbreviation1()).append("|");
        }
        return "((%s)\\s)?\\d+:\\d+(?:[-,]\\s*\\d+)*"
                .formatted(booksForRegex.toString().trim());
    }
}
