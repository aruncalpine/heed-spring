package com.zno.heed.CassandraEntities;

import java.util.Date;
import java.util.UUID;

import org.springframework.data.cassandra.core.mapping.PrimaryKey;
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

	@PrimaryKey
	UUID id = UUID.randomUUID();
	Long chatUserId;
	String messages;
	String type;
	String fileName;
	Date createdDateTime;
	Boolean isDeleted;
	Date updateDateTime;
	String  latitude;
	String longitude;

}
