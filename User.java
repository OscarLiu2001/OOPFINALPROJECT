package BikeUser;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


public class User {
	private long phone_number;
	private String password;
	private String email;
	private Long card_id;
	private int remain_money;
	private boolean rent_status = false;
	private LocalDateTime last_rent_time;
	private String currently_renting_BikeUID;
	private String last_rent_stationUID;
	
	//DIFAULT CONSTRUCTOR
	public User() {
		
	}
	
	//CONTRUCTOR FOR REGISTRATION
	public User(long phone_number, String password) throws Exception {
		this.phone_number = phone_number;
		this.password = new String(password);
		String insertSQL = "INSERT INTO user (phone_number, password) VALUES (?,?)";
		String selectSQL = "SELECT phone_number FROM user";
		try {
			PreparedStatement preparedStatement0 = DataBaseConnecter.connect().prepareStatement(selectSQL);
			ResultSet rs = preparedStatement0.executeQuery();
			while(rs.next()) {
				if(this.phone_number == rs.getLong("phone_number")) {
					throw new Exception("Phone Number has been registered!");
				}
			}
			rs.close();
			preparedStatement0.close();
			
			if(password.length() != 0) {
				PreparedStatement preparedStatement = DataBaseConnecter.connect().prepareStatement(insertSQL);
				preparedStatement.setLong(1, this.phone_number);
				preparedStatement.setString(2, this.password);
				preparedStatement.executeUpdate();
				preparedStatement.close();
			}
			else {
				throw new Exception("please enter your password!");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
		
		}
	
	//LOGGING IN: LOAD ALL THE DATA IN THE ROW FOR THE CORRESPONDIND PHONE NUMBER
	public void loggin(long phone_number, String password) throws Exception{
		try {
			String selectSQL = "SELECT * FROM user WHERE phone_number = ?";
			PreparedStatement preparedStatement0 = DataBaseConnecter.connect().prepareStatement(selectSQL);
			preparedStatement0.setLong(1, phone_number);
			ResultSet rs = preparedStatement0.executeQuery();
			if(rs.next()) {
				if(password.length() == 0) {
					throw new Exception("please enter your password!");
				}
				else if(rs.getString("password").equals(password)) {
					this.phone_number = phone_number;
					this.password = new String(password);
					this.card_id = rs.getLong("card_id");
					this.remain_money = (int)rs.getInt("card_remain_money");
					this.rent_status = (boolean)rs.getBoolean("rent_status");
					if(rs.getString("email") != null) {
						this.email = rs.getString("email");
					}
					if(rs.getString("currently_renting_BikeUID") != null) {
						this.currently_renting_BikeUID = rs.getString("currently_renting_BikeUID");
					}
					if(rs.getTimestamp("last_rent_time") != null) {
						this.last_rent_time = rs.getTimestamp("last_rent_time").toLocalDateTime();
					}
					if(rs.getString("last_rent_stationUID") != null) {
						this.last_rent_stationUID = rs.getString("last_rent_stationUID");
					}
					rs.close();
					preparedStatement0.close();
				}
				else{
					throw new Exception("Wrong password!");
				}
				
			}
			else {
				throw new Exception("Phone Number has not been registered!");
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		}
	
	//REGISTER FOR CARD
	public void setCard(long card_id) throws Exception{
		try {
			if(this.card_id == null || this.card_id == 0) {
				this.card_id = card_id;
				this.remain_money = 0;
				String updateSQL = "UPDATE user SET card_id = ? WHERE phone_number = ?";				
				PreparedStatement preparedStatement = DataBaseConnecter.connect().prepareStatement(updateSQL);
				preparedStatement.setLong(2,this.phone_number);
				preparedStatement.setLong(1, this.card_id);
				preparedStatement.executeUpdate();
				preparedStatement.close();
			}
			else {
				throw new Exception("This card has been registered!");
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}
	
	//search for renting history
	public ArrayList<String> search_history() throws Exception{
		ArrayList<String> records = new ArrayList<>();
		String selectSQL = "SELECT * FROM record WHERE phone_number = ?";
		try {
			PreparedStatement preparedStatement0 = DataBaseConnecter.connect().prepareStatement(selectSQL);
			preparedStatement0.setLong(1, phone_number);
			ResultSet rs = preparedStatement0.executeQuery();
			int i = 0;
			while(rs.next()) {
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss ");
				String rent_time = rs.getTimestamp("rent_time").toLocalDateTime().format(formatter);
				String return_time = rs.getTimestamp("return_time").toLocalDateTime().format(formatter);
				long cost = rs.getLong("cost");
				String rent_station = rs.getString("rent_station");
				String return_station = rs.getString("return_station");
				String output = i++ + ": Rent time: " + rent_time + ",  Return time: " + return_time + ", cost: " + cost + ", Rent station: " + rent_station + ", Return station: " + return_station + "\n"; 
				records.add(output);
			}
			rs.close();
			preparedStatement0.close();
		return records;
		}
		
		catch(SQLException e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
			
		}
	}
	
	//Return remain money
	public int getRemainMoney() {
		return this.remain_money;
	}
	
	//TOP UP MONEY TO THE CARD
	public void top_up(int top_up_value) throws Exception {
		try {
			if (top_up_value > 0) {
				if(this.card_id != null && this.card_id != 0){
					this.remain_money += top_up_value;
					String updateSQL = "UPDATE user SET card_remain_money = ? WHERE card_id = ?";				
					PreparedStatement preparedStatement = DataBaseConnecter.connect().prepareStatement(updateSQL);
					preparedStatement.setLong(1, this.remain_money);
					preparedStatement.setLong(2, this.card_id);
					preparedStatement.executeUpdate();
					preparedStatement.close();
					
					String insertSQL1 = "INSERT INTO card_history (remain_money, deal_time, money, card_id) VALUES (?,?,?,?)";				
					PreparedStatement preparedStatement1 = DataBaseConnecter.connect().prepareStatement(insertSQL1);
					preparedStatement1.setLong(1, this.remain_money);
					preparedStatement1.setLong(3, top_up_value);
					preparedStatement1.setLong(4, this.card_id);
					preparedStatement1.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));
					preparedStatement1.executeUpdate();
					preparedStatement1.close();
					
				}
				else {
					throw new Exception("Card not registered yet!");
				}
				
			}	
			else {
				throw new Exception("invalid top up money!");
			}
		}
		catch(InputMismatchException e1) {
			System.out.println(e1.getMessage());
		}
		catch(SQLException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		
	}
	
	//SETTING EMAIL
	public void Set_email(String email) throws Exception {
		if(email.length() == 0) {
			throw new Exception("please enter your email");
		}
		else if(this.email != null) {
			throw new Exception("You have already registered an email.");
		}
		this.email = new String(email);
		try {
			String updateSQL = "UPDATE user SET email = ? WHERE phone_number = ?";				
			PreparedStatement preparedStatement = DataBaseConnecter.connect().prepareStatement(updateSQL);
			preparedStatement.setString(1, this.email);
			preparedStatement.setLong(2, this.phone_number);
			preparedStatement.executeUpdate();
			preparedStatement.close();
		}
		catch(SQLException e){
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
	}
	
	//SETTING RENT STATUS
	private void set_rent_status(boolean status) {
		try {
			String updateSQL = "UPDATE uer SET rent_status = ? ";
			PreparedStatement preparedStatement1 = DataBaseConnecter.connect().prepareStatement(updateSQL);
			preparedStatement1.setBoolean(1, status);
			preparedStatement1.executeUpdate();
			preparedStatement1.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		
	}

	//GETTING RENT STATUS
	public boolean get_rent_status() {
		return this.rent_status;
	}
	
	//Renting a bike
	public void rent_bike(Dock dock)throws Exception {	
		try {
			if(this.remain_money > 0 ){
				if (this.rent_status == false) {
					String selectSQL = "SELECT Bike, StationUID FROM docks WHERE DockUID = ?";
					PreparedStatement preparedStatement0 = DataBaseConnecter.connect().prepareStatement(selectSQL);
					preparedStatement0.setString(1, dock.getDockUID());
					ResultSet rs = preparedStatement0.executeQuery();
					if(rs.next()) {
						if(rs.getString("Bike").length() == 0) {
							throw new Exception("This dock currently has no bike, please try another one!");
						}
						
						else {
							this.last_rent_stationUID = new String(rs.getString("StationUID"));
							Bike bike = new Bike(rs.getString("Bike"));
							rs.close();
							preparedStatement0.close();
							if(bike.getStatus() == Bike.Status.operating) {
								bike.set_status("currently_rented");
								this.rent_status = true;
								this.last_rent_time = LocalDateTime.now();
								this.currently_renting_BikeUID = bike.getBike_id();
								dock.setBike("");
								
								String updateSQL = "UPDATE user SET rent_status = ?, last_rent_time = ?, currently_renting_BikeUID = ?, last_rent_StationUID = ? WHERE phone_number = ?";				
								PreparedStatement preparedStatement = DataBaseConnecter.connect().prepareStatement(updateSQL);
								preparedStatement.setBoolean(1, this.rent_status);
								preparedStatement.setTimestamp(2, Timestamp.valueOf(this.last_rent_time));
								preparedStatement.setString(3, this.currently_renting_BikeUID);
								preparedStatement.setString(4, this.last_rent_stationUID);
								preparedStatement.setLong(5, this.phone_number);
								preparedStatement.executeUpdate();
								preparedStatement.close();
								
						}
						
						else if(bike.getStatus() == null) {
							throw new Exception("No bikes at this dock now, try another one!");
						}
						else{
							throw new Exception(bike.getStatus() + ", try other bikes!");
						}
					}
					}
					else {
						throw new Exception("Unknown Dock");
					}
				}
				else {
					throw new Exception("You have already rented a bike, please return the bike fisrt.");
				}	
			}
			else {
				throw new Exception("no money remain in your card, please charge");
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
			throw new Exception("This dock currently has no bike!");
		}
	}
	
	//Returning a bike
	public void return_bike(Dock dock)throws Exception {
		try {	
			if(this.rent_status == true) {
				if(dock.getBike().length() == 0) {
					this.rent_status = false;
					Bike bike = new Bike(this.currently_renting_BikeUID);
					LocalDateTime now = LocalDateTime.now();
					long cost = cal_cost(this.last_rent_time, LocalDateTime.now());
					this.remain_money -= cost;
					
					
					
				
					String insertSQL0 = "INSERT INTO record (rent_time, BikeUID, rent_station, phone_number, return_time, return_station,cost) VALUES (?,?,?,?,?,?,?)";
					PreparedStatement preparedStatement1 = DataBaseConnecter.connect().prepareStatement(insertSQL0);
					preparedStatement1.setTimestamp(1, Timestamp.valueOf(this.last_rent_time));
					preparedStatement1.setString(2, bike.getBike_id());
					preparedStatement1.setString(3, this.last_rent_stationUID);
					preparedStatement1.setLong(4, this.phone_number);
					preparedStatement1.setTimestamp(5, Timestamp.valueOf(now));
					preparedStatement1.setString(6, dock.getStationUID());
					preparedStatement1.setLong(7, cost);
					preparedStatement1.executeUpdate();
					preparedStatement1.close();
				
					String insertSQL = "INSERT INTO card_history (card_id, deal_time, money, remain_money) VALUES (?,?,?,?)";
					PreparedStatement preparedStatement2 = DataBaseConnecter.connect().prepareStatement(insertSQL);
					preparedStatement2.setLong(1, this.card_id);
					preparedStatement2.setTimestamp(2, Timestamp.valueOf(now));
					preparedStatement2.setLong(3, -1*cost);
					preparedStatement2.setLong(4, this.remain_money);
					preparedStatement2.executeUpdate();
					preparedStatement2.close();
				
					String updateSQL2 = "UPDATE docks SET Bike = ? WHERE DockUID = ?";
					PreparedStatement preparedStatement3 = DataBaseConnecter.connect().prepareStatement(updateSQL2);
					preparedStatement3.setString(1, this.currently_renting_BikeUID);
					preparedStatement3.setString(2, dock.getDockUID());
					preparedStatement3.executeUpdate();
					preparedStatement3.close();
					
					String updateSQL3 = "UPDATE user SET card_remain_money = ?, rent_status = ?, currently_renting_BikeUID = ? WHERE phone_number = ?";
					PreparedStatement preparedStatement4 = DataBaseConnecter.connect().prepareStatement(updateSQL3);
					preparedStatement4.setLong(1, this.remain_money);
					preparedStatement4.setBoolean(2, this.rent_status);
					preparedStatement4.setString(3, null);
					preparedStatement4.setLong(4, this.phone_number);
					preparedStatement4.executeUpdate();
					preparedStatement4.close();
				
					bike.set_status("operating");
					this.currently_renting_BikeUID = null;
				}
				else {
					throw new Exception("This dock has already been parked!");
				}
				}
			else {
				throw new Exception("You have not rented a bike yet!");
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
		
	}
	
	//HELPER METHOD FOR CALCULATING COST
	private static long cal_cost(LocalDateTime rent_time, LocalDateTime return_time) {
		Duration time_spent = Duration.between(rent_time,return_time);
		long minutes = time_spent.toMinutes();
		if(minutes < 30) {
			return 0;
		}
		else if(minutes < 60 && minutes >= 30) {
			return 10;
		}
		else if(minutes < 240 && minutes >= 60) {
			return (long)Math.ceil(((double)(minutes - 30))/30)*10;
		}
		else if(minutes < 480 && minutes >= 240){
			return (long)Math.ceil(((double)(minutes - 240))/30)*20 + 70;
		}
		else {
			return (long)Math.ceil(((double)(minutes - 480))/30)*40 + 230;
		}
	}
	
	//repair info
	public void repair_report(Bike bike, String fix_info) throws Exception {
		bike.set_fix_info(fix_info);
		bike.set_status("non_operating");
	}
	
	//searching for bikes based on stationUID
	public ArrayList<String> search_station(String StationName) throws Exception{
		try {
			ArrayList<String> info = new ArrayList<>();
			String selectSQL0 = "SELECT station.StationAddress_Zh_tw, station.StationUID, docks.Bike FROM station JOIN docks ON station.StationUID = docks.StationUID WHERE StationName_Zh_tw = ?";
			PreparedStatement preparedStatement0 = DataBaseConnecter.connect().prepareStatement(selectSQL0);
			preparedStatement0.setString(1, StationName);
			ResultSet rs = preparedStatement0.executeQuery();
			int bikes_count = 0;
			int empty_docks = 0;
			String location = null;
			while(rs.next()) {
				location = rs.getString("StationAddress_Zh_tw");
				String BikeUID = rs.getString("Bike");
				if(!(BikeUID.equals(""))) {
					bikes_count++;
				}
				else {
					empty_docks++;
				} 
				
			}
			rs.close();
			preparedStatement0.close();
			if(bikes_count != 0 || empty_docks != 0) {
				String data = "StaitonName: " + StationName + ", Location: " + location + ", Number of Bikes: " + bikes_count + ", Normal: " + bikes_count + ", Electronic: 0"  + ", Empty Docks: " + empty_docks;
				info.add(data);
				return info;
			}
			else {
				throw new Exception("no bikes information for this station.");

			}
			
			
		}
		catch(SQLException e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
			
		}
	}
	
	//SHOW CARD HISTORY
	public ArrayList<String> card_history() throws Exception{
		ArrayList<String> card_records = new ArrayList<>();
		String selectSQL = "SELECT * FROM card_history WHERE card_id = ?";
		try {
			PreparedStatement preparedStatement0 = DataBaseConnecter.connect().prepareStatement(selectSQL);
			preparedStatement0.setLong(1, this.card_id);
			ResultSet rs = preparedStatement0.executeQuery();
			int i = 1;
			while(rs.next()) {
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss ");
				String deal_time = rs.getTimestamp("deal_time").toLocalDateTime().format(formatter);
				long money = rs.getLong("money");
				String remain_money = rs.getString("remain_money");
				String output = i++ + ": Deal time: " + deal_time +  ", Money: " + money + ", Remain Money: " + remain_money + "\n"; 
				card_records.add(output);
			}
			rs.close();
			preparedStatement0.close();
		return card_records;
		}
		
		catch(SQLException e) {
			e.printStackTrace();
			throw new Exception("Error occurred: " + e.getMessage());
			
		}
	}
}

