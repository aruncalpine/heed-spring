package com.zno.heed.DBConfiguration;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.config.AbstractCassandraConfiguration;
import org.springframework.data.cassandra.config.SchemaAction;

@Configuration
public class CassandraConf extends AbstractCassandraConfiguration {

  /*
   * Provide a contact point to the configuration.
   */
  public String getContactPoints() {
    return "127.0.0.1";
  }

  /*
   * Provide a keyspace name to the configuration.
   */
  public String getKeyspaceName() {
    return "heed";
  }
  
  public SchemaAction getSchemaAction() {
		return SchemaAction.CREATE_IF_NOT_EXISTS;
	}
  
  
}