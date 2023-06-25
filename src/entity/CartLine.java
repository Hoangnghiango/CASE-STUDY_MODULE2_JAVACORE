package entity;

import service.RoomService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class CartLine {
    private int id;
    private int cartId;
    private int roomId;
    private int quantity;
    private double subtotal;
    private LocalDate fromDate;
    private LocalDate toDate;

    public CartLine() {
    }

    public CartLine(int id, int cartId, int roomId, int quantity, double subtotal) {
        this.id = id;
        this.cartId = cartId;
        this.roomId = roomId;
        this.quantity = quantity;
        this.subtotal = subtotal;
    }

    public CartLine(int id, int cartId, int roomId, int quantity, double subtotal, LocalDate fromDate, LocalDate toDate) {
        this.id = id;
        this.cartId = cartId;
        this.roomId = roomId;
        this.quantity = quantity;
        this.subtotal = subtotal;
        this.fromDate = fromDate;
        this.toDate = toDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    public int getCartId() {
        return cartId;
    }

    public void setCartId(int cartId) {
        this.cartId = cartId;
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

    @Override
    public String toString() {
        return  id + "," + cartId + "," +  roomId + "," + quantity + "," + subtotal+","
                + fromDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.US))+","
                + toDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.US)) ;
    }
    public String display(){
        return RoomService.getRoomNameByRoomId(roomId) + "|______________________" + subtotal + "______________" + quantity +"__________\n"
                + " Ngay nhan phong : " + fromDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.US))+"\n"
                + " Ngay tra phong : " + toDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.US)) +"\n";
    }
}
