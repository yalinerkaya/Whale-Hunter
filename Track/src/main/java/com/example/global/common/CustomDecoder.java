package com.example.global.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import feign.codec.Decoder;
import feign.jackson.JacksonDecoder;
import org.apache.commons.lang.StringUtils;
import org.springframework.cloud.openfeign.support.ResponseEntityDecoder;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;

/**
 * packageName    : com.example.global.common
 * fileName       : CustomDecoder
 * author         : Jay
 * date           : 2023-08-20
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023-08-20        Jay       최초 생성
 */
public class CustomDecoder implements Decoder {
    private final Decoder decoder;
    private final ObjectMapper objectMapper;

    public CustomDecoder() {
        this.decoder = new ResponseEntityDecoder(new JacksonDecoder());
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public Object decode(Response response, Type type) throws IOException {
        if (type instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) type;
            Type rawType = parameterizedType.getRawType();
            if (rawType.equals(CommonResponse.class)) {
                Class<?> responseType = (Class<?>) parameterizedType.getActualTypeArguments()[0];
                return convertToCommonResponse(response);
            }
        }
        return decoder.decode(response, type);
    }

    private Object convertToCommonResponse(Response response) throws IOException {
        Object body = objectMapper.readValue(response.body().asInputStream(), Object.class);

        Result<Object> responseBody = new Result<>();

        if (body instanceof Map) {
            Map<String, Object> map = (Map<String, Object>) body;
            responseBody.setData((map.containsKey("data")) ? map.get("data") : null);
            responseBody.setMessage(StringUtils.defaultString(String.valueOf(map.get("message")), ""));
        } else {
            responseBody.setData(body);
        }

        return new CommonResponse<>(responseBody);
    }
}
