package com.zno.heed.chatdata;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRepository  extends JpaRepository<Chat_History, Integer>{

	List<Chat_History>findBySource(String source);
}
