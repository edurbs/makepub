package com.company.makepub.app.utils.jsonparser;

import com.company.makepub.app.gateway.JsonParser;
import com.google.gson.Gson;
import jakarta.annotation.Nonnull;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GsonParser<T> implements JsonParser<T>  {
    @Override
    @Nonnull
    public List<T> parse(@Nonnull String json, @Nonnull Class<T[]> clazz) {
        if(json.isBlank()) {
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
