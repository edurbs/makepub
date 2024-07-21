package com.company.makepub.app.domain;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;

public record MarkupRecord(
        @NotNull String id,
        @NotNull String htmlStart,
        @Nullable String htmlEnd,
        boolean isParagraph,
        boolean isFootnoteSymbol,
        boolean isFootnoteText,
        boolean isQuestion,
        @Nullable String description) {
    public boolean isFootnote(){
        return isFootnoteSymbol() || isFootnoteText();
    }

}
