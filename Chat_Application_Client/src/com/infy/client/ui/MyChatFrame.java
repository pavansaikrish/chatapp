package com.infy.client.ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.IOException;
import java.util.Vector;

import javax.swing.AbstractAction;
import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JRootPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.WindowConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.infy.socket.History;
import com.infy.socket.Message;
import com.infy.socket.SocketClient;
@SuppressWarnings("deprecation")
public class MyChatFrame extends javax.swing.JFrame {

	private static final long serialVersionUID = 1L;
	public static MyChatFrame myChatFrame;
	public JFrame frameLogin;
	public JFrame frameHome;
	public JFrame frameOneToOne;
	public JFrame frameGroupChat;
	public JFrame frameSearchForGroup;
	public JFrame frameSearchFromHome;
	public JFrame frameSignUp;
	public JFrame framePasswordChange;
	public JScrollPane sJScrollPane;
	
	public  JButton swithcTOLoginButton;
	private JLabel headerLabel;
	private JLabel loginLabel;
	private JLabel textLabel;
	private JLabel passwordLabel;

	public SocketClient client;
	public int port;
	public String serverAddr, username, password;
	public Thread clientThread;
	public File file;
	public String historyFile = "D:/History.xml";
	public HistoryFrame historyFrame;
	public History hist;
	private JPasswordField textPassword;

	private JLabel textUserNameLabel;
	private JLabel textPasswordLabel;
	private JLabel textConfirmPasswordLabel;

	public JLabel statusLabel;
	public JLabel statusLabel1;
	private JTextField textUserName;
	private JTextField serverIP;
	JCheckBox checkBox;

	private JButton signUpButton;
	private JButton loginButton;
	
	private JList availabelUserList;
	public JList availabelUserList1;
	private JTextField userText;
	public JTextArea commentTextArea = new JTextArea();
	private JLabel otherUserLabel;
	private JTextField textlabel;
	private JButton homeButton;
	public String otherUserName;
	private JButton sendFileButton;
	public boolean loginFlag;
	public Vector<String> userForGroupChatlist = new Vector<String>();
	
	public Vector<String> userOnlineChatlist = new Vector<String>();
	public Vector<String> userOnlineSearchedlist = new Vector<String>();
	
	private JList searchListForGroupChat;
	private String otherUserForGroupChat;
	private JPasswordField textConfirmPassword;
	private JTextField textUserName1;
	private JPasswordField textPassword1;
	private JLabel headingLabel, oldPassword, newPassword, confirmPassword;
	public JLabel	statusLabel2;
	private JPasswordField oldPass, newPass, confirmPass;
	private JButton submitButton;
	public boolean oneToOneFrameVisibility;

	public MyChatFrame() {
		initComponents();
		this.setTitle("Chat-APP");
		setResizable(false);

		this.addWindowListener(new WindowListener() {

			public void windowOpened(WindowEvent e) {
			}

			public void windowClosing(WindowEvent e) {
				try {
					client.send(new Message("message", username, ".bye",
							"SERVER", null));
					clientThread.stop();
					dispose();
					
				} catch (Exception ex) {
				}
			}

			public void windowClosed(WindowEvent e) {
			}

			public void windowIconified(WindowEvent e) {
			}

			public void windowDeiconified(WindowEvent e) {
			}

			public void windowActivated(WindowEvent e) {
			}

			public void windowDeactivated(WindowEvent e) {
			}
		});

		hist = new History(historyFile);
	}

	public static void main(String[] args) {

		myChatFrame = new MyChatFrame();

	}

	public boolean isWin32() {
		return System.getProperty("os.name").startsWith("Windows");
	}

	private void initComponents() {

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

		addLoginFrame();

		pack();
	}

	
	private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
		serverAddr = "localhost";
		port = 5555;
		try {
			if (textUserName.getText().isEmpty()) {
				JOptionPane.showMessageDialog(null,
						"Error: Please Enter User Name", "Error Massage",
						JOptionPane.ERROR_MESSAGE);
			} else if (textPassword.getText().isEmpty()) {
				JOptionPane.showMessageDialog(null,
						"Error: Please Enter Password", "Error Massage",
						JOptionPane.ERROR_MESSAGE);
			} else {
				client = new SocketClient(this, serverIP.getText());
				clientThread = new Thread(client);
				System.out.println(11);

				clientThread.start();

				if (!textUserName.getText().isEmpty()
						&& !textPassword.getText().isEmpty()) {
					username = textUserName.getText();
					password = textPassword.getText();
					client.send(new Message("login", username, password,
							"SERVER", null));

				} else {
					statusLabel.setForeground(new Color(255, 00, 00));
					statusLabel
							.setText("Please provide Username and Password .");
				}
			}
		} catch (Exception ex) {
			statusLabel.setForeground(new Color(255, 00, 00));
			statusLabel.setText("Please Check your Server Connection .");
		}

	}

