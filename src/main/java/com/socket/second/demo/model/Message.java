package com.socket.second.demo.model;

import lombok.Data;

@Data
public class Message {
    private MessageType type;
    private String message;
    private String username;

    public Message() {
    }

    public Message(MessageType type, String message) {
        this.type = type;
        this.message = message;
    }

	public MessageType getType() {
		return type;
	}

	public void setType(MessageType type) {
		this.type = type;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	

	@Override
	public String toString() {
		return "Message [message=" + message + "]";
	}
    
}
