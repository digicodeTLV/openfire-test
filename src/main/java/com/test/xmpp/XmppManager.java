package com.test.xmpp;

import com.test.util.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ChatManager;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.ConnectionConfiguration.SecurityMode;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.SmackConfiguration;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.filter.PacketTypeFilter;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.packet.Presence.Type;
import org.jivesoftware.smackx.muc.DiscussionHistory;
import org.jivesoftware.smackx.muc.MultiUserChat;

import java.util.Random;


/**
 * Created by tymoshenkol on 31-Aug-16.
 */
@Slf4j
public class XmppManager {

	private static final int packetReplyTimeout = 500000; // millis

	private String server;
	private int port;

	private ConnectionConfiguration config;
	private XMPPConnection connection;

	private ChatManager chatManager;
	private MultiUserChat multiUserChat;
	private MessageListener messageListener;

	public XmppManager (String server, int port) {
		this.server = server;
		this.port = port;
	}

	public static XmppManager loginAndJoin (String username) throws Exception {
		XmppManager xmppManager = new XmppManager(XmppCfg.OF_SERVER, XmppCfg.OF_SERVER_PORT);

		xmppManager.init();
		xmppManager.performLogin(username, XmppCfg.OF_TEST_PASSWORD);
		xmppManager.setStatus(true, "Hello everyone");

		xmppManager.joinMultiUserChatRoom(username, XmppCfg.OF_TEST_ROOM_NAME);
		//xmppManager.joinMultiUserChatRoom(username, XmppCfg.OF_TEST_ROOM_NAME2);

		return xmppManager;
	}

	public static void loginJoinAndWait (String username, Integer num) throws Exception {

		XmppManager xmppManager = loginAndJoin(username);
		sendMsg(xmppManager, num);

		boolean isRunning = true;

		while (isRunning) {
			Thread.sleep(XmppCfg.sendMessageTimeout);
			sendMsg(xmppManager, num);
		}
		xmppManager.destroy();
	}

	private static  void sendMsg(XmppManager xmppManager , int num) throws Exception{
		if (XmppCfg.doSendMessages) {
			xmppManager.sendMessage(getMsg(num), XmppCfg.OF_TEST_ROOM_NAME, Message.Type.groupchat);
		}
	}

	private static String getMsg (int num) {
		return "Hello! I'm №" + num + " [" + DateUtil.getNow() + "]";
	}

	public void init () throws XMPPException {

		//log.debug("Initializing connection to server {} port {}", server, port);

		SmackConfiguration.setPacketReplyTimeout(packetReplyTimeout);

		config = new ConnectionConfiguration(server, port);
		config.setSASLAuthenticationEnabled(false);
		config.setSecurityMode(SecurityMode.disabled);

		connection = new XMPPConnection(config);
		connection.connect();

		//log.debug("Connected: {}" , connection.isConnected());

		chatManager = connection.getChatManager();

		messageListener = new MyMessageListener();

	}

	public void performLogin (String username, String password) throws XMPPException {
		if (connection != null && connection.isConnected()) {
			//log.debug("performLogin: {}", username);
			connection.login(username, password);
		}
	}

	public void setStatus (boolean available, String status) {

		Presence.Type type = available ? Type.available : Type.unavailable;
		Presence presence = new Presence(type);

		presence.setStatus(status);
		connection.sendPacket(presence);

	}

	public void destroy () {
		if (connection != null && connection.isConnected()) {
			connection.disconnect();
		}
	}

	public void sendMessage (String message, String buddyJID, Message.Type type) throws XMPPException {
		//log.debug("Sending mesage '{}' to user {}", message, buddyJID);
		//Chat chat = chatManager.createChat(buddyJID, messageListener);
		Message msg = new Message();
		msg.setBody(message);
		msg.setTo(buddyJID);
		msg.setFrom(connection.getUser());
		msg.setType(type);
		//log.debug("Sending mesage: {}",msg.toXML());
		multiUserChat.sendMessage(msg);
	}

	public void createEntry (String user, String name) throws Exception {
		log.debug("Creating entry for buddy '{}' with name {}", user, name);
		Roster roster = connection.getRoster();
		roster.createEntry(user, name, null);
	}

	public void joinMultiUserChatRoom (String userName, String roomName) {
		// Create a MultiUserChat using an XMPPConnection for a room
		multiUserChat = new MultiUserChat(connection, roomName);

		DiscussionHistory history = new DiscussionHistory();
		history.setMaxStanzas(0);
		try {
			multiUserChat.join(userName, "", history, packetReplyTimeout);

			// Register a packet listener for all the messages sent to this client.

			PacketTypeFilter typeFilter = new PacketTypeFilter(Message.class);

			if (!connection.isConnected()) {
				log.error("~~~~~~~~~~~~~~~~DISCONNECTED!");
				//	System.exit(0);
			}

			connection.addPacketListener(new PacketListener() {
				public void processPacket (Packet packet) {
					log.debug(packet.toXML());
					//handlePacket(packet);
				}
			}, typeFilter);


		} catch (Exception e) {
			log.error("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!JOIN: error for {}", userName);
			log.error(e.getMessage(), e.getCause());
			log.error(e.getMessage(), e);
			//System.exit(0);
		}
	}

	class MyMessageListener implements MessageListener {

		public void processMessage (Chat chat, Message message) {
			log.debug("Received message[{}] from {} : '{}' ", message.getType(), message.getFrom(), message.getBody());
		}

	}

}
