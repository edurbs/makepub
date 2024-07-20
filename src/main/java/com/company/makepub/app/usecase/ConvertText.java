package com.company.makepub.app.usecase;

import com.company.makepub.app.domain.Markup;
import com.company.makepub.app.gateway.UUIDGenerator;

import java.util.ArrayList;
import java.util.List;

public class ConvertText {
    private final UUIDGenerator uuidGenerator;
    private final List<Markup> markups = new ArrayList<>();

    private final List<String> footNotes = new ArrayList<>();
    private int footNoteIndex = 0;

    public ConvertText(UUIDGenerator uuidGenerator, List<Markup> markups) {
        this.uuidGenerator = uuidGenerator;
        this.markups.addAll(markups);
    }

    public String convert(final String text) {
        StringBuilder textConverted = new StringBuilder();
        List<String> lines = List.of(text.split("\n"));
        for(String line : lines) {
            for (Markup markup : markups) {
                int index = line.indexOf(markup.id());
                while (index >= 0) {
                    int lineSize = line.length();
                    line = convertLine(markup, line, index);
                    int diff =  line.length() - lineSize;
                    index += diff;
                    index = line.indexOf(markup.id(), index + 1);
                }
            }
            textConverted.append(line).append("\n");
        }
        return textConverted.toString().trim();
    }

    private String convertLine(Markup markup, final String text, final int firstIndex) {
        String textConverted = text;
        textConverted = replaceAt(firstIndex, textConverted, markup.htmlStart());
        if(!markup.htmlEnd().isEmpty()) {
            int nextIndex = textConverted.indexOf(markup.id());
            if(nextIndex > 0){
                textConverted = replaceAt(nextIndex, textConverted, markup.htmlEnd());
            }
        }
        if(markup.isParagraph()) {
            textConverted = convertLineAsBlock(markup, textConverted);
        }
        if(markup.isFootnote()) {
            textConverted = replaceVariableFootnote(markup, textConverted);
        }
        return textConverted;
    }

    private String convertLineAsBlock(Markup markup, final String text) {
        String textConverted = text;
        textConverted = textConverted + markup.htmlEnd();
        return textConverted;
    }

    private String replaceVariableFootnote(Markup markup, final String text) {
        String idFootnoteVariable = "{idFootNote}";
        String textConverted = text;
        if(markup.isFootnoteSymbol()) {
            String uuid = uuidGenerator.generate();
            footNotes.add(uuid);
            return textConverted.replace(idFootnoteVariable, uuid);
        }
        if(footNoteIndex < footNotes.size()) {
            String uuid = footNotes.get(footNoteIndex);
            textConverted = textConverted.replace(idFootnoteVariable, uuid);
            footNoteIndex++;
        }
        return textConverted;
    }

    private String replaceAt(final int index, final String text, final String html){
        return text.substring(0, index)
                + html
                + text.substring(index + 1);

    }

}
