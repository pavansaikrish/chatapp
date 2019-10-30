package com.infy.socket;

import java.io.*;
import java.net.*;
import java.util.List;
import java.util.Vector;

import javax.crypto.Cipher;

import com.infy.server.ui.ServerStartPage;

class ServerThread extends Thread {

	public SocketServer server = null;
	public Socket socket = null;
	public int ID = -1;
	public String username = "";
	public ObjectInputStream streamIn = null;
	public ObjectOutputStream streamOut = null;
	public ServerStartPage ui;

	public ServerThread(SocketServer _server, Socket _socket) {
		super();
		server = _server;
		socket = _socket;
		ID = socket.getPort();
		ui = _server.ui;
	}

	public void send(Message msg) {
		try {

			streamOut.writeObject(msg);
			streamOut.flush();

		} catch (IOException ex) {
			ex.printStackTrace();

		}
	}

	public int getID() {
		return ID;
	}

	@SuppressWarnings("deprecation")
	public void run() {
		ui.textArea.append("\nServer Thread " + ID + " running.");
		while (true) {
			try {

				Message msg = (Message) streamIn.readObject();
				
				server.handle(ID, msg);
			} catch (Exception ioe) {
				
				Database db=new Database();
				db.logout(username);
				
				//ioe.printStackTrace();
				server.remove(ID);
				stop();
			}
		}
	}

	public void open() throws IOException {
		streamOut = new ObjectOutputStream(socket.getOutputStream());
		streamOut.flush();
		streamIn = new ObjectInputStream(socket.getInputStream());
	}

	public void close() throws IOException {
		if (socket != null)
			socket.close();
		if (streamIn != null)
			streamIn.close();
		if (streamOut != null)
			streamOut.close();
	}
}

public class SocketServer implements Runnable {

	public ServerThread clients[];
	public ServerSocket server = null;
	public Thread thread = null;
	public int clientCount = 0, port = 5555;
	public ServerStartPage ui;
	public Database db;

	public String groupStringArray[] = { "Group-1", "Group-2", "Group-3",
			"Group-4", "Group-5", "Group-6" };

	public SocketServer(ServerStartPage frame) {

		clients = new ServerThread[50];
		ui = frame;
		db = new Database(ui.filePath);

		try {
			server = new ServerSocket(port);
			port = server.getLocalPort();
			ui.textArea.append("Server startet. IP : "
					+ InetAddress.getLocalHost() + ", Port : "
					+ server.getLocalPort());
			start();
		} catch (IOException ioe) {
			ui.textArea.append("Can not bind to port : " + port + "\nRetrying");
			ui.RetryStart(0);
		}
	}

	public SocketServer(ServerStartPage frame, int Port) {

		clients = new ServerThread[50];
		ui = frame;
		port = Port;
		db = new Database(ui.filePath);

		try {
			server = new ServerSocket(port);
			port = server.getLocalPort();
			ui.textArea.append("Server startet. IP : "
					+ InetAddress.getLocalHost() + ", Port : "
					+ server.getLocalPort());
			start();
		} catch (IOException ioe) {
			ui.textArea.append("\nCan not bind to port " + port + ": "
					+ ioe.getMessage());
		}
	}

	public void run() {
		while (thread != null) {
			try {
				ui.textArea.append("\nWaiting for a client ...");
				addThread(server.accept());
			} catch (Exception ioe) {
				ui.textArea.append("\nServer accept error: \n");
				ui.RetryStart(0);
			}
		}
	}

	public void start() {
		if (thread == null) {
			thread = new Thread(this);
			thread.start();
		}
	}

	@SuppressWarnings("deprecation")
	public void stop() {
		if (thread != null) {
			thread.stop();
			thread = null;
		}
	}

	private int findClient(int ID) {
		for (int i = 0; i < clientCount; i++) {
			if (clients[i].getID() == ID) {
				return i;
			}
		}
		return -1;
	}

