package utility;
 
import java.sql.*;

import utility.ChatConfig;

public class Java2MySql 
{
	
		  String url = ChatConfig.PROPERTIES.getProperty("DBConnectionURL");
		  String dbName = ChatConfig.PROPERTIES.getProperty("DBName");
		  String driver = ChatConfig.PROPERTIES.getProperty("DBDriverClass");
		  String userName = ChatConfig.PROPERTIES.getProperty("DBUserName"); 
		  String password = ChatConfig.PROPERTIES.getProperty("DBPassword");
		  public void showData() {
			  try {
		  Class.forName(driver);
		  Connection conn = DriverManager.getConnection(url+dbName,userName,password);
		  Statement st = conn.createStatement();
		  ResultSet res = st.executeQuery("SELECT * FROM user");
		  System.out.println("USERNAME"+"\t" + "PASSWORD");
		  System.out.println("--------"+"\t" + "--------");
		  while (res.next()) {
		  String name = res.getString("username");
		  String pass = res.getString("password");
		  System.out.println(name +"\t\t" +pass);
		  }

		  conn.close();
		  } catch (Exception e) {
		  e.printStackTrace();
		  }
		  }
		  public boolean validateNewName(String givenName) {
			  boolean valid = true;
			  try {
		  Class.forName(driver);
		  Connection conn = DriverManager.getConnection(url+dbName,userName,password);
		  Statement st = conn.createStatement();
		  ResultSet res = st.executeQuery("SELECT * FROM login");
		  while (res.next()) {
			  if(res.getString("username").equalsIgnoreCase(givenName)){
				  valid=false;
			  }
		  }
		  conn.close();
		  
		  
		  } catch (Exception e) {
		  e.printStackTrace();
		  }
			  return valid;
		  }
		  public boolean validateUser(String givenName,String givenPassword) {
			  boolean valid = false;
			  try {
		  Class.forName(driver);
		  Connection conn = DriverManager.getConnection(url+dbName,userName,password);
		  Statement st = conn.createStatement();
		  ResultSet res = st.executeQuery("SELECT * FROM login");
		  while (res.next()) {
			  if(res.getString("username").equalsIgnoreCase(givenName) && res.getString("password").equals(givenPassword)){
				  valid=true;
			  }
		  }
		  conn.close();
		  
		  
		  } catch (Exception e) {
		  e.printStackTrace();
		  }
			  return valid;
		  }
		  
		  
		  public void addUser(String user,String pass) {
			  
			  try {
				  Class.forName(driver);
				  Connection conn = DriverManager.getConnection(url+dbName,userName,password);
				  Statement st = conn.createStatement();
				  int row = st.executeUpdate("INSERT into login values('"+user+"','"+pass+"')");
				  if(row>1){
					  System.out.println("User Added to database successfully");
				  }
				  
				  conn.close();
				  
				  
				  } catch (Exception e) {
				  e.printStackTrace();
				  }
					
			  
		}
		  
		  public static void main(String[] args) {
			Java2MySql sql = new Java2MySql();
			//sql.addUser("Pavansai", "password");
			sql.showData();
			//System.out.println(sql.validateUser("Ashish", "infy@123"));
		}
}


