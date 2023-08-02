package com.zno.heed.CassandraEntities;

import java.util.Date;
import java.util.UUID;

import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import com.zno.heed.nettySocket.model.LocationData;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Table
@AllArgsConstructor
@NoArgsConstructor
@Data

public class ChatMessages {
	UUID id = UUID.randomUUID();
	@PrimaryKeyColumn(type=PrimaryKeyType.PARTITIONED)
	Long chatUserId;
	String messages;
	String type;
	String fileName;
	@PrimaryKeyColumn(type = PrimaryKeyType.CLUSTERED) // Use CLUSTERED for clustering column
	Date createdDateTime;
	Boolean isDeleted;
	Date updateDateTime;
	String  latitude;
	String longitude;
}
