package com.program.manager;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;

public class FileManager {





    private  final ObjectMapper mapper;

    public FileManager(){
        this.mapper = new ObjectMapper();
    }

    public FileManager(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    public void saveAsJson(String path, Object obj) {
        try {
            mapper.writeValue(new File(path), obj);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public <T> T loadFromJson(String path, Class<T> clazz) {
        try {
            return mapper.readValue(new File(path), clazz);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
