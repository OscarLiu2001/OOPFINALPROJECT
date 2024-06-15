package com.example.ubike;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import BikeUser.Bike;

public class UBikeMaintenanceGUI extends JFrame {
    private JTextArea textArea;
    private JTextField bikeUIDField;
    private JTextField newTypeField;
    private JTextField stationUIDField;
    private JTextField dockUIDField;
    private JTextField bikeUIDToAddField;
    private JTextField bikeUID; 
    private JButton queryBikeButton;
    private JButton updateBikeButton;
    private JButton queryDockButton;
    private JButton addToDockButton;
    private JButton removeFromDockButton;
    private JButton queryStationButton;
    private JButton set_fix_Button;

    public UBikeMaintenanceGUI() {
        setTitle("UBike ���רt��");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        // �d�ߩM��s�������A
        JLabel bikeUIDLabel = new JLabel("���� UID:");
        bikeUIDLabel.setBounds(50, 20, 100, 30);
        add(bikeUIDLabel);

        bikeUIDField = new JTextField();
        bikeUIDField.setBounds(150, 20, 150, 30);
        add(bikeUIDField);

        queryBikeButton = new JButton("�d�ߨ���");
        queryBikeButton.setBounds(320, 20, 150, 30);
        queryBikeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String bikeUID = bikeUIDField.getText();
                String status = StationManager.queryBikeStatus(bikeUID);
                textArea.setText(status);
            }
        });
        add(queryBikeButton);

        JLabel newTypeLabel = new JLabel("�s����:");
        newTypeLabel.setBounds(50, 70, 100, 30);
        add(newTypeLabel);

        newTypeField = new JTextField();
        newTypeField.setBounds(150, 70, 150, 30);
        add(newTypeField);

        updateBikeButton = new JButton("��s����");
        updateBikeButton.setBounds(320, 70, 150, 30);
        updateBikeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String bikeUID = bikeUIDField.getText();
                String newType = newTypeField.getText();
                boolean success = StationManager.updateBikeStatus(bikeUID, newType);
                if (success) {
                    textArea.setText("����������s���\�C");
                } else {
                    textArea.setText("��s�����������ѡC");
                }
            }
        });
        add(updateBikeButton);

        // �d�ߩM�޲z���I�M���W
        JLabel stationUIDLabel = new JLabel("���I UID:");
        stationUIDLabel.setBounds(50, 120, 100, 30);
        add(stationUIDLabel);

        stationUIDField = new JTextField();
        stationUIDField.setBounds(150, 120, 150, 30);
        add(stationUIDField);

        queryStationButton = new JButton("�d�߯��I");
        queryStationButton.setBounds(320, 120, 150, 30);
        queryStationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String stationUID = stationUIDField.getText();
                String status = StationManager.queryStationStatus(stationUID);
                textArea.setText(status);
            }
        });
        add(queryStationButton);

        JLabel dockUIDLabel = new JLabel("���W UID:");
        dockUIDLabel.setBounds(50, 170, 100, 30);
        add(dockUIDLabel);

        dockUIDField = new JTextField();
        dockUIDField.setBounds(150, 170, 150, 30);
        add(dockUIDField);

        queryDockButton = new JButton("�d�ߨ��W");
        queryDockButton.setBounds(320, 170, 150, 30);
        queryDockButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String dockUID = dockUIDField.getText();
                String status = StationManager.queryDockStatus(dockUID);
                textArea.setText(status);
            }
        });
        add(queryDockButton);

        JLabel bikeUIDToAddLabel = new JLabel("�n�K�[������ UID:");
        bikeUIDToAddLabel.setBounds(50, 220, 150, 30);
        add(bikeUIDToAddLabel);

        bikeUIDToAddField = new JTextField();
        bikeUIDToAddField.setBounds(200, 220, 150, 30);
        add(bikeUIDToAddField);

        addToDockButton = new JButton("�W�[�쨮�W");
        addToDockButton.setBounds(370, 220, 150, 30);
        addToDockButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String dockUID = dockUIDField.getText();
                String bikeUID = bikeUIDToAddField.getText();
                boolean success = StationManager.addBikesToDock(dockUID, bikeUID);
                if (success) {
                    textArea.setText("�������\�K�[�쨮�W�C");
                } else {
                    textArea.setText("�K�[�����쨮�W���ѡC");
                }
            }
        });
        add(addToDockButton);

        removeFromDockButton = new JButton("�q���W����");
        removeFromDockButton.setBounds(530, 220, 150, 30);
        removeFromDockButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String dockUID = dockUIDField.getText();
                boolean success = StationManager.removeBikesFromDock(dockUID);
                if (success) {
                    textArea.setText("�������\�q���W�����C");
                } else {
                    textArea.setText("�q���W�����������ѡC");
                }
            }
        });
        add(removeFromDockButton);
        
        JLabel set_fix = new JLabel("��J���׭��m������: ");
        set_fix.setBounds(50, 260, 150, 30);
        add(set_fix);
        
        bikeUID = new JTextField();
        bikeUID.setBounds(200, 260, 150, 30);
        add(bikeUID);
        
        set_fix_Button = new JButton("���ק���");
        set_fix_Button.setBounds(370, 260, 150, 30);
        set_fix_Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String BikeUID = bikeUID.getText();
                try {
                	Bike bike = new Bike(BikeUID);
                	bike.set_fix_info("Null");
                	bike.set_status("operating");
                	textArea.setText("���m���\");
                }
                catch(Exception e1) {
                	textArea.setText(e1.getMessage());
                }
       
                
            }
        });
        add(set_fix_Button);

        // ��ܰϰ�
        textArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setBounds(50, 320, 700, 200);
        add(scrollPane);
    }

    public static void main(String[] args) {
        // ���ռƾڮw�s��
        DatabaseHelper.testConnection();
        // �ЫةM��� GUI
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                UBikeMaintenanceGUI gui = new UBikeMaintenanceGUI();
                gui.setVisible(true);
            }
        });
    }
}
