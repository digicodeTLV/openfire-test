package com.test;

import com.test.xmpp.RunnableXmppClient;

/**
 * Created by tymoshenkol on 31-Aug-16.
 */
public class XmppTest {
	public static void main (String[] args) throws Exception {
		int start = 1;
		int end = 100;
		if (args.length >= 2) {
			start = Integer.valueOf(args[0]);
			end = Integer.valueOf(args[1]);
		}
		if (args.length >= 3) {
			RunnableXmppClient.setOfServer(args[2]);
		}
		for (int i = start; i <= end; i++) {
			(new Thread(new RunnableXmppClient(i))).start();
			//Thread.sleep(10);
		}
	}
}
