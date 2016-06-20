package seniorOfJava.JDBC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import java.util.logging.Logger;

public class WorkWithDB{

	public static final Logger logger = Logger.getLogger("myLogger");
	static final String userName = "root";
	static final String password = "11111";
	static final String url = "jdbc:mysql://localhost/site";
	static final String driverName = "com.mysql.jdbc.Driver";
	
	public static void main(String[] args) throws SQLException{
		Scanner scanner = new Scanner(System.in);
		logger.info("go");
		
		Connection connection = null;
		Statement statement = null;
		
			connection = DriverManager.getConnection(url,userName,password);
			 statement = connection.createStatement();
		
		
		try {
			Class.forName(driverName);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		//createDB(statement); //when you need to create a table in your base
				
		boolean go = true;
		while(go){		
			
		System.out.println(" What do you want to do?");
		System.out.println(" select and press number:");
		System.out.println("                           1 - Create user");
		System.out.println("                           2 - Show all users");
		System.out.println("                           3 - Delete user for id");
		System.out.println("                           press any other key - Exit");
		System.out.println("");
		
		
		switch(scanner.nextByte()){
		case 1: addUserToTable(statement);
			break;
		case 2: showUser(statement);
			break;
		case 3: deleteUser(statement);
			break;
		default: go = false;
		}
		}		
		if(!statement.isClosed()){ 
			statement.close();
			logger.info("statement close");
			}
		if(!connection.isClosed()){ 
			connection.close();
			logger.info("connection close");
			}
		logger.info("end");
		}
	
	private static void deleteUser(Statement statement) {
		Scanner sc = new Scanner(System.in);
		System.out.println(" Input User's ID:\n");
		try {
			statement.executeUpdate("DELETE FROM users WHERE UserID = '" 
									+ sc.nextInt() + "'");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	private static void showUser(Statement statement) {
		try {
		    ResultSet rs = statement.executeQuery("SELECT * FROM users");
			while(rs.next()){
			System.out.println("---------------------------------------------");
		    System.out.println(" user's ID = " + rs.getInt("UserID"));
		    System.out.println(" user's name = " + rs.getString("FirstName"));
		    System.out.println(" user's login = " + rs.getString("Login"));
		    System.out.println(" user's email = " + rs.getString("Email"));
		    System.out.println(" user's password = " + rs.getString("Password"));
		    System.out.println("---------------------------------------------");
		    }
		} catch (SQLException e){
			e.printStackTrace();
		}
	}

	private static void addUserToTable(Statement statement){
		User user = createUser();
	try {
		statement.executeUpdate("INSERT INTO users SET" +
			" FirstName = '" + user.getFirstName() +
			"', Login = '"     + user.getLogin() +
			"', Email = '"     + user.getEmail() +
			"', Password = '"  + user.getPassword() +
			"'"/*
			+ ", PhoneNumber = '" + user.getPhoneNumber() +
			"', Adress = '" + user.getAdress() +
			"', BirthDay = " + user.getBirthDay() +
			", BirthMonth = " + user.getBirthMonth() +
			", BirthYear = " + user.getBirthYear()*/ );
	    } catch (SQLException e) {
		e.printStackTrace();
	    }
	logger.info("user  \"" + user.getFirstName() + "\" added to table");
    }
	
    private static void createDB(Statement statement){
	    logger.info("before create database");	
	    try {
			statement.executeUpdate("Create TABLE Users ("
					+ "UserID int AUTO_INCREMENT,"
			        + "FirstName varchar(30) NOT NULL,"
					+ "Login varchar(30) NOT NULL,"
			        + "Email varchar(30) NOT NULL,"
			        + "Password varchar(30) NOT NULL,"
			      /*  + "PhoneNumber varchar(30) NOT NULL,"
			        + "Adress varchar(30) NOT NULL,"
			        + "BirthDay int NOT NULL,"
			        + "BirthMonth int NOT NULL,"
			        + "BirthYear int NOT NULL,"*/
			        + "PRIMARY KEY (UserID)     )");
		} catch (SQLException e) {
			e.printStackTrace();
		} 
	    logger.info("after create database");
	   	}

    private static User createUser(){
	Scanner sc = new Scanner(System.in);
	User user = new User();
	
	System.out.println("enter user's name");
	user.setFirstName(sc.nextLine());
	
	System.out.println("enter user's login");
	user.setLogin(sc.nextLine());
	
	System.out.println("enter user's email");
	user.setEmail(sc.nextLine());
	
	System.out.println("enter user's password");
	user.setPassword(sc.nextLine());
	
	/*System.out.println("enter user's phoneNumber");
	user.setPhoneNumber(sc.nextLine());
	
	System.out.println("enter user's adress");
	user.setAdress(sc.nextLine());
	
	System.out.println("enter user's birthDay");
	user.setBirthDay(sc.nextInt());
	
	System.out.println("enter user's birthMonth");
	user.setBirthMonth(sc.nextInt());
	
	System.out.println("enter user's birthYear");
	user.setBirthYear(sc.nextInt());*/
	
	System.out.println("");
	System.out.println("-----------------------------------------------------");
	System.out.println("--------User created!--------------------------------");
	System.out.println("-----------------------------------------------------");
	
	return user;
}

}
