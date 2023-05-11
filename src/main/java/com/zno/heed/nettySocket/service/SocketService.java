package com.zno.heed.nettySocket.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIONamespace;
import com.zno.heed.nettySocket.model.Message;
import com.zno.heed.nettySocket.model.MessageType;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SocketService {

    public void sendMessage(SocketIONamespace toSender,UUID id, String eventName, SocketIOClient senderClient, String message) {
      //  for (
               // SocketIOClient client : senderClient.getNamespace().getRoomOperations(room).getClients()) {
    	SocketIOClient client = toSender.getClient(id);
        		
            if (!client.getSessionId().equals(senderClient.getSessionId())) {
                client.sendEvent(eventName,
                        new Message(MessageType.SERVER, message));
            }
        }
    }
