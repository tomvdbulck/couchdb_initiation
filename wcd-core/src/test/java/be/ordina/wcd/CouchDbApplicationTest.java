package be.ordina.wcd;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.lightcouch.CouchDbClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.core.env.Environment;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = CouchDbApplication.class)
public class CouchDbApplicationTest {

	private static final String COUCHDB_NAME = "couchdb.name";
	
	@Autowired
	private CouchDbClient client;
	@Autowired
	private Environment env;
	
	@Test
	public void contextLoads() {
	}
	
	@Test
	public void testConnection() {
		// get default DB name from couchdb.properties
		String expectedDbName = env.getProperty(COUCHDB_NAME);
		Assert.assertNotNull(expectedDbName);
		// check if DB exists on the server
		String dbName = client.context().info().getDbName();
		Assert.assertEquals(expectedDbName, dbName);
	}

}
