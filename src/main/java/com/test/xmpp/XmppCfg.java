package com.test.xmpp;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by tymoshenkol on 31-Aug-16.
 */
@Slf4j
public class XmppCfg {

	public static final int OF_SERVER_PORT = 5222;
	public static final String OF_TEST_PASSWORD = "123";
	public static final String OF_TEST_LOGIN_APPENDER = "test1";


	public static final String[] OF_SERVERS = new String[]{
			"tymoshenkol", "aimm-buntu"
			, "192.168.1.123"
			, "192.168.1.162"
	};

	public static String OF_DOMAIN_NAME = "aimm-buntu";
	public static String OF_SERVER = OF_SERVERS[3];
	public static String OF_TEST_ROOM_NAME = "unlimitedtest@conference."+OF_DOMAIN_NAME;
	public static String OF_TEST_ROOM_NAME2 = "unlimitedtest2@conference."+OF_DOMAIN_NAME;
	public static String OF_TEST_LUIZA = "luiza@"+OF_DOMAIN_NAME;
	public static boolean doSendMessages = false;
	public static long sendMessageTimeout = 30000;

	public static void setDoSendMessages (boolean doSendMessages) {
		XmppCfg.doSendMessages = doSendMessages;
	}

	public static void setOfServer (String ofServer) {
		OF_SERVER = ofServer;
	}
	public static void setOfDomainName (String ofDomainName) {
		OF_DOMAIN_NAME = ofDomainName;
		OF_TEST_ROOM_NAME = "unlimitedtest@conference."+OF_DOMAIN_NAME;
		OF_TEST_ROOM_NAME2 = "unlimitedtest2@conference."+OF_DOMAIN_NAME;
		OF_TEST_LUIZA = "luiza@"+OF_DOMAIN_NAME;
	}

	public static void setSendMessageTimeout (Long sendMessageTimeout) {
		XmppCfg.sendMessageTimeout = sendMessageTimeout;
	}
}
