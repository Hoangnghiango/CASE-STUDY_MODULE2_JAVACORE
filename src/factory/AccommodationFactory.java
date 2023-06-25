package factory;


import entity.Address;
import entity.accomodation.Bungalow;
import entity.accomodation.Hotel;
import entity.accomodation.Resort;
import entity.accomodation.Villa;
import entity.accomodation.abstraction.Accommodation;

public class AccommodationFactory {

    private static final String HOTEL = "HOTEL";
    private static final String VILLA = "VILLA";
    private static final String BUNGALOW = "BUNGALOW";
    private static final String RESORT = "RESORT";


    private AccommodationFactory(){

    }
    public static final Accommodation getAccommodation(String accommodationType,int host_id, String accommodation_name, int star, String description, int numberOfAddress, String street, String ward, String district, String city, double rating){
        String accommodationTypeToUpperCase = accommodationType.toUpperCase();
        switch (accommodationTypeToUpperCase){
//            case HOTEL:
//                return new Hotel(host_id, accommodation_name, star, description, numberOfAddress, street, ward, district, city, rating);
//            case VILLA:
//                return new Villa(host_id, accommodation_name, star, description, numberOfAddress, street, ward, district, city, rating);
//            case BUNGALOW:
//                return new Bungalow(host_id, accommodation_name, star, description, numberOfAddress, street, ward, district, city, rating);
//            case RESORT:
//                return new Resort(host_id, accommodation_name, star, description, numberOfAddress, street, ward, district, city, rating);
            default:
                System.out.println("This type of accommodation is not supported ");

        }

        return null;
    }
}
