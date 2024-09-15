package com.company.makepub.app.gateway;

import java.util.List;

public interface HtmlParser
{
    String parse(String site, String tag);
    String query(String site, String query);
    String getTextBetweenTagId(String site, String tagIdStart, String tagIdEnd, List<String> tagsToRemove);
}
