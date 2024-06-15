package com.example.ubike;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StationManager {

    // 琩高ó进篈
    public static String queryBikeStatus(String bikeUID) {
        String sql = "SELECT * FROM bikes WHERE BikeUID = ?";
        try (Connection conn = DatabaseHelper.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, bikeUID);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return "ó进 UID: " + bikeUID + "摸: " + rs.getString("Type");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "礚猭т赣 UID ó进";
    }

    // 穝ó进篈
    public static boolean updateBikeStatus(String bikeUID, String newType) {
        String sql = "UPDATE bikes SET Type = ? WHERE BikeUID = ?";
        try (Connection conn = DatabaseHelper.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, newType);
            pstmt.setString(2, bikeUID);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // 琩高翴獺のㄤó琖篈
    public static String queryStationStatus(String stationUID) {
        StringBuilder result = new StringBuilder();
        String stationSql = "SELECT * FROM station WHERE StationUID = ?";
        String dockSql = "SELECT * FROM docks WHERE StationUID = ?";

        try (Connection conn = DatabaseHelper.connect();
             PreparedStatement stationPstmt = conn.prepareStatement(stationSql)) {
            stationPstmt.setString(1, stationUID);
            try (ResultSet rs = stationPstmt.executeQuery()) {
                if (rs.next()) {
                    result.append("翴 UID: ").append(stationUID).append("\n");
                    result.append("翴 ID: ").append(rs.getString("StationID")).append("\n");
                    result.append("翴嘿: ").append(rs.getString("StationName_Zh_tw")).append("\n");
                    result.append("翴: ").append(rs.getString("StationAddress_Zh_tw")).append("\n\n");
                }
            }

            try (PreparedStatement dockPstmt = conn.prepareStatement(dockSql)) {
                dockPstmt.setString(1, stationUID);
                try (ResultSet rs = dockPstmt.executeQuery()) {
                    result.append("ó琖篈:\n");
                    while (rs.next()) {
                        String dockUID = rs.getString("DockUID");
                        String bikeUID = rs.getString("Bike");
                        if (bikeUID != null && !bikeUID.isEmpty()) {
                            result.append("ó琖 UID: ").append(dockUID).append("ó进 UID: ").append(bikeUID).append("\n");
                        } else {
                            result.append("ó琖 UID: ").append(dockUID).append("礚ó进\n");
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return "礚猭т赣 UID 翴";
        }

        return result.toString();
    }

    // 琩高ó琖篈
    public static String queryDockStatus(String dockUID) {
        String sql = "SELECT * FROM docks WHERE DockUID = ?";
        try (Connection conn = DatabaseHelper.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, dockUID);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    String bike = rs.getString("Bike");
                    if (bike != null && !bike.isEmpty()) {
                        return "ó琖 UID: " + dockUID + "Τó进 UID: " + bike;
                    } else {
                        return "ó琖 UID: " + dockUID + "ヘ玡礚ó进";
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "礚猭т赣 UID ó琖";
    }

    // 糤ó进ó琖
    public static boolean addBikesToDock(String dockUID, String bikeUID) {
        String sql = "UPDATE docks SET Bike = ? WHERE DockUID = ?";
        try (Connection conn = DatabaseHelper.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, bikeUID);
            pstmt.setString(2, dockUID);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // 眖ó琖簿埃ó进
    public static boolean removeBikesFromDock(String dockUID) {
        String sql = "UPDATE docks SET Bike = NULL WHERE DockUID = ?";
        try (Connection conn = DatabaseHelper.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, dockUID);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}