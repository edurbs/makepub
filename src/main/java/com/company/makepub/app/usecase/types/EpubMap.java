package com.company.makepub.app.usecase.types;

public enum EpubMap {

    COVER("OEBPS/Text/cover.xhtml"),
    NAV("OEBPS/Text/nav.xhtml"),
    TEXT("OEBPS/Text/Section0001.xhtml"),
    MUSIC("OEBPS/Music/Section0002.xhtml"),
    SCRIPTURES("OEBPS/Scriptures/Section0003.xhtml"),
    IMAGE("OEBPS/Images/cover.png"),
    STYLE("OEBPS/sgc-nav.css"),
    CONTENT("OEBPS/content.opf");

    private final String path;

    EpubMap(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
