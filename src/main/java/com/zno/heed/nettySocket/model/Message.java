package com.zno.heed.nettySocket.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Message {
    private MessageType type;
    private String message;
    private String mobileNumber;
    private Long chatUserId;
    private LocationData locationData;


    public Message(MessageType type, String message, Long chatUserId ) {
        this.type = type;
        this.message = message;
        this.chatUserId=chatUserId;
    }


	public Message(MessageType type,LocationData locationData,  Long chatUserId) {
		super();
		this.type = type;
		this.locationData = locationData;
		 this.chatUserId=chatUserId;
	}
    
   
/*	@Override
	public String toString() {
		return "Message [message=" + message + "]";
	}

*/
    

}