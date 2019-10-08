package com.MeMez.CloneBots;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import org.java_websocket.WebSocketImpl;

public class BotThread implements Runnable {

	private static Random rand = new Random();

	public static int attempt = 0;

	public static String getRandArray(String[] items) {
		return items[rand.nextInt(items.length)];
	}

	public static String getHost(String a) {
		a = a.replace("/[ws]/g", "");
		a = a.replace("/[/]/g", "");
		a = a.substring(1);
		// console.log(a);
		return a;

	}

	BotClient c = null;
	BufferedReader abc = null;
	BufferedReader abc1 = null;
	String Randomdata;
	String Randomdata1;

	public void run() {
		// Setting up Header to fake the server that we are using a real bowser to join
		Map<String, String> httpHeaders = new HashMap<String, String>();
		httpHeaders.put("Accept-Encoding", "gzip, deflate");
		httpHeaders.put("Accept-Language", "en-US,en;q=0.9,zh-CN;q=0.8,zh;q=0.7");
		httpHeaders.put("Cache-Control", "no-cache");
		httpHeaders.put("Connection", "Upgrade");
		httpHeaders.put("Sec-WebSocket-Version", "13");
		httpHeaders.put("Pragma", "no-cache");
		httpHeaders.put("Host", getHost(App.serverIp));
		httpHeaders.put("Connection", "Upgrade");
		httpHeaders.put("Origin", App.serverOrigin);
		httpHeaders.put("User-Agent",
				"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/68.0.3440.106 Safari/537.36");

		// TODO Auto-generated method stub
		//System.out.println("attempt: " + attempt);
			try {
			abc = new BufferedReader(new FileReader("HttpProxy.txt"));
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		
		}
			try {
				abc1 = new BufferedReader(new FileReader("SocksProxy.txt"));
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		
		List<String> data = new ArrayList<String>();
		String s;
		try {
			while ((s = abc.readLine()) != null) {
				data.add(s);
				// System.out.println(s);
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			abc.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String[] data1 = data.toArray(new String[] {});
		
		List<String> data2 = new ArrayList<String>();
		String s1;
		try {
			while ((s1 = abc1.readLine()) != null) {
				data2.add(s1);
				// System.out.println(s);
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			abc1.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String[] data3 = data2.toArray(new String[] {});
		WebSocketImpl.DEBUG = false;
		try {
			c = new BotClient(new URI(App.serverIp), httpHeaders);
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//Set this for lower ping
		//c.setTcpNoDelay(true);
		try {
			Randomdata = getRandArray(data1);
			String[] splitedRandomdata = Randomdata.split(":");
			int port = Integer.parseInt(splitedRandomdata[1]);
			Randomdata1 = getRandArray(data3);
			String[] splitedRandomdata1 = Randomdata1.split(":");
			int port1 = Integer.parseInt(splitedRandomdata1[1]);
			//System.out.println(Randomdata);
			if(rand.nextBoolean()) {
			    c.setProxy(new Proxy(Proxy.Type.HTTP, new InetSocketAddress(splitedRandomdata[0], port)));
			}else {
				c.setProxy(new Proxy(Proxy.Type.SOCKS, new InetSocketAddress(splitedRandomdata[0], port1)));
			}
			
		} catch (Exception ex) {
			System.out.println("Proxy: "+Randomdata+" is not working.");
			App.executeBotThread();
		}
		c.connect();
		
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {

			@Override
			public void run() {
				if(App.needStop == true) {
					Thread.currentThread().interrupt();
				}
			}
		}, 0, 1750);
	}

}
