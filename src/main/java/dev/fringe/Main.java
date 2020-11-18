package dev.fringe;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletResponse;

import org.apache.oltu.oauth2.as.issuer.OAuthIssuer;
import org.apache.oltu.oauth2.as.request.OAuthTokenRequest;
import org.apache.oltu.oauth2.as.response.OAuthASResponse;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.OAuthResponse;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Import;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import dev.fringe.config.OAuthIssuerConfig;

@Import(OAuthIssuerConfig.class)
public class Main implements InitializingBean{
	
	@Autowired OAuthIssuer oAuthIssuer;
	
	public static void main(String[] args) throws OAuthSystemException {
		new AnnotationConfigApplicationContext(Main.class);
	}

	public void afterPropertiesSet() throws Exception {
//		System.out.println(refreshToken());
//		System.out.println(accessToken());
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setParameter("code", "authorizationCode");
		request.setParameter("grant_type", "authorization_code");
		request.setParameter("client_secret", "1232");
		request.setParameter("redirect_uri", "http://localhost:8081/oauth/client/accessToken");
		request.setParameter("client_id", "clientId");
		request.setMethod("POST");
		request.setContentType("application/x-www-form-urlencoded");
		OAuthTokenRequest oauthRequest = new OAuthTokenRequest(request);
		String accessToken = oAuthIssuer.accessToken();
		String refreshToken = oAuthIssuer.refreshToken();
		MockHttpServletResponse mockHttpServletResponse = new MockHttpServletResponse();
//		System.out.println(Timestamp.valueOf(LocalDateTime.now().plusDays(1)).getTime());
		OAuthResponse res = OAuthASResponse.tokenResponse(HttpServletResponse.SC_OK).setAccessToken(accessToken).setRefreshToken(refreshToken).setTokenType("bearer").setExpiresIn(String.valueOf(Timestamp.valueOf(LocalDateTime.now().plusHours(24)).getTime())).buildJSONMessage();
//		res.getHeaders().forEach((k,v) -> System.out.println("k =" + k + ", v =  " + v));
		System.out.println(res.getBody());
//		res.getHeaders().forEach((k,v) ->{
//			mockHttpServletResponse.setHeader(k, v);
//			System.out.println(String.format("%s, %s", k, v));
//		});
		
		System.out.println(TimeUnit.DAYS.toSeconds(1));
		System.out.println(TimeUnit.DAYS.toMinutes(1));
		System.out.println(TimeUnit.MINUTES.toSeconds(1440));
	}
	public String refreshToken() throws OAuthSystemException {
		return oAuthIssuer.refreshToken();
	}
	public String accessToken() throws OAuthSystemException {
		return oAuthIssuer.accessToken();
	}


}
