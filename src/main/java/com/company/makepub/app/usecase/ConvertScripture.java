package com.company.makepub.app.usecase;

import com.company.makepub.app.gateway.RequestApi;

public class ConvertScripture {

    private final RequestApi requestApi;

    public ConvertScripture(RequestApi requestApi) {
        this.requestApi = requestApi;
    }

    public String execute(final String text) {
        String apiUrl = "https://conversorxv.eduardo.soares.nom.br/rest/services/convert/execute";
        return requestApi.get(apiUrl, text);
    }

}


