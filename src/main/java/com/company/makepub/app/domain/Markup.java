package com.company.makepub.app.domain;

public record Markup(String id, String htmlStart, String htmlEnd, boolean isParagraph, boolean isFootnoteSymbol, boolean isFootnoteText) {
    public boolean isFootnote(){
        return isFootnoteSymbol() || isFootnoteText();
    }

}
