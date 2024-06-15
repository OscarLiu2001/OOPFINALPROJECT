package com.example.ubike;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BikeManager {

    public static String queryBikeStatus(String bikeId) {  // э钡Μ String 摸 bikeId
        String sql = "SELECT Status FROM bikes WHERE BikeUID = ?";
        try (Connection conn = DatabaseHelper.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, bikeId);  // ㄏノ setString ㄓ砞竚把计
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("Status");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "礚猭т赣 ID ó进";
    }

    public static boolean updateBikeStatus(String bikeId, String newStatus) {  // э钡Μ String 摸 bikeId
        String sql = "UPDATE bikes SET Status = ? WHERE BikeUID = ?";
        try (Connection conn = DatabaseHelper.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, newStatus);
            pstmt.setString(2, bikeId);  // ㄏノ setString ㄓ砞竚把计
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
