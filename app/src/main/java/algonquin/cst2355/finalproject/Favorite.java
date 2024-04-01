package algonquin.cst2355.finalproject;

public class Favorite {
    private int id;
    private String latitude;
    private String longitude;
    private String sunrise;

    public Favorite() {
    }

    public Favorite(int id, String latitude, String longitude, String sunrise) {
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.sunrise = sunrise;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getSunrise() {
        return sunrise;
    }

    public void setSunrise(String sunrise) {
        this.sunrise = sunrise;
    }

    // To display the object as a string in UI
    @Override
    public String toString() {
        return "Lat: " + latitude + ", Lng: " + longitude + ", Sunrise: " + sunrise;
    }
}
