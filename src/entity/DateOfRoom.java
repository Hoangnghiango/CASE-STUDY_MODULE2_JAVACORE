package entity;

import java.time.LocalDate;

public class DateOfRoom {
    private static int count;
    private int id;
    private LocalDate fromDate;
    private LocalDate toDate;
    private int roomId;

    public DateOfRoom(LocalDate fromDate, LocalDate toDate, int roomId) {
        this.id = ++count;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.roomId = roomId;
    }

    public DateOfRoom(int id, LocalDate fromDate, LocalDate toDate, int roomId) {
        this.id = id;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.roomId = roomId;
    }


    public int getId() {
        return id;
    }


    public LocalDate getFromDate() {
        return fromDate;
    }

    public void setFromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
    }

    public LocalDate getToDate() {
        return toDate;
    }

    public void setToDate(LocalDate toDate) {
        this.toDate = toDate;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    @Override
    public String toString() {
        return "DateOfRoom{" +
                "id=" + id +
                ", fromDate=" + fromDate +
                ", toDate=" + toDate +
                ", roomId=" + roomId +
                '}';
    }
    public String toFile(){
        return id + "," + fromDate + "," + toDate + "," + roomId;
    }
}
