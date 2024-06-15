package BikeUser;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.time.LocalDateTime;
import java.util.ArrayList;



public class UserGUI extends JFrame implements ItemListener{
	
	private User user;

    // Components
    private JTextField phoneNumberField;
    private JPasswordField passwordField;
    private JTextField CardIDField;
    private JTextField EmailField;
    private JTextField topUpField;
    private JTextField searchBikeField;
    private JTextField searchDockField;
    private JTextField reportBikeUID;
    private JButton loginButton;
    private JButton registerButton;
    private JButton registerforCardButton;
    private JButton EmailButton;
    private JButton topUpButton;
    private JButton showRecord;
    private JButton showCardRecord;
    private JButton showRemainMoney;
    private JButton searchBikeButton;
    private JButton rentBikeButton;
    private JButton returnBikeButton;
    private JButton reportProblem;
    private JTextArea displayArea;
    private Choice choice;
    private String selectedItem;
    private String BikeUID;
    
    public UserGUI() {

        // Set up the frame
        setTitle("Bike Rental System");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Set layout manager
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        // Add components
        // Phone Number Label and TextField
        gbc.gridx = -1;
        gbc.gridy = -1;
        add(new JLabel("Phone Number:"), gbc);

        phoneNumberField = new JTextField(10);
        gbc.gridx = 1;
        add(phoneNumberField, gbc);

        // Password Label and PasswordField
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(new JLabel("Password:"), gbc);

        passwordField = new JPasswordField(10);
        gbc.gridx = 1;
        add(passwordField, gbc);

        // Login Button
        loginButton = new JButton("Login");
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        add(loginButton, gbc);
        loginButton.addActionListener(e -> login());

        // Register Button
        registerButton = new JButton("Register");
        gbc.gridy = 1;
        add(registerButton, gbc);
        registerButton.addActionListener(e -> register());

        // Register for card
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(new JLabel("Card Number:"), gbc);

        CardIDField = new JTextField(16);
        gbc.gridx = 1;
        add(CardIDField, gbc);

        registerforCardButton = new JButton("Register for card");
        gbc.gridx = 2;
        add(registerforCardButton, gbc);
        registerforCardButton.addActionListener(e -> setCard());
        
        //Register for email 
        gbc.gridx = 0;
        gbc.gridy = 3;
        add(new JLabel("Email:"), gbc);

        EmailField = new JTextField(16);
        gbc.gridx = 1;
        add(EmailField, gbc);

        EmailButton = new JButton("Register for email");
        gbc.gridx = 2;
        add(EmailButton, gbc);
        EmailButton.addActionListener(e -> setEmail());
        
        
        //Top up  
        gbc.gridx = 0;
        gbc.gridy = 4;
        add(new JLabel("Top up amount:"), gbc);

        topUpField = new JTextField(16);
        gbc.gridx = 1;
        add(topUpField, gbc);

        topUpButton = new JButton("Top up ");
        gbc.gridx = 2;
        add(topUpButton, gbc);
        topUpButton.addActionListener(e -> topUp());
        
        //Show remain Money 
        gbc.gridx = 0;
        gbc.gridy = 5;
        showRemainMoney = new JButton("Show Remain Money ");
        add(showRemainMoney, gbc);
        showRemainMoney.addActionListener(e -> showRemainMoney());
        
        // Show Record Button
        showRecord = new JButton("Show Record");
        gbc.gridx = 1;
        add(showRecord, gbc);
        showRecord.addActionListener(e -> showRecord());
        
        // Show Card Record Button
        showCardRecord = new JButton("Show Card Record");
        gbc.gridx = 2;
        add(showCardRecord, gbc);
        showCardRecord.addActionListener(e -> showCardRecord());
        
        
        //search for bikes
        gbc.gridx = 0;
        gbc.gridy = 6;
        add(new JLabel("Enter Station Name for searching"), gbc);
        
        gbc.gridx = 1;
        searchBikeField = new JTextField(50);
        add(searchBikeField,gbc);
        
        searchBikeButton = new JButton("Search");
        gbc.gridx = 2;
        add(searchBikeButton, gbc);
        searchBikeButton.addActionListener(e -> searchBike());
        
        //enter the dock for rent/return
        gbc.gridx = 0;
        gbc.gridy = 7;
        add(new JLabel("Enter the dock for rent/return"), gbc);
        
        gbc.gridx = 1;
        searchDockField = new JTextField(16);
        add(searchDockField,gbc); 
        
        // Rent Bike Button
        rentBikeButton = new JButton("Rent Bike");
        gbc.gridx = 2;
        add(rentBikeButton, gbc);
        rentBikeButton.addActionListener(e -> rentBike());

        // Return Bike Button
        returnBikeButton = new JButton("Return Bike");
        gbc.gridx = 3;
        add(returnBikeButton, gbc);
        returnBikeButton.addActionListener(e -> returnBike());

        //report fixing information
        
        gbc.gridx = 0;
        gbc.gridy = 8;
        add(new JLabel("Report a Problem"),gbc);
        
        gbc.gridx = 1;
        reportBikeUID = new JTextField(20);
        add(reportBikeUID,gbc);
        
        gbc.gridx = 2;
        choice = new Choice();
        choice.add("Flat_Tyre");
        choice.add("Broken_Seats");
        choice.add("Broken_Brakes");
        choice.add("Others");
        add(choice,gbc);
        choice.addItemListener(this);
        
        gbc.gridx = 3;
        reportProblem = new JButton("Report");
        add(reportProblem, gbc);
        reportProblem.addActionListener(e -> reportProblem());
        
        
        // Display Area
        displayArea = new JTextArea(10, 30);
        displayArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(displayArea);
        gbc.gridx = 0;
        gbc.gridy = 9;
        gbc.gridwidth = 3;
        add(scrollPane, gbc);

        setVisible(true);
        
        
    }
    // Methods to handle button actions
    private void register() {
    	try {
    		long phoneNumber = Long.parseLong(phoneNumberField.getText());
            String password = new String(passwordField.getPassword());
            user = new User(phoneNumber, password);
            updateDisplay("Registration successful.");
    	}
    	catch(NumberFormatException e0) {
    		updateDisplay("Wrong phone number format, can contain numbers!");
    	}
    	catch(Exception ex) {
    		updateDisplay(ex.getMessage());
    	}
    	
    }
    