///////////////********** LOGIN PAGE **********////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
	private void addLoginFrame() {
		
		frameLogin = new JFrame();
		frameLogin.setResizable(false);
		frameLogin.setTitle("Chat Application");
		frameLogin.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		frameLogin.setSize(400, 600);
		frameLogin.setLayout(null);

		Color c = new Color(238, 238, 238);
		
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent) {
				System.exit(0);
			}
		});
		
		headerLabel = new JLabel("");
		loginLabel = new JLabel("");
		
		textLabel = DesignUI.getLabel("User Name : ");
		
		passwordLabel = DesignUI.getLabel("Password : ");
		
		
		headerLabel.setText("Chat Application");
		headerLabel.setFont(new Font("Serif", Font.BOLD, 30));
		headerLabel.setForeground(new Color(99, 00, 33, 225));

		loginLabel.setText("Login");
		loginLabel.setFont(new Font("Serif", Font.BOLD, 20));
		loginLabel.setForeground(new Color(00, 00, 11, 225));
		loginLabel.setFocusable(true);

		
		
		statusLabel = new JLabel("");
		
		statusLabel.setText("");
		
		statusLabel.setSize(350, 100);

		

		signUpButton = DesignUI.getLink("SignUp");
		signUpButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				frameLogin.hide();
				addSignUpFrame();
				// frameSignUp.show();
			}
		});
		

		Listener1 listen = new Listener1();
		textUserName = DesignUI.getTextFieldkey("Enter username", 20, listen);
		textPassword = DesignUI.getPasswordFieldWithKeyListener("password", 20, listen);
		loginButton = DesignUI.getButtonKey("Login", listen);
		serverIP = new JTextField("localhost", 20);
		serverIP.setEnabled(false);
		checkBox = new JCheckBox();
		checkBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				serverIP.setEnabled(checkBox.isSelected());
				serverIP.setText("localhost");
			}
		});
		checkBox.setToolTipText("External Server");
		checkBox.setOpaque(false);
		checkBox.setSelected(false);
		headerLabel.setBounds(90,10,400,50);
		loginLabel.setBounds(170, 60, 200, 50);
		textLabel.setBounds(80, 120, 100, 30);
		textUserName.setBounds(170, 120, 160, 30);
		passwordLabel.setBounds(94, 180, 100, 30);
		textPassword.setBounds(170, 180, 160, 30);
		loginButton.setBounds(160, 240, 80, 30);
		checkBox.setBounds(150, 280, 30, 30);
		serverIP.setBounds(170, 280, 160, 30);
		signUpButton.setBounds(150, 320, 100, 30);
		statusLabel.setBounds(100, 360, 500, 30);


		frameLogin.add(headerLabel);
		frameLogin.add(loginLabel);
		frameLogin.add(textLabel);
		frameLogin.add(textUserName);
		frameLogin.add(passwordLabel);
		frameLogin.add(textPassword);
		frameLogin.add(loginButton);
		frameLogin.add(checkBox);
		frameLogin.add(serverIP);
		frameLogin.add(signUpButton);
		frameLogin.add(statusLabel);
		frameLogin.show();

	}
public class Listener1 extends AbstractAction implements ActionListener{

		
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			
			jButton1ActionPerformed(e);
		}
		}
///////////////********** HOME PAGE **********////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public void addHomeFrame() {

		this.frameHome = new JFrame();
		frameHome.setResizable(false);
		CommonUI commonUI = new CommonUI();
		commonUI.addWindowListener(new WindowListener() {

			public void windowOpened(WindowEvent arg0) {}

			public void windowIconified(WindowEvent arg0) {
			}

			public void windowDeiconified(WindowEvent arg0) {

			}

			public void windowDeactivated(WindowEvent arg0) {

			}

			public void windowClosing(WindowEvent arg0) {
				try {
					
					client.send(new Message("message", username, ".bye",
							"SERVER", null));
					clientThread.stop();
					realeaseResources();
					myChatFrame.frameLogin.show();
					
				} catch (Exception ex) {
					System.out.println("WindowCLosing Error");
				}
			}

			public void windowClosed(WindowEvent arg0) {
try {
					
					client.send(new Message("message", username, ".bye",
							"SERVER", null));
					clientThread.stop();
					realeaseResources();
					myChatFrame.frameLogin.show();
					
				} catch (Exception ex) {
					System.out.println("WindowCLosing Error");
				}
			}

			public void windowActivated(WindowEvent arg0) {
			}
		});
		
		commonUI.userLabel.setText("Welcome," + username);
		JButton searchButton = DesignUI.getImageButton("Search User","searchUser.png");
		searchButton.setBackground(DesignUI.c);
		JButton changePasswordButton = DesignUI.getLink("Change Password");
		changePasswordButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frameHome.hide();
				addPasswordFrame();
				framePasswordChange.show();
			}
		});

		JButton logoutButton = DesignUI.getLink("Logout");

		logoutButton.setHorizontalAlignment(JButton.LEFT);

		logoutButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					client.send(new Message("message", username, ".bye",
							"SERVER", null));
					clientThread.stop();
				} catch (Exception ex) {
				}
				realeaseResources();
				myChatFrame.frameLogin.show();

			}
		});

		JLabel usersLabel = DesignUI.getLabel();
		usersLabel.setText("Available Users");
		usersLabel.setFont(new Font("Serif", Font.BOLD, 30));
		usersLabel.setForeground(Color.darkGray);

		System.out.println(userOnlineChatlist + "\t  Add Home page");
		availabelUserList = new JList(userOnlineChatlist);
		System.out.println(".....................");
		 System.out.println(availabelUserList+" ");
			availabelUserList.setSelectedIndex(0);
				availabelUserList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			
		
		
		availabelUserList.addListSelectionListener(new ListSelectionListener() {

			public void valueChanged(ListSelectionEvent arg0) {
				int i = availabelUserList.getSelectedIndex();
				if(i!=0)
					valueChangeActionPerformed(arg0);
			}
		});
		JScrollPane scroll = new JScrollPane(availabelUserList);

		searchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frameHome.hide();
				addsearchPageFromHome();
			}
		});
		homeButton = commonUI.getHomeButton();
		homeButton.disable();
		
		
		
		
		changePasswordButton.setBounds(445, 10, 150, 30);
		searchButton.setBounds(1, 110, 150, 30);
		searchButton.setBorderPainted(true);
		usersLabel.setBounds(40, 150, 200, 50);
		scroll.setBounds(40, 210, 500, 300);
		logoutButton.setBounds(40, 540, 150, 30);
		
		availabelUserList.setAutoscrolls(true);
		
		commonUI.add(changePasswordButton);
		commonUI.add(searchButton);
		commonUI.add(usersLabel);
		commonUI.add(scroll);
		commonUI.add(logoutButton);
		this.frameHome = commonUI;
		
	}
