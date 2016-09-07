package com.test.xmpp;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by tymoshenkol on 31-Aug-16.
 */
@Slf4j
public class RunnableXmppSpamClient implements Runnable {

	private static AtomicInteger count = new AtomicInteger(0);
	private int num;
	private int timeDelay = 1000;


	public RunnableXmppSpamClient (int i, int t) {
		num = i;
		timeDelay = t;
		count.addAndGet(1);
	}

	public void run () {
		BlockingQueue<Void> pause = new ArrayBlockingQueue<Void>(1);

		try {
			String username = "test1"+num;
			XmppManager xmppManager = XmppManager.loginAndJoin(username);


			xmppManager.sendMessage("Hello! Я тестовый пользователь: " + username, XmppCfg.OF_TEST_LUIZA);
			Integer n = 0;
			while (true) {
				xmppManager.sendMessage("Hello! I'm message #" + (n++), XmppCfg.OF_TEST_LUIZA);
				pause.poll(timeDelay, TimeUnit.MILLISECONDS);
				if (n > 10000) break;
			}

			xmppManager.destroy();
		} catch (Exception e) {

		}
	}

}