    private void login() {
    	try {
    		long phoneNumber = Long.parseLong(phoneNumberField.getText());
            String password = new String(passwordField.getPassword());
            this.user = new User();
            if (user != null) {
                user.loggin(phoneNumber, password);
                updateDisplay("Login successful.");
            } else {
            	updateDisplay("Login failed.");
            }
    	}
    	catch(NumberFormatException e0) {
    		updateDisplay("Wrong phone number format, can contain numbers!");
    	}
    	catch(Exception ex) {
    		updateDisplay(ex.getMessage());
    	}
    }
    
    private void setCard() {
    	try {
    		long card_id = Long.parseLong(CardIDField.getText());         
            if (user != null) {
                user.setCard(card_id);
                updateDisplay("Card regitration successful!");
            } else {
            	updateDisplay("Please log in first.");
            }
    	}
    	catch(NumberFormatException e0) {
    		updateDisplay("Wrong card number format, can contain numbers!");
    	}
    	catch(Exception ex) {
    		updateDisplay(ex.getMessage());
    	}
    	
    }
    
    private void setEmail() {
    	try {
    		String email = new String(EmailField.getText());        
            if (user != null) {
                user.Set_email(email);
                updateDisplay("Email regitration successful!");
            } else {
            	updateDisplay("Please log in first.");
            }
    	}
    	catch(Exception ex) {
    		updateDisplay(ex.getMessage());
    	}
    }
    
    private void topUp() {
    	try {
    		int amount = Integer.parseInt(topUpField.getText());        
            if (user != null) {
                user.top_up(amount);;
                updateDisplay("Top Up successful! Your remaining money: " + user.getRemainMoney());
            } else {
            	updateDisplay("Please log in first.");
            }
    	}
    	catch(NumberFormatException e0) {
    		updateDisplay("Invalid top up number, please enter only numbers!");
    	}
    	catch(Exception ex) {
    		updateDisplay(ex.getMessage());
    	}
    }
    
    private void showRemainMoney() {
    	try {      
            if (user != null) {
                int remain_money = user.getRemainMoney();
                updateDisplay("The remain money in your card: " + remain_money);
            } else {
            	updateDisplay("Please log in first.");
            }
    	}
    	catch(Exception ex) {
    		updateDisplay(ex.getMessage());
    	}
    }
    

    private void showRecord() {
    	try {
    		 if (user != null) {
                 ArrayList<String> history = user.search_history();
                 updateDisplayHold("This is your records:",history);
             } else {
            	 updateDisplay("Please log in first.");
             }
    	}
    	catch(Exception ex) {
    		updateDisplay(ex.getMessage());
    	}
    }
    
    private void showCardRecord() {
    	try {
   		 	if (user != null) {
                ArrayList<String> history = user.card_history();
                updateDisplayHold("This is your card records:",history);
                
            } else {
            	updateDisplay("Please log in first.");
            }
    	}	
    	catch(Exception ex) {
    		updateDisplay(ex.getMessage());
    	}
    }
    
    private void searchBike() {
    	try {
   		 	if (user != null) {
                ArrayList<String> results = user.search_station(searchBikeField.getText());
                for (String result : results) {
                	updateDisplay(result);
                }
            } else {
            	updateDisplay("Please log in first.");
            }
    	}
    	catch(Exception ex) {
    		updateDisplay(ex.getMessage());
    	}
    }
    
    private void rentBike() {
    	try {
   		 	if (user != null) {
                String dockUID = new String(searchDockField.getText());
                Dock dock = new Dock(dockUID);
                user.rent_bike(dock);
                updateDisplay("Successfully rented a bike!");
            } else {
            	updateDisplay("Please log in first.");
            }
    	}
    	catch(Exception ex) {
    		updateDisplay(ex.getMessage());
    	}
    }
    
    private void returnBike() {
    	try {
   		 	if (user != null) {
                String dockUID = new String(searchDockField.getText());
                Dock dock = new Dock(dockUID);
                user.return_bike(dock);
                updateDisplay("Successfully returned a bike!");
            } else {
            	updateDisplay("Please log in first.");
            }
    	}
    	catch(Exception ex) {
    		updateDisplay(ex.getMessage());
    	}
    }
    
    
    
    private void updateDisplay(String display) {
        displayArea.setText("User info: \n");
        displayArea.append(new String(display));
        
        
    }
    
    private void updateDisplayHold(String info, ArrayList<String> holds) {
    	displayArea.setText(info + "\n");
    	for(String hold : holds){
    		displayArea.append(hold);
    	}
    }
    
    private void reportProblem() {
    	try {
   		 	if (user != null) {
                Bike bike = new Bike(this.BikeUID);
                user.repair_report(bike, this.selectedItem);
                updateDisplay("Successfully updated a problem: " + this.selectedItem);
            } else {
            	updateDisplay("Please log in first.");
            }
    	}
    	catch(Exception ex) {
    		updateDisplay("please choose a problem to report");
    	}
    	
    }
	@Override
	public void itemStateChanged(ItemEvent e) {
		selectedItem = new String(choice.getSelectedItem());
		BikeUID = new String(reportBikeUID.getText());
		 
	}
	

}
