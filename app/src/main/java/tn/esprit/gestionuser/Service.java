package tn.esprit.gestionuser;

public class Service {
    private long id;
    private String name;
    private String location;
    private String details;
    private float price;



    // Constructor for retrieving an existing offer from the database
    public Service(long id, String name, String details, float price) {
        this.id = id;
        this.name = name;
        this.details = details;
        this.price = price;
    }

    // Getters and Setters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
    
}