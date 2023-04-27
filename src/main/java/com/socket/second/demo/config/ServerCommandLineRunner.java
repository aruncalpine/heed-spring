package com.socket.second.demo.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.corundumstudio.socketio.SocketIOServer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
public class ServerCommandLineRunner implements CommandLineRunner {
    private final SocketIOServer server;
    
    
    public ServerCommandLineRunner(SocketIOServer server) {
		this.server = server;
	}


	@Override
    public void run(String... args) throws Exception {
        server.start();
    }
}