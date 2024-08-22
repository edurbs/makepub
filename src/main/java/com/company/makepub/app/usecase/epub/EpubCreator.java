package com.company.makepub.app.usecase.epub;

import com.company.makepub.app.domain.EpubFile;
import com.company.makepub.app.usecase.exceptions.UseCaseException;
import com.company.makepub.app.usecase.scripture.LinkScriptures;
import com.company.makepub.app.usecase.types.EpubMap;
import com.company.makepub.app.usecase.types.LinkReferencePage;
import com.company.makepub.app.usecase.types.StringConversor;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class EpubCreator {

    private final StringConversor markupConversor;
    private final LinkReferencePage linkMusic;
    private final LinkScriptures linkScriptures;
    private final String mainText;
    private final Map<EpubMap, String> finalEpubMap = new HashMap<>();
    private final CreateCover createCover;

    public EpubCreator(StringConversor markupConversor, LinkReferencePage linkMusic, LinkScriptures linkScriptures, String mainText, CreateCover createCover) {
        this.markupConversor = markupConversor;
        this.linkMusic = linkMusic;
        this.linkScriptures = linkScriptures;
        this.mainText = mainText;
        this.createCover = createCover;
    }

    public EpubFile execute(){
        String convertedText = markupConversor.convert(mainText);
        String linkedTextWithMusic = createLinkedMusic(convertedText);
        String linkedTextWithMusicAndScriptures = createLinkedScritpures(linkedTextWithMusic);
        finalEpubMap.put(EpubMap.TEXT, linkedTextWithMusicAndScriptures);
        createOtherEpubPages();
        return createEpubFile();
    }

    private void createOtherEpubPages() {
        finalEpubMap.put(EpubMap.CONTENT, EpubMap.CONTENT.getDefaultText());
        finalEpubMap.put(EpubMap.COVER, EpubMap.COVER.getDefaultText());
        finalEpubMap.put(EpubMap.NAV, EpubMap.NAV.getDefaultText());
        finalEpubMap.put(EpubMap.STYLE, EpubMap.STYLE.getDefaultText());
    }

    private String createLinkedScritpures(String linkedTextWithMusic) {
        return linkScriptures.execute(linkedTextWithMusic);
    }

    private String createLinkedMusic(String convertedText) {
        Map<EpubMap, String> musicMap = linkMusic.execute(convertedText);
        finalEpubMap.put(EpubMap.MUSIC, musicMap.get(EpubMap.MUSIC));
        return musicMap.get(EpubMap.TEXT);
    }

    private EpubFile createEpubFile() {
        String zipFilename = "file.epub";
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] image = createCover.execute("Roꞌmadöꞌöꞌꞌwa", "Jeová nhorõwa ꞌre, ꞌre aihöimana uꞌötsi mono!", "19-25 Agosto na 2024 na", "Romnhoré 24:");
        try (ZipOutputStream zos = new ZipOutputStream(baos)) {
            for(EpubMap epubMap : EpubMap.values()){
                switch (epubMap) {
                    case EpubMap.IMAGE -> addByteArrayToZip(zos, epubMap.getPath(), image);
                    case EpubMap.TEXT, EpubMap.MUSIC-> addStringToZip(zos, epubMap.getPath(), finalEpubMap.get(epubMap));
                    default -> addStringToZip(zos, epubMap.getPath(), epubMap.getDefaultText());
                }
            }
        } catch (IOException e) {
            throw new UseCaseException("Não foi possível criar arquivo zip", e.getCause());
        }
        return new EpubFile(zipFilename, baos.toByteArray());
    }
    private static void addStringToZip(ZipOutputStream zos, String fileName, String content) throws IOException {
        ZipEntry zipEntry = new ZipEntry(fileName);
        zos.putNextEntry(zipEntry);

        byte[] bytes = content.getBytes(StandardCharsets.UTF_8);
        InputStream is = new ByteArrayInputStream(bytes);
        byte[] buffer = new byte[1024];
        int length;
        while ((length = is.read(buffer)) >= 0) {
            zos.write(buffer, 0, length);
        }
        is.close();
        zos.closeEntry();
    }

    private static void addByteArrayToZip(ZipOutputStream zos, String fileName, byte[] content) throws IOException {
        ZipEntry zipEntry = new ZipEntry(fileName);
        zos.putNextEntry(zipEntry);

        InputStream is = new ByteArrayInputStream(content);
        byte[] buffer = new byte[1024];
        int length;
        while ((length = is.read(buffer)) >= 0) {
            zos.write(buffer, 0, length);
        }
        is.close();
        zos.closeEntry();
    }
}
