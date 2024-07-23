package com.company.makepub.utils.jsonparser;

import com.company.makepub.app.gateway.JsonParser;
import com.google.gson.Gson;

import java.util.List;

public class GsonParser<T> implements JsonParser<T>  {
    @Override
    public List<T> parse(String json, Class<T[]> clazz) {
        if(json == null || json.isBlank()) {
            return List.of();
        }
        try {
            T[] array = new Gson().fromJson(json, clazz);
            return List.of(array);
        } catch (Exception e) {
            return List.of();
        }
    }
}