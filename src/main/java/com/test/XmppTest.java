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
public class XmppTest {


	public static void main (String[] args) throws Exception {
		BlockingQueue<Void> pause = new ArrayBlockingQueue<Void>(1);
		int start = 1000;
		int end = 1999;

		if (args.length >= 2) {
			start = Integer.valueOf(args[0]);
			end = Integer.valueOf(args[1]);
		}
		if (args.length >= 3) {
			XmppCfg.setOfServer(args[2]);
		}

		if(args.length >=4 ){
			XmppCfg.setOfDomainName(args[3]);
		}

		if(args.length >=5 ){
			XmppCfg.setDoSendMessages(Boolean.parseBoolean(args[4]));
		}

		for (int i = start; i <= end; i++) {
			log.debug("--: {}",new Date());
			(new Thread(new RunnableXmppClient(i))).start();
			pause.poll(500, TimeUnit.MILLISECONDS);
		}
	}
}
