package edu.kent.babelpages.lib.aws;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.services.s3.S3Client;


@Configuration
public class S3Config {
    /*
    Will read region, access key and secret from default "aws configure" command configuration
     */
    @Bean
    public S3Client s3Client() {
        return S3Client.builder().build();
    }
}
