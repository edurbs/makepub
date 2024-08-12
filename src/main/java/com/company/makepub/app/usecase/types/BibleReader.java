package com.company.makepub.app.usecase.types;

public interface BibleReader {
    String getScripture(String book, int chapter, int verse); // TODO chave String book to ENUM
    String getScripture(String book, int chapter, int startVerse, int endVerse);
}
