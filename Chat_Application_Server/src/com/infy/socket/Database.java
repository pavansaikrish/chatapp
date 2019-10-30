package com.infy.socket;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import utility.ChatConfig;

public class Database {

	public String filePath;
	String url = ChatConfig.PROPERTIES.getProperty("DBConnectionURL");
	String dbName = ChatConfig.PROPERTIES.getProperty("DBName");
	String driver = ChatConfig.PROPERTIES.getProperty("DBDriverClass");
	String userName = ChatConfig.PROPERTIES.getProperty("DBUserName");
	String password = ChatConfig.PROPERTIES.getProperty("DBPassword");

	public Database(String filePath) {
		this.filePath = filePath;
	}
	public Database(){}

	public boolean userExists(String uname, String pwd) {
		boolean flag = false;
		try {

			Class.forName(driver);
			Connection conn = DriverManager.getConnection(url + dbName,
					userName, password);
			Statement st = conn.createStatement();
			ResultSet res = st
					.executeQuery("SELECT * FROM user WHERE username='" + uname
							+ "'");

			if (res.next()) {
				flag = true;
			} else {

				st.executeUpdate("insert into user values('" + uname + "','"
						+ pwd + "','offline')");
				flag = false;
			}
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	public boolean checkLogin(String uname, String pwd) {
		try {

			Class.forName(driver);
			Connection conn = DriverManager.getConnection(url + dbName,
					userName, password);
			Statement st = conn.createStatement();
			ResultSet res = st
					.executeQuery("SELECT * FROM user WHERE username='" + uname
							+ "' AND password='" + pwd + "'");

			if (res.next()) {
				st.executeUpdate("UPDATE user SET status='online' WHERE username='"
						+ uname + "' AND password='" + pwd + "'");
				return true;
			}
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public void logout(String uname) {
		try {

			Class.forName(driver);
			Connection conn = DriverManager.getConnection(url + dbName,
					userName, password);
			Statement st = conn.createStatement();
			@SuppressWarnings("unused")
			int res = st
					.executeUpdate("UPDATE user SET status='offline' WHERE username='"
							+ uname + "'");

			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<String> findOnlineUsers(String uname) {
		List<String> list = new ArrayList<>();
		try {

			Class.forName(driver);
			Connection conn = DriverManager.getConnection(url + dbName,
					userName, password);
			Statement st = conn.createStatement();
			ResultSet rs = st
					.executeQuery("SELECT username FROM user where status='online' and username != '"
							+ uname + "'");
			while (rs.next()) {
				String str = rs.getString("username");
				list.add(str);
			}
			if (list.isEmpty()) {
				list = null;
			}
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public List<String> findOnlineUsers1(String sender, String uname) {
		List<String> list = new ArrayList<>();
		try {

			Class.forName(driver);
			Connection conn = DriverManager.getConnection(url + dbName,
					userName, password);
			PreparedStatement st = conn
					.prepareStatement("SELECT username FROM user where status='online' and  username !=? and  username like ?");
			st.setString(1, sender);
			st.setString(2, uname + "%");
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				String str = rs.getString("username");
				list.add(str);
			}
			System.out.println(list);
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public boolean changePassword(String sender, String content,String oldPassword) {
		boolean flag = false;

		try {
			Class.forName(driver);
			Connection conn = DriverManager.getConnection(url + dbName,
					userName, password);

			Statement st = conn.createStatement();
			  ResultSet res = st.executeQuery("SELECT password FROM user where username='"+sender+"'");
			  String oldPassword1="";
			  if(res.next()){
			    oldPassword1 = res.getString("password");
			   }
		 
			if (!oldPassword.equals(oldPassword1)) {
				flag =false;
			} else {
				PreparedStatement pst = conn
						.prepareStatement("UPDATE user SET password=? where username = ? ");
				pst.setString(2, sender);
				pst.setString(1, content);
				int rs = pst.executeUpdate();
				if (rs == 1)
					flag = true;
			} 
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;

	}
public static void main(String[] args) {
	new Database("").changePassword("ashish", "ashish1", "ashish");
}
}
