package net.kindleit.gae.example.server;

import com.google.appengine.tools.development.ApiProxyLocalFactory;
import com.google.apphosting.api.ApiProxy;
import com.google.apphosting.api.MockEnvironment;

/**
 * Performs basic {@link ApiProxy} setUp/tearDown, as described <a
 * href="http://code.google.com/appengine/docs/java/howto/unittesting.html">here</a>.
 * 
 * @author androns
 */
public abstract class LocalServiceTest {

	/**
	 * Sets up {@link ApiProxy} with {@link MockEnvironment} and
	 * {@link ApiProxyLocalImpl}.
	 */
	public void setUp() {
		ApiProxy.setEnvironmentForCurrentThread(new MockEnvironment());
		ApiProxy.setDelegate(new ApiProxyLocalFactory().create(null));
	}

	/**
	 * Tears down {@link ApiProxy}.
	 */
	public void tearDown() {

		// not strictly necessary to null these out but there's no harm either
		ApiProxy.setDelegate(null);
		ApiProxy.setEnvironmentForCurrentThread(null);
	}

}
