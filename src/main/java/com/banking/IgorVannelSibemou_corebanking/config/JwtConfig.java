package com.banking.IgorVannelSibemou_corebanking.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtConfig {

    @Value("${jwt.secret:defaultSecretKeyToChangeInProduction}")
    private String secret;

    @Value("${jwt.expiration:86400000}") // 24 heures par défaut
    private long expiration;

    @Value("${jwt.header:Authorization}")
    private String header;

    @Value("${jwt.prefix:Bearer }")
    private String prefix;

    public String getSecret() {
        return secret;
    }

    public long getExpiration() {
        return expiration;
    }

    public String getHeader() {
        return header;
    }

    public String getPrefix() {
        return prefix;
    }
}