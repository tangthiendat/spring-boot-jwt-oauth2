package com.ttdat.springbootjwtoauth2.config;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@ConfigurationProperties(prefix = "application.security.rsa")
public class RSAKeyRecord {
    RSAPrivateKey rsaPrivateKey;
    RSAPublicKey rsaPublicKey;
}
