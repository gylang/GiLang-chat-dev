package com.gilang.network.layer.show;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ClassUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StringDeserializer;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;

/**
 * @author gylang
 * data 2022/6/28
 */
@SuppressWarnings({"unchecked", "rawtypes"})
@TranslatorType(0b0010000)
public class JacksonPackageTranslator implements PackageTranslator {

    public static final ObjectMapper mapper = new ObjectMapper();
    static {
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addSerializer(Long.class, ToStringSerializer.instance);
        simpleModule.addSerializer(Long.TYPE, ToStringSerializer.instance);
        mapper.registerModule(simpleModule);
    }


    @Override
    public Object toObject(byte[] bs, Type type) {
        try {
            if (ArrayUtil.isEmpty(bs)) {
                return null;
            }
            if (ClassUtil.isSimpleValueType((Class<?>) type)) {
                return Convert.convert(type, new String(bs, StandardCharsets.UTF_8));
            }
            return mapper.readValue(bs, (Class) type);
        } catch (IOException e) {
            throw new RuntimeException("Jackson deserialize exception : " + e.getMessage());
        }
    }

    @Override
    public byte[] toByte(Object object) {
        try {
            if (null == object) {
                return new byte[0];
            }
            if (ClassUtil.isSimpleValueType((object.getClass()))) {
                return Convert.convert(String.class, object).getBytes(StandardCharsets.UTF_8);
            }
            return mapper.writeValueAsBytes(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Jackson serialize exception : " + e.getMessage());

        }
    }
}
