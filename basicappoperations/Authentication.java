package basicappoperations;

import java.sql.*;
public class Authentication {

	static String table = " from crud.users ";
	
	
	public static boolean passCheck(String userId,String pass,Connection conn) {
		
		try {
			PreparedStatement stm = conn.prepareStatement("select password"+table+"where user_id = ?");
			
			stm.setString(1, userId);
			
			ResultSet rs = stm.executeQuery();
			
			if(!rs.next()) {
				return false;
			}
			
			String userPass = rs.getString("password");
			if(decrypt(userPass).equals(pass)) {
				return true;
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return false;
		
	}
	
	
	
	public static String encrypt(String pass) {
		int key = 2;
		char[] arr = pass.toCharArray();
		
		String encPass = "";
		
		for(char ch : arr) {
			encPass = encPass + (char)((int)ch+key);
		}
		
		return encPass;
		
	}
	
	public static String decrypt(String pass) {
		
		int key = 2;
		char[] arr = pass.toCharArray();
		
		String dePass = "";
		
		for(char ch : arr) {
			dePass = dePass + (char)((int)ch-key);
		}
		
		return dePass;
	}
	
	public static boolean exists(String userId,Connection conn) {
		
		
		try {
			PreparedStatement stm = conn.prepareStatement("select id "+table+"where user_id = ?" );
			
			stm.setString(1, userId);
			
			ResultSet rs = stm.executeQuery();
			if (!rs.next()) {
				return false;
			}
			
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return true;
		
	}
}

