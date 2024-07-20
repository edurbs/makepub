package com.company.makepub.view.converter;


import com.company.makepub.app.domain.MarkupRecord;
import com.company.makepub.app.usecase.ConvertText;
import com.company.makepub.app.usecase.myUUIDGenerator;
import com.company.makepub.entity.Markup;
import com.company.makepub.view.main.MainView;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.router.Route;
import io.jmix.core.DataManager;
import io.jmix.flowui.component.textarea.JmixTextArea;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Route(value = "Converter", layout = MainView.class)
@ViewController("Converter")
@ViewDescriptor("Converter.xml")
public class Converter extends StandardView {
    @ViewComponent
    private JmixTextArea textAreaInput;
    @ViewComponent
    private JmixTextArea textAreaOutput;
    @Autowired
    private DataManager dataManager;

    @Subscribe(id = "convertButton", subject = "clickListener")
    public void onConvertButtonClick(final ClickEvent<JmixButton> event) {
        String inputText = textAreaInput.getValue();
        List<MarkupRecord> markupRecordList = dataManager.load(Markup.class).all().list()
                .stream().map(
                    m -> new MarkupRecord(
                            m.getId(),
                            m.getHtmlStart(),
                            m.getHtmlEnd(),
                            m.getIsParagraph(),
                            m.getIsFootnoteSymbol(),
                            m.getIsFootnoteText(),
                            m.getIsQuestion(),
                            m.getCreatedBy()
                    )
            ).toList();
        String outputText = new ConvertText(new myUUIDGenerator(), markupRecordList).convert(inputText);
        textAreaOutput.setValue(outputText);
    }




}