package com.example.ubike;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StationManager {

    // �d�ߨ������A
    public static String queryBikeStatus(String bikeUID) {
        String sql = "SELECT * FROM bikes WHERE BikeUID = ?";
        try (Connection conn = DatabaseHelper.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, bikeUID);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return "���� UID: " + bikeUID + "�A����: " + rs.getString("Type");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "�L�k���� UID �������C";
    }

    // ��s�������A
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

    // �d�߯��I�H���Ψ䨮�W���A
    public static String queryStationStatus(String stationUID) {
        StringBuilder result = new StringBuilder();
        String stationSql = "SELECT * FROM station WHERE StationUID = ?";
        String dockSql = "SELECT * FROM docks WHERE StationUID = ?";

        try (Connection conn = DatabaseHelper.connect();
             PreparedStatement stationPstmt = conn.prepareStatement(stationSql)) {
            stationPstmt.setString(1, stationUID);
            try (ResultSet rs = stationPstmt.executeQuery()) {
                if (rs.next()) {
                    result.append("���I UID: ").append(stationUID).append("\n");
                    result.append("���I ID: ").append(rs.getString("StationID")).append("\n");
                    result.append("���I�W��: ").append(rs.getString("StationName_Zh_tw")).append("\n");
                    result.append("���I�a�}: ").append(rs.getString("StationAddress_Zh_tw")).append("\n\n");
                }
            }

            try (PreparedStatement dockPstmt = conn.prepareStatement(dockSql)) {
                dockPstmt.setString(1, stationUID);
                try (ResultSet rs = dockPstmt.executeQuery()) {
                    result.append("���W���A:\n");
                    while (rs.next()) {
                        String dockUID = rs.getString("DockUID");
                        String bikeUID = rs.getString("Bike");
                        if (bikeUID != null && !bikeUID.isEmpty()) {
                            result.append("���W UID: ").append(dockUID).append("�A���� UID: ").append(bikeUID).append("\n");
                        } else {
                            result.append("���W UID: ").append(dockUID).append("�A�L����\n");
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return "�L�k���� UID �����I�C";
        }

        return result.toString();
    }

    // �d�ߨ��W���A
    public static String queryDockStatus(String dockUID) {
        String sql = "SELECT * FROM docks WHERE DockUID = ?";
        try (Connection conn = DatabaseHelper.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, dockUID);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    String bike = rs.getString("Bike");
                    if (bike != null && !bike.isEmpty()) {
                        return "���W UID: " + dockUID + "�A������ UID: " + bike;
                    } else {
                        return "���W UID: " + dockUID + "�A�ثe�L����";
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "�L�k���� UID �����W�C";
    }

    // �W�[�����쨮�W
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

    // �q���W��������
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