private void jhomeActionPerformed(java.awt.event.ActionEvent evt) {
	
	//addHomeFrame();
	}
	private void realeaseResources(){
		if(myChatFrame.frameGroupChat!=null)
			myChatFrame.frameGroupChat.dispose();
		
		if(myChatFrame.frameOneToOne!=null)
			myChatFrame.frameOneToOne.dispose();
		
		if(myChatFrame.framePasswordChange!=null)
			myChatFrame.framePasswordChange.dispose();
		
		if(myChatFrame.frameSearchForGroup!=null)
			myChatFrame.frameSearchForGroup.dispose();
		
		if(myChatFrame.frameSearchFromHome!=null)
			myChatFrame.frameSearchFromHome.dispose();
		
		if(myChatFrame.frameSignUp!=null)
			myChatFrame.frameSignUp.dispose();
		
		if(myChatFrame.frameHome!=null)
			myChatFrame.frameHome.dispose();
	}

	public void valueChangeActionPerformed(ListSelectionEvent e) {

		this.otherUserName = availabelUserList.getSelectedValue().toString();

		if (!this.otherUserName.equals("select online user to chat")) {
			this.otherUserForGroupChat = this.otherUserName;
	//		client.send(new Message("messageOneToOnechatWindow", username, "",otherUserName,""));  
			oneToOneFrameVisibility=true;
			this.frameHome.hide();
			addOneToOneFrame();
		}

		// this.frameOneToOne.show();
	}
public void actionEventLogout(java.awt.event.ActionEvent evt){
	try {
		client.send(new Message("message", username, ".bye",
				"SERVER", null));
		clientThread.stop();
	} catch (Exception ex) {
	}
	realeaseResources();
}
	public JButton sendButton;
///////////////********** ONE-TO-ONE CHAT PAGE **********////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public void addOneToOneFrame() {
		this.frameOneToOne = new JFrame();
		
		final CommonUI commonUI = new CommonUI();
		
		otherUserLabel = new JLabel("");

		textlabel = new JTextField("Enter");

		statusLabel = new JLabel("");

		JButton groupChatButton = DesignUI.getImageButton("Group Chat","GroupChat.png");
		groupChatButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jGroupChatButtonActionPerformed(e);
			}
		});
		groupChatButton.setBackground(DesignUI.c);
		groupChatButton.setBorderPainted(false);

		textlabel.setFont(new Font("Serif", Font.ITALIC, 20));

		commentTextArea = new JTextArea(
				" ", 5, 20);
		commentTextArea.setEditable(false);
		final JScrollPane scrollPane = new JScrollPane(commentTextArea);
		scrollPane.setAutoscrolls(true);
		scrollPane.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener() {
			
			@Override
			public void adjustmentValueChanged(AdjustmentEvent e) {
				 e.getAdjustable().setValue(e.getAdjustable().getMaximum()); 
				
			}
		});
		
		
		
		final JTextField userText2 = new JTextField(10);
		userText2.setText("browse for file...");

		commonUI.userLabel.setText("Welcome " + username);

		otherUserLabel.setText(otherUserName + ",online");
		otherUserLabel.setFont(new Font("Serif", Font.BOLD, 15));
		otherUserLabel.setBackground(new Color(204, 205, 255));

