package com.company.makepub.utils.uuid;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class MyUUIDGeneratorTest {

    @Test
    @DisplayName("Should generate UUID")
    void generate() {
        MyUUIDGenerator myUUIDGenerator = new MyUUIDGenerator();
        String uuid = myUUIDGenerator.generate();
        assertDoesNotThrow( () ->(UUID.fromString(uuid) ));
    }
}