package com.test.xmpp;

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
	private MessageListener messageListener;

	public XmppManager (String server, int port) {
		this.server = server;
		this.port = port;
	}

	public void init () throws XMPPException {

		log.debug("Initializing connection to server {} port {}", server, port);

		SmackConfiguration.setPacketReplyTimeout(packetReplyTimeout);

		config = new ConnectionConfiguration(server, port);
		config.setSASLAuthenticationEnabled(false);
		config.setSecurityMode(SecurityMode.disabled);

		connection = new XMPPConnection(config);
		connection.connect();

		log.debug("Connected: {}" , connection.isConnected());

		chatManager = connection.getChatManager();
		messageListener = new MyMessageListener();

	}

	public void performLogin (String username, String password) throws XMPPException {
		if (connection != null && connection.isConnected()) {
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

	public void sendMessage (String message, String buddyJID) throws XMPPException {
		log.debug("Sending mesage '{}' to user {}", message, buddyJID);
		Chat chat = chatManager.createChat(buddyJID, messageListener);
		chat.sendMessage(message);
	}

	public void createEntry (String user, String name) throws Exception {
		log.debug("Creating entry for buddy '{}' with name {}", user, name);
		Roster roster = connection.getRoster();
		roster.createEntry(user, name, null);
	}


	public void joinMultiUserChatRoom (String userName, String roomName) {
		// Create a MultiUserChat using an XMPPConnection for a room
		MultiUserChat multiUserChat = new MultiUserChat(connection, roomName );

		DiscussionHistory history = new DiscussionHistory();
		history.setMaxStanzas(-1);
		try {
			multiUserChat.join(userName, "", history, packetReplyTimeout);

			// Register a packet listener for all the messages sent to this client.
			PacketTypeFilter typeFilter = new PacketTypeFilter(Message.class);

			connection.addPacketListener(new PacketListener() {
				public void processPacket(Packet packet) {
					log.debug(packet.toXML());
					//handlePacket(packet);
				}
			}, typeFilter);


		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	class MyMessageListener implements MessageListener {

		public void processMessage (Chat chat, Message message) {
			String from = message.getFrom();
			String body = message.getBody();
			log.debug("Received message '{}' from {}", body, from);
		}

	}

}
