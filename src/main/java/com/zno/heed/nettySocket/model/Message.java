package com.zno.heed.nettySocket.model;

import lombok.Data;

@Data
public class Message {
    private MessageType type;
    private String message;
    private String mobileNumber;

    public Message() {
    }

    public Message(MessageType type, String message) {
        this.type = type;
        this.message = message;
    }


	@Override
	public String toString() {
		return "Message [message=" + message + "]";
	}
    
}