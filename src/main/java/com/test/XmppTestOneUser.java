package com.test;

import com.test.xmpp.RunnableXmppClient;
import com.test.xmpp.XmppCfg;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * Created by tymoshenkol on 31-Aug-16.
 */
@Slf4j
public class XmppTestOneUser {


	public static void main (String[] args) throws Exception {
		XmppCfg.setOfServer("tymoshenkol");
		//Username = "test1"
		(new Thread(new RunnableXmppClient(1))).start();
	}
}
