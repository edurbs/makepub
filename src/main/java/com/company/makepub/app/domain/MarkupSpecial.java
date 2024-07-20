package com.company.makepub.app.domain;

import jakarta.annotation.Nullable;

public enum MarkupSpecial  {

    ITALIC("_", "<i>", "</i>", false, false, false),
    BOLD("~", "<b>", "</b>", false, false, false),
    FOOTNOTE_SYMBOL("*", "<b><sup><a epub:type=\"noteref\" href=\"#{idFootNote}\">*</a></sup></b>", "", false, true, false),
    FOOTNOTE_TEXT("£", "<aside id=\"{idFootNote}\" epub:type=\"footnote\"><p>", "</p></aside>", true, false, true);

    private final String id;
    private final String htmlStart;
    private final String htmlEnd;
    private final boolean isParagraph;
    private final boolean isFootnoteSymbol;
    private final boolean isFootnoteText;

    MarkupSpecial(String id, String htmlStart, String htmlEnd, boolean isParagraph, boolean isFootnoteSymbol, boolean isFootnoteText) {
        this.id = id;
        this.htmlStart = htmlStart;
        this.htmlEnd = htmlEnd;
        this.isParagraph = isParagraph;
        this.isFootnoteSymbol = isFootnoteSymbol;
        this.isFootnoteText = isFootnoteText;
    }

    public String getId() {
        return id;
    }

    public String getHtmlStart() {
        return htmlStart;
    }

    public String getHtmlEnd() {
        return htmlEnd;
    }

    public boolean isParagraph() {
        return isParagraph;
    }

    public boolean isFootnoteSymbol() {
        return isFootnoteSymbol;
    }

    public boolean isFootnoteText() {
        return isFootnoteText;
    }

    @Nullable
    public static MarkupSpecial fromId(String id) {
        for (MarkupSpecial at : MarkupSpecial.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }

    public static boolean isFootnote(MarkupSpecial markup) {
        return markup.isFootnoteSymbol() || markup.isFootnoteText();
    }


}