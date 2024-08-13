package com.company.makepub.app.usecase.scripture;

import java.util.regex.Matcher;

public class ScriptureAddressExtractor {

    private final Matcher matcher;
    private String lastBookName;
    private String allVerses;
    private String addressPrefix;
    private int chapter;
    private String bookName;

    public ScriptureAddressExtractor(Matcher matcher, String lastBookName) {
        this.matcher = matcher;
        this.lastBookName = lastBookName;
        execute();
    }

    public int getChapter() {
        return chapter;
    }

    public String getBookName() {
        return bookName;
    }

    public String getAllVerses() {
        return allVerses;
    }

    public String getAddressPrefix() {
        return addressPrefix;
    }

    private void execute() {
        final String fullScriptureAddress;
        final String scriptureAddress;
        if(isAddressWithBookName(matcher)) {
            bookName = matcher.group(2).trim();
            lastBookName = bookName;
            fullScriptureAddress = matcher.group(0).trim();
            scriptureAddress = fullScriptureAddress.replace(bookName, "");
        }else{
            bookName = lastBookName;
            fullScriptureAddress = matcher.group(0).trim();
            scriptureAddress = fullScriptureAddress;
        }
        chapter = Integer.parseInt(scriptureAddress.split(":")[0].trim());
        addressPrefix = bookName + " " + chapter + ":";
        if(bookName.isBlank()){
            addressPrefix = " " + chapter + ":";
        }
        allVerses = scriptureAddress.split(":")[1];

    }
    private boolean isAddressWithBookName(Matcher matcher) {
        return matcher.groupCount() == 2;
    }
}
