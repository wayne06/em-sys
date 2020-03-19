package xyz.qzpx.em.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.TimeZone;

@SpringBootConfiguration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("*")
                .allowedHeaders("*");
    }

    //@Override
    //public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
    //    MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
    //    ObjectMapper objectMapper = converter.getObjectMapper();
    //    // 生成JSON时,将所有Long转换成String
    //    SimpleModule simpleModule = new SimpleModule();
    //    simpleModule.addSerializer(Long.class, ToStringSerializer.instance);
    //    simpleModule.addSerializer(Long.TYPE, ToStringSerializer.instance);
    //    objectMapper.registerModule(simpleModule);
    //    // 时间格式化
    //    objectMapper.setTimeZone(TimeZone.getTimeZone("GMT+0800"));
    //    objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    //    //objectMapper.setDateFormat(new SimpleDateFormat("HH:mm"));
    //    // 设置格式化内容
    //    converter.setObjectMapper(objectMapper);
    //    converters.add(0, converter);
    //}
}
