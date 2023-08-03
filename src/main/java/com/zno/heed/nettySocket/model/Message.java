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


    public Message(MessageType type, String message) {
        this.type = type;
        this.message = message;
    }


	public Message(LocationData locationData) {
		super();
		this.locationData = locationData;
	}
    
   
/*	@Override
	public String toString() {
		return "Message [message=" + message + "]";
	}

*/
    

}