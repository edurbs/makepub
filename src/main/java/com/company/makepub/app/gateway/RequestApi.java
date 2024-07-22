package com.company.makepub.app.gateway;

import com.company.makepub.app.usecase.exceptions.UseCaseException;

public interface RequestApi {
    String get(String apiUrl, String textToConvert) throws UseCaseException;
}
