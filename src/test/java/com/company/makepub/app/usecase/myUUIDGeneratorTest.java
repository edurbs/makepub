package com.company.makepub.app.usecase;

import com.company.makepub.app.gateway.UUIDGenerator;

public class myUUIDGeneratorTest implements UUIDGenerator {
    private int number = 0;
    @Override
    public String generate() {
        number++;
        return String.valueOf(122+number);
    }
}
