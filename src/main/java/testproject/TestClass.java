package testproject;

import testproject.dao.DataMasterDAO;
import testproject.model.DataMaster;

import java.util.List;

public class TestClass {
    
    public static void main(String[] args) {
        System.out.println("Testing Oracle Database Connection...");
        
        // Test database connectivity
        DataMasterDAO dao = new DataMasterDAO();
        List<DataMaster> dataList = dao.getAllData();
        
        if (dataList.isEmpty()) {
            System.out.println("No data found in DATA_MSTER table.");
            System.out.println("Please ensure:");
            System.out.println("1. Oracle database is running");
            System.out.println("2. DATA_MSTER table exists");
            System.out.println("3. Database credentials are correct");
            System.out.println("4. Oracle JDBC driver is in classpath");
        } else {
            System.out.println("Successfully connected to Oracle database!");
            System.out.println("Found " + dataList.size() + " records in DATA_MSTER table:");
            System.out.println("----------------------------------------");
            
            for (DataMaster data : dataList) {
                System.out.println(data);
            }
        }
    }
    
    public static void testDatabaseConnection() {
        new TestClass();
    }
}
