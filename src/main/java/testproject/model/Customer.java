package testproject.model;

public class Customer {
    private int custId;
    private String custName;
    private String branchCode;
    
    public Customer() {}
    
    public Customer(int custId, String custName, String branchCode) {
        this.custId = custId;
        this.custName = custName;
        this.branchCode = branchCode;
    }
    
    // Getters and Setters
    public int getCustId() {
        return custId;
    }
    
    public void setCustId(int custId) {
        this.custId = custId;
    }
    
    public String getCustName() {
        return custName;
    }
    
    public void setCustName(String custName) {
        this.custName = custName;
    }
    
    public String getBranchCode() {
        return branchCode;
    }
    
    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }
    
    @Override
    public String toString() {
        return "Customer [custId=" + custId + ", custName=" + custName + ", branchCode=" + branchCode + "]";
    }
}
