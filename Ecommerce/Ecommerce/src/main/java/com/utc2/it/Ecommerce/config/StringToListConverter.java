package com.utc2.it.Ecommerce.config;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.databind.util.Converter;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class StringToListConverter implements Converter<String, List<Long>> {

    private final ObjectMapper objectMapper;

    public StringToListConverter(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public List<Long> convert(String source) {
        try {
            return objectMapper.readValue(source, new TypeReference<List<Long>>() {});
        } catch (IOException e) {
            // Xử lý lỗi chuyển đổi
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public JavaType getInputType(TypeFactory typeFactory) {
        return null;
    }

    @Override
    public JavaType getOutputType(TypeFactory typeFactory) {
        return null;
    }
}