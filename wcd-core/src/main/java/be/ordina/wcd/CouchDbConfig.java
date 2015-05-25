package be.ordina.wcd;

import org.lightcouch.CouchDbClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("couchdb.properties")
public class CouchDbConfig {

	@Bean(destroyMethod = "shutdown")
	public CouchDbClient dbClient() {
		return new CouchDbClient();
	}
	
}
