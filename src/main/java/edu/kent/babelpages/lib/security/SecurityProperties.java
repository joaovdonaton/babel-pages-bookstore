package edu.kent.babelpages.lib.security;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("/security.properties")
@ConfigurationProperties(prefix = "security")
@Data
public class SecurityProperties {
    private String jwtSecret;
    private String jwtIssuer;
    private String jwtExpiration;
    private String jwtHeaderPrefix;
    private boolean devMode;
    private String devAdminUsername;
    private String devAdminPassword;
    private String corsClientOrigin;
}
