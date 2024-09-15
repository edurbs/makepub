package com.company.makepub.app.utils.uuid;

import com.company.makepub.app.gateway.UUIDGenerator;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class MyUUIDGenerator implements UUIDGenerator {

    public String generate() {
        return UUID.randomUUID().toString();
    }
}
