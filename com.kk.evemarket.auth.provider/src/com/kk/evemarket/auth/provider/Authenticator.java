package com.kk.evemarket.auth.provider;

import java.io.IOException;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.oltu.oauth2.client.OAuthClient;
import org.apache.oltu.oauth2.client.URLConnectionClient;
import org.apache.oltu.oauth2.client.request.OAuthClientRequest;
import org.apache.oltu.oauth2.client.request.OAuthClientRequest.AuthenticationRequestBuilder;
import org.apache.oltu.oauth2.client.response.OAuthClientResponse;
import org.apache.oltu.oauth2.client.response.OAuthJSONAccessTokenResponse;
import org.apache.oltu.oauth2.client.response.OAuthResourceResponse;
import org.apache.oltu.oauth2.common.OAuth;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.types.GrantType;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.event.EventAdmin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.kk.evemarket.auth.api.IAuthenticatedCallback;
import com.kk.evemarket.auth.api.IAuthenticator;
import com.kk.evemarket.common.trade.CharacterInfo;

@Component(name = "com.kk.evemarket.auth.provider", immediate = true)
public class Authenticator extends AbstractHandler implements IAuthenticator {
	private static Logger LOGGER = LoggerFactory.getLogger("Authenticator");

	private TimerTask refreshTask;
	private Timer refreshTimer;

	// private IAuthenticatedCallback callback;
	private String refreshToken;
	private String token;
	private String base64Encoded;
	private String code;
	private CharacterInfo characterInfo;
	private EventAdmin eventAdmin;

	@Reference
	void setEventAdmin(EventAdmin service) {
		eventAdmin = service;
		AuthenticatorEventHandler.setEventHandler(eventAdmin);
	}

	@Activate
	void activate() {
		LOGGER.info("Authenticator.activate()");
		AuthenticatorEventHandler.callback = this;
	}

	@Deactivate
	void deactivate() {
	}

	@Override
	public void authenticate(IAuthenticatedCallback callback) {

		serverStart();
		buildAuthentication();
	}

	public void authenticate() {

		serverStart();
		buildAuthentication();
	}

	@Override
	public String getToken() {
		return token;
	}

	public static CharacterInfo getCharacterInfo(String accessToken)
			throws OAuthSystemException, OAuthProblemException, IOException {

		OAuthClient oAuthClient = new OAuthClient(new URLConnectionClient());

		OAuthClientRequest tokenVerifyRequest = OAuthClientRequest.authorizationLocation(Constants.AUTH_VERIFY_URL)
				.buildHeaderMessage();
		tokenVerifyRequest.addHeader(OAuth.HeaderType.AUTHORIZATION, "Bearer " + accessToken);
		tokenVerifyRequest.addHeader("Host", "login.eveonline.com");

		OAuthResourceResponse oAuthResponse = oAuthClient.resource(tokenVerifyRequest, OAuth.HttpMethod.GET,
				OAuthResourceResponse.class);

		LOGGER.info("Utils.getCharacterInfo(): " + oAuthResponse.getBody());

		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());

		CharacterInfo character =
				// createLocalDateTimeMapper()
				mapper.readValue(oAuthResponse.getBody(), CharacterInfo.class);

