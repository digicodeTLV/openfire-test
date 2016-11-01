package com.test.xmpp;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by tymoshenkol on 31-Aug-16.
 */
@Slf4j
public class RunnableXmppClient implements Runnable {


	private static AtomicInteger count = new AtomicInteger(0);
	private int num;

	public RunnableXmppClient (int i) {
		num = i;
		count.addAndGet(1);

	}

	public void run () {
		if (count.get() % 100 == 0) {
			log.debug("Hello from a thread #{}", count.get());
		}
		try {
			//loginAndJoin("luiza");
			XmppManager.loginJoinAndWait(XmppCfg.OF_TEST_LOGIN_APPENDER + num, num);
			boolean isRunning = true;
			while (isRunning) {
				Thread.sleep(150);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
