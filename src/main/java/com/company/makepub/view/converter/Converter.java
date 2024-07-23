package com.company.makepub.view.converter;


import com.company.makepub.app.domain.EpubFile;
import com.company.makepub.app.domain.MarkupRecord;
import com.company.makepub.app.gateway.HtmlParser;
import com.company.makepub.app.gateway.UUIDGenerator;
import com.company.makepub.app.gateway.UrlReader;
import com.company.makepub.app.usecase.ConvertMarkupToHtml;
import com.company.makepub.app.usecase.EpubCreator;
import com.company.makepub.app.usecase.LinkMusic;
import com.company.makepub.app.usecase.LinkScriptures;
import com.company.makepub.utils.htmlparser.JsoupHtmlParser;
import com.company.makepub.utils.linkreader.JavaUrlReader;
import com.company.makepub.utils.uuid.MyUUIDGenerator;
import com.company.makepub.entity.mapper.MarkupMapper;
import com.company.makepub.entity.repository.MarkupRepository;
import com.company.makepub.view.main.MainView;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.Notifications;
import io.jmix.flowui.component.textarea.JmixTextArea;
import io.jmix.flowui.component.upload.FileUploadField;
import io.jmix.flowui.download.DownloadFormat;
import io.jmix.flowui.download.Downloader;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.kit.component.upload.event.FileUploadFailedEvent;
import io.jmix.flowui.kit.component.upload.event.FileUploadSucceededEvent;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@Route(value = "Converter", layout = MainView.class)
@ViewController("Converter")
@ViewDescriptor("Converter.xml")
public class Converter extends StandardView {

    @ViewComponent
    private JmixTextArea textAreaInput;

    @Autowired
    private MarkupRepository markupRepository;

    @Autowired
    private Notifications notifications;

    @Autowired
    private Downloader downloader;

    private byte[] coverImage;

    @Subscribe(id = "convertButton", subject = "clickListener")
    public void onConvertButtonClick(final ClickEvent<JmixButton> event) {
        String inputText = textAreaInput.getValue();
        List<MarkupRecord> markupRecordList = new ArrayList<>();
        markupRepository.findAll()
                .forEach(m -> markupRecordList.add(
                        new MarkupMapper().to(m)));

        UUIDGenerator uuidGenerator = new MyUUIDGenerator();
        UrlReader javaUrlReader = new JavaUrlReader();
        HtmlParser htmlParser = new JsoupHtmlParser(javaUrlReader);
        ConvertMarkupToHtml markupConversor = new ConvertMarkupToHtml(uuidGenerator, markupRecordList);
        LinkMusic linkMusic = new LinkMusic(htmlParser, uuidGenerator, "DANHOꞌRE");
        LinkScriptures linkScriptures = new LinkScriptures(htmlParser, uuidGenerator);

        EpubFile epubFile = new EpubCreator(
                uuidGenerator,
                htmlParser,
                markupConversor,
                linkMusic,
                linkScriptures,
                inputText,
                coverImage
                ).execute();

        if(epubFile.content()!=null){
            notifications.create("Epub criado com sucesso!")
                    .withType(Notifications.Type.SUCCESS)
                    .withPosition(Notification.Position.BOTTOM_END)
                    .show();
            downloader.download(
                    epubFile.content(),
                    epubFile.filename(),
                    DownloadFormat.getByExtension("EPUB"));
        }
    }

    @Subscribe("coverUpload")
    public void onCoverUploadFileUploadSucceeded(final FileUploadSucceededEvent<FileUploadField> event) {
        notifications.create("Arquivo carregado com sucesso!")
                .withType(Notifications.Type.SUCCESS)
                .show();
        coverImage = event.getSource().getValue();
    }

    @Subscribe("coverUpload")
    public void onCoverUploadFileUploadFailed(final FileUploadFailedEvent<FileUploadField> event) {
        notifications.create("Erro ao carregar arquivo!")
                .withType(Notifications.Type.ERROR)
                .show();

    }


}