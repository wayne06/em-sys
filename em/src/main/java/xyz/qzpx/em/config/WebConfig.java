package xyz.qzpx.em.config;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import xyz.qzpx.em.interceptor.LoginInterceptor;

@SpringBootConfiguration
public class WebConfig implements WebMvcConfigurer {

    @Bean
    public LoginInterceptor getLoginInterceptor() {
        return new LoginInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(getLoginInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/api/user/register")
                .excludePathPatterns("/api/user/login")
                .excludePathPatterns("/api/user/logout")
                .excludePathPatterns("/api/user/authentication");
    }


    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowCredentials(true)
                //.allowedOrigins("http://139.159.183.141")
                .allowedOrigins("http://localhost:8080")
                .allowedMethods("POST", "GET", "PUT", "OPTIONS", "DELETE")
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
