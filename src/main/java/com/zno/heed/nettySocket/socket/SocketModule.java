package com.zno.heed.nettySocket.socket;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIONamespace;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.annotation.OnEvent;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;
import com.corundumstudio.socketio.listener.DisconnectListener;
import com.zno.heed.CassandraEntities.ChatMessages;
import com.zno.heed.CassandraRepositories.ChatMessageRepository;
import com.zno.heed.MysqlEntites.ChatUsers;
import com.zno.heed.MysqlEntites.User;
import com.zno.heed.MysqlRepositories.ChatRepository;
import com.zno.heed.MysqlRepositories.UsersRepository;
import com.zno.heed.nettySocket.model.DeleteMessage;
import com.zno.heed.nettySocket.model.ListMessages;
import com.zno.heed.nettySocket.model.LocationData;
import com.zno.heed.nettySocket.model.Message;
import com.zno.heed.nettySocket.model.Response;
import com.zno.heed.nettySocket.model.UpdateMessage;
import com.zno.heed.nettySocket.service.SocketService;

@Component
public class SocketModule {
	@Autowired
	UsersRepository usersRepository;
	@Autowired
	ChatMessageRepository chatMessageRepository;
	@Autowired
	ChatRepository chatRepository;

	private final SocketIOServer server;
	private final SocketService socketService;

	HashMap<String, SocketIONamespace> mapNameSpace = new HashMap<String, SocketIONamespace>();
	HashMap<String, UUID> mapSessionId = new HashMap<String, UUID>();

	public SocketModule(SocketIOServer server, SocketService socketService) {
		this.server = server;
		this.socketService = socketService;
		server.addConnectListener(onConnected());
		server.addDisconnectListener(onDisconnected());
		server.addEventListener("send_message", Message.class, onChatReceived());
		server.addEventListener("update_message", UpdateMessage.class, onUpdateReceived());
		server.addEventListener("delete_message", DeleteMessage.class, onDeletedReceived());
		server.addEventListener("list_message", ListMessages.class, onListReceived());
		server.addEventListener("send_location", Message.class, onLocationShared());
	}

	private ConnectListener onConnected() {
		return (client) -> {
			// String username = client.getHandshakeData().getSingleUrlParam("username");
			String token = client.getHandshakeData().getHttpHeaders().get("BarerToken");

			System.out.println("the token is  " + token);

			User user = usersRepository.findByUserToken(token);
			String mobileNumber = user.getMobilePhone();

			mapNameSpace.put(mobileNumber, client.getNamespace());
			mapSessionId.put(mobileNumber, client.getSessionId());
			// Client.joinRoom(room);
			System.out.println("Socket ID[{}]  Connected to socket   " + client.getSessionId().toString()
					+ " Client remote address    " + client.getRemoteAddress() + " client's name space   "
					+ client.getNamespace());
		};
	}

	private DisconnectListener onDisconnected() {
		return client -> {
			System.out.println("Client[{}] - Disconnected from socket  " + client.getSessionId().toString());
		};
	}

	private DataListener<Message> onChatReceived() {
		return (senderClient, data, ackSender) -> {

			System.out.println("What is data    " + data.toString());

			String token = senderClient.getHandshakeData().getHttpHeaders().get("BarerToken");
			Response response = socketService.saveMessage(token, data);
			if(data.getMobileNumber()!=null) {
				socketService.sendMessage(mapNameSpace.get(data.getMobileNumber()),
						mapSessionId.get(data.getMobileNumber()), "get_message", senderClient, data.getMessage());

			}
			if(data.getChatUserId()!=null) {
				System.out.println("the moblie number is  " );
				String mobileNumber = chatRepository.findDestUserMobilePhoneById(data.getChatUserId());
				System.out.println("the moblie number is     "+ mobileNumber );
				socketService.sendMessage(mapNameSpace.get(mobileNumber),
						mapSessionId.get(mobileNumber), "get_message", senderClient, data.getMessage());
			}
			ackSender.sendAckData(response);
		};
	}

	private DataListener<UpdateMessage> onUpdateReceived() {
		return (senderClient, data, ackSender) -> {

			System.out.println("What is data    " + data.toString());

			String token = senderClient.getHandshakeData().getHttpHeaders().get("BarerToken");
			String mobileNumber = chatRepository.findDestUserMobilePhoneById(data.getChatUserId());	
			socketService.updateMessage(data);
			socketService.sendMessage(mapNameSpace.get(mobileNumber),
					mapSessionId.get(mobileNumber), "get_message", senderClient, data.getMessage());
             String  string ="updated";
             ackSender.sendAckData(string);
		};
	}
	
	private DataListener<DeleteMessage> onDeletedReceived() {
		return (senderClient, data, ackSender) -> {
			socketService.deleteMessage(data);
			String string = "deleted";
			ackSender.sendAckData(string);
		};
	}

	private DataListener<ListMessages> onListReceived() {
		return (senderClient, data, ackSender) -> {
			List<ChatMessages> chatMessages = chatMessageRepository.findAllByChatUserId(data.getChatUserID());
			senderClient.sendEvent("list_messages", chatMessages);
		};
	}
	
	private DataListener<Message> onLocationShared() {
		return (senderClient, data, ackSender) -> {
			String token = senderClient.getHandshakeData().getHttpHeaders().get("BarerToken");
			Response response = socketService.saveLocation(token, data);
			if(data.getMobileNumber()!=null) {
	        socketService.sendLocation(mapNameSpace.get(data.getMobileNumber()),
					mapSessionId.get(data.getMobileNumber()), "get_message", senderClient, data);
			}
			if(data.getChatUserId()!=null) {
				System.out.println("the moblie number is  " );
				String mobileNumber = chatRepository.findDestUserMobilePhoneById(data.getChatUserId());
				System.out.println("the moblie number is     "+ mobileNumber );
				socketService.sendLocation(mapNameSpace.get(mobileNumber),
						mapSessionId.get(mobileNumber), "get_message", senderClient, data);
			}
			
	        ackSender.sendAckData(response);
	    };
	
	}	
		
}