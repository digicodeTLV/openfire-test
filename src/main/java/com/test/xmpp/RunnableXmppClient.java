package com.test.xmpp;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by tymoshenkol on 31-Aug-16.
 */
@Slf4j
public class RunnableXmppClient implements Runnable {

	public static String OF_SERVER = "dev29";
	private static String OF_TEST_ROOM_NAME = "unlimitedtest@conference."+OF_SERVER;
	private static String OF_TEST_LUIZA = "luiza@"+OF_SERVER;

	public static void setOfServer (String ofServer) {
		OF_SERVER = ofServer;
		OF_TEST_ROOM_NAME = "unlimitedtest@conference."+ofServer;
		OF_TEST_LUIZA = "luiza@"+OF_SERVER;
	}

	private static final int OF_SERVER_PORT = 5222;
	private static final String OF_TEST_PASSWORD = "123";
	private static final String OF_TEST_LOGIN_APPENDER = "test1";


	private static AtomicInteger count = new AtomicInteger(0);
	private int num;

	public RunnableXmppClient (int i) {
		num = i;
		count.addAndGet(1);

	}

	public void run () {
		log.debug("Hello from a thread #{}", count.get());
		try {
			//loginAndJoin("luiza");
			loginAndJoin(OF_TEST_LOGIN_APPENDER + num);
			boolean isRunning = true;
			while (isRunning) {
				Thread.sleep(50);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	private void loginAndJoin (String username) throws Exception {


		XmppManager xmppManager = new XmppManager(OF_SERVER, OF_SERVER_PORT);


		xmppManager.init();
		xmppManager.performLogin(username, OF_TEST_PASSWORD);
		xmppManager.setStatus(true, "Hello everyone");

		if (num % 100 == 0) {
			xmppManager.sendMessage("Hello! Я тестовый пользователь №" + num, OF_TEST_LUIZA);
		}

		xmppManager.joinMultiUserChatRoom(username, OF_TEST_ROOM_NAME);
		boolean isRunning = true;


		while (isRunning) {
			Thread.sleep(50);
		}

		xmppManager.destroy();

	}

}
