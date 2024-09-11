package com.company.makepub.app.domain;

import jakarta.annotation.Nullable;

public record ScriptureAddress (@Nullable Book book, int chapter, int verse, int endVerse) {
}
