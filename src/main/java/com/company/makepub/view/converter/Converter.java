package com.company.makepub.view.converter;


import com.company.makepub.app.domain.MarkupRecord;
import com.company.makepub.app.usecase.ConvertText;
import com.company.makepub.app.usecase.myUUIDGenerator;
import com.company.makepub.entity.mapper.MarkupMapper;
import com.company.makepub.entity.repository.MarkupRepository;
import com.company.makepub.view.main.MainView;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.component.textarea.JmixTextArea;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Route(value = "Converter", layout = MainView.class)
@ViewController("Converter")
@ViewDescriptor("Converter.xml")
public class Converter extends StandardView {
    @ViewComponent
    private JmixTextArea textAreaInput;
    @ViewComponent
    private JmixTextArea textAreaOutput;
    @Autowired
    private MarkupRepository markupRepository;

    @Subscribe(id = "convertButton", subject = "clickListener")
    public void onConvertButtonClick(final ClickEvent<JmixButton> event) {
        String inputText = textAreaInput.getValue();
        List<MarkupRecord> markupRecordList = new ArrayList<>();
        markupRepository.findAll()
                .forEach(m -> markupRecordList.add(
                        new MarkupMapper().to(m)));
        String outputText = new ConvertText(new myUUIDGenerator(), markupRecordList)
                .convert(inputText);
        textAreaOutput.setValue(outputText);
    }
}