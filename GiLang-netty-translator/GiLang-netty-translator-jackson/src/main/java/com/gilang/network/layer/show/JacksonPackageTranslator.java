package com.gilang.network.layer.show;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.lang.reflect.Type;

/**
 * @author gylang
 * data 2022/6/28
 */
@SuppressWarnings({"unchecked", "rawtypes"})
public class JacksonPackageTranslator implements PackageTranslator {

    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public Object toObject(byte[] bs, Type type) {
        try {
            return mapper.readValue(bs, (Class) type);
        } catch (IOException e) {
            throw new RuntimeException("Jackson deserialize exception : " + e.getMessage());
        }
    }

    @Override
    public byte[] toByte(Object object) {
        try {
            return mapper.writeValueAsBytes(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Jackson serialize exception : " + e.getMessage());

        }
    }
}