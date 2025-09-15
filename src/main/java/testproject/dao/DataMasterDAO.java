package testproject.dao;

import testproject.model.DataMaster;
import testproject.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DataMasterDAO {
    
    public List<DataMaster> getAllData() {
        List<DataMaster> dataList = new ArrayList<>();
        String query = "SELECT * FROM DATA_MASTER ORDER BY ID";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                DataMaster data = new DataMaster();
                data.setId(rs.getInt("ID"));
                data.setDataCode(rs.getString("DATA_CODE"));
                data.setDataValue(rs.getString("DATA_VALUE"));
                data.setDescription(rs.getString("DESCRIPTION"));
                dataList.add(data);
            }
            
        } catch (SQLException e) {
            System.err.println("Error fetching data from DATA_MASTER: " + e.getMessage());
            e.printStackTrace();
        }
        
        return dataList;
    }
    
    public DataMaster getDataById(int id) {
        String query = "SELECT * FROM DATA_MASTER WHERE ID = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                DataMaster data = new DataMaster();
                data.setId(rs.getInt("ID"));
                data.setDataCode(rs.getString("DATA_CODE"));
                data.setDataValue(rs.getString("DATA_VALUE"));
                data.setDescription(rs.getString("DESCRIPTION"));
                return data;
            }
            
        } catch (SQLException e) {
            System.err.println("Error fetching data by ID: " + e.getMessage());
            e.printStackTrace();
        }
        
        return null;
    }
}
