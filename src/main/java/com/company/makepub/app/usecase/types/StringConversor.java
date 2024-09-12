package com.company.makepub.app.usecase.types;

import jakarta.annotation.Nonnull;

public interface StringConversor {
    @Nonnull String convert(String text);
}
