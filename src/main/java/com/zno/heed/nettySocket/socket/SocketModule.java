package com.zno.heed.nettySocket.socket;

import java.util.HashMap;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.corundumstudio.socketio.SocketIONamespace;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;
import com.corundumstudio.socketio.listener.DisconnectListener;
import com.zno.heed.MysqlEntites.User;
import com.zno.heed.MysqlRepositories.UsersRepository;
import com.zno.heed.nettySocket.model.Message;
import com.zno.heed.nettySocket.service.SocketService;

@Component
public class SocketModule {
	@Autowired
	UsersRepository usersRepository;

   private final SocketIOServer server;
   private final SocketService socketService;
  
  HashMap<String,SocketIONamespace> mapNameSpace=new HashMap<String,SocketIONamespace>();
  HashMap<String,UUID> mapSessionId=new HashMap<String,UUID>();
  
    public SocketModule(SocketIOServer server , SocketService socketService) {
        this.server = server;
        this.socketService = socketService;
        server.addConnectListener(onConnected());
        server.addDisconnectListener(onDisconnected());
        server.addEventListener("send_message", Message.class, onChatReceived());

    }

    private DataListener<Message> onChatReceived() {
        return (senderClient, data, ackSender) -> {
        	
        	System.out.println("What is data    "+ data.toString());
        	
        String token =senderClient.getHandshakeData().getHttpHeaders().get("BarerToken");
     	User sendUser = usersRepository.findByUserToken(token);
     	User receiveUser = usersRepository.findByMobilePhone(data.getMobileNumber());
     	   
        	socketService.saveHistory(sendUser,receiveUser);
//        	socketService.saveMessage(sendUser,receiveUser,data);
            socketService.sendMessage(mapNameSpace.get(data.getMobileNumber()),mapSessionId.get(data.getMobileNumber()),"get_message", senderClient, data.getMessage());
          String string = new String("acknowlegement");  
            ackSender.sendAckData(string);
        };
    }

    private ConnectListener onConnected() {
        return (client) -> {
// String username = client.getHandshakeData().getSingleUrlParam("username");
     String token =client.getHandshakeData().getHttpHeaders().get("BarerToken");
      	    
          System.out.println("the token is  "+token);
       
     User user = usersRepository.findByUserToken(token);
     String mobileNumber = user.getMobilePhone();
    
     mapNameSpace.put(mobileNumber, client.getNamespace());
     mapSessionId.put(mobileNumber, client.getSessionId());
// client.joinRoom(room);      	
          System.out.println("Socket ID[{}]  Connected to socket   "+ client.getSessionId().toString()+" Client remote address    " + client.getRemoteAddress()+ " client's name space   "+ client.getNamespace());
        };
    }

    private DisconnectListener onDisconnected() {
        return client -> {
            System.out.println("Client[{}] - Disconnected from socket  "+ client.getSessionId().toString());
        };
    }

}