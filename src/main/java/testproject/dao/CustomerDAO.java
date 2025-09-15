package testproject.dao;

import testproject.model.Customer;
import testproject.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAO {
    
    public List<Customer> getAllCustomers() {
        List<Customer> customerList = new ArrayList<>();
        String query = "SELECT id as cust_id, customer_name as cust_name, branch_code as branch_code FROM data_master ORDER BY id";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                Customer customer = new Customer();
                customer.setCustId(rs.getInt("cust_id"));
                customer.setCustName(rs.getString("cust_name"));
                customer.setBranchCode(rs.getString("branch_code"));
                customerList.add(customer);
            }
            
        } catch (SQLException e) {
            System.err.println("Error fetching data from data_master: " + e.getMessage());
            e.printStackTrace();
        }
        
        return customerList;
    }
    
    public Customer getCustomerById(int id) {
        String query = "SELECT id as cust_id, customer_name as cust_name, branch_code as branch_code FROM data_master WHERE id = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                Customer customer = new Customer();
                customer.setCustId(rs.getInt("cust_id"));
                customer.setCustName(rs.getString("cust_name"));
                customer.setBranchCode(rs.getString("branch_code"));
                return customer;
            }
            
        } catch (SQLException e) {
            System.err.println("Error fetching customer by ID: " + e.getMessage());
            e.printStackTrace();
        }
        
        return null;
    }
}
