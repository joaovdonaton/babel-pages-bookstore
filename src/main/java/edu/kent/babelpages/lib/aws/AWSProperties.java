package edu.kent.babelpages.lib.aws;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("/aws.properties")
@ConfigurationProperties(prefix = "aws")
@Data
public class AWSProperties {
    private String bucketName;
    private String profilePicturesPrefix;
    private String region;
}
