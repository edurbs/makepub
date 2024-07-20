package com.company.makepub.entity.mapper;

import com.company.makepub.app.domain.MarkupRecord;
import com.company.makepub.entity.Markup;

public class MarkupMapper {

    public MarkupRecord to(Markup markup) {
        return new MarkupRecord(
                markup.getId(),
                markup.getHtmlStart(),
                markup.getHtmlEnd(),
                markup.getIsParagraph(),
                markup.getIsFootnoteSymbol(),
                markup.getIsFootnoteText(),
                markup.getIsQuestion(),
                markup.getCreatedBy()
        );
    }
}
