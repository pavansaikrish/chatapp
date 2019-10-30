package com.infy.client.ui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.font.TextAttribute;
import java.util.Hashtable;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;

public class DesignUI extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8353508419620586789L;
	private static JLabel label;
	private static JTextField textField;
	private static JTextArea textArea;
	private static JPasswordField password;
	private static JPanel panel;
	private static JButton button;
	
	 public static Color c=new Color(238, 238, 238);
	
	 public static ImageIcon createImageIcon(String path, String description) {
			java.net.URL imgURL = MyChatFrame.class.getResource(path);
			if (imgURL != null) {
				return new ImageIcon(imgURL, description);
			} else {
				System.err.println("Couldn't find file: " + path);
				return null;
			}
		}
	 
	 public static JButton getImageButton(String image){
		 ImageIcon icon = DesignUI.createImageIcon(image, "java");
			button=new JButton(icon);
			button.setBorder(null);
			return button;
		}
	 public static JButton getImageButtonWithKeyListener(String image,Action listen){
		 ImageIcon icon = DesignUI.createImageIcon(image, "java");
			button=new JButton(icon);
			button.setBorder(null);
			button.addActionListener(listen);
			button.getInputMap().put(KeyStroke.getKeyStroke("ENTER"), "action");
			button.getActionMap().put("action", listen);
			return button;
		}
		public static JButton getImageButton(String buttonName,String image){
			 ImageIcon icon = DesignUI.createImageIcon(image, "java");
			button=new JButton(buttonName,icon);
			button.setBorder(null);
			return button;
		}
	 
	public static JLabel getLabel(){
	 
		label = new JLabel();
		label.setFont(new Font("Serif", Font.BOLD, 15));
		label.setBackground(c);
     return label;
	}
    
	public static JLabel getLabel(String name){
		 
		label = new JLabel(name);
		label.setFont(new Font("Serif", Font.BOLD, 15));
		label.setBackground(c);
     return label;
	}
	
	public static JPanel getPanel(){
		
		panel= new JPanel();
		//panel.setLayout(new FlowLayout());
		panel.setLayout(new GridLayout(1, 2, 10, 10));
		panel.setBackground(c);
		return panel;
	}
	
	public static JButton getButton(String buttonName){
		button=new JButton(buttonName);
		return button;
	}
	public static JButton getButton(Icon icon){
		button=new JButton(icon);
		button.setBorder(null);
		return button;
	}
	public static JButton getButton(String buttonName,Icon icon){
		button=new JButton(buttonName,icon);
		button.setBorder(null);
		return button;
	}
	
	public static JTextField getTextField(){
		textField=new JTextField();
		return textField;
	}
	
	public static JTextField getTextField(String name){
		textField=new JTextField(name);
		return textField;
	}
	public static JTextField getTextField(String name,int size){
		textField=new JTextField(name,size);
		textField.setFont(new Font("Serif", Font.ITALIC, 15));
		textField.setForeground(new Color(195, 195, 195));
	
	textField.addMouseListener(new MouseAdapter() {
		@Override
		public void mouseClicked(MouseEvent e) {
			textField.setText("");
			textField.setForeground(new Color(0,0,0));
			textField.setFont(new Font("Serif", Font.PLAIN, 15));
		}
	});return textField;
	}
	
	public static JPasswordField getPasswordField(){
		password=new JPasswordField();
		return password;
	}
	
	public static JPasswordField getPasswordField(String name,int size){
		password=new JPasswordField(name,size);
		password.setFont(new Font("Serif", Font.ITALIC, 15));
		password.setForeground(new Color(195, 195, 195));
	
		password.addMouseListener(new MouseAdapter() {
		@Override
		public void mouseClicked(MouseEvent e) {
			password.setText("");
			password.setForeground(new Color(0,0,0));
			password.setFont(new Font("Serif", Font.PLAIN, 15));
		}
	});return password;
	}
	
	public static JPasswordField getPasswordFieldWithKeyListener(String name,int size,Action listen){
		password=new JPasswordField(name,size);
		password.setFont(new Font("Serif", Font.ITALIC, 15));
		password.setForeground(new Color(195, 195, 195));
		password.addFocusListener(new FocusListener() {
			
			@Override
			public void focusLost(FocusEvent arg0) {
				if(password.getText().equals("")){
					password.setText("password");
					password.setFont(new Font("Serif", Font.ITALIC, 15));
					password.setForeground(new Color(195, 195, 195));
				}
			}
			
			@Override
			public void focusGained(FocusEvent arg0) {
				
					password.setText("");
					password.setForeground(new Color(0,0,0));
					password.setFont(new Font("Serif", Font.PLAIN, 15));
				
			}
		});
		password.addMouseListener(new MouseAdapter() {
		@Override
		public void mouseClicked(MouseEvent e) {
			password.setText("");
			password.setForeground(new Color(0,0,0));
			password.setFont(new Font("Serif", Font.PLAIN, 15));
		}
	});password.addActionListener(listen);
	password.getInputMap().put(KeyStroke.getKeyStroke("ENTER"), "action");
	password.getActionMap().put("action", listen);
		return password;
	}
	
	public static JPasswordField getPasswordField(String name){
		password=new JPasswordField(name);
		return password;
	}
	
	public static JTextArea getTextArea(){
		textArea=new JTextArea();
		return textArea;
	}
	public static JTextArea getTextArea(String name){
		textArea=new JTextArea(name);
		return textArea;
	}
	
	
	public static JButton getLink(String name){
		 JButton searchButton = new JButton(name);
		 searchButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					
				}
			});
		 searchButton.setBorder(null);
		 searchButton.setBorderPainted(false);
		 searchButton.setFont(new Font("Serif", Font.PLAIN, 15));
		searchButton.setBackground(DesignUI.c);
			

			Hashtable<TextAttribute, Object> map1 = new Hashtable<TextAttribute, Object>();
			map1.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
			searchButton.setFont(searchButton.getFont().deriveFont(map1));
			return searchButton;
	}
	public static JButton getButtonKey(String name,Action listen){
		JButton sendButton = DesignUI.getButton(name);
		sendButton.addActionListener(listen);
		sendButton.getInputMap().put(KeyStroke.getKeyStroke("ENTER"), "action");
		sendButton.getActionMap().put("action", listen);
		return sendButton;
	}
	
	public static JTextField getTextFieldkey(final String name, int size,Action listen){
	textField=new JTextField(name,size);
	textField.setFont(new Font("Serif", Font.ITALIC, 15));
	textField.setForeground(new Color(195, 195, 195));
textField.addFocusListener(new FocusListener() {
	
	@Override
	public void focusLost(FocusEvent arg0) {
		if(textField.getText().equals("")){
		textField.setText(name);
		textField.setFont(new Font("Serif", Font.ITALIC, 15));
		textField.setForeground(new Color(195, 195, 195));
		}
	}
	
	@Override
	public void focusGained(FocusEvent arg0) {
		if(textField.getText().equals(name)){
			textField.setText("");
			textField.setForeground(new Color(0,0,0));
			textField.setFont(new Font("Serif", Font.PLAIN, 15));
		}
	}
});
textField.addMouseListener(new MouseAdapter() {
	@Override
	public void mouseClicked(MouseEvent e) {
		if(textField.getText().equals(name)){
		textField.setText("");
		textField.setForeground(new Color(0,0,0));
		textField.setFont(new Font("Serif", Font.PLAIN, 15));
	}}
});
textField.addActionListener(listen);
textField.getInputMap().put(KeyStroke.getKeyStroke("ENTER"), "action");
textField.getActionMap().put("action", listen);
return textField;}
}
