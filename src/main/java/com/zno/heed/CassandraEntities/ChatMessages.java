package com.zno.heed.CassandraEntities;

import java.util.Date;
import java.util.UUID;

import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import com.datastax.oss.driver.api.core.uuid.Uuids;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Table
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ChatMessages {

	@PrimaryKey
	UUID id = UUID.randomUUID();
	int chatUserId;
	String messages;
	String type;
	String fileName;
	Date createdDateTime;
	Boolean isDeleted;
	Date updatedDateTime;
	
	
}
