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
import com.zno.heed.nettySocket.model.Message;
import com.zno.heed.nettySocket.service.SocketService;
import com.zno.heed.user.User;
import com.zno.heed.user.UsersRepository;

@Component
public class SocketModule {
	@Autowired
	UsersRepository usersRepository;

   private final SocketIOServer server;
  private final SocketService socketService;
  
  HashMap<String,SocketIONamespace> map=new HashMap<String,SocketIONamespace>();
  HashMap<String,UUID> map2=new HashMap<String,UUID>();
  
    public SocketModule(SocketIOServer server , SocketService socketService) {
        this.server = server;
        this.socketService = socketService;
        server.addConnectListener(onConnected());
        server.addDisconnectListener(onDisconnected());
        server.addEventListener("send_message", Message.class, onChatReceived());

    }

    private DataListener<Message> onChatReceived() {
        return (senderClient, data, ackSender) -> {
        	System.out.println(data.toString()+"  Mobile Number  "+data.getMobileNumber());
            socketService.sendMessage(map.get(data.getMobileNumber()),map2.get(data.getMobileNumber()),"get_message", senderClient, data.getMessage());
        };
    }

    private ConnectListener onConnected() {
        return (client) -> {
       // 	String username = client.getHandshakeData().getSingleUrlParam("username");
      	    String token =client.getHandshakeData().getHttpHeaders().get("BarerToken");
      	    
    System.out.println("the token is  "+token);
       
     User user = usersRepository.findByUserToken(token);
     String mobileNumber = user.getMobilePhone();
    
      	    map.put(mobileNumber, client.getNamespace());
        	map2.put(mobileNumber, client.getSessionId());
        //   client.joinRoom(room);
        	
    System.out.println("Socket ID[{}]  Connected to socket   "+ client.getSessionId().toString()+" Client remote address    " + client.getRemoteAddress()+ " client's name space   "+ client.getNamespace());
        };

    }

    private DisconnectListener onDisconnected() {
        return client -> {
            System.out.println("Client[{}] - Disconnected from socket  "+ client.getSessionId().toString());
        };
    }

}