package edu.kent.babelpages.lib.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.util.Arrays;

@Configuration
public class JacksonConfig {
    /*
    See https://github.com/swagger-api/swagger-ui/issues/6462#issuecomment-929189296

    Had to use workaround from this comment, could not get swagger to properly send the curl request with the application/json content-type.
    This enables Jackson to convert JSON data into an object, even if it is an octet-stream
     */
    @Bean
    public MappingJackson2HttpMessageConverter octetStreamJsonConverter() {
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setSupportedMediaTypes(Arrays.asList(new MediaType("application", "json"),
                new MediaType("application", "octet-stream")));
        return converter;
    }
}
