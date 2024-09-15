package com.company.makepub.app.usecase.epub;

import com.company.makepub.app.gateway.UUIDGenerator;
import com.company.makepub.app.usecase.types.StringConversor;
import jakarta.annotation.Nonnull;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class ConvertMarkupToHtml implements StringConversor {
    private final UUIDGenerator uuidGenerator;
    private final List<String> footNotes = new ArrayList<>();
    private int footNoteIndex = 0;

    public ConvertMarkupToHtml(UUIDGenerator uuidGenerator) {
        this.uuidGenerator = uuidGenerator;
    }

    @Nonnull
    @Override
    public String convert(@Nonnull final String text) {
        StringBuilder textConverted = new StringBuilder();
        List<String> lines = List.of(text.split("\n"));
        for(String line : lines) {
            for(MarkupEnum markupEnum : MarkupEnum.values()) {
                line = applyMarkups(line, markupEnum);
            }
            textConverted.append(line).append("\n");
        }
        return textConverted.toString().trim();
    }

    @Nonnull
    private String applyMarkups(@Nonnull String line, @Nonnull MarkupEnum markupEnum) {
        int index = line.indexOf(markupEnum.getId());
        if(markupEnum.isParagraph() && index==0){
            line = convertLine(markupEnum, line, index);
        }else if (!markupEnum.isParagraph()){
            line = formatAsNotParagraph(markupEnum, line, index);
        }
        return line;
    }

    @Nonnull
    private String formatAsNotParagraph(@Nonnull MarkupEnum markupEnum, @Nonnull String line, int index) {
        while (index >= 0) {
            int lineSize = line.length();
            line = convertLine(markupEnum, line, index);
            int diff =  line.length() - lineSize;
            index += diff;
            index = line.indexOf(markupEnum.getId(), index + 1);
        }
        return line;
    }

    @Nonnull
    private String convertLine(@Nonnull MarkupEnum markupEnum, @Nonnull final String text, final int firstIndex) {
        String textConverted = text;
        if(markupEnum.isParagraph()) {
            textConverted = convertLineAsBlock(markupEnum, textConverted);
        }else{
            textConverted = replaceAt(firstIndex, textConverted, markupEnum.getHtmlStart());

            if(markupEnum.getHtmlEnd()!=null && !markupEnum.getHtmlEnd().isBlank()) {
                int nextIndex = textConverted.indexOf(markupEnum.getId());
                if(nextIndex > 0){
                    textConverted = replaceAt(nextIndex, textConverted, markupEnum.getHtmlEnd());
                }
            }
        }
        if(markupEnum.isFootnote()) {
            textConverted = replaceVariableFootnote(markupEnum, textConverted);
        }
        if(markupEnum.isQuestion()) {
            textConverted = convertLineAsQuestionWithBox(textConverted);
        }

        return textConverted;
    }

    @Nonnull
    private String convertLineAsQuestionWithBox(@Nonnull final String textConverted) {
        String uuidQuestion = uuidGenerator.generate();
        String questionBox = """
                <div class="gen-field">
                <textarea id="%s" class="du-color--textSubdued du-fontSize--baseMinus1" ></textarea>
                </div>""".formatted(uuidQuestion);
        return textConverted + questionBox;
    }

    @Nonnull
    private String convertLineAsBlock(@Nonnull MarkupEnum markupEnum, @Nonnull final String text) {
        String textConverted;
        try{
            final String regex = "^(" + markupEnum.getId() + ")(\\d{1,2}[^.])";
            final String subst = " <sup>$2</sup>";
            final Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE);
            final Matcher matcher = pattern.matcher(text);
            textConverted = matcher.replaceAll(subst);
        } catch (Exception e) {
            textConverted = text;
        }
        String htmlEnd = markupEnum.getHtmlEnd() == null ? "" : markupEnum.getHtmlEnd();
        textConverted = markupEnum.getHtmlStart() + textConverted.substring(1) + htmlEnd;
        return textConverted;
    }

    @Nonnull
    private String replaceVariableFootnote(@Nonnull MarkupEnum markupEnum, @Nonnull final String text) {
        String idFootnoteVariable = "{idFootNote}";
        String textConverted = text;
        if(markupEnum.isFootnoteSymbol()) {
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

    @Nonnull
    private String replaceAt(final int index, @Nonnull final String text, @Nonnull final String html){
        return text.substring(0, index)
                + html
                + text.substring(index + 1);

    }

}
