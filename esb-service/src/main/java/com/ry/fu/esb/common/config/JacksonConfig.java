package com.ry.fu.esb.common.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ry.fu.esb.common.jackson.JacksonBeanSerializerModifier;
import com.ry.fu.esb.common.jackson.SerializerFeature;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.nio.charset.Charset;

/**
 * @Author: telly
 * @Description:
 * @Date: Create in 18:02 2018/1/29
 */
@Configuration
public class JacksonConfig {

    @Bean
    public ObjectMapper jacksonObjectMapper(Jackson2ObjectMapperBuilder builder) {
        ObjectMapper objectMapper = builder.createXmlMapper(false).build();
        objectMapper.setSerializerFactory(objectMapper.getSerializerFactory()
                .withSerializerModifier(
                        new JacksonBeanSerializerModifier(
                                SerializerFeature.WriteNullListAsEmpty,
                                SerializerFeature.WriteNullStringAsEmpty,
                                SerializerFeature.WriteNullNumberAsZero,
                                SerializerFeature.WriteNullBooleanAsFalse
                        )));


//        /**
//         * 通过该方法对mapper对象进行设置，所有序列化的对象都将按改规则进行系列化
//         * Include.Include.ALWAYS 默认
//         * Include.NON_DEFAULT 属性为默认值不序列化
//         * Include.NON_EMPTY 属性为 空（""） 或者为 NULL 都不序列化，则返回的json是没有这个字段的。这样对移动端会更省流量
//         * Include.NON_NULL 属性为NULL 不序列化
//         */
//        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
//
//        // 字段保留，将null值转为""
//        objectMapper.getSerializerProvider().setNullValueSerializer(new JsonSerializer<Object>() {
//            @Override
//            public void serialize(Object o, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
//                jsonGenerator.writeString("");
//            }
//        });
        return objectMapper;
    }

    @Bean
    public MappingJackson2HttpMessageConverter getMappingJackson2HttpMessageConverter(ObjectMapper objectMapper) {
        MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
        mappingJackson2HttpMessageConverter.setObjectMapper(objectMapper);
//        List <MediaType> supportedMediaTypes = new ArrayList <>();
//        supportedMediaTypes.add(MediaType.APPLICATION_ATOM_XML);
//        supportedMediaTypes.add(MediaType.APPLICATION_FORM_URLENCODED);
//        supportedMediaTypes.add(MediaType.APPLICATION_OCTET_STREAM);
//        supportedMediaTypes.add(MediaType.APPLICATION_PDF);
//        supportedMediaTypes.add(MediaType.APPLICATION_RSS_XML);
//        supportedMediaTypes.add(MediaType.APPLICATION_XHTML_XML);
//        supportedMediaTypes.add(MediaType.APPLICATION_XML);
//        supportedMediaTypes.add(MediaType.IMAGE_GIF);
//        supportedMediaTypes.add(MediaType.IMAGE_JPEG);
//        supportedMediaTypes.add(MediaType.IMAGE_PNG);
//        supportedMediaTypes.add(MediaType.TEXT_EVENT_STREAM);
//        supportedMediaTypes.add(MediaType.TEXT_HTML);
//        supportedMediaTypes.add(MediaType.TEXT_MARKDOWN);
//        supportedMediaTypes.add(MediaType.TEXT_PLAIN);
//        supportedMediaTypes.add(MediaType.TEXT_XML);
//        mappingJackson2HttpMessageConverter.setSupportedMediaTypes(supportedMediaTypes);
        mappingJackson2HttpMessageConverter.setDefaultCharset(Charset.forName("UTF-8"));
        return mappingJackson2HttpMessageConverter;
    }


}
