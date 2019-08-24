package com.kk.evemarket.auth.provider;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import com.kk.evemarket.auth.provider.Authenticator;

/*
 * Example JUNit test case
 *
 */

public class ProviderImplTest {

	/*
	 * Example test method
	 */

	@Test
	public void simple() {
		Authenticator impl = new Authenticator();
		assertNotNull(impl);
	}

}
