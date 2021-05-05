package com.flaconi.shipping.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "warehouse")
@Data
public class CutOffConfig {

    private String cutOffTime;
}
