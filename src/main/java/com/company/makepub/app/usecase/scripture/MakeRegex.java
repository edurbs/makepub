package com.company.makepub.app.usecase.scripture;

import com.company.makepub.app.domain.Book;
import jakarta.annotation.Nonnull;
import org.jetbrains.annotations.Contract;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class MakeRegex {
    private static final Pattern pattern = Pattern.compile(getRegex(), Pattern.UNICODE_CASE|Pattern.CASE_INSENSITIVE);

    @Nonnull
    public Matcher getMatcher(@Nonnull String text) {
        String cleanedText = cleanText(text);
        return pattern.matcher(cleanedText);
    }

    @Nonnull
    @Contract(pure = true)
    private String cleanText(@Nonnull String text) {
        return text.replace("\u00A0", " "); // non-breaking space
    }

    @Nonnull
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
