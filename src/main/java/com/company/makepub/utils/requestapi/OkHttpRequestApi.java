package com.company.makepub.utils.requestapi;

import com.company.makepub.app.gateway.RequestApi;
import com.company.makepub.app.usecase.exceptions.UseCaseException;
import okhttp3.*;

import java.io.IOException;

public class OkHttpRequestApi implements RequestApi {
    @Override
    public String get(String apiUrl, String textToConvert) throws UseCaseException {
        if(textToConvert == null || textToConvert.isBlank()) {
            return "";
        }
        String requestBody = """
                {
                    "text" : "%s"
                }
                """.formatted(textToConvert);
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(apiUrl)
                .post(RequestBody.create(requestBody, MediaType.get("application/json")))
                .build();
        try (Response response = client.newCall(request).execute()) {
            if (response.body() != null) {
                return response.body().string();
            }
        } catch (IOException e) {
            throw new UseCaseException("Erro ao consultar a API: " + apiUrl, e.getCause());
        }
        return textToConvert;
    }
}
