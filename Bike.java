package BikeUser;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Bike {
	private String bike_id;
	public  static enum Bike_type{Normal, Electronic};
	private Bike_type bike_type;
	public static enum Area{TPE, NWT, TAO, HSZ, HSQ, MIA, TXG, CYI, TNN, KHH, PIF };
	private Area area; 
	public enum Status{non_operating, operating, currently_rented, missing, unknown};
	private Status status;
	public static enum Fix_info{Flat_Tyre, Broken_Seats,Broken_Brakes,Others, Null};
	private Fix_info fix_info;
	
	//set a bike 
	public Bike(String bikeUID) throws Exception{
		try {
			String selectSQL = "SELECT * FROM bikes WHERE BikeUID = ?";				
			PreparedStatement preparedStatement = DataBaseConnecter.connect().prepareStatement(selectSQL);
			preparedStatement.setString(1,bikeUID);
			ResultSet rs = preparedStatement.executeQuery();
			this.bike_id = new String(bikeUID);
			if(rs.next()) {
				this.area = Area.valueOf(rs.getString("AuthorityID"));
				this.bike_type = Bike_type.valueOf(rs.getString("Type"));
				this.status = Status.valueOf(rs.getString("Status"));
				if(rs.getString("Fix_info") != null) {
					this.fix_info = Fix_info.valueOf(rs.getString("Fix_info"));
				}
			}
			else {
				throw new Exception("no bike found");
			}	
		}
		catch(SQLException e) {
			e.printStackTrace();
			throw new Exception("Invalid BikeUID, please enter a correct one!");
		}
	}

	//get the BikeUID
	public String getBike_id() {
		return new String(this.bike_id);
	}
	//update the status of the bike
	public void set_status(String status_s) throws Exception{
		status = Bike.Status.valueOf(status_s);
		try {
			String updateSQL = "UPDATE bikes SET Status = ?  WHERE BikeUID = ?";				
			PreparedStatement preparedStatement = DataBaseConnecter.connect().prepareStatement(updateSQL);
			switch(status) {
			case non_operating:
				this.status = Bike.Status.non_operating;
				preparedStatement.setString(1,"non_operating");
				preparedStatement.setString(2, this.bike_id);
				preparedStatement.executeUpdate();
				break;
			case operating:
				this.status = Bike.Status.operating;
				preparedStatement.setString(1,"operating");
				preparedStatement.setString(2, this.bike_id);
				preparedStatement.executeUpdate();
				break;
			case missing:
				this.status = Bike.Status.missing;
				preparedStatement.setString(1,"missing");
				preparedStatement.setString(2, this.bike_id);
				preparedStatement.executeUpdate();
				break;
			case currently_rented:
				this.status = Bike.Status.currently_rented;
				preparedStatement.setString(1,"currently_rented");
				preparedStatement.setString(2, this.bike_id);
				preparedStatement.executeUpdate();
				break;
			case unknown:
				break;
		}
		}
		catch(SQLException e) {
			e.printStackTrace();
			throw new Exception("Invalid BikeUID, please enter a correct one!");
		}
		
	}
	
	//get current status of a bike
	public Bike.Status getStatus() {
		return this.status;
	}
	
	//set the fix information of a bike
	public void set_fix_info(String fix_info_s) throws Exception {
		Bike.Fix_info fix_info = Bike.Fix_info.valueOf(fix_info_s);
		try {
			String updateSQL = "UPDATE bikes SET Fix_info = ?  WHERE BikeUID = ?";				
			PreparedStatement preparedStatement = DataBaseConnecter.connect().prepareStatement(updateSQL);
			switch(fix_info) {
			case Flat_Tyre:
				preparedStatement.setString(1,"flat_tyre");
				preparedStatement.setString(2, this.bike_id);
				preparedStatement.executeUpdate();
				break;
			case Broken_Seats:
				preparedStatement.setString(1,"broken_seats");
				preparedStatement.setString(2, this.bike_id);
				preparedStatement.executeUpdate();
				break;
			case Broken_Brakes:
				preparedStatement.setString(1,"broken_brakes");
				preparedStatement.setString(2, this.bike_id);
				preparedStatement.executeUpdate();
				break;
			case Others:
				preparedStatement.setString(1,"others");
				preparedStatement.setString(2, this.bike_id);
				preparedStatement.executeUpdate();
				break;
			case Null:
				preparedStatement.setNull(1, java.sql.Types.VARCHAR);;
				preparedStatement.setString(2, this.bike_id);
				preparedStatement.executeUpdate();
				break;
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
			throw new Exception("Invalid BikeUID, please enter a correct one!");
		}
	}
	
	
	
	
}