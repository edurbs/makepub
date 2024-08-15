package com.company.makepub.app.usecase.types;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EpubMapTest {
    @Test
    void loadCss() {
        EpubMap css = EpubMap.STYLE;
        assertTrue(css.getDefaultText().contains("svg:not(:root).svg-inline--fa"));
    }

}