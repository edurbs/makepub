package com.company.makepub.view.converter;


import com.company.makepub.app.domain.EpubFile;
import com.company.makepub.app.domain.JsonBookRecord;
import com.company.makepub.app.domain.MarkupRecord;
import com.company.makepub.app.gateway.*;
import com.company.makepub.app.usecase.epub.ConvertMarkupToHtml;
import com.company.makepub.app.usecase.epub.CreateCover;
import com.company.makepub.app.usecase.epub.EpubCreator;
import com.company.makepub.app.usecase.epub.LinkMusic;
import com.company.makepub.app.usecase.jw.NwtpReader;
import com.company.makepub.app.usecase.jw.TnmReader;
import com.company.makepub.app.usecase.scripture.ConvertScripture;
import com.company.makepub.app.usecase.scripture.LinkScriptures;
import com.company.makepub.app.usecase.scripture.MakeRegex;
import com.company.makepub.app.usecase.scriptureearth.ReadJsonBooks;
import com.company.makepub.app.usecase.scriptureearth.ScriptureEarthReader;
import com.company.makepub.entity.mapper.MarkupMapper;
import com.company.makepub.entity.repository.MarkupRepository;
import com.company.makepub.utils.htmlparser.JsoupHtmlParser;
import com.company.makepub.utils.jsonparser.GsonParser;
import com.company.makepub.utils.linkreader.JavaUrlReader;
import com.company.makepub.utils.requestapi.OkHttpRequestApi;
import com.company.makepub.utils.uuid.MyUUIDGenerator;
import com.company.makepub.view.main.MainView;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.Notifications;
import io.jmix.flowui.component.textarea.JmixTextArea;
import io.jmix.flowui.component.textfield.TypedTextField;
import io.jmix.flowui.component.upload.FileUploadField;
import io.jmix.flowui.download.DownloadFormat;
import io.jmix.flowui.download.Downloader;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.kit.component.upload.event.FileUploadFailedEvent;
import io.jmix.flowui.kit.component.upload.event.FileUploadSucceededEvent;
import io.jmix.flowui.view.*;
import org.checkerframework.checker.units.qual.C;
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
    @ViewComponent
    private TypedTextField<Object> textFieldSubtitulo;
    @ViewComponent
    private TypedTextField<Object> textFieldPeriodo;
    @ViewComponent
    private TypedTextField<Object> textFieldEstudo;
    @ViewComponent
    private JmixButton convertButton;

    @Subscribe(id = "convertButton", subject = "clickListener")
    public void onConvertButtonClick(final ClickEvent<JmixButton> event) {
        var button = convertButton;
        button.setEnabled(false);
        String inputText = textAreaInput.getValue();
        String subtitulo = textFieldSubtitulo.getValue();
        String periodo = textFieldPeriodo.getValue();
        String estudo = textFieldEstudo.getValue();
        List<MarkupRecord> markupRecordList = new ArrayList<>();
        markupRepository.findAll()
                .forEach(m -> markupRecordList.add(
                        new MarkupMapper().to(m)));

        UUIDGenerator uuidGenerator = new MyUUIDGenerator();
        UrlReader javaUrlReader = new JavaUrlReader();
        HtmlParser htmlParser = new JsoupHtmlParser(javaUrlReader);
        ConvertMarkupToHtml markupConversor = new ConvertMarkupToHtml(uuidGenerator, markupRecordList);
        LinkMusic linkMusic = new LinkMusic(htmlParser, uuidGenerator, "DANHOꞌRE");
        MakeRegex makeRegex = new MakeRegex();
        JsonParser<JsonBookRecord> jsonParser = new GsonParser<>();
        UrlReader urlReader = new JavaUrlReader();
        ReadJsonBooks readJsonBooks = new ReadJsonBooks(jsonParser, urlReader);
        RequestApi okHttpRequestApi = new OkHttpRequestApi();
        ConvertScripture convertScripture = new ConvertScripture(okHttpRequestApi);
        ScriptureEarthReader scriptureEarthReader = new ScriptureEarthReader(htmlParser, readJsonBooks, convertScripture);
        NwtpReader nwtpReader = new NwtpReader(htmlParser);
        TnmReader tnmReader = new TnmReader(htmlParser);
        LinkScriptures linkScriptures = new LinkScriptures(makeRegex, uuidGenerator, scriptureEarthReader, nwtpReader, tnmReader);
        CreateCover createCover = new CreateCover();

        EpubFile epubFile = new EpubCreator(
                markupConversor,
                linkMusic,
                linkScriptures,
                inputText,
                createCover
                ).execute(subtitulo, periodo, estudo);

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
        button.setEnabled(true);
    }

}