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

        // Create a test object
        TestObj obj = new TestObj();
        obj.name = "Hello";
        obj.value = 42;

        // Path for saving
        File file = tempDir.resolve("test.json").toFile();
        String filePath = file.getAbsolutePath();

        // Save
        fileManager.saveAsJson(filePath, obj);

        // Load
        TestObj loaded = fileManager.loadFromJson(filePath, TestObj.class);

        assertNotNull(loaded);
        assertEquals("Hello", loaded.name);
        assertEquals(42, loaded.value);
    }

    @Test
    void testLoadInvalidJsonReturnsNull() {
        FileManager fileManager = new FileManager(new ObjectMapper());

        // Create invalid file
        File file = tempDir.resolve("broken.json").toFile();

        // Write invalid content
        try {
            java.nio.file.Files.writeString(file.toPath(), "NOT A JSON");
        } catch (Exception e) {
            fail(e);
        }

        // Should catch exception internally and return null
        TestObj loaded = fileManager.loadFromJson(file.getAbsolutePath(), TestObj.class);

        assertNull(loaded);
    }

    // Helper object for serialization
    static class TestObj {
        public String name;
        public int value;

        public TestObj() {}
    }
}
