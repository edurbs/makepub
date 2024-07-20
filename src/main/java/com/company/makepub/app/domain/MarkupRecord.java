package com.company.makepub.app.domain;

import jakarta.annotation.Nullable;

public record MarkupRecord(String id, String htmlStart, @Nullable String htmlEnd, boolean isParagraph, boolean isFootnoteSymbol, boolean isFootnoteText, boolean isQuestion, @Nullable String description) {
    public boolean isFootnote(){
        return isFootnoteSymbol() || isFootnoteText();
    }

}