Listener listen = new Listener();
userText = DesignUI.getTextFieldkey("Type message here", 20,listen);
		sendButton = DesignUI.getButtonKey("Send", listen);
		ListenerShare listenshare = new ListenerShare();
		sendFileButton = DesignUI.getButtonKey("Send File", listenshare);
		sendFileButton.setEnabled(false);
		
		final JFileChooser fileDialog = new JFileChooser();
		JButton showFileDialogButton = new JButton("Browse for File");
		showFileDialogButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				int returnVal = fileDialog.showOpenDialog(commonUI);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					file = fileDialog.getSelectedFile();
					statusLabel.setText("File Selected :" + file.getName());

					if (file != null) {
						if (!file.getName().isEmpty()) {
							sendFileButton.setEnabled(true);
						}
					}
				} else {
					statusLabel.setText("Open command cancelled by user.");
				}
			}
		});
		

		otherUserLabel.setBounds(30, 150, 200, 50);
		groupChatButton.setBounds(400, 150, 180, 50);
		scrollPane.setBounds(100, 230, 400, 200);
		userText.setBounds(100, 450, 290, 30);
		sendButton.setBounds(400, 450, 100, 30);
		statusLabel.setBounds(100, 525, 290, 30);
		showFileDialogButton.setBounds(100, 550, 290, 30);
		sendFileButton.setBounds(400, 550, 100, 30);

		homeButton = commonUI.getHomeButton();
		homeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jhomeButtonActionPerformed(e);
			}
		});

		
		
		commonUI.add(otherUserLabel);
		commonUI.add(groupChatButton);
		commonUI.add(scrollPane);
		commonUI.add(userText);
		commonUI.add(sendButton);
		commonUI.add(statusLabel);
		commonUI.add(showFileDialogButton);
		commonUI.add(sendFileButton);
		commonUI.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.frameOneToOne = commonUI;
		this.frameOneToOne.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent event) {
				jhomeButtonActionPerformed(event);
			}
		});
	//	commonUI.show();
	}
	
	
	

	private void jhomeButtonActionPerformed(java.awt.event.ActionEvent evt) {
		this.frameOneToOne.hide();
		oneToOneFrameVisibility=false;                   
		this.frameHome.show();
	}
	private void jhomeButtonActionPerformed(java.awt.event.WindowEvent evt) {
		this.frameOneToOne.hide();
		oneToOneFrameVisibility=false;
		this.frameHome.show();
	}
	private void jhomeButtonActionPerformedFromGroupChat(java.awt.event.WindowEvent evt) {
		this.frameGroupChat.hide();
		this.frameHome.show();
	}
	private void jhomeButtonActionPerformedFromSearch(java.awt.event.WindowEvent evt) {

		this.frameSearchFromHome.hide();
		this.frameHome.show();
	}
	private void jhomeButtonActionPerformedFromSearchForGroup(java.awt.event.WindowEvent evt) {

		this.frameSearchForGroup.hide();
		this.frameHome.show();
	}
	
	private void jGroupChatButton1ActionPerformed(java.awt.event.ActionEvent evt) {
		this.otherUserForGroupChat = null;
		this.userForGroupChatlist.clear();
		this.frameHome.hide();

		addGroupChat1(null);
	}

	private void jGroupChatButtonActionPerformed(java.awt.event.ActionEvent evt) {

		this.frameOneToOne.hide();
		addGroupChat1(null);
		client.send(new Message("messageGroupchatWindow", username, "",
				otherUserName, userForGroupChatlist.toString()));
		client.send(new Message("messageGroup", username, "", otherUserName,
				userForGroupChatlist.toString()));

	}

	private void jsendButtonActionPerformed(java.awt.event.ActionEvent evt) {
		String msg = userText.getText();
		userText.setText("");
		client.send(new Message("messageOneToOnechatWindow", username, "",otherUserName,""));  
		client.send(new Message("message", username, msg, otherUserName, null));
	}

	public String getUserName() {
		return username;
	}

	public JTextArea getCommentTextArea() {
		return commentTextArea;
	}

	private void jhomeButton1ActionPerformed(java.awt.event.ActionEvent evt) {
		this.userForGroupChatlist.clear();
		this.frameLogin.hide();
		this.frameGroupChat.hide();
		this.frameHome.show();
	}
	public class Listener extends AbstractAction implements ActionListener{

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
		
			jsendButtonActionPerformed(e);
		}
		
	}
	public class ListenerShare extends AbstractAction implements ActionListener{

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			long size = file.length();
			if (size < 240 * 1024 * 1024) {
				statusLabel.setText("");
				sendFileButton.setEnabled(false);
				client.send(new Message("messageOneToOnechatWindow", username, "",otherUserName,""));  
				client.send(new Message("upload_req", username, file
						.getName(), otherUserName, null));

			} else {
				commentTextArea
						.append("[Application > Me] : File size is too large[Maximum allowed size is 120MB]\n");
			}
		}
		
	}
	JTextField textArea1;
	

	JTextField searchUser = DesignUI.getTextField();
	///////////////********** SEARCH PAGE FROM GROUP_CHAT PAGE**********////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public void addsearchPage() {
		this.frameSearchForGroup = new JFrame();
		CommonUI commonUI = new CommonUI();
		commonUI.userLabel.setText("Welcome "+ username);
		
		JButton homeButton = commonUI.getHomeButton();

		homeButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				frameSearchForGroup.hide();
				frameHome.show();

			}
		});

		ListenerSearch listen = new ListenerSearch();
		searchUser = DesignUI.getTextFieldkey("Type name to search for user", 20, listen);
		JButton searchButton = DesignUI.getImageButtonWithKeyListener("searchbutton.png", listen);
		searchButton.setBackground(DesignUI.c);
		
		searchListForGroupChat = new JList(userOnlineSearchedlist);

		searchListForGroupChat
				.addListSelectionListener(new ListSelectionListener() {
					public void valueChanged(ListSelectionEvent arg0) {
						jListValueChangeActionPerformed(arg0);
					}
				});

		searchUser.setBounds(30, 150, 500, 30);
		searchButton.setBounds(30, 190, 100, 35);
		searchListForGroupChat.setBounds(30, 250, 500, 300);

		commonUI.add(searchUser);
		commonUI.add(searchButton);
		commonUI.add(searchListForGroupChat);
		commonUI.setVisible(true);
		commonUI.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.frameSearchForGroup = commonUI;
		this.frameSearchForGroup.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent event) {
				jhomeButtonActionPerformedFromSearchForGroup(event);
			}
		});
		this.frameSearchForGroup.show();
	}

	private void jListValueChangeActionPerformed(ListSelectionEvent evt) {
		String selectedName = searchListForGroupChat.getSelectedValue()
				.toString();
		if (!userForGroupChatlist.contains(selectedName)) {
			userForGroupChatlist.add(selectedName);

			client.send(new Message("newMemberOfGroup", username, selectedName,
					"", userForGroupChatlist.toString()));
		}

		this.frameSearchForGroup.hide();
		this.frameGroupChat.hide();
		addGroupChat1(null);
		this.frameGroupChat.show();
	}
