package javaauthapp;

import java.sql.*;
import java.util.Scanner;

import jdbccrud.Authentication;

public class App {

	static int userToken;

	public static void main(String[] args) {

		Connection conn = null;
		Scanner sc = new Scanner(System.in);

		try {
			String url = "jdbc:postgresql://localhost:5432/jdbccrud";
			String user = "DB_user";
			String pass = "DB_pass";

			conn = DriverManager.getConnection(url, user, pass);

			while (true) {
				System.out.println("Choices:");
				System.out.println("1:Signup  2:login");
				System.out.println();
				System.out.print("Choice: ");
				int choice = sc.nextInt();

				if (choice == 1) {

					signup(conn, sc);

				} else if (choice == 2) {

					userToken = login(conn, sc);

					if (userToken != -1) {
						int cho = -1;
						while (cho != 0) {
							System.out.println("Choices:");
							System.out.println("0:Logout  1:ResetPass");
							System.out.print("Choice:");
							cho = sc.nextInt();
							switch (cho) {
							case 0: {
								logout();
								break;
							}
							case 1: {
								resetPass(conn, sc);
								break;
							}

							}
						}

					}

				} else if (choice == 0) {
					break;
				}
			}

		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	public static int login(Connection conn, Scanner sc) {

		System.out.print("Enter userId:");
		String userId = sc.next();

		System.out.print("Enter Password:");
		String password = sc.next();

		if (Authentication.passCheck(userId, password, conn)) {
			System.out.println("---------------------------");
			System.out.println("Login Successful!!");
			System.out.println("---------------------------");
			System.out.println();

			PreparedStatement stm;
			try {
				stm = conn.prepareStatement("select id from crud.users where user_Id = ?");
				stm.setString(1, userId);
				ResultSet rs = stm.executeQuery();
				rs.next();
				String userToken = rs.getString("id");

				return Integer.parseInt(userToken);

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		System.out.println("---------------------------");
		System.err.println("Invalid Username or Password!!");
		System.out.println("---------------------------");
		return -1;

	}

	public static boolean signup(Connection conn, Scanner sc) {

		System.out.print("Enter userId:");
		String userId = sc.next();

		System.out.print("Enter Password:");
		String password = sc.next();

		if (userId == "" || password == "") {
			System.out.println("---------------------------");
			System.err.println("Invalid Username or Password!!");
			System.out.println("---------------------------");
			return false;
		} else if (Authentication.exists(userId, conn)) {
			System.out.println("---------------------------");
			System.err.println("UserName already Exists!!");
			System.out.println("---------------------------");

			return false;
		}

		password = Authentication.encrypt(password);

		try {

			PreparedStatement stm = conn.prepareStatement("insert into crud.users (user_id,password) values (? , ?)");
			stm.setString(1, userId);
			stm.setString(2, password);

			stm.executeUpdate();
			System.out.println("Sign Up Successfull");
		} catch (SQLException e) {

			e.printStackTrace();
		}
		return true;
	}

	public static void logout() {
		userToken = -1;
		System.out.println("---------------------------");
		System.out.println("Logout Successfull!!");
		System.out.println("---------------------------");
	}

	public static boolean resetPass(Connection conn, Scanner sc) {
		System.out.println("---------------------------");
		System.out.print("Enter New Password: ");
		String newPass = Authentication.encrypt(sc.next());

		try {
			PreparedStatement stm = conn.prepareStatement("Update crud.users set password = ? where id = ?");
			stm.setString(1, newPass);
			stm.setInt(2, userToken);
			System.out.println(stm.executeUpdate());
			System.out.println("---------------------------");
			System.out.println("Password Updated!!");
			System.out.println("---------------------------");
			return true;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;

	}
}