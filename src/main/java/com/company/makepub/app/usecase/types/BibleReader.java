package com.company.makepub.app.usecase.types;

import com.company.makepub.app.domain.ScriptureAddress;

public interface BibleReader {
    String getScripture(String book, int chapter, int verse); // TODO chave String book to ENUM
    String getScripture(String book, int chapter, int startVerse, int endVerse);
    String getScripture(ScriptureAddress scriptureAddress);
}
