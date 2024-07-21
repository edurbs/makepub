package com.company.makepub.entity.mapper;

import com.company.makepub.app.domain.MarkupRecord;
import com.company.makepub.entity.Markup;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MarkupMapperTest {

    @Test
    @DisplayName("Should convert Markup to MarkupRecord")
    void to() {
        Markup markup = new Markup();
        markup.setId("id");
        markup.setHtmlStart("htmlStart");
        markup.setHtmlEnd("htmlEnd");
        markup.setIsParagraph(true);
        markup.setIsFootnoteSymbol(true);
        markup.setIsFootnoteText(true);
        markup.setIsQuestion(true);
        markup.setDescription("description");

        MarkupMapper markupMapper = new MarkupMapper();
        MarkupRecord markupRecord = markupMapper.to(markup);

        assertEquals(markup.getId(), markupRecord.id());
        assertEquals(markup.getHtmlStart(), markupRecord.htmlStart());
        assertEquals(markup.getHtmlEnd(), markupRecord.htmlEnd());
        assertEquals(markup.getIsParagraph(), markupRecord.isParagraph());
        assertEquals(markup.getIsFootnoteSymbol(), markupRecord.isFootnoteSymbol());
        assertEquals(markup.getIsFootnoteText(), markupRecord.isFootnoteText());
        assertEquals(markup.getIsQuestion(), markupRecord.isQuestion());
        assertEquals(markup.getDescription(), markupRecord.description());
    }
}