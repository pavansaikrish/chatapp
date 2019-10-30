package com.infy.server.ui;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import com.infy.socket.SocketServer;


public class ServerStartPage extends JFrame{ 
	 
	
	private static final long serialVersionUID = 1L;
	private JLabel headerLabel;
	private JPanel controlPanel;
	private JScrollPane scrollPane;
	public JTextArea textArea;
	public JButton startServerButton;
	 public String filePath = "D:/Data.xml";
	private SocketServer server;
	
	public ServerStartPage() {
		prepareGUI();
	}

	public static void main(String[] args) {
		new ServerStartPage();

	}

	private void prepareGUI() {

		setLayout(null);


		headerLabel = new JLabel();
		headerLabel.setText("Chat Application");
		 headerLabel.setFont(new Font("Serif", Font.BOLD, 30));
	     headerLabel.setForeground(new Color(99,00,33,225));


		controlPanel = new JPanel();
		controlPanel.setLayout(new FlowLayout());

		textArea=new JTextArea();
		textArea.setEditable(false);
		scrollPane = new JScrollPane(textArea);
		
		startServerButton = new JButton("Start Server");
		startServerButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			
					jButton1ActionPerformed(e);
			}
		});
				
		headerLabel.setBounds(150, 50, 400, 50);
		startServerButton.setBounds(200, 120, 120, 30);
		scrollPane.setBounds(20, 180, 450, 200);
		
		add(headerLabel);
		add(startServerButton);
		add(scrollPane);
		setVisible(true);
		setSize(500,500);
		setTitle("Server");
		
	}
	
	 private void jButton1ActionPerformed(ActionEvent evt) {
	        server = new SocketServer(this);
	        startServerButton.setEnabled(false);
	 }
	 
	public void RetryStart(int port) {
		  if(server != null){ server.stop(); }
	        server = new SocketServer(this, port);
	        startServerButton.setEnabled(false);
	}
	
}


