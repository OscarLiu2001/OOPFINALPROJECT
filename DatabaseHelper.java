package com.example.ubike;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseHelper {
    // �ϥΧA����ڼƾڮw��󪺵�����|
	public static final String URL = "jdbc:mariadb://localhost:3306/ubike?useUnicode=true&characterEncoding=utf-8";
	public static final String user = "root";
    public static final String password = "oscar268457391";

    // �إ߼ƾڮw�s������k
    public static Connection connect() throws SQLException {
    	Connection conn = DriverManager.getConnection(URL, user, password);
    	return conn;
    }

    // ���ռƾڮw�s������k
    public static void testConnection() {
        try (Connection conn = connect()) {
            if (conn != null) {
                System.out.println("�ƾڮw�s�����\�I");
            } else {
                System.out.println("�ƾڮw�s�����ѡI");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("�ƾڮw�s�����ѡI");
        }
    }
}
