package com.kk.evemarket.auth.api;

import org.osgi.annotation.versioning.ProviderType;

/**
 * This is an example OSGi enRoute bundle that has a component that implements
 * an API.
 */

@ProviderType
public interface IAuthenticator {

	void authenticate(IAuthenticatedCallback callback);

	String getToken();

}
