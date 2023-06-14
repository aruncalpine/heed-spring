package com.zno.heed.CassandraRepositories;

import java.util.UUID;

import org.springframework.data.cassandra.repository.AllowFiltering;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import com.zno.heed.CassandraEntities.ChatMessages;

import dto.ChatMessagesDto;
@Repository
public interface ChatMessageRepository extends CassandraRepository<ChatMessages, UUID>{

}