public class ListenerSearch extends AbstractAction implements ActionListener{

		
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			client.send(new Message("SearchUserForGroup",username,searchUser.getText(),"Server",""));
		}
		}
///////////////********** GROUPCHAT PAGE REQUIREMENTS **********////////////////////////////////////////////////////////////////////////////////////////////////////////////////
public JList participantsList;

private void jAddParticipationButtonActionPerformed(ActionEvent event) {
this.frameGroupChat.hide();
addsearchPage();

}
public class ListenerGroup extends AbstractAction implements ActionListener{


private static final long serialVersionUID = 1L;

@Override
public void actionPerformed(ActionEvent e) {
String msg = textArea1.getText();
textArea1.setText("");

client.send(new Message("messageGroup", username, msg,
		"InGroup", userForGroupChatlist.toString()));
}
}
	///////////////********** GROUP_CHAT PAGE **********////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public void addGroupChat1(String list) {
		this.frameGroupChat = new JFrame();

		final CommonUI commonUI = new CommonUI();
		

		statusLabel = DesignUI.getLabel();

		homeButton = commonUI.getHomeButton();
		
		commonUI.userLabel.setText("Welcome " + username);

		commentTextArea = new JTextArea(
				" ", 5, 20);
		commentTextArea.setEditable(false);
		JScrollPane scroll = new JScrollPane(commentTextArea);
		scroll.setAutoscrolls(true);
		scroll.setWheelScrollingEnabled(true);
		scroll.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener() {
			
			@Override
			public void adjustmentValueChanged(AdjustmentEvent e) {
				 e.getAdjustable().setValue(e.getAdjustable().getMaximum());  
				
			}
		});
		ListenerGroup listen = new ListenerGroup();
		textArea1 = DesignUI.getTextFieldkey("Type message here", 15,listen); 

		JButton sendButton = DesignUI.getButtonKey("Send",listen);
		final JButton sendButton1 = DesignUI.getButton("Share");

		final JFileChooser fileDialog = new JFileChooser();
		JButton showFileDialogButton = DesignUI.getButton("Browse for File");
		showFileDialogButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sendButton1.setEnabled(true);
				int returnVal = fileDialog.showOpenDialog(commonUI);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					file = fileDialog.getSelectedFile();
					statusLabel.setText("File Selected :" + file.getName());
				} else {
					statusLabel.setText("Open command cancelled by user.");
				}
			}
		});

		
		sendButton1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e1) {
				long size = file.length();
				if (size < 240 * 1024 * 1024) {
					statusLabel.setText("");
					sendButton1.setEnabled(false);
					System.out.println(userForGroupChatlist+"\t inside   the very much autheticated list");
					client.send(new Message("upload_req", username, file
							.getName(), otherUserName, userForGroupChatlist
							.toString()));

				} else {
					commentTextArea
							.append("[Application > Me] : File is size too large\n");
				}
			}
		});

		JButton addParticipantButton = DesignUI.getButton("Share");
		addParticipantButton.setText("(+)Add Users");
		addParticipantButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e1) {
				jAddParticipationButtonActionPerformed(e1);
			}
		});

		headerLabel = DesignUI.getLabel();
		headerLabel.setText("Participants:");
		headerLabel.setFont(new Font("Serif", Font.BOLD, 20));
		if (!username.equals(null) && !userForGroupChatlist.contains(username)){
			userForGroupChatlist.add(username);
			System.out.println(userForGroupChatlist+"\\t b))))))))))))))))))))))");
		}
		if (!(otherUserForGroupChat == null)
				&& !userForGroupChatlist.contains(otherUserForGroupChat)){
			userForGroupChatlist.add(otherUserForGroupChat);
			System.out.println(userForGroupChatlist+"\\t b &&&&&&&&&&&&&&&&&&&&&&&&&&");
		}
		
		
		if(list!=null){
		
		String[] listUser = list.split(",");
		for (int i = 0; i < listUser.length; i++) {
			if (i == 0) {
				String firstnameinlist = listUser[i].substring(1);
				if (!userForGroupChatlist.contains(firstnameinlist.trim()))
					userForGroupChatlist.add(firstnameinlist.trim());
				continue;
			} else if (i == listUser.length - 1) {
				String lastnameinlist = listUser[i].substring(0,
						listUser[i].length() - 1);
				if (!userForGroupChatlist.contains(lastnameinlist.trim()))
					userForGroupChatlist.add(lastnameinlist.trim());
				continue;
			}
			if (!userForGroupChatlist.contains(listUser[i].trim()))
				userForGroupChatlist.add(listUser[i].trim());
		}	
		}
		participantsList = new JList(userForGroupChatlist);
		 participantsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			
		 participantsList.addListSelectionListener(new ListSelectionListener() {

			public void valueChanged(ListSelectionEvent arg0) {
				int option = JOptionPane.showOptionDialog(frameGroupChat, "Do you want to remove "+participantsList.getSelectedValue()+" from chat ?","Options",0,JOptionPane.QUESTION_MESSAGE,null,null,null);
				//JOptionPane.showInputDialog(frameGroupChat, "Do you want remove"+participantsList.getSelectedValue()+"from chat",null);
				System.out.println(userForGroupChatlist +"\t  in mychat frame   122");
				if(option == JOptionPane.YES_OPTION && participantsList.getSelectedValue().toString().equals(username)){
					System.out.println(userForGroupChatlist+"\t  in mychat frame   12233");
					JOptionPane.showMessageDialog(frameGroupChat,
						    "You cannot remove yourself !",
						    "Oops",
						    JOptionPane.WARNING_MESSAGE);

				}
				
				else if (option == JOptionPane.YES_OPTION && !participantsList.getSelectedValue().toString().equals(username) ){
					System.out.println(userForGroupChatlist+"\t  in mychat frame   12244");
					String removeMember=participantsList.getSelectedValue().toString();
					
					client.send(new Message("removeFromGroupChatList", username, participantsList.getSelectedValue().toString(), "", userForGroupChatlist.toString()));
					
					if(removeMember.equals(otherUserForGroupChat)){
						otherUserForGroupChat=null;
					}
					
					userForGroupChatlist.remove(removeMember);
					System.out.println(userForGroupChatlist+"\t 5%%%%%%%%%%%%%%%");
					
					
					frameGroupChat.hide();
					//participantsList = new JList(userForGroupChatlist);
					
					addGroupChat1(userForGroupChatlist.toString());
					frameGroupChat.show();
					System.out.println(userForGroupChatlist+"\t"+  888888888888888888888888888888888888d);
					
				}
			}
		});
		JScrollPane participantScroll = new JScrollPane(participantsList);

		headerLabel.setBounds(50, 120, 150, 30);
		participantScroll.setBounds(80, 155, 200, 90);
		addParticipantButton.setBounds(410, 155, 110, 30);
		scroll.setBounds(100, 260, 400, 200);
		textArea1.setBounds(100, 480, 300, 30);
		sendButton.setBounds(410, 480, 90, 30);
		statusLabel.setBounds(100, 570, 400, 30);
		showFileDialogButton.setBounds(100, 600, 300, 30);
		sendButton1.setBounds(410, 600, 90, 30);

		commonUI.add(headerLabel);
		commonUI.add(participantScroll);
		commonUI.add(addParticipantButton);
		commonUI.add(scroll);
		commonUI.add(textArea1);
		commonUI.add(sendButton);
		commonUI.add(statusLabel);
		commonUI.add(showFileDialogButton);
		commonUI.add(sendButton1);
		
		commonUI.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.frameGroupChat = commonUI;
		this.frameGroupChat.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent event) {
				jhomeButtonActionPerformedFromGroupChat(event);
				client.send(new Message("closeGroupChatWindow", username, "", "",  userForGroupChatlist.toString()));
				
			}
		});
		homeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jhomeButton1ActionPerformed(e);
				client.send(new Message("closeGroupChatWindow", username, "", "",  userForGroupChatlist.toString()));
			}
		});
		//frameGroupChat.setVisible(true);

	}

	///////////////********** SIGNUP PAGE **********////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private void addSignUpFrame() {
		this.frameSignUp = new JFrame();
		this.frameSignUp.setResizable(false);
		frameSignUp.setResizable(false);
		frameSignUp.setLayout(null);
		frameSignUp.setSize(500, 600);
		frameSignUp.setBackground(Color.red);
		
		JButton backButton = DesignUI.getImageButton("back.png");
		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addLoginFrame();
				frameSignUp.hide();
			}
		});
		
		frameSignUp.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent) {
				System.exit(0);
			}
			
		});

		headerLabel = DesignUI.getLabel();
		loginLabel = DesignUI.getLabel();
		
		textUserNameLabel = DesignUI.getLabel("User Name : ");
		
		textPasswordLabel = DesignUI.getLabel("Password :");
		
		textConfirmPasswordLabel = DesignUI.getLabel("Confirm Password : ");

		headerLabel.setText("CHAT APPLICATION");
		headerLabel.setFont(new Font("Serif", Font.BOLD, 30));
		headerLabel.setForeground(new Color(99, 00, 33, 225));

		loginLabel.setText("New Registration");
		loginLabel.setFont(new Font("Serif", Font.BOLD, 20));
		loginLabel.setForeground(new Color(00, 00, 11, 225));
		statusLabel1 = new JLabel("", JLabel.CENTER);

	   

	   ListenerSignUp listen = new ListenerSignUp();
	   textUserName1=DesignUI.getTextFieldkey("Enter UserName", 15, listen);
	   textPassword1=DesignUI.getPasswordFieldWithKeyListener("password", 15, listen);
	   //textConfirmPassword=DesignUI.getPasswordFieldWithKeyListener("password", 15, listen);
	   final String name = "password";
	   int size = 15;
	   textConfirmPassword=new JPasswordField(name,size);
	   textConfirmPassword.setFont(new Font("Serif", Font.ITALIC, 15));
	   textConfirmPassword.setForeground(new Color(195, 195, 195));
	   textConfirmPassword.addFocusListener(new FocusListener() {
			
			@Override
			public void focusLost(FocusEvent arg0) {
				if(textConfirmPassword.getText().equals("")){
					textConfirmPassword.setText("password");
					textConfirmPassword.setFont(new Font("Serif", Font.ITALIC, 15));
					textConfirmPassword.setForeground(new Color(195, 195, 195));
				}
			}
			
			@Override
			public void focusGained(FocusEvent arg0) {
				
				textConfirmPassword.setText("");
				textConfirmPassword.setForeground(new Color(0,0,0));
				textConfirmPassword.setFont(new Font("Serif", Font.PLAIN, 15));
				
			}
		});
	   textConfirmPassword.addMouseListener(new MouseAdapter() {
		@Override
		public void mouseClicked(MouseEvent e) {
			textConfirmPassword.setText("");
			textConfirmPassword.setForeground(new Color(0,0,0));
			textConfirmPassword.setFont(new Font("Serif", Font.PLAIN, 15));
		}
	});textConfirmPassword.addActionListener(listen);
	textConfirmPassword.getInputMap().put(KeyStroke.getKeyStroke("ENTER"), "action");
	textConfirmPassword.getActionMap().put("action", listen);
	
		JButton registerButton = DesignUI.getButtonKey("Register", listen);
		
		
		SwitchToLogin listenLogin = new SwitchToLogin();
		swithcTOLoginButton = DesignUI.getButtonKey("Click here to login", listenLogin);

		headerLabel.setBounds(100, 10, 400, 50);
		backButton.setBounds(1, 22, 40, 30);
		loginLabel.setBounds(180, 80, 200, 35);
		textUserNameLabel.setBounds(123, 140, 150, 30);
		textUserName1.setBounds(215, 140, 190, 30);
		textPasswordLabel.setBounds(137, 200, 150, 30);
		textPassword1.setBounds(215, 200, 190, 30);
		textConfirmPasswordLabel.setBounds(80, 260, 150, 30);
		textConfirmPassword.setBounds(215, 260, 190, 30);
		registerButton.setBounds(200, 360, 100, 30);
		statusLabel1.setBounds(5, 450, 500, 30);
		swithcTOLoginButton.setBounds(180, 500, 140, 30);

		
		frameSignUp.add(backButton);
		frameSignUp.add(headerLabel);
		frameSignUp.add(loginLabel);
		frameSignUp.add(textUserNameLabel);
		frameSignUp.add(textUserName1);
		frameSignUp.add(textPasswordLabel);
		frameSignUp.add(textPassword1);
		frameSignUp.add(textConfirmPasswordLabel);
		frameSignUp.add(textConfirmPassword);
		frameSignUp.add(registerButton);
		frameSignUp.add(statusLabel1);
		frameSignUp.add(swithcTOLoginButton);

		swithcTOLoginButton.hide();

		this.frameSignUp.show();

	}
	private void registerBtnActionPerformed(ActionEvent evt){
		try {
			client = new SocketClient(this, serverIP.getText());
			clientThread = new Thread(client);
			clientThread.start();
		} catch (IOException e) {
	      e.printStackTrace();
		}
	
	}
	public class SwitchToLogin extends AbstractAction implements ActionListener{

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent arg0) {
			statusLabel.setText("");
			frameSignUp.hide();
			frameSignUp.dispose();
			frameLogin.show();
			
		}
		
	}
	
	public class ListenerSignUp extends AbstractAction implements ActionListener {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			if (textUserName1.getText().isEmpty()) {
				JOptionPane.showMessageDialog(null,
						"Error: Please Enter User Name", "Error Message",
						JOptionPane.ERROR_MESSAGE);
			} else if (textPassword1.getText().isEmpty()) {
				JOptionPane.showMessageDialog(null,
						"Error: Please Enter Password", "Error Message",
						JOptionPane.ERROR_MESSAGE);
			} else if (textConfirmPassword.getText().isEmpty()) {
				JOptionPane.showMessageDialog(null,
						"Error: Please Enter Confirm Password",
						"Error Message", JOptionPane.ERROR_MESSAGE);
			} else if (!textPassword1.getText().equals(
					textConfirmPassword.getText())) {
				JOptionPane
						.showMessageDialog(
								null,
								"Error: Password and confirm password not matching",
								"Error Message", JOptionPane.ERROR_MESSAGE);
			} else if (!textUserName1.getText().isEmpty()
					&& !textPassword1.getText().isEmpty()
					&& !textConfirmPassword.getText().isEmpty()) {
				
				  	String uname = textUserName1.getText();
			        String pwd = textPassword1.getText();
			        
			        registerBtnActionPerformed(e);
			        
			        if(!uname.isEmpty() && !pwd.isEmpty()){
			            client.send(new Message("signup", uname, pwd, "SERVER",null));
			        }
			}
		}
	}
	JTextField searchUserHome = DesignUI.getTextField();
