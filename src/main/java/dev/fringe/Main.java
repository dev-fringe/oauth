package dev.fringe;

import org.apache.oltu.oauth2.as.issuer.OAuthIssuer;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Import;

import dev.fringe.config.OAuthIssuerConfig;

@Import(OAuthIssuerConfig.class)
public class Main implements InitializingBean{
	
	@Autowired OAuthIssuer oAuthIssuer;
	
	public static void main(String[] args) throws OAuthSystemException {
		new AnnotationConfigApplicationContext(Main.class);
	}

	public void afterPropertiesSet() throws Exception {
		System.out.println(refreshToken());
		System.out.println(accessToken());
	}
	public String refreshToken() throws OAuthSystemException {
		return oAuthIssuer.refreshToken();
	}
	public String accessToken() throws OAuthSystemException {
		return oAuthIssuer.accessToken();
	}


}
