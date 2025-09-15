package testproject.model;

public class DataMaster {
    private int id;
    private String dataCode;
    private String dataValue;
    private String description;
    
    public DataMaster() {}
    
    public DataMaster(int id, String dataCode, String dataValue, String description) {
        this.id = id;
        this.dataCode = dataCode;
        this.dataValue = dataValue;
        this.description = description;
    }
    
    // Getters and Setters
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getDataCode() {
        return dataCode;
    }
    
    public void setDataCode(String dataCode) {
        this.dataCode = dataCode;
    }
    
    public String getDataValue() {
        return dataValue;
    }
    
    public void setDataValue(String dataValue) {
        this.dataValue = dataValue;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    @Override
    public String toString() {
        return "DataMaster [id=" + id + ", dataCode=" + dataCode + ", dataValue=" + dataValue + ", description="
                + description + "]";
    }
}
