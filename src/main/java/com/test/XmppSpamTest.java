package com.test;

import com.test.xmpp.RunnableXmppClient;
import com.test.xmpp.RunnableXmppSpamClient;
import com.test.xmpp.XmppCfg;
import com.test.xmpp.XmppManager;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * Created by tymoshenkol on 31-Aug-16.
 */
@Slf4j
public class XmppSpamTest {

	public static void main (String[] args) throws Exception {

		int T = 1000;
		for (int i = 25000; i <= 25010; i++) {
			log.debug("--: {}",new Date());
			(new Thread(new RunnableXmppSpamClient(i, T))).start();
		}

	}


}
