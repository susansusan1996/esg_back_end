package com.esg.component;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 取 public key的名稱
 */
@Component
@ConfigurationProperties(prefix = "url")
public class UrlComponent {

    private String corsUrl;

    public String getCorsUrl() {
        return corsUrl;
    }

    public void setCorsUrl(String corsUrl) {
        this.corsUrl = corsUrl;
    }
}
