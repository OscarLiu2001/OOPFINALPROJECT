package BikeUser;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Dock {
	private String DockUID;
	private String DockID;
	private String StationUID;
	private String Bike;
	
	public Dock(String DockUID)throws Exception {
		try {
			String selectSQL = "SELECT * FROM docks WHERE DockUID = ?";
			PreparedStatement preparedStatement0 = DataBaseConnecter.connect().prepareStatement(selectSQL);
			preparedStatement0.setString(1, DockUID);
			ResultSet rs = preparedStatement0.executeQuery();
			if(rs.next()) {
				this.DockUID = new String(DockUID);
				this.DockID = new String(rs.getString("DockID"));
				this.StationUID = new String(rs.getString("StationUID"));
				this.Bike = new String(rs.getString("Bike"));
			}
			else {
				throw new Exception("Dock not exist!");
			}
			rs.close();
			preparedStatement0.close();
		}
		catch(SQLException e) {
			e.printStackTrace();
			throw new Exception("Invalid DockUID, please try again!");
		}
	}
	
	public String getDockUID() {
		return  new String(this.DockUID);
	}
	
	public void setBike(String BikeUID) throws Exception{
		try {
			String updateSQL = "UPDATE docks SET Bike = ? WHERE DockUID = ?";				
			PreparedStatement preparedStatement = DataBaseConnecter.connect().prepareStatement(updateSQL);
			preparedStatement.setString(1, BikeUID);
			preparedStatement.setString(2, this.DockUID);
			preparedStatement.executeUpdate();
		}
		catch(SQLException e) {
			e.printStackTrace();
			throw new Exception("Invalid DockUID, please try again!");
		}
	}
	
	public String getStationUID() {
		return  new String(this.StationUID);
	}
	
	public String getBike() {
		return new String(this.Bike);
	}

}

