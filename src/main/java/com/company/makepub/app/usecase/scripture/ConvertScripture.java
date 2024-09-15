package com.company.makepub.app.usecase.scripture;

import com.company.makepub.app.gateway.RequestApi;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ConvertScripture {

    
    private final RequestApi requestApi;

    public String execute(final String text) {
        String apiUrl = "https://conversorxv.eduardo.soares.nom.br/rest/services/convert/execute";
        return requestApi.get(apiUrl, text);
    }

}


