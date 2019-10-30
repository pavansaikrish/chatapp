package com.infy.client.ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractButton;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class CommonUI extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3334627348907916771L;
	private JLabel headerLabel;
	public JLabel userLabel;
	private JLabel statusLabel;
	private JPanel controlPanel;
	private JButton homeButton;
	
	public JButton getHomeButton() {
		return homeButton;
	}
	
	 
	public CommonUI() {
		 setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		  
		setResizable(false);
	setLayout(null);
	
	homeButton=DesignUI.getImageButton("home.png");
	
	
	 userLabel = DesignUI.getLabel();
	 userLabel.setText("welcome");
	 userLabel.setFont(new Font("Serif", Font.BOLD, 15));
     userLabel.setBackground(new Color(204, 205, 255));
	 
	 headerLabel = DesignUI.getLabel();
	 headerLabel.setText("Chat Application");
	 headerLabel.setHorizontalAlignment(JLabel.CENTER);
	 headerLabel.setFont(new Font("Serif", Font.BOLD, 30));
     headerLabel.setForeground(new Color(99,00,33,225));
	 
     statusLabel = DesignUI.getLabel();
     statusLabel.setText("active");
     statusLabel.setHorizontalAlignment(JLabel.RIGHT);
     statusLabel.setFont(new Font("Serif", Font.BOLD, 15));
     statusLabel.setBackground(new Color(204, 205, 255));
     
     controlPanel = DesignUI.getPanel();
     controlPanel.add(userLabel);
     controlPanel.add(statusLabel);
     
    homeButton.setBounds(30, 10, 40, 30);
  headerLabel.setBounds(10, 30, 600,50 );
  controlPanel.setBounds(30, 70, 530, 30);
     
     
     add(homeButton);
     add(headerLabel);
     add(controlPanel);
     setVisible(true);
     setSize(600, 700);
	} 
	
	
	 

	public static void main(String[] args) {
		new CommonUI();
		
	}
}
