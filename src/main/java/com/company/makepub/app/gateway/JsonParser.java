package com.company.makepub.app.gateway;

import java.util.List;

public interface JsonParser <T> {
    List<T> parse(String json, Class<T[]> clazz);
}
