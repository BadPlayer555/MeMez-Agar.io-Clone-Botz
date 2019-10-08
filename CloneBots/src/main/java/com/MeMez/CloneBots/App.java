package com.MeMez.CloneBots;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;

/**
 * Hello world!
 *
 */
public class App {
	static int maxBot = 500;// default 100
	static int loadedBots = 0;
	static boolean isRunningBots = false;
	static String serverOrigin = null;
	static String playerX = null;
	static String playerY = null;
	static String serverIp = "";
	static ExecutorService botExecutor = Executors.newFixedThreadPool(maxBot);
	static boolean doChatSpam = false;
	static boolean doSplit = false;
	static boolean doEject = false;
	static boolean needStop = false;

	public static void main(String[] args) throws URISyntaxException, IOException, InterruptedException {
		// execute BotThread

		System.out.println("[Application] Registering Native Hook");
		try {
			GlobalScreen.registerNativeHook();
		} catch (NativeHookException ex) {
			System.err.println("There was a problem registering the native hook.");
			System.err.println(ex.getMessage());

			System.exit(1);
		}

		GlobalScreen.addNativeKeyListener(new GlobalKeyListener());
		// Get the logger for "org.jnativehook" and set the level to off.
		Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
		logger.setLevel(Level.OFF);

		// Change the level for all handlers attached to the default logger.
		Handler[] handlers = Logger.getLogger("").getHandlers();
		for (int i = 0; i < handlers.length; i++) {
			handlers[i].setLevel(Level.OFF);
		}

		System.out.println("[Application] Staring server");
		Server.start();
	}

	public static void startBot() throws InterruptedException {
		if (loadedBots != maxBot) {
			needStop = false;
			int i = 0;
			while (i <= maxBot) {
				botExecutor.execute(new BotThread());
				loadedBots++;
				i++;
				Thread.sleep(100);
				//isRunningBots = true;
			}
		}

	}

	public static void executeBotThread() {
		botExecutor.execute(new BotThread());
	}
}
