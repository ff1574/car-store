package model;


public class Manufacturer {
    private int manufacturerId;
    private String manufacturerName;
    private String manufacturerCountry;
    private String manufacturerWebsite;

    // Constructor
    public Manufacturer() {
    }

    // Constructor with all fields
    public Manufacturer(int manufacturerId, String manufacturerName, String manufacturerCountry,
            String manufacturerWebsite) {
        this.manufacturerId = manufacturerId;
        this.manufacturerName = manufacturerName;
        this.manufacturerCountry = manufacturerCountry;
        this.manufacturerWebsite = manufacturerWebsite;
    }

    // Getter and Setter methods
    public int getManufacturerId() {
        return manufacturerId;
    }

    public void setManufacturerId(int manufacturerId) {
        this.manufacturerId = manufacturerId;
    }

    public String getManufacturerName() {
        return manufacturerName;
    }

    public void setManufacturerName(String manufacturerName) {
        this.manufacturerName = manufacturerName;
    }

    public String getManufacturerCountry() {
        return manufacturerCountry;
    }

    public void setManufacturerCountry(String manufacturerCountry) {
        this.manufacturerCountry = manufacturerCountry;
    }

    public String getManufacturerWebsite() {
        return manufacturerWebsite;
    }

    public void setManufacturerWebsite(String manufacturerWebsite) {
        this.manufacturerWebsite = manufacturerWebsite;
    }
}
