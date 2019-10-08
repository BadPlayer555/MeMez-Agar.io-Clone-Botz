package com.MeMez.CloneBots;

import java.util.Timer;
import java.util.TimerTask;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;
import com.corundumstudio.socketio.listener.DisconnectListener;

public class Server {
	public static void start() throws InterruptedException {
		Configuration config = new Configuration();
		config.setHostname("localhost");
		config.setPort(8080);

		System.out.println("[Server] ServerStarted !");
		System.out.println("[Server] Using HostName: " + config.getHostname());
		System.out.println("[Server] Listening on Port: " + config.getPort());

		final SocketIOServer server = new SocketIOServer(config);
		server.addEventListener("start", ChatObject.class, new DataListener<ChatObject>() {
			public void onData(SocketIOClient client, ChatObject data, AckRequest ackRequest)
					throws InterruptedException {
				System.out.println("[Server] User requested start.");
				System.out.println("[Server] Ip: " + data.getIp());
				System.out.println("[Server] Origin: "+data.getOrigin());
				App.serverIp = data.getIp();
				App.serverOrigin = data.getOrigin();
				App.startBot();
			}
		});

		server.addEventListener("movement", ChatObject.class, new DataListener<ChatObject>() {
			public void onData(SocketIOClient client, ChatObject data, AckRequest ackRequest)
					throws InterruptedException {
				App.playerX = data.getx();
				App.playerY = data.gety();
			}
		});

		server.addEventListener("stop", ChatObject.class, new DataListener<ChatObject>() {
			public void onData(SocketIOClient client, ChatObject data, AckRequest ackRequest)
					throws InterruptedException {
				App.needStop = true;
			}
		});
		server.addDisconnectListener(new DisconnectListener() {
			public void onDisconnect(SocketIOClient client) {
				System.out.println("[Server] User Disconnected");
			}
		});
		server.addConnectListener(new ConnectListener() {
			public void onConnect(SocketIOClient client) {
				System.out.println("[Server] User Connected");
			}
		});
		server.start();

		Timer sendBotCount = new Timer();
		sendBotCount.schedule(new TimerTask() {

			@Override
			public void run() {
				server.getBroadcastOperations().sendEvent("botCount", App.loadedBots);
			}
		}, 0, 100);

	}

}
