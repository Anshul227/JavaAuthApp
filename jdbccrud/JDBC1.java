package jdbccrud;
import java.sql.*;
import java.util.Scanner;


public class JDBC1 {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		
		try {
			String url = "jdbc:postgresql://localhost:5432/jdbccrud";
			String user = "postgres";
			String pass = "tiger";
			
			Connection conn = DriverManager.getConnection(url,user,pass);
			System.out.println("Connection established");
			
			
			while(true) {
				
				System.out.println("Enter the id:");
				String sql = sc.nextLine();
				
				System.out.println("Enter the name:");
				
				sql = sql+",'"+sc.nextLine()+"'";
				
				System.out.println("Enter the age:");
				sql = sql+","+sc.nextLine();
				
				System.out.println("Enter the gender:");
				sql = sql+",'"+sc.nextLine()+"')";
				
				PreparedStatement stm = conn.prepareStatement("insert into crud.student values("+sql);
				
				stm.execute();
				
				//conn.close();
						
				
			}
			
					
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		
		
	}
}
