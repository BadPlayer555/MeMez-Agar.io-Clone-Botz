package com.MeMez.CloneBots;

import java.net.URI;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.handshake.ServerHandshake;

public class BotClient extends WebSocketClient {

	public BotClient(URI serverUri, Draft draft) {
		super(serverUri, draft);
	}

	public BotClient(URI serverURI) {
		super(serverURI);
	}

	public BotClient(URI serverUri, Map<String, String> httpHeaders) {
		super(serverUri, httpHeaders);
	}
	
	public void sendPacket(byte[] packet) {
		if (getReadyState() == READYSTATE.OPEN) {
			send(packet);
		} else {
			try {
				Thread.sleep(750);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			sendPacket(packet);
		}
	}
	
	public void cellCraftOnOpen() {
		//System.out.println("CellcraftOnOpen");
		sendPacket(new byte[] { (byte) 254, 5, 0, 0, 0 });
		sendPacket(new byte[] { (byte) 255, 50, (byte) 137, 112, 79 });

		Timer timer = new Timer();
		timer.schedule(new TimerTask() {

			@Override
			public void run() {
				sendPacket(new byte[] { 90, 51, 24, 34, (byte) 131 });
			}
		}, 0, 1750);
		Timer timer1 = new Timer();
		timer1.schedule(new TimerTask() {

			@Override
			public void run() {
				sendPacket(new byte[] { 42 });
				sendPacket(new byte[] {0,59,0,65,0,103,0,97,0,114,0,32,0,73,0,110,0,102,0,105,0,110,0,105,0,116,0,121,0});
			}
		}, 0, 5000);
		Timer timer2 = new Timer();
		timer2.schedule(new TimerTask() {

			@Override
			public void run() {
				ByteBuffer buf = ByteBuffer.allocate(13);
				buf.order(ByteOrder.LITTLE_ENDIAN);
				buf.put(0, (byte) 16);
				buf.putInt(1, Integer.parseInt(App.playerX));
				buf.putInt(5, Integer.parseInt(App.playerY));
				buf.putInt(9, 0);
				sendPacket(buf.array());
			}
		}, 0, 500);
		Timer checkEvent = new Timer();
		checkEvent.schedule(new TimerTask() {

			@Override
			public void run() {
				if (App.doChatSpam) {
					sendPacket(new byte[] { 99, 0, 76, 0 });
				}
				if (App.doEject) {
					sendPacket(new byte[] { 36 });
				}
				if (App.doSplit) {
					sendPacket(new byte[] { 17 });
				}
			}
		}, 0, 1000);
		// if you plan to refuse connection based on ip or httpfields overload:
		// onWebsocketHandshakeReceivedAsClient
	}
	
	public void senpaOnOpen() {
		sendPacket(new byte[] { (byte) 252,10,0,111,0,110,0,120,0,99,0,110,0,107,0,95,0,49,0,48,0,49,0,5,0,37,0,107,0,100,0,72,0,103,0});
		sendPacket(new byte[] { 30,13,0,65,0,103,0,97,0,114,0,32,0,73,0,110,0,102,0,105,0,110,0,105,0,116,0,121,0,0,0,0,0,0,0,0,0 });

		Timer timer = new Timer();
		timer.schedule(new TimerTask() {

			@Override
			public void run() {
				sendPacket(new byte[] { (byte) 130 });
			}
		}, 0, 1000);
		Timer timer1 = new Timer();
		timer1.schedule(new TimerTask() {

			@Override
			public void run() {
				sendPacket(new byte[] { 31 });
			}
		}, 0, 5000);
		Timer timer2 = new Timer();
		timer2.schedule(new TimerTask() {

			@Override
			public void run() {
				ByteBuffer buf = ByteBuffer.allocate(13);
				buf.order(ByteOrder.LITTLE_ENDIAN);
				buf.put(0, (byte) 16);
				buf.putInt(1, Integer.parseInt(App.playerX));
				buf.putInt(5, Integer.parseInt(App.playerY));
				buf.putInt(9, 0);
				sendPacket(buf.array());
			}
		}, 0, 500);
		Timer checkEvent = new Timer();
		checkEvent.schedule(new TimerTask() {

			@Override
			public void run() {
				if (App.doChatSpam) {
					sendPacket(new byte[] { 99, 0, 76, 0 });
				}
				if (App.doEject) {
					sendPacket(new byte[] { 21 });
					sendPacket(new byte[] { 19 });
				}
				if (App.doSplit) {
					sendPacket(new byte[] { 17 });
				}
			}
		}, 0, 1000);
		// if you plan to refuse connection based on ip or httpfields overload:
		// onWebsocketHandshakeReceivedAsClient
	}

	@Override
	public void onOpen(ServerHandshake handshakedata) {
		System.out.println("opened connection" + handshakedata);
		if(App.serverOrigin.equals("http://www.cellcraft.io")) {
			cellCraftOnOpen();
		}
		if(App.serverOrigin.equals("http://senpa.io")) {
			senpaOnOpen();
		}
	}

	@Override
	public void onMessage(String message) {
		// System.out.println( "received: " + message );
	}

	@Override
	public void onMessage(ByteBuffer message) {
		// System.out.println( "received: " + message );
	}

	@Override
	public void onClose(int code, String reason, boolean remote) {
		App.executeBotThread();
		// The codecodes are documented in class org.java_websocket.framing.CloseFrame
		System.out.println(
				"Connection closed by " + (remote ? "remote peer" : "us") + " Code: " + code + " Reason: " + reason);
	}

	@Override
	public void onError(Exception ex) {
		App.executeBotThread();
		ex.printStackTrace();
		// if the error is fatal then onClose will be called additionally
	}

}
