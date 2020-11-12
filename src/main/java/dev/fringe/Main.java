package dev.fringe;

import org.apache.oltu.oauth2.as.issuer.OAuthIssuer;
import org.apache.oltu.oauth2.as.request.OAuthTokenRequest;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Import;
import org.springframework.mock.web.MockHttpServletRequest;

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
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setParameter("code", "authorizationCode");
		request.setParameter("grant_type", "authorization_code");
		request.setParameter("client_secret", "1232");
		request.setParameter("redirect_uri", "http://localhost:8081/oauth/client/accessToken");
		request.setParameter("client_id", "clientId");
		request.setMethod("POST");
		request.setContentType("application/x-www-form-urlencoded");
		OAuthTokenRequest oauthRequest = new OAuthTokenRequest(request);
	}
	public String refreshToken() throws OAuthSystemException {
		return oAuthIssuer.refreshToken();
	}
	public String accessToken() throws OAuthSystemException {
		return oAuthIssuer.accessToken();
	}


}
