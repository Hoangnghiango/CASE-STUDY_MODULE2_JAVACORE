package entity;

import service.AccommodationService;
import service.RoomService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DateBoard {
    private static int count = 0;
    private int id;
    private LocalDate date;
    private int accId;
    private int hostId;
    private int roomId;
    private int quantity;
    private double price;

    public DateBoard(int id, LocalDate date, int roomId, int number) {
        this.id = id;
        this.date = date;
        this.roomId = roomId;
        this.quantity = number;
    }

    public DateBoard(LocalDate date, int roomId, int number) {
        this.id = ++count;
        this.date = date;
        this.roomId = roomId;
        this.quantity = number;
    }

    public DateBoard(int id, LocalDate date, int accId, int hostId, int roomId, int quantity, double price) {
        this.id = id;
        this.date = date;
        this.accId = accId;
        this.hostId = hostId;
        this.roomId = roomId;
        this.quantity = quantity;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getAccId() {
        return accId;
    }

    public void setAccId(int accId) {
        this.accId = accId;
    }

    public int getHostId() {
        return hostId;
    }

    public void setHostId(int hostId) {
        this.hostId = hostId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
    //    @Override
//    public String toString() {
//        return id + "," +
//                date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.US)) +
//                "," + roomId + "," + quantity;
//    }

    @Override
    public String toString() {
        return id +
                "," +  date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.US)) +
                "," + accId +
                "," + hostId +
                "," + roomId +
                "," + quantity +
                "," + price ;
    }
    public String display(){
        return "ID : "+id +
                " | Ngày : " +  date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.US)) +
                " | Ten cho nghi: "+ AccommodationService.getAccommodationNameByAccId(accId)+
                " | ID chỗ nghỉ : " + accId +
                " | ID Chủ nhà : " + hostId +
                " | Thong tin phong : " + RoomService.getRoomNameByRoomId(roomId)+
                " | ID PHÒNG : " + roomId +
                " | SỐ LƯỢNG : " + quantity +
                " | GIÁ : " + price ;
    }
}
