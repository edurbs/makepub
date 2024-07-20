package com.company.makepub.app.usecase;

import com.company.makepub.app.gateway.UUIDGenerator;

import java.util.UUID;

public class myUUIDGenerator implements UUIDGenerator {

    public String generate() {
        return UUID.randomUUID().toString();
    }
}
