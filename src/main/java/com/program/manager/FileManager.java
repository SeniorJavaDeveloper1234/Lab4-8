package com.program.manager;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;

public class FileManager {

    private static final Logger logger = LogManager.getLogger(FileManager.class);

    private final ObjectMapper mapper;

    public FileManager(){
        this.mapper = new ObjectMapper();
        logger.info("Створено FileManager з ObjectMapper за замовчуванням");
    }

    public FileManager(ObjectMapper mapper) {
        this.mapper = mapper;
        logger.info("Створено FileManager з переданим ObjectMapper");
    }

    public void saveAsJson(String path, Object obj) {
        try {
            mapper.writeValue(new File(path), obj);
            logger.info("Обʼєкт успішно збережено у файл {}", path);
        } catch (IOException e) {
            logger.error("Помилка збереження обʼєкта у файл {}", path, e);
        }
    }

    public <T> T loadFromJson(String path, Class<T> clazz) {
        try {
            T result = mapper.readValue(new File(path), clazz);
            logger.info("Обʼєкт успішно завантажено з файлу {}", path);
            return result;
        } catch (IOException e) {
            logger.error("Помилка завантаження обʼєкта з файлу {}", path, e);
            return null;
        }
    }
}