		return character;
	}

	@Override
	public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		CompletableFuture.supplyAsync(() -> {
			OAuthClientResponse accessToken = null;
			base64Encoded = "Basic " + base64Encode(Constants.CLIENT_ID, Constants.SECRET_KEY);

			code = request.getParameter("code");

			if (code == null) {
				LOGGER.error("code is null");
			} else {
				try {
					accessToken = getAccessToken(Constants.ACCESS_TOKEN_URL, code, base64Encoded,
							GrantType.AUTHORIZATION_CODE);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			return accessToken;
		}).thenApply((result) -> {
			if (result == null)
				return null;
			else {
				token = ((OAuthJSONAccessTokenResponse) result).getAccessToken();
				refreshToken = ((OAuthJSONAccessTokenResponse) result).getRefreshToken();

				AuthenticatorEventHandler.getEventHandler()
						.postEvent(AuthenticatorEventHandler.getAccessTokenEvent(token));
				AuthenticatorEventHandler.getEventHandler()
						.postEvent(AuthenticatorEventHandler.getRefreshTokenEvent(refreshToken));

				Browser.open(Constants.REDIRECT);
				return result;
			}
		}).thenApply((result) -> {

			String accessToken = ((OAuthJSONAccessTokenResponse) result).getAccessToken();

			try {
				characterInfo = getCharacterInfo(accessToken);

				AuthenticatorEventHandler.getEventHandler()
						.postEvent(AuthenticatorEventHandler.getCharacterInfoEvent(characterInfo));
			} catch (OAuthSystemException | OAuthProblemException | IOException e) {
				e.printStackTrace();
			}

			return result;

		}).thenAccept(result -> {

			if (result != null) {
				if (refreshTimer == null) {
					if (refreshTask == null) {
						refreshTask = new TimerTask() {

							@Override
							public void run() {
								try {
									LOGGER.info("Refresh");
									OAuthClientResponse accessToken = getRefreshToken(Constants.ACCESS_TOKEN_URL, code,
											base64Encoded, GrantType.REFRESH_TOKEN, refreshToken);
									token = ((OAuthJSONAccessTokenResponse) accessToken).getAccessToken();
									refreshToken = ((OAuthJSONAccessTokenResponse) accessToken).getRefreshToken();

									AuthenticatorEventHandler.getEventHandler()
											.postEvent(AuthenticatorEventHandler.getAccessTokenEvent(token));
									AuthenticatorEventHandler.getEventHandler()
											.postEvent(AuthenticatorEventHandler.getRefreshTokenEvent(refreshToken));

									// TOREMOVE: callbacks
									// callback.authenticated(target);

								} catch (UnsupportedOperationException | IOException | OAuthProblemException
										| OAuthSystemException e) {
									e.printStackTrace();
								}
							}
						};
					}
					refreshTimer = new Timer(true);
					refreshTimer.scheduleAtFixedRate(refreshTask, Constants.TOKEN_REFRESH_PERIOD,
							Constants.TOKEN_REFRESH_PERIOD);
				}
			}

		});
	}

	private void serverStart() {

		Server server = new Server(Constants.SERVER_PORT);
		server.setHandler(this);

		new Thread(() -> {
			try {
				server.start();
				server.join();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}).start();

	}

	private void buildAuthentication() {
		try {
			AuthenticationRequestBuilder reqBuilder = OAuthClientRequest.authorizationLocation(Constants.AUTH_URL)
					.setResponseType(OAuth.OAUTH_CODE).setState("uniquestate123").setClientId(Constants.CLIENT_ID)
					.setRedirectURI(Constants.REDIRECT);

			String scopeString = Arrays.asList(Constants.SCOPES).stream().collect(Collectors.joining(" ")).toString();
			scopeString = "esi-location.read_location.v1 esi-location.read_ship_type.v1 esi-ui.open_window.v1 esi-wallet.read_character_wallet.v1 esi-assets.read_assets.v1 esi-markets.read_character_orders.v1";
			reqBuilder.setScope(scopeString);

			OAuthClientRequest request = reqBuilder.buildQueryMessage();
			Browser.open(request.getLocationUri());
		} catch (OAuthSystemException e) {
			e.printStackTrace();
		}
	}

	private OAuthClientResponse getAccessToken(String url, String code, String auth, GrantType grantType)
			throws UnsupportedOperationException, IOException, OAuthProblemException, OAuthSystemException {

		OAuthClientRequest tokenRequest;
		tokenRequest = OAuthClientRequest.tokenLocation(url).setGrantType(grantType).setCode(code).buildBodyMessage();

		tokenRequest.addHeader(OAuth.HeaderType.AUTHORIZATION, auth);
		OAuthClient oAuthClient = new OAuthClient(new URLConnectionClient());
		OAuthJSONAccessTokenResponse oauthResponse = oAuthClient.accessToken(tokenRequest);
		refreshToken = oauthResponse.getRefreshToken();

		return oauthResponse;
	}

	private OAuthClientResponse getRefreshToken(String url, String code, String auth, GrantType grantType,
			String refreshToken)
			throws UnsupportedOperationException, IOException, OAuthProblemException, OAuthSystemException {

		OAuthClientRequest tokenRequest;
		tokenRequest = OAuthClientRequest.tokenLocation(url).setGrantType(grantType).setCode(code)
				.setRefreshToken(refreshToken).buildBodyMessage();

		tokenRequest.addHeader(OAuth.HeaderType.AUTHORIZATION, auth);
		OAuthClient oAuthClient = new OAuthClient(new URLConnectionClient());
		OAuthJSONAccessTokenResponse oauthResponse = oAuthClient.accessToken(tokenRequest);

		return oauthResponse;
	}

	private String base64Encode(String clientId, String clientSecret) {
		String encodedString = null;

		encodedString = org.apache.commons.codec.binary.Base64
				.encodeBase64String((clientId + ":" + clientSecret).getBytes());

		// encodedString = Base64.getEncoder().encodeToString((clientId + ":" +
		// clientSecret).getBytes());
		return encodedString;
	}
}
