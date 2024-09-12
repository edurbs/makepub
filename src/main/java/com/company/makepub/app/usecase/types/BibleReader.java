package com.company.makepub.app.usecase.types;

import com.company.makepub.app.domain.ScriptureAddress;
import jakarta.annotation.Nonnull;

public interface BibleReader {
    @Nonnull String getScripture(String book, int chapter, int verse);
    @Nonnull String getScripture(String book, int chapter, int startVerse, int endVerse);
    @Nonnull String getScripture(ScriptureAddress scriptureAddress);
}
