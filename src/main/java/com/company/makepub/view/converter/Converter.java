package com.company.makepub.view.converter;


import com.company.makepub.app.domain.EpubFile;
import com.company.makepub.app.usecase.epub.EpubCreator;
import com.company.makepub.view.main.MainView;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.Dialogs;
import io.jmix.flowui.Notifications;
import io.jmix.flowui.backgroundtask.BackgroundTask;
import io.jmix.flowui.backgroundtask.BackgroundTaskHandler;
import io.jmix.flowui.backgroundtask.BackgroundWorker;
import io.jmix.flowui.backgroundtask.TaskLifeCycle;
import io.jmix.flowui.component.textarea.JmixTextArea;
import io.jmix.flowui.component.textfield.TypedTextField;
import io.jmix.flowui.download.DownloadFormat;
import io.jmix.flowui.download.Downloader;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.view.*;
import jakarta.annotation.Nonnull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.TimeUnit;


@Route(value = "Converter", layout = MainView.class)
@ViewController("Converter")
@ViewDescriptor("Converter.xml")
public class Converter extends StandardView {

    @ViewComponent
    private JmixTextArea textAreaInput;

    @Autowired
    private Notifications notifications;

    @Autowired
    private Downloader downloader;

    @ViewComponent
    private TypedTextField<Object> textFieldSubtitulo;
    @ViewComponent
    private TypedTextField<Object> textFieldPeriodo;
    @ViewComponent
    private TypedTextField<Object> textFieldEstudo;
    @ViewComponent
    private JmixButton convertButton;

    @Autowired
    protected BackgroundWorker backgroundWorker;
    protected BackgroundTaskHandler<Void> taskHandler;
    @Autowired
    private Dialogs dialogs;

    Logger log = LoggerFactory.getLogger(Converter.class);

    @Autowired
    private EpubCreator epubCreator;

    @Subscribe(id = "convertButton", subject = "clickListener")
    public void onConvertButtonClick(final ClickEvent<JmixButton> event) {
        convertButton.setEnabled(false);
        BackgroundTask<Integer, Void> task = new ConversionTask();
        dialogs.createBackgroundTaskDialog(task)
                .withHeader("Gerando EPUB")
                .withText("Por favor aguarde...")
                .open();
    }

    private class ConversionTask extends BackgroundTask<Integer, Void> {
        private EpubFile epubFile;
        public ConversionTask() {
            super(10, TimeUnit.MINUTES, Converter.this);
        }

        @Override
        public void done(@Nonnull Void result) {
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
            convertButton.setEnabled(true);
        }

        @Override
        @Nonnull
        public Void run(@Nonnull TaskLifeCycle<Integer> taskLifeCycle) {
            String inputText = textAreaInput.getValue();
            log.info(inputText);
            String subtitulo = textFieldSubtitulo.getValue();
            String periodo = textFieldPeriodo.getValue();
            String estudo = textFieldEstudo.getValue();
            //UUIDGenerator uuidGenerator = new MyUUIDGenerator();
            //UrlReader javaUrlReader = new JavaUrlReader();
            //HtmlParser htmlParser = new JsoupHtmlParser(javaUrlReader);
            //ConvertMarkupToHtml markupConversor = new ConvertMarkupToHtml(uuidGenerator);
            //LinkMusic linkMusic = new LinkMusic(htmlParser, uuidGenerator);
            //LinkScriptures linkScriptures = getLinkScriptures(htmlParser, uuidGenerator);
            //CreateCover createCover = new CreateCover();
//            epubFile = new EpubCreator(
//                    markupConversor,
//                    linkMusic,
//                    linkScriptures,
//                    inputText,
//                    createCover
//            )
            epubFile = epubCreator.execute(inputText,subtitulo, periodo, estudo);
            return null;
        }
    }




}