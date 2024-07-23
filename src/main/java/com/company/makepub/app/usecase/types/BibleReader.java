package com.company.makepub.app.usecase.types;

public interface BibleReader {
    String getScripture(String book, int chapter, int verse);
    String getScripture(String book, int chapter, int startVerse, int endVerse);
}
