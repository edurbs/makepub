package com.company.makepub.app.gateway;

import com.company.makepub.app.usecase.exceptions.UseCaseException;

import java.util.List;

public interface HtmlParser
{
    String parse(String site, String tag);
    String getTextBetweenTagId(String site, String tagIdStart, String tagIdEnd, List<String> tagsToRemove);
}
