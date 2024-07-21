package com.company.makepub.app.usecase.types;

import java.util.Map;

public interface LinkReferencePage {
    Map<EpubMap, String> execute(String text);
}
