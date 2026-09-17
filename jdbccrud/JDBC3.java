package jdbccrud;

import java.sql.*;
import java.util.Scanner;

public class JDBC3 {

	public static void main(String[] args) {
		
		Connection conn = null;
		Scanner sc = new Scanner(System.in);
		
		try {
			String url = "jdbc:postgresql://localhost:5432/jdbccrud";
			String user = "postgres";
			String pass = "tiger";
			
			conn = DriverManager.getConnection(url,user,pass);
			//System.out.println(conn);
			
//			String userId = "Anshul";
//			String password = "anshul@1231";
			
//			login(userId,password,conn);
//			System.out.println(signup(userId, password, conn));
			
			System.out.println("Enter: ");
			System.out.println("1 for Signup");
			System.out.println("2 for login");
			System.out.print("Choice: ");
			int choice = sc.nextInt();
			
			if (choice==1) {
				
				signup(conn, sc);
			}
			else if(choice==2){

				login(conn, sc);
			}
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		finally {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	public static void login(Connection conn, Scanner sc) {
		
		System.out.print("Enter userId:");
		String userId = sc.next();
		
		System.out.print("Enter Password:");
		String password = sc.next();
		
		if(Authentication.passCheck(userId, password, conn))
		{
			System.out.println("Login Successful!!");
		}
		else {
			System.out.println("Incorrect Username or Password!!");
		}
	}
	
	public static boolean signup(Connection conn, Scanner sc) {
		
		System.out.print("Enter userId:");
		String userId = sc.next();
		
		System.out.print("Enter Password:");
		String password = sc.next();
		
		if (userId == "" || password == "") {
			System.err.println("Invalid Username or Password!!");
			return false;
		}
		else if (Authentication.exists(userId, conn)) {
			System.err.println("UserName already Exists!!");
			return false;
		}
		
		password = Authentication.encrypt(password);
		
		try {
			
			PreparedStatement stm = conn.prepareStatement("insert into crud.users (user_id,password) "
					+ "values ('" +userId+ "','" +password + "')");
			stm.executeUpdate();
			System.out.println("Sign Up Successfull");
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		return true;
	}
}
