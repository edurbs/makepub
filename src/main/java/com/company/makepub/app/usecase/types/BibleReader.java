package com.company.makepub.app.usecase.types;

import com.company.makepub.app.domain.ScriptureAddress;

public interface BibleReader {
     String getScripture(String book, int chapter, int verse);
     String getScripture(String book, int chapter, int startVerse, int endVerse);
     String getScripture(ScriptureAddress scriptureAddress);
}
