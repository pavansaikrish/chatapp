package com.infy.socket;

import java.awt.Color;
import java.io.*;
import java.net.*;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import com.infy.client.ui.MyChatFrame;

public class SocketClient implements Runnable {

	public int port;
	public String serverAddr;
	public Socket socket;
	public MyChatFrame ui;
	public ObjectInputStream In;
	public ObjectOutputStream Out;
	//public History hist;

	public SocketClient(MyChatFrame frame, String serverIp) throws IOException {
		ui = frame;
		this.serverAddr = "localhost";
		this.port = 5555;
		if(serverIp != null && !serverIp.isEmpty())
			serverAddr = serverIp;
		socket = new Socket(/*InetAddress.getLocalHost()*/serverAddr, port);

		Out = new ObjectOutputStream(socket.getOutputStream());
		Out.flush();
		In = new ObjectInputStream(socket.getInputStream());

		//hist = ui.hist;
	}

	

	public void run() {
		boolean keepRunning = true;
		while (keepRunning) {

			try {
				Message msg = (Message) In.readObject();
				System.out.println("Incoming : " + msg.toString());

				if (msg.type.equals("message")) {
					if (msg.recipient.equals(ui.getUserName())) {

						ui.getCommentTextArea().append(
								"[" + msg.sender + " > Me] : " + msg.content
										+ "\n");
					}else if (msg.sender.equals(ui.getUserName())) {

						ui.getCommentTextArea().append(
								"[ Me > " +msg.recipient+ "] : " + msg.content
										+ "\n");
					}
					else {

						ui.getCommentTextArea().append(
								"[" + msg.sender + " > " + msg.recipient
										+ "] : " + msg.content + "\n");
					}

				} else if (msg.type.equals("messageGroup")) {
					if (msg.recipient.equals(ui.getUserName())) {

						ui.getCommentTextArea().append(
								"[" + msg.sender + " >  : " + msg.content
										+ "\n");
					} else {

						ui.getCommentTextArea().append(
								"[" + msg.sender + " > " + msg.recipient
										+ "] : " + msg.content + "\n");
					}

				} else if (msg.type.equals("newMemberOfGroup")) {
					 
					if (!ui.userForGroupChatlist.contains(msg.content)) {
						 
						if(ui.frameGroupChat!=null)
							ui.frameGroupChat.hide();
						
						if(msg.userForGroupChatlist!=null){
						 	ui.addGroupChat1(msg.userForGroupChatlist);
						}
						
					 ui.frameGroupChat.show();
					}
				}	else if (msg.type.equals("messageOneToOnechatWindow")) {
					if (msg.userForGroupChatlist.contains(msg.recipient)) {
						
						/*if(ui.frameOneToOne!=null){
							ui.frameOneToOne.hide();
						}*/
						if(ui.oneToOneFrameVisibility==false){
						     ui.otherUserName=msg.sender;
						     ui.addOneToOneFrame();
						     ui.frameOneToOne.show();
						     ui.oneToOneFrameVisibility=true;
						}
						
					}

				}

				else if (msg.type.equals("messageGroupchatWindow")) {
					if (msg.userForGroupChatlist.contains(msg.recipient)) {
						
						ui.addGroupChat1(msg.userForGroupChatlist);
						ui.frameGroupChat.show();
					}
				}
				else if (msg.type.equals("refreshListOfOtherOnlineUsers")) {
					if (!ui.userOnlineChatlist.contains(msg.sender)) {
						ui.frameHome.hide();
						ui.userOnlineChatlist.add(msg.sender);
						ui.addHomeFrame();
						ui.frameHome.show();
					}

				}else if (msg.type.equals("signout")) {
					if (ui.userOnlineChatlist.contains(msg.sender)) {
						ui.frameHome.hide();
						ui.userOnlineChatlist.remove(msg.sender);
						ui.addHomeFrame();
						ui.frameHome.show();
					}

				}
				else if (msg.type.equals("changePassword")) {
					if (msg.content.equals("True")) {
						ui.statusLabel2.setForeground(new Color(00,255,00));
						ui.statusLabel2.setText("Password changed SuccessFully");
					 
					}else if (msg.content.equals("False")){
						ui.statusLabel2.setForeground(new Color(255,00,00));
						ui.statusLabel2.setText("Please provide correct password(old-password)");
					}

				}
				else if (msg.type.equals("SearchUser")) {
					
					String[] listUser = msg.userForGroupChatlist.split(",");
					ui.userOnlineSearchedlist.clear();
					 
					if(!ui.userOnlineSearchedlist.contains("Select Online user to chat"))
						ui.userOnlineSearchedlist.add("Select Online user to chat");
					for (int i = 0; i < listUser.length; i++) {
						if (i == 0 && listUser.length==1) {
							String firstnameinlist = listUser[i].substring(1,listUser[i].length()-1);
							if (!ui.userOnlineSearchedlist.contains(firstnameinlist.trim()))
								ui.userOnlineSearchedlist.add(firstnameinlist.trim());
							
							
						}else if (i == 0) {
							String firstnameinlist = listUser[i].substring(1);
							if (!ui.userOnlineSearchedlist.contains(firstnameinlist.trim()))
								ui.userOnlineSearchedlist.add(firstnameinlist.trim());
							
						}
						else if (i == listUser.length - 1 ) {
							String lastnameinlist = listUser[i].substring(0,listUser[i].length() - 1);
							if (!ui.userOnlineSearchedlist.contains(lastnameinlist.trim()))
								
								ui.userOnlineSearchedlist.add(lastnameinlist.trim());
						
						}
						else if (!ui.userOnlineSearchedlist.contains(listUser[i].trim())){
							ui.userOnlineSearchedlist.add(listUser[i].trim());
						}
						ui.frameSearchFromHome.hide();
						ui.addsearchPageFromHome();
						ui.frameSearchFromHome.show();
					
					}
					
				}else if (msg.type.equals("SearchUserForGroup")) {
					
					String[] listUser = msg.userForGroupChatlist.split(",");
					ui.userOnlineSearchedlist.clear();
					 
					if(!ui.userOnlineSearchedlist.contains("Select Online user to chat"))
						ui.userOnlineSearchedlist.add("Select Online user to chat");
					for (int i = 0; i < listUser.length; i++) {
						if (i == 0 && listUser.length==1) {
							String firstnameinlist = listUser[i].substring(1,listUser[i].length()-1);
							if (!ui.userOnlineSearchedlist.contains(firstnameinlist.trim()))
								ui.userOnlineSearchedlist.add(firstnameinlist.trim());
							
							
						}else if (i == 0) {
							String firstnameinlist = listUser[i].substring(1);
							if (!ui.userOnlineSearchedlist.contains(firstnameinlist.trim()))
								ui.userOnlineSearchedlist.add(firstnameinlist.trim());
							
						}
						else if (i == listUser.length - 1 ) {
							String lastnameinlist = listUser[i].substring(0,listUser[i].length() - 1);
							if (!ui.userOnlineSearchedlist.contains(lastnameinlist.trim()))
								
								ui.userOnlineSearchedlist.add(lastnameinlist.trim());
						
						}
						else if (!ui.userOnlineSearchedlist.contains(listUser[i].trim())){
							ui.userOnlineSearchedlist.add(listUser[i].trim());
						}
						ui.frameSearchForGroup.hide();
						ui.addsearchPage();
						ui.frameSearchForGroup.show();
					
					}
					
				}else if (msg.type.equals("login")) {
					if (msg.content.equals("TRUE")) {
						
						if(msg.userForGroupChatlist==null){
							if(!ui.userOnlineChatlist.contains("Select Online user to chat"))
								ui.userOnlineChatlist.add("Select Online user to chat");
						}
						else{
							if(!ui.userOnlineChatlist.contains("Select Online user to chat"))
								ui.userOnlineChatlist.add("Select Online user to chat");
						String[] listUser = msg.userForGroupChatlist.split(",");
						
								for (int i = 0; i < listUser.length; i++) {
									
									if (i == 0 && listUser.length==1) {
										String firstnameinlist = listUser[i].substring(1,listUser[i].length()-1);
										if (!ui.userOnlineChatlist.contains(firstnameinlist.trim()))
											ui.userOnlineChatlist.add(firstnameinlist.trim());
										
									}else if (i == 0) {
										String firstnameinlist = listUser[i].substring(1);
										if (!ui.userOnlineChatlist.contains(firstnameinlist.trim()))
											ui.userOnlineChatlist.add(firstnameinlist.trim());
										
									}
									else if (i == listUser.length - 1 ) {
										String lastnameinlist = listUser[i].substring(0,listUser[i].length() - 1);
										if (!ui.userOnlineChatlist.contains(lastnameinlist.trim()))
											
											ui.userOnlineChatlist.add(lastnameinlist.trim());
									
									}
									else if (!ui.userOnlineChatlist.contains(listUser[i].trim())){
									
										ui.userOnlineChatlist.add(listUser[i].trim());
									}
								}
						}
				ui.frameLogin.hide();
				ui.addHomeFrame();
				ui.frameHome.show();
				
			} else {
				JOptionPane.showMessageDialog(null,
						"Error: User Already Logged In or not Exits",
						"Error Massage", JOptionPane.ERROR_MESSAGE);
				ui.statusLabel.setForeground(new Color(255, 00, 00));
				ui.statusLabel.setText(msg.recipient
						+ " is already logged in ");
			}
		}else if (msg.type.equals("upload_req")) {
			
					if (JOptionPane.showConfirmDialog(ui,"Accept  FILE : '"
							+ msg.content + "'  ?", "Receiving file from : "+msg.sender, 0, JOptionPane.QUESTION_MESSAGE) == 0) {

						JFileChooser jf = new JFileChooser();
						jf.setSelectedFile(new File(msg.content));
						int returnVal = jf.showSaveDialog(ui);

						String saveTo = jf.getSelectedFile().getPath();
						if (saveTo != null
								&& returnVal == JFileChooser.APPROVE_OPTION) {
							Download dwn = new Download(saveTo, ui);
							Thread t = new Thread(dwn);
							t.start();
							
							send(new Message("upload_res", ui.username,
									("" + dwn.port), msg.sender, null));
						} else {
							send(new Message("upload_res", ui.username, "NO",
									msg.sender, null));
						}
					} else {
						send(new Message("upload_res", ui.username, "NO",
								msg.sender, null));
					}
				} else if (msg.type.equals("upload_res")) {
					if (!msg.content.equals("NO")) {
						int port = Integer.parseInt(msg.content);
						String addr = msg.sender;

						Upload upl = new Upload(addr, port, ui.file, ui);
						Thread t = new Thread(upl);
						t.start();
					} else {
						ui.commentTextArea.append("[SERVER > Me] : "
								+ msg.sender + " rejected file request\n");
					}
				}else if(msg.type.equals("removeFromGroupChatList")){
					
					if(ui.userForGroupChatlist.contains(msg.content) && !ui.username.equals(msg.content)){
						System.out.println("closeGroupChatWindow \t 44444444444444444444444 "+msg.sender );
						ui.userForGroupChatlist.remove(msg.content);
						ui.frameGroupChat.hide();
						ui.frameGroupChat.show();
						
					}else if(ui.userForGroupChatlist.contains(msg.content) && ui.username.equals(msg.content)){
						System.out.println("closeGroupChatWindow \t 33333333333333333333333333 "+msg.sender );
						ui.userForGroupChatlist.removeAllElements();
						ui.frameGroupChat.hide();
						JOptionPane.showMessageDialog(ui,
							   msg.sender +"  removed you from group chat",
							    "Oops",
							    JOptionPane.WARNING_MESSAGE);
					
					}
				}else if(msg.type.equals("closeGroupChatWindow")){
					
					if(ui.userForGroupChatlist.contains(msg.sender) && !ui.username.equals(msg.sender)){
						System.out.println("closeGroupChatWindow \t 2222222222222222222222 "+msg.sender );
						ui.userForGroupChatlist.remove(msg.sender);
						ui.frameGroupChat.hide();
						ui.frameGroupChat.show();
						
					}else if(ui.userForGroupChatlist.contains(msg.content) && ui.username.equals(msg.sender)){
						System.out.println("closeGroupChatWindow \t 11111111111111111111 "+msg.sender );
						ui.userForGroupChatlist.removeAllElements();
						ui.frameGroupChat.hide();
					}
				}
				else if(msg.type.equals("signup")){
                    if(msg.content.equals("TRUE")){
                    	ui.statusLabel1.setForeground(new Color(00, 200, 00));
    					ui.statusLabel1.setText("Successfully Registered with user name "
    									+ msg.recipient + " and password "
    									+ msg.sender);
    					ui.swithcTOLoginButton.show();
                       
                    }
                    else{
                    	ui.statusLabel1.setForeground(new Color(200, 00, 00));
    					ui.statusLabel1.setText(" Not Registered with user name "
    									+ msg.recipient + " and password "
    									+ msg.sender+"\tTry with another");
                    }
                    
                } 
				else {
					
					ui.commentTextArea
							.append("[SERVER > Me] : Unknown message type\n");
				}
			} catch (Exception ex) {
				keepRunning = false;
				ex.printStackTrace();
				ui.getCommentTextArea().append(
						"[Application > Me] : Connection Failure\n");

				ui.clientThread.stop();

				System.out.println("Exception SocketClient run()");
				ex.printStackTrace();
			}
		}
	}

	public void send(Message msg) {
		try {
			
			Out.writeObject(msg);
			Out.flush();
			System.out.println("Outgoing : " + msg.toString());

		} catch (IOException ex) {
			ex.printStackTrace();
			System.out.println("Exception SocketClient send()");
		}
	}

	public void closeThread(Thread t) {
		t = null;
	}
}