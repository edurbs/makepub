package com.company.makepub.utils.uuid;

import com.company.makepub.app.gateway.UUIDGenerator;

import java.util.UUID;

public class MyUUIDGenerator implements UUIDGenerator {

    public String generate() {
        return UUID.randomUUID().toString();
    }
}
