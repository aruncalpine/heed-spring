package dto;

import java.util.Date;
import java.util.UUID;

import lombok.Data;
@Data
public class ChatMessagesDto {

	UUID id;
	int chatUserId;
	String messages;
	String type;
	String fileName;
	Date createdDateTime;
	Boolean isDeleted;
	Date updatedDateTime;
	
}
