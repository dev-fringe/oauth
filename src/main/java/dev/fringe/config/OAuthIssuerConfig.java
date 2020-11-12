package dev.fringe.config;

import org.apache.oltu.oauth2.as.issuer.OAuthIssuer;
import org.apache.oltu.oauth2.as.issuer.OAuthIssuerImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OAuthIssuerConfig {

	@Value("${app.privatekey:0123456789abcdef}") String key;
	
	@Bean OAuthIssuer oAuthIssuer() {
		return new OAuthIssuerImpl(new MD5Generator(key.toCharArray()));
	}
}
