package be.ordina.wcd;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.lightcouch.CouchDbClient;
import org.lightcouch.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.core.env.Environment;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import be.ordina.wcd.model.Beer;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = CouchDbApplication.class)
public class CouchDbApplicationTest {
	
	private static final Logger LOG = LoggerFactory.getLogger(CouchDbApplicationTest.class);
	
	private static final String COUCHDB_NAME = "couchdb.name";
	
	private final int expectedDocs = 1217;
	
	@Autowired
	private CouchDbClient client;
	@Autowired
	private Environment env;
	
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
		Beer beer = new Beer("duvel", "Duvel", 8.5, 3.50);
		
		// save beer in DB
		Response response = client.save(beer);
		
		// get document and revision ID from DB
		String docId = response.getId();
		String revisionId = response.getRev();
		Assert.assertTrue(client.contains(beer.getId()));
		
		// find saved beer by ID
		Beer foundBeer = client.find(Beer.class, beer.getId());
		// check that it's the same beer we saved
		Assert.assertEquals(beer.getId(), docId);
		Assert.assertEquals(beer.getBrand(), foundBeer.getBrand());
		
		// delete the beer from DB
		client.remove(docId, revisionId);
		Assert.assertFalse(client.contains(docId));
		
		// retrieve deleted document by providing revision id
		Beer lostAndFound = client.find(Beer.class, docId, revisionId);
		Assert.assertNotNull(lostAndFound);
		
		LOG.trace("we found it: " + lostAndFound.getBrand());
		
	}
	
	@Test
	public void testImportedData() {
		List<Beer> list = client.view("workshop/by_brand")
				  .includeDocs(true)
				  .query(Beer.class);

		Assert.assertEquals(expectedDocs, list.size());
	}
	
}