///////////////********** SEARCH PAGE FROM HOME_PAGE **********////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public void addsearchPageFromHome() {
		this.frameSearchFromHome = new JFrame();
		frameSearchFromHome.setResizable(false);
		CommonUI commonUI = new CommonUI();



		ListenerSearchHome listen = new ListenerSearchHome();
		searchUserHome = DesignUI.getTextFieldkey("Type name to search for user", 20, listen);
		JButton searchButton = DesignUI.getImageButtonWithKeyListener("searchbutton.png",listen);
		searchButton.setBackground(DesignUI.c);
		
		
		availabelUserList1 = new JList(userOnlineSearchedlist);
		availabelUserList1.setSelectedIndex(0);
		availabelUserList1
				.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		availabelUserList1
				.addListSelectionListener(new ListSelectionListener() {
					public void valueChanged(ListSelectionEvent arg0) {
						int i=availabelUserList1.getSelectedIndex();
						if(i!=0)
							jList1ValueChangeActionPerformed(arg0);
					}
				});
		  sJScrollPane = new JScrollPane(availabelUserList1);

		  searchUserHome.setBounds(30, 150, 500, 30);
		searchButton.setBounds(30, 190, 100, 35);
		sJScrollPane.setBounds(30, 250, 500, 300);
		JButton homeButton = commonUI.getHomeButton();

		homeButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				frameSearchFromHome.hide();
				frameHome.show();

			}
		});

		availabelUserList1.setAutoscrolls(true);
		commonUI.add(searchUserHome);
		commonUI.add(searchButton);
		commonUI.add(sJScrollPane);
		commonUI.setVisible(true);
		commonUI.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.frameSearchFromHome = commonUI;
		this.frameSearchFromHome.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent event) {
				jhomeButtonActionPerformedFromSearch(event);
			}
		});
		
		this.frameSearchFromHome.show();
	}

	private void jList1ValueChangeActionPerformed(ListSelectionEvent evt) {
		if (!userOnlineChatlist.contains(availabelUserList1.getSelectedValue()
				.toString())) {
			userOnlineChatlist.add(availabelUserList1.getSelectedValue()
					.toString());
		}

		this.frameSearchFromHome.hide();
		addHomeFrame();
		this.frameHome.show();
	}
