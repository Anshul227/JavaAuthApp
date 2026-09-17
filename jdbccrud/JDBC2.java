package jdbccrud;
import java.sql.*;

public class JDBC2 {

	public static void main(String[] args) {
		Connection conn = null;
		try {
			String url = "jdbc:postgresql://localhost:5432/jdbccrud";
			String user = "postgres";
			String pass = "tiger";
			
			 conn = DriverManager.getConnection(url,user,pass);
			 
			String table = " from crud.student";
			
			PreparedStatement sql =  conn.prepareStatement("select * "+table);
			
			ResultSet rs = sql.executeQuery();
			
			while(rs.next()) {
				System.out.println(rs.getInt("id")+" "+rs.getString("name")+
						" "+rs.getInt("age")+" "+rs.getString("gender"));
			}
			
			
			
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		finally {
			
			try {
				conn.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
			
		}
		
	}
}
