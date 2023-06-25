package factory;


import entity.room.*;
import entity.room.abstraction.Room;

public class RoomFactory {
    private static final String SINGLE = "SINGLE";
    private static final String DOUBLE = "DOUBLE";
    private static final String EXECUTIVE = "EXECUTIVE";
    private static final String FAMILY = "FAMILY";
    private static final String PENTHOUSE = "PENTHOUSE";
    private static final String SUITE = "SUITE";
    private static final String DELUXE = "DELUXE";


    private RoomFactory(){

    }
    public static final Room getRoom(int roomId, int accommodation_id, String roomType, String room_name, String bed_info, double price, int quantity,int maxPerson){
        String roomTypeToUpperCase = roomType.toUpperCase();
        try{
            switch( roomTypeToUpperCase){
                case SINGLE:
                    return new Room(roomId,accommodation_id,"Single",room_name,bed_info,price,quantity,maxPerson);
                case DOUBLE:
                    return new Room(roomId,accommodation_id,"Double",room_name,bed_info,price,quantity,maxPerson);
                case EXECUTIVE:
                    return new Room(roomId,accommodation_id,"Executive",room_name,bed_info,price,quantity,maxPerson);
                case FAMILY:
                    return new Room(roomId,accommodation_id,"Family",room_name,bed_info,price,quantity,maxPerson);
                case PENTHOUSE:
                    return new Room(roomId,accommodation_id,"Penthouse",room_name,bed_info,price,quantity,maxPerson);
                case SUITE:
                    return new Room(roomId,accommodation_id,"Suite",room_name,bed_info,price,quantity,maxPerson);
                case DELUXE:
                    return new Room(roomId,accommodation_id,"Deluxe",room_name,bed_info,price,quantity,maxPerson);
                default:
                    throw new IllegalArgumentException("This type of room is not support");
            }

        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

        return new Room(roomId,accommodation_id,"Double",room_name,bed_info,price,quantity,maxPerson);
    }
}
