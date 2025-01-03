package com.esiran.greenpay.admin.config;

import com.esiran.greenpay.common.util.IdWorker;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    private final BaseInterceptor baseInterceptor;
    public WebMvcConfig(BaseInterceptor baseInterceptor) {
        this.baseInterceptor = baseInterceptor;
    }

    @Bean
    public IdWorker idWorker(){
        return new IdWorker(1,1,1);
    }


    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowCredentials(true)
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .maxAge(3600);
    }
    @Bean
    public ObjectMapper objectMapper(){
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        objectMapper.disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE);
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        javaTimeModule.addSerializer(LocalDateTime.class,
                new LocalDateTimeSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        javaTimeModule.addDeserializer(LocalDateTime.class,
                new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        objectMapper.registerModule(javaTimeModule).registerModule(new ParameterNamesModule());
        return objectMapper;
    }

    // @Bean
    // public DelayQueueTaskRegister delayQueueTaskRegister(){
    //     DelayQueueTaskRegister delayQueueTaskRegister = new DelayQueueTaskRegister();
    //     delayQueueTaskRegister.register("order:expire",orderExpireTaskRunner);
    //     delayQueueTaskRegister.register("order:acpay",orderACPayTaskRunner);
    //     delayQueueTaskRegister.register("order:notify",orderNotifyTaskRunner);
    //     delayQueueTaskRegister.register("agentpay:query",agentPayQueryTaskRunner);
    //     delayQueueTaskRegister.register("supplement:query",supplementQueryTaskRunner);
    //     return delayQueueTaskRegister;
    // }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(baseInterceptor).addPathPatterns("/**");
    }
}
