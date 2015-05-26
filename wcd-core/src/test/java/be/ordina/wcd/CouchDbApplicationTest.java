package be.ordina.wcd;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.lightcouch.CouchDbClient;
import org.lightcouch.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.core.env.Environment;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import be.ordina.wcd.model.Beer;

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
	
	@Test
	public void testCRUD() {
		Beer beer = new Beer("Duvel", 8.5, 3.50);
		
		// save beer in DB
		Response response = client.post(beer);
		
		// get created document ID from DB
		String docId = response.getId();
		String revisionId = response.getRev();
		
		// find saved beer by ID
		Beer foundBeer = client.find(Beer.class, docId);
		
		// check that it's the same beer we saved
		Assert.assertEquals(beer.getBrand(), foundBeer.getBrand());
		
		// delete the beer from DB
		client.remove(docId, revisionId);
	}
	
}
