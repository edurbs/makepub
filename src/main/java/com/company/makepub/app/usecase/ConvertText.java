package com.company.makepub.app.usecase;

import com.company.makepub.app.domain.MarkupRecord;
import com.company.makepub.app.gateway.UUIDGenerator;

import java.util.ArrayList;
import java.util.List;

public class ConvertText {
    private final UUIDGenerator uuidGenerator;
    private final List<MarkupRecord> markupRecords = new ArrayList<>();

    private final List<String> footNotes = new ArrayList<>();
    private int footNoteIndex = 0;

    public ConvertText(UUIDGenerator uuidGenerator, List<MarkupRecord> markupRecords) {
        this.uuidGenerator = uuidGenerator;
        this.markupRecords.addAll(markupRecords);
    }

    public String convert(final String text) {
        StringBuilder textConverted = new StringBuilder();
        List<String> lines = List.of(text.split("\n"));
        for(String line : lines) {
            for (MarkupRecord markupRecord : markupRecords) {
                line = applyMarkups(line, markupRecord);
            }
            textConverted.append(line).append("\n");
        }
        return textConverted.toString().trim();
    }

    private String applyMarkups(String line, MarkupRecord markupRecord) {
        int index = line.indexOf(markupRecord.id());
        if(markupRecord.isParagraph() && index==0){
            line = convertLine(markupRecord, line, index);
        }else if (!markupRecord.isParagraph()){
            line = formatAsNotParagraph(markupRecord, line, index);
        }
        return line;
    }

    private String formatAsNotParagraph(MarkupRecord markupRecord, String line, int index) {
        while (index >= 0) {
            int lineSize = line.length();
            line = convertLine(markupRecord, line, index);
            int diff =  line.length() - lineSize;
            index += diff;
            index = line.indexOf(markupRecord.id(), index + 1);
        }
        return line;
    }

    private String convertLine(MarkupRecord markupRecord, final String text, final int firstIndex) {
        String textConverted = text;
        if(markupRecord.isParagraph()) {
            textConverted = convertLineAsBlock(markupRecord, textConverted);
        }else{
            textConverted = replaceAt(firstIndex, textConverted, markupRecord.htmlStart());

            if(markupRecord.htmlEnd()!=null && !markupRecord.htmlEnd().isBlank()) {
                int nextIndex = textConverted.indexOf(markupRecord.id());
                if(nextIndex > 0){
                    textConverted = replaceAt(nextIndex, textConverted, markupRecord.htmlEnd());
                }
            }
        }
        if(markupRecord.isFootnote()) {
            textConverted = replaceVariableFootnote(markupRecord, textConverted);
        }
        if(markupRecord.isQuestion()) {
            textConverted = convertLineAsQuestionWithBox(textConverted);
        }

        return textConverted;
    }

    private String convertLineAsQuestionWithBox(final String textConverted) {
        String uuidQuestion = uuidGenerator.generate();
        String questionBox = "<textarea id=\"" + uuidQuestion + "\" rows=\"3\" cols=\"40\"></textarea>";
        return textConverted + questionBox;
    }

    private String convertLineAsBlock(MarkupRecord markupRecord, final String text) {
        String textConverted = text;
        String htmlEnd = markupRecord.htmlEnd() == null ? "" : markupRecord.htmlEnd();
        textConverted = markupRecord.htmlStart() + textConverted.substring(1) + htmlEnd;
        return textConverted;
    }

    private String replaceVariableFootnote(MarkupRecord markupRecord, final String text) {
        String idFootnoteVariable = "{idFootNote}";
        String textConverted = text;
        if(markupRecord.isFootnoteSymbol()) {
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
