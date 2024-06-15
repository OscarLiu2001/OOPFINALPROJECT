package com.example.ubike;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseHelper {
    // 使用你的實際數據庫文件的絕對路徑
	public static final String URL = "jdbc:mariadb://localhost:3306/ubike?useUnicode=true&characterEncoding=utf-8";
	public static final String user = "root";
    public static final String password = "oscar268457391";

    // 建立數據庫連接的方法
    public static Connection connect() throws SQLException {
    	Connection conn = DriverManager.getConnection(URL, user, password);
    	return conn;
    }

    // 測試數據庫連接的方法
    public static void testConnection() {
        try (Connection conn = connect()) {
            if (conn != null) {
                System.out.println("數據庫連接成功！");
            } else {
                System.out.println("數據庫連接失敗！");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("數據庫連接失敗！");
        }
    }
}
