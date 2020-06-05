package kr.co.abcmart.common.security;

import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;

public interface IRememberMeTokenService {

	public void createNewToken(PersistentRememberMeToken token);
	
	public void updateToken(PersistentRememberMeToken token);

	public PersistentRememberMeToken getTokenForSeries(PersistentRememberMeToken token);

	public void removeUserTokens(PersistentRememberMeToken token);

}
