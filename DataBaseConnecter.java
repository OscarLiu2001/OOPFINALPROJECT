package BikeUser;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DataBaseConnecter {
	public static final String URL = "jdbc:mariadb://localhost:3306/ubike?useUnicode=true&characterEncoding=utf-8";
	public static final String user = "root";
    public static final String password = "oscar268457391";
    
    public static Connection connect() throws SQLException{
    	Connection conn = DriverManager.getConnection(URL, user, password);
    	return conn;
    }
    public static void testConnection() {
        try (Connection conn = DataBaseConnecter.connect()){
            if (conn != null) {
                System.out.println("successfully connected!");
            } else {
                System.out.println("conncetion fail");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("conncetion fail");
        }
    }
    
}