	public synchronized void handle(int ID, Message msg) {

		if (msg.content.equals(".bye")) {
			db.logout(msg.sender);
			System.out.println("on socket server \t"+msg.toString());
			Announce("signout", msg.sender,"SERVER");
			remove(ID);
		} else {

			if (msg.type.equals("login")) {

				if (findUserThread(msg.sender) == null) {
					if (db.checkLogin(msg.sender, msg.content)) {

						clients[findClient(ID)].username = msg.sender;
						List<String> list = db.findOnlineUsers(msg.sender);

						if (list != null){
							clients[findClient(ID)].send(new Message("login",
									"SERVER", "TRUE", msg.sender, list
											.toString()));
							 Announce1("refreshListOfOtherOnlineUsers", msg.sender, msg.content,
										list.toString());
						}
						else
							clients[findClient(ID)].send(new Message("login",
									"SERVER", "TRUE", msg.sender, null));

						Announce("newuser", "SERVER", msg.sender);
						SendUserList(msg.sender);
					} else {
						clients[findClient(ID)].send(new Message("login",
								"SERVER", "FALSE", msg.sender, null));
					}
				} else {
					clients[findClient(ID)].send(new Message("login", "SERVER",
							"FALSE", msg.sender, null));
				}
			} else if (msg.type.equals("message")) {
				if (msg.recipient.equals("All")) {

					Announce("message", msg.sender, msg.content);
				} else {

					findUserThread(msg.recipient).send(
							new Message(msg.type, msg.sender, msg.content,
									msg.recipient, null));

					clients[findClient(ID)].send(new Message(msg.type,
							msg.sender, msg.content, msg.recipient, null));

				}
			} else if (msg.type.equals("changePassword")) {
				if (db.changePassword(msg.sender, msg.content,msg.userForGroupChatlist)) {
					clients[findClient(ID)].send(new Message(msg.type,
							"SERVER", "True", msg.sender, null));
				} else {
					clients[findClient(ID)].send(new Message(msg.type,
							"SERVER", "False", msg.sender, null));
				}

			}

			else if (msg.type.equals("SearchUser")) {

				clients[findClient(ID)].send(new Message("SearchUser",
						"SERVER", "TRUE", msg.sender, db.findOnlineUsers1(msg.sender, msg.content).toString()));

			}else if (msg.type.equals("SearchUserForGroup")) {

				clients[findClient(ID)].send(new Message("SearchUserForGroup",
						"SERVER", "TRUE", msg.sender, db.findOnlineUsers1(msg.sender, msg.content).toString()));

			}  else if (msg.type.equals("messageGroupchatWindow")) {

				Announce1("messageGroupchatWindow", msg.sender, msg.content,
						msg.userForGroupChatlist);

			} else if (msg.type.equals("messageOneToOnechatWindow")) {

				Announce1("messageOneToOnechatWindow", msg.sender, "",msg.recipient);

			}
			else if (msg.type.equals("newMemberOfGroup")) {
				System.out.println(msg.toString());
				Announce2("newMemberOfGroup", msg.sender, msg.content,
						msg.userForGroupChatlist);

			}else if (msg.type.equals("removeFromGroupChatList")) {
				
				Announce3("removeFromGroupChatList", msg.sender, msg.content,
						msg.userForGroupChatlist);

			} else if (msg.type.equals("closeGroupChatWindow")) {
				
				Announce2("closeGroupChatWindow", msg.sender, "",
						msg.userForGroupChatlist);

			} else if (msg.type.equals("messageGroup")) {

				Announce("messageGroup", msg.sender, msg.content,
						msg.userForGroupChatlist);

			} else if (msg.type.equals("test")) {
				clients[findClient(ID)].send(new Message("test", "SERVER",
						"OK", msg.sender, null));
			} else if (msg.type.equals("signup")) {
				if (findUserThread(msg.sender) == null) {
					if (!db.userExists(msg.sender, msg.content)) {

						clients[findClient(ID)].username = msg.sender;
						clients[findClient(ID)].send(new Message("signup",
								msg.content, "TRUE", msg.sender, null));

						remove(ID);
					} else {
						clients[findClient(ID)].send(new Message("signup",
								"SERVER", "FALSE", msg.sender, null));
					}
				} else {
					clients[findClient(ID)].send(new Message("signup",
							"SERVER", "FALSE", msg.sender, null));
				}
			} else if (msg.type.equals("upload_req")) {
				/*if (msg.recipient.equals("All")) {
					Announce("upload_req", msg.sender, msg.content);

				} else */
					
					if ((msg.userForGroupChatlist != null)
						&& (!msg.userForGroupChatlist.isEmpty())) {
					System.out.println("in side server verererere]\t"+ msg.userForGroupChatlist);
					AnnounceForFileInGroup("upload_req", msg.sender, msg.content,
							msg.userForGroupChatlist);
				} else {
					findUserThread(msg.recipient).send(
							new Message("upload_req", msg.sender, msg.content,
									msg.recipient, null));
				}
			} else if (msg.type.equals("upload_res")) {
				if (!msg.content.equals("NO")) {
					String IP = findUserThread(msg.sender).socket
							.getInetAddress().getHostAddress();
					findUserThread(msg.recipient).send(
							new Message("upload_res", IP, msg.content,
									msg.recipient, null));
				} else {
					findUserThread(msg.recipient).send(
							new Message("upload_res", msg.sender, msg.content,
									msg.recipient, null));
				}
			}
		}
	}