public class ListenerSearchHome extends AbstractAction implements ActionListener{

		
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			client.send(new Message("SearchUser",username,searchUserHome.getText(),"Server",""));
		}
		}
///////////////********** CHANGE PASSWORD PAGE **********////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	private void addPasswordFrame() {
		this.framePasswordChange = new JFrame();
		CommonUI commonUI = new CommonUI();

		headingLabel = DesignUI.getLabel("Change Password");
		headingLabel.setFont(new Font("Serif", Font.PLAIN, 22));

		oldPassword = DesignUI.getLabel("Old Password        : *");
		newPassword = DesignUI.getLabel("New Password       : *");
		confirmPassword = DesignUI.getLabel("Confirm Password : *");

		oldPass = DesignUI.getPasswordField("password",15);
		oldPass.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				oldPass.setText("");
				oldPass.setForeground(new Color(0,0,0));
				oldPass.setFont(new Font("Serif", Font.PLAIN, 15));
			}
		});
		
		newPass = DesignUI.getPasswordField("password",15);
		newPass.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				newPass.setText("");
				newPass.setForeground(new Color(0,0,0));
				newPass.setFont(new Font("Serif", Font.PLAIN, 15));
			}
		});
		
		confirmPass = DesignUI.getPasswordField("password",15);
		confirmPass.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				confirmPass.setText("");
				confirmPass.setForeground(new Color(0,0,0));
				confirmPass.setFont(new Font("Serif", Font.PLAIN, 15));
			}
		});

		statusLabel2 = DesignUI.getLabel();

		submitButton = DesignUI.getButton("Change Password");
		submitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (oldPass.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null,
							"Error: Please Enter your old Password", "Error Message",
							JOptionPane.ERROR_MESSAGE);
				}
				else if (newPass.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null,
							"Error: Please Enter your new Password", "Error Message",
							JOptionPane.ERROR_MESSAGE);
				} else if (confirmPass.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null,
							"Error: Please Enter your Confirm new Password",
							"Error Message", JOptionPane.ERROR_MESSAGE);
				} else if (!newPass.getText().equals(
						confirmPass.getText())) {
					JOptionPane
							.showMessageDialog(
									null,
									"Error: Password and confirm password not matching",
									"Error Message", JOptionPane.ERROR_MESSAGE);
				}  else if (newPass.getText().equals(
						oldPass.getText())) {
					JOptionPane
							.showMessageDialog(
									null,
									"Error: Old Password and New password Must not Match",
									"Error Message", JOptionPane.ERROR_MESSAGE);
				}else if (!oldPass.getText().isEmpty()
						&& !newPass.getText().isEmpty()
						&& !confirmPass.getText().isEmpty()) {
					 
					client.send(new Message("changePassword", username, newPass.getText(), "SERVER",oldPass.getText()));		
				}
			}
		});

		headingLabel.setBounds(235, 120, 200, 40);
		oldPassword.setBounds(140, 200, 150, 30);
		oldPass.setBounds(290, 200, 250, 30);
		newPassword.setBounds(140, 260, 150, 30);
		newPass.setBounds(290, 260, 250, 30);
		confirmPassword.setBounds(140, 320, 150, 30);
		confirmPass.setBounds(290, 320, 250, 30);
		submitButton.setBounds(220, 380, 180, 30);
		statusLabel2.setBounds(180, 480, 300, 30);

		JButton homeButton = commonUI.getHomeButton();
		homeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				framePasswordChange.hide();
				frameHome.show();
			}
		});

		commonUI.add(headingLabel);
		commonUI.add(oldPassword);
		commonUI.add(oldPass);
		commonUI.add(newPassword);
		commonUI.add(newPass);
		commonUI.add(confirmPassword);
		commonUI.add(confirmPass);
		commonUI.add(submitButton);
		commonUI.add(statusLabel2);
		this.framePasswordChange = commonUI;

	}
}
