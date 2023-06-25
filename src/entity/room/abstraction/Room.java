package entity.room.abstraction;

public class Room {
    private static int count = 0;
    private int id;
    private int accommodation_id;
    private String roomType;
    private String room_name;
    private String bed_info;
    private double price;
    private int quantity;

    private int maxPerson;

    public Room() {
    }

    public Room(int id, int accommodation_id, String roomType, String room_name, String bed_info, double price, int quantity,int maxPerson) {
        this.id = id;
        this.accommodation_id = accommodation_id;
        this.roomType = roomType;
        this.room_name = room_name;
        this.bed_info = bed_info;
        this.price = price;
        this.quantity = quantity;
        this.maxPerson = maxPerson;
    }

    public Room(int accommodation_id, String roomType, String room_name, String bed_info, double price, int quantity,int maxPerson) {
        this.id = ++count;
        this.accommodation_id = accommodation_id;
        this.roomType = roomType;
        this.room_name = room_name;
        this.bed_info = bed_info;
        this.price = price;
        this.quantity = quantity;
        this.maxPerson = maxPerson;

    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAccommodation_id() {
        return accommodation_id;
    }

    public void setAccommodation_id(int accommodation_id) {
        this.accommodation_id = accommodation_id;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public String getRoom_name() {
        return room_name;
    }

    public void setRoom_name(String room_name) {
        this.room_name = room_name;
    }

    public String getBed_info() {
        return bed_info;
    }

    public void setBed_info(String bed_info) {
        this.bed_info = bed_info;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getMaxPerson() {
        return maxPerson;
    }

    public void setMaxPerson(int maxPerson) {
        this.maxPerson = maxPerson;
    }

    @Override
    public String toString() {
        return  id + "," + accommodation_id + "," +roomType + "," + room_name + "," + bed_info + "," + price + "," + quantity + "," + maxPerson ;
    }
    public String display(){
        return room_name +
                " | " + id +
                " | " + roomType +
                " | " + bed_info +
                " | " +"Gia: " + price +
                " | So luong : "+ quantity+
                " | So nguoi toi da: " +maxPerson;
    }
}
