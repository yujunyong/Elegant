package org.elegant.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.jooq.conf.Settings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.event.ApplicationEventMulticaster;
import org.springframework.context.event.SimpleApplicationEventMulticaster;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.datetime.standard.DateTimeFormatterRegistrar;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import reactor.core.publisher.Hooks;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.Executors;

@Configuration
public class AppConfig implements WebFluxConfigurer, InitializingBean {
    private static final Logger logger = LoggerFactory.getLogger(AppConfig.class);
    private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Bean
    @Primary
    public ObjectMapper jacksonObjectMapper(Jackson2ObjectMapperBuilder builder) {
        JavaTimeModule module = new JavaTimeModule();
        module.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(dtf));

        return builder.modules(module).createXmlMapper(false).build();
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        var r = new DateTimeFormatterRegistrar();
        r.setDateFormatter(dtf);
        r.setTimeFormatter(dtf);
        r.setDateTimeFormatter(dtf);

        r.registerFormatters(registry);
    }

    @Bean
    public Settings settings() {
        Settings settings = new Settings();
        settings.setRenderCatalog(false);
        settings.setRenderSchema(false);
        settings.setMapJPAAnnotations(false);
        settings.setQueryTimeout(10);
        settings.setFetchSize(1000);
        return settings;
    }

    @Bean
    public ApplicationEventMulticaster applicationEventMulticaster() {
        SimpleApplicationEventMulticaster eventMulticaster
                = new SimpleApplicationEventMulticaster();

        eventMulticaster.setTaskExecutor(Executors.newCachedThreadPool());
        return eventMulticaster;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Hooks.onOperatorError((t, d) -> {
            logger.error("have a error", t);
            return t;
        });
    }
}
