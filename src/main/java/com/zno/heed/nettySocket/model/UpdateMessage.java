package com.zno.heed.nettySocket.model;

import java.util.UUID;

import lombok.Data;
@Data
public class UpdateMessage {
 
	private	UUID id;
	private String message;
	private Long chatUserId;
}
