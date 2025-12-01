package com.program.manager;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class FileManagerTest {

    @TempDir
    Path tempDir;

    @Test
    void testSaveAsJsonAndLoad() {
        FileManager fileManager = new FileManager(new ObjectMapper());

        TestObj obj = new TestObj();
        obj.name = "Hello";
        obj.value = 42;

        File file = tempDir.resolve("test.json").toFile();
        String filePath = file.getAbsolutePath();

        fileManager.saveAsJson(filePath, obj);

        TestObj loaded = fileManager.loadFromJson(filePath, TestObj.class);

        assertNotNull(loaded);
        assertEquals("Hello", loaded.name);
        assertEquals(42, loaded.value);
    }

    @Test
    void testLoadInvalidJsonReturnsNull() {
        FileManager fileManager = new FileManager(new ObjectMapper());

        File file = tempDir.resolve("broken.json").toFile();

        try {
            java.nio.file.Files.writeString(file.toPath(), "NOT A JSON");
        } catch (Exception e) {
            fail(e);
        }

        TestObj loaded = fileManager.loadFromJson(file.getAbsolutePath(), TestObj.class);

        assertNull(loaded);
    }

    static class TestObj {
        public String name;
        public int value;

        public TestObj() {}
    }
}
