package com.company.makepub.app.usecase.scriptureearth;

import com.company.makepub.app.domain.BookAddress;
import com.company.makepub.app.domain.BookName;
import com.company.makepub.app.domain.JsonBookRecord;
import com.company.makepub.app.domain.ScriptureEarthBookName;
import com.company.makepub.app.gateway.JsonParser;
import com.company.makepub.app.gateway.UrlReader;

import java.util.List;
import java.util.stream.Collectors;

public class ReadJsonBooks {

    private final JsonParser<JsonBookRecord> jsonParser;
    private final UrlReader urlReader;

    public ReadJsonBooks(JsonParser<JsonBookRecord> jsonParser, UrlReader urlReader) {
        this.jsonParser = jsonParser;
        this.urlReader = urlReader;
    }

    public List<BookAddress> execute() {
        String jsonBooks = getJsonBooks();
        List<JsonBookRecord> jsonBookRecords = jsonParser.parse(jsonBooks, JsonBookRecord[].class);
        String baseRef = "https://www.scriptureearth.org/data/xav/sab/xav/";
        return jsonBookRecords
                .stream()
                .map(record ->  {
                    if(record.ref() == null) {
                        return null;
                    }
                    return new BookAddress(
                            getEnumFromName(record.ref()),
                            baseRef + record.ref()
                    );}
                )
                .collect(Collectors.toList());
    }

    private BookName getEnumFromName(final String link) {
        // given xav-19-LUK-001.html then extract LUK
        int index = link.indexOf("-") + 4;
        if (index >= 0) {
            String nameExtracted = link.substring(index, index + 3);
            ScriptureEarthBookName scriptureEarthBookName = ScriptureEarthBookName.fromScriptureEarthString(nameExtracted);
            return BookName.getBookNameFromScriptureEarth(scriptureEarthBookName);
        }
        return null;
    }

    private String getJsonBooks() {
        String urlXav = "https://www.scriptureearth.org/data/xav/sab/xav/js/book-names.js";
        String jsonBooks = urlReader.execute(urlXav);
        return fixJsonString(jsonBooks);
    }

    private String fixJsonString(String jsonBooks) {
        // remove "var books =" at start
        jsonBooks = jsonBooks.substring(11);
        // remove last 3 lines
        jsonBooks = jsonBooks.substring(0, jsonBooks.lastIndexOf("\n"));
        jsonBooks = jsonBooks.substring(0, jsonBooks.lastIndexOf("\n"));
        jsonBooks = jsonBooks.substring(0, jsonBooks.lastIndexOf("\n"));
        // remove last comma
        jsonBooks = jsonBooks.substring(0, jsonBooks.lastIndexOf(","));
        // add again last bracket, removed above
        jsonBooks = jsonBooks + "\n]";
        // fix name and ref to json be valid
        jsonBooks = jsonBooks.replaceAll("name", "\"name\"");
        jsonBooks = jsonBooks.replaceAll("ref", "\"ref\"");
        return jsonBooks;
    }
}
