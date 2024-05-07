package org.example.be_java_hisp_w26_g04.util.crud.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class CustomMapper {

    public static <T> T mapper(Object source, Class<T> destinationClass) {
        ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
        return objectMapper.convertValue(source, destinationClass);
    }
}
