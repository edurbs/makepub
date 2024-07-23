package com.company.makepub.app.gateway;

import com.company.makepub.app.usecase.exceptions.UseCaseException;

public interface UrlReader {
    String execute(String link) throws UseCaseException;
}
