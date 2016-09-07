package com.test.xmpp;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by tymoshenkol on 31-Aug-16.
 */
@Slf4j
public class XmppCfg {

	public static final int OF_SERVER_PORT = 5222;
	public static final String OF_TEST_PASSWORD = "123";
	public static final String OF_TEST_LOGIN_APPENDER = "test1";

	public static String OF_SERVER = "aimm-buntu"; //"dev29";
	public static String OF_TEST_ROOM_NAME = "unlimitedtest@conference."+OF_SERVER;
	public static String OF_TEST_LUIZA = "luiza@"+OF_SERVER;

	public static void setDoSendMessages (boolean doSendMessages) {
		XmppCfg.doSendMessages = doSendMessages;
	}

	public static boolean doSendMessages = false;

	public static void setOfServer (String ofServer) {
		OF_SERVER = ofServer;
		OF_TEST_ROOM_NAME = "unlimitedtest@conference."+ofServer;
		OF_TEST_LUIZA = "luiza@"+OF_SERVER;
	}


}
