package com.company.makepub.app.usecase;

import com.company.makepub.app.domain.MarkupSpecial;
import com.company.makepub.app.gateway.UUIDGenerator;

import java.util.ArrayList;
import java.util.List;

public class ConvertText {
    private final UUIDGenerator uuidGenerator;
    private final List<String> footNotes = new ArrayList<>();
    private int footNoteIndex = 0;

    public ConvertText(UUIDGenerator uuidGenerator) {
        this.uuidGenerator = uuidGenerator;
    }

    public String convert(final String text) {
        StringBuilder textConverted = new StringBuilder();
        List<String> lines = List.of(text.split("\n"));
        for(String line : lines) {
            for (MarkupSpecial markup : MarkupSpecial.values()) {
                int index = line.indexOf(markup.getId());
                while (index >= 0) {
                    int lineSize = line.length();
                    line = convertText(markup, line, index);
                    int diff =  line.length() - lineSize;
                    index += diff;
                    index = line.indexOf(markup.getId(), index + 1);
                }
            }
            textConverted.append(line).append("\n");
        }
        return textConverted.toString().trim();
    }

    private String convertText(MarkupSpecial markup, String textConverted, int firstIndex) {
        textConverted = replaceAt(firstIndex, textConverted, markup.getHtmlStart());
        if(!markup.getHtmlEnd().isEmpty()) {
            int nextIndex = textConverted.indexOf(markup.getId());
            if(nextIndex > 0){
                textConverted = replaceAt(nextIndex, textConverted, markup.getHtmlEnd());
            }
        }
        if(MarkupSpecial.isFootnote(markup)) {
            textConverted = replaceVariableFootnote(markup, textConverted);
        }
        return textConverted;
    }

    private String replaceVariableFootnote(MarkupSpecial markup, final String text) {
        String idFootnoteVariable = "{idFootNote}";
        String textConverted = text;
        if(markup == MarkupSpecial.FOOTNOTE_SYMBOL) {
            String uuid = uuidGenerator.generate();
            footNotes.add(uuid);
            return textConverted.replace(idFootnoteVariable, uuid);
        }else if(markup == MarkupSpecial.FOOTNOTE_TEXT) {
            if(footNoteIndex < footNotes.size()) {
                String uuid = footNotes.get(footNoteIndex);
                textConverted = textConverted.replace(idFootnoteVariable, uuid);
                textConverted = textConverted + markup.getHtmlEnd();
                footNoteIndex++;
            }
            return textConverted;
        }
        return textConverted;
    }

    private String replaceAt(final int index, final String text, final String html){
        return text.substring(0, index)
                + html
                + text.substring(index + 1);

    }

}
