package net.kindleit.gae.example.server;

import com.google.appengine.api.datastore.dev.LocalDatastoreService;
import com.google.appengine.tools.development.ApiProxyLocal;
import com.google.apphosting.api.ApiProxy;

/**
 * Performs datastore setup, as described <a
 * href="http://code.google.com/appengine/docs/java/howto/unittesting.html">here</a>.
 * 
 * @author androns
 */
public abstract class LocalDatastoreTest extends LocalServiceTest {

	/**
	 * @see LocalServiceTest#setUp()
	 */
	@Override
	public void setUp() {
		super.setUp();
		ApiProxyLocal proxy = (ApiProxyLocal) ApiProxy.getDelegate();
		proxy.setProperty(LocalDatastoreService.NO_STORAGE_PROPERTY, Boolean.TRUE.toString());
	}

	/**
	 * @see LocalServiceTest#tearDown()
	 */
	@Override
	public void tearDown() {
		ApiProxyLocal proxy = (ApiProxyLocal) ApiProxy.getDelegate();
		LocalDatastoreService datastoreService = (LocalDatastoreService) proxy.getService("datastore_v3");
		datastoreService.clearProfiles();
		super.tearDown();
	}
}
