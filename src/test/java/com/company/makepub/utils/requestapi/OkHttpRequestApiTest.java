package com.company.makepub.utils.requestapi;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OkHttpRequestApiTest {

    @Test
    @DisplayName("Should convert the text")
    void get() {
        String text = "Zezu 2";
        String actual = new OkHttpRequestApi().get("https://conversorxv.eduardo.soares.nom.br/rest/services/convert/execute", text);
        String expected = "Jesus 2";
        assertEquals(expected, actual);
    }
}