	public void Announce(String type, String sender, String content) {
		Message msg = new Message(type, sender, content, "All", null);
		for (int i = 0; i < clientCount; i++) {
			if (findUserThread(sender) != clients[i])
				clients[i].send(msg);
		}
	}

	public void Announce(String type, String sender, String content,
			String usersIngroup) {
		Message msg = new Message(type, sender, content, "userInGroup",
				usersIngroup);
		for (int i = 0; i < clientCount; i++) {
			if (usersIngroup.contains(clients[i].username))
				clients[i].send(msg);
		}
	}

	public void Announce1(String type, String sender, String content,
			String usersIngroup) {

		for (int i = 0; i < clientCount; i++) {
			if (findUserThread(sender) != clients[i]
					&& usersIngroup.contains(clients[i].username)) {
				Message msg = new Message(type, sender, content,
						clients[i].username, usersIngroup);
				clients[i].send(msg);
			}
		}
	}
	public void AnnounceForFileInGroup(String type, String sender, String content,
			String usersIngroup) {

		for (int i = 0; i < clientCount; i++) {
			if (findUserThread(sender)!=clients[i] && usersIngroup.contains(clients[i].username)) {
				Message msg = new Message(type, sender, content,
						clients[i].username, usersIngroup);
				clients[i].send(msg);
			}
		}
	}

	public void Announce3(String type, String sender, String content,
			String usersIngroup) {

		for (int i = 0; i < clientCount; i++) {
			if (usersIngroup.contains(clients[i].username) ) {
				Message msg = new Message(type, sender, content,
						clients[i].username, usersIngroup);
				clients[i].send(msg);
			}
		}
	}
	public void Announce2(String type, String sender, String content,
			String usersIngroup) {

		for (int i = 0; i < clientCount; i++) {
			if (findUserThread(sender) != clients[i]
					&& usersIngroup.contains(clients[i].username)) {
				Message msg = new Message(type, sender, content,
						clients[i].username, usersIngroup);
				clients[i].send(msg);
			}
		}
	}

	public void SendUserList(String toWhom) {
		for (int i = 0; i < clientCount; i++) {
			findUserThread(toWhom).send(
					new Message("newuser", "SERVER", clients[i].username,
							toWhom, null));
		}
	}

	public ServerThread findUserThread(String usr) {
		for (int i = 0; i < clientCount; i++) {

			if (clients[i].username.equals(usr)) {

				return clients[i];
			}
		}
		return null;
	}

	@SuppressWarnings("deprecation")
	public synchronized void remove(int ID) {
		int pos = findClient(ID);
		if (pos >= 0) {
			ServerThread toTerminate = clients[pos];
			ui.textArea.append("\nRemoving client thread " + ID + " at " + pos);
			if (pos < clientCount - 1) {
				for (int i = pos + 1; i < clientCount; i++) {
					clients[i - 1] = clients[i];
				}
			}
			clientCount--;
			try {
				toTerminate.close();
			} catch (IOException ioe) {
				ui.textArea.append("\nError closing thread: " + ioe);
			}
			toTerminate.stop();
		}
	}

	private void addThread(Socket socket) {
		if (clientCount < clients.length) {
			ui.textArea.append("\nClient accepted: " + socket);
			clients[clientCount] = new ServerThread(this, socket);
			try {
				clients[clientCount].open();
				clients[clientCount].start();
				clientCount++;
			} catch (IOException ioe) {
				ui.textArea.append("\nError opening thread: " + ioe);
			}
		} else {
			ui.textArea.append("\nClient refused: maximum " + clients.length
					+ " reached.");
		}
	}
}
