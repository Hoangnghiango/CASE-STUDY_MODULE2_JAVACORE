package entity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateRoom {
   private int id;
   private Date date;
   private int roomId;
   private boolean available;
   private static int count = 0;

   public DateRoom(Date date, int roomId, boolean available) {
      this.id = ++count;
      this.date = date;
      this.roomId = roomId;
      this.available = available;
   }

   public DateRoom(int id, Date date, int roomId, boolean available) {
      this.id = id;
      this.date = date;
      this.roomId = roomId;
      this.available = available;
   }

   public int getId() {
      return id;
   }

   public void setId(int id) {
      this.id = id;
   }

   public String getFormatDate() {
      return new SimpleDateFormat("dd-MM-yyyy").format(date);
   }

   public void setDate(String date) {
      try {
         this.date = new SimpleDateFormat("dd-MM-yyyy").parse(date);
      } catch (ParseException e) {
         e.printStackTrace();
      }
   }

   public int getRoomId() {
      return roomId;
   }

   public void setRoomId(int roomId) {
      this.roomId = roomId;
   }

   public boolean isAvailable() {
      return available;
   }

   public void setAvailable(boolean available) {
      this.available = available;
   }

   @Override
   public String toString() {
      return "Thong tin ngay phong: " +
              " \n  id=" + id +
              " \n  date='" + getFormatDate() +
              " \n  roomId=" + roomId +
              " \n  available=" + available ;
   }
}
