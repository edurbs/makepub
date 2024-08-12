package com.company.makepub.app.usecase.scripture;

import com.company.makepub.app.gateway.RequestApi;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class ConvertScriptureTest {

    @Mock
    private RequestApi mockRequestApi;

    private ConvertScripture convertScripture;

    @BeforeEach
    public void setUp() {
        convertScripture = new ConvertScripture(mockRequestApi);
    }

    @Test
    void execute() {
        String given = "some text";
        String expected = "another text";
        Mockito.when(mockRequestApi.get(Mockito.any(), Mockito.any())).thenReturn(expected);
        assertEquals("another text", convertScripture.execute(given));
    }
}