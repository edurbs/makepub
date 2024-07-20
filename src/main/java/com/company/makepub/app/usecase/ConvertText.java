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
                line = applyMarkups(line, markup);
            }
            textConverted.append(line).append("\n");
        }
        return textConverted.toString().trim();
    }

    private String applyMarkups(String line, Markup markup) {
        int index = line.indexOf(markup.id());
        if(markup.isParagraph() && index==0){
            line = convertLine(markup, line, index);
        }else if (!markup.isParagraph()){
            line = formatAsNotParagraph(markup, line, index);
        }
        return line;
    }

    private String formatAsNotParagraph(Markup markup, String line, int index) {
        while (index >= 0) {
            int lineSize = line.length();
            line = convertLine(markup, line, index);
            int diff =  line.length() - lineSize;
            index += diff;
            index = line.indexOf(markup.id(), index + 1);
        }
        return line;
    }

    private String convertLine(Markup markup, final String text, final int firstIndex) {
        String textConverted = text;
        if(markup.isParagraph()) {
            textConverted = convertLineAsBlock(markup, textConverted);
        }else{
            textConverted = replaceAt(firstIndex, textConverted, markup.htmlStart());
            if(!markup.htmlEnd().isEmpty()) {
                int nextIndex = textConverted.indexOf(markup.id());
                if(nextIndex > 0){
                    textConverted = replaceAt(nextIndex, textConverted, markup.htmlEnd());
                }
            }
        }
        if(markup.isFootnote()) {
            textConverted = replaceVariableFootnote(markup, textConverted);
        }
        if(markup.isQuestion()) {
            textConverted = convertLineAsQuestionWithBox(textConverted);
        }

        return textConverted;
    }

    private String convertLineAsQuestionWithBox(final String textConverted) {
        String uuidQuestion = uuidGenerator.generate();
        String questionBox = "<textarea id=\"" + uuidQuestion + "\" rows=\"3\" cols=\"40\"></textarea>";
        return textConverted + questionBox;
    }

    private String convertLineAsBlock(Markup markup, final String text) {
        String textConverted = text;
        textConverted = markup.htmlStart() + textConverted.substring(1) + markup.htmlEnd();
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
