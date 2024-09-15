package com.company.makepub.app.domain;

import org.springframework.lang.Nullable;

public record BookAddress(@Nullable Book book, @Nullable String url) {
}
