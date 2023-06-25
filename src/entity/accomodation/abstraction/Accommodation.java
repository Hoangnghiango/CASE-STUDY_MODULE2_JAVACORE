package entity.accomodation.abstraction;

import entity.Address;

public class Accommodation {

    private static int count = 0;

    private int id;
    private int host_id;
    private String accommodation_name;
    // trong constructor can co accommodation type
    private String accommodation_type;

    private int star;
    private String description;
    private int numberOfAddress;
    private String street;
    private String ward;
    private String district;
    private String city;
    private double rating;


    public Accommodation() {
    }

    public Accommodation(int id, int host_id, String accommodation_name, String accommodation_type, int star, String description, int numberOfAddress, String street, String ward, String district, String city, double rating) {
        this.id = id;
        this.host_id = host_id;
        this.accommodation_name = accommodation_name;
        this.accommodation_type = accommodation_type;
        this.star = star;
        this.description = description;
        this.numberOfAddress = numberOfAddress;
        this.street = street;
        this.ward = ward;
        this.district = district;
        this.city = city;
        this.rating = rating;
    }

    public Accommodation(int host_id, String accommodation_name, String accommodation_type, int star, String description, int numberOfAddress, String street, String ward, String district, String city, double rating) {
        this.id = ++count;
        this.host_id = host_id;
        this.accommodation_name = accommodation_name;
        this.accommodation_type = accommodation_type;
        this.star = star;
        this.description = description;
        this.numberOfAddress = numberOfAddress;
        this.street = street;
        this.ward = ward;
        this.district = district;
        this.city = city;
        this.rating = rating;
    }

    public Accommodation(int host_id, String accommodation_name) {
        this.host_id = host_id;
        this.accommodation_name = accommodation_name;
    }



    public int getId() {
        return id;
    }

    public int getHost_id() {
        return host_id;
    }

    public void setHost_id(int host_id) {
        this.host_id = host_id;
    }

    public String getAccommodation_name() {
        return accommodation_name;
    }

    public void setAccommodation_name(String accommodation_name) {
        this.accommodation_name = accommodation_name;
    }

    public String getAccommodation_type() {
        return accommodation_type;
    }

    public void setAccommodation_type(String accommodation_type) {
        this.accommodation_type = accommodation_type;
    }

    public int getStar() {
        return star;
    }

    public void setStar(int star) {
        this.star = star;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getNumberOfAddress() {
        return numberOfAddress;
    }

    public void setNumberOfAddress(int numberOfAddress) {
        this.numberOfAddress = numberOfAddress;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getWard() {
        return ward;
    }

    public void setWard(String ward) {
        this.ward = ward;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    @Override
    public String toString() {
        return id +
                "," + host_id +
                "," + accommodation_name +
                "," + accommodation_type +
                "," + star +
                "," + description +
                "," + numberOfAddress +
                "," + street +
                "," + ward +
                "," + district +
                "," + city +
                "," + rating
                ;
    }
    public String display(){
        return "BUSINESS NAME: "+accommodation_name +
                " | ID : " + id +
                " | TYPE : " + accommodation_type +
                " | STAR :" + star +
                " | Địa chỉ : " + numberOfAddress+","+ street+","+ ward+","+ district+","+ city+
                " | Điểm đánh giá : "+ rating;
     }
}
