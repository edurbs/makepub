package com.company.makepub.app.domain;

public record Markup(String id, String htmlStart, String htmlEnd, boolean isParagraph, boolean isFootnoteSymbol, boolean isFootnoteText, boolean isQuestion, String description) {
    public boolean isFootnote(){
        return isFootnoteSymbol() || isFootnoteText();
    }

}
