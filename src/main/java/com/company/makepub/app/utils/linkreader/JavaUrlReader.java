package com.company.makepub.app.utils.linkreader;

import com.company.makepub.app.gateway.UrlReader;
import com.company.makepub.app.usecase.exceptions.UseCaseException;
import jakarta.annotation.Nonnull;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;

@Component
public class JavaUrlReader implements UrlReader {
    @Override
    @Nonnull
    public String execute(@Nonnull String urlAddress) throws UseCaseException {
        URL url;
        try {
            url = new URI(urlAddress).toURL();
        } catch (Exception e) {
            throw new UseCaseException("Não foi possível abrir o url", e.getCause());
        }
        try (InputStream in = url.openStream()) {
            return  new String(in.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new UseCaseException("Não foi possível ler o url", e.getCause());
        }
    }
}
