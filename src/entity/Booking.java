package entity;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

public class Booking {
    private int id;
    private int customer_id;
    private LocalDate fromDate;
    private LocalDate toDate;
    private int host_id;
    private int accId;
    private int cartId;
    private String visitorInfo;
    private String email;
    private LocalDate bookingDate;
    private double total;

    public Booking(int id, int customer_id, LocalDate fromDate, LocalDate toDate, int host_id, int accId,int cartId, String visitorInfo, String email, LocalDate bookingDate, double total) {
        this.id = id;
        this.customer_id = customer_id;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.host_id = host_id;
        this.accId = accId;
        this.cartId =cartId;
        this.visitorInfo = visitorInfo;
        this.email = email;
        this.bookingDate = bookingDate;
        this.total = total;
    }

    public int getId() {
        return id;
    }


    public int getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }

    public int getHost_id() {
        return host_id;
    }

    public void setHost_id(int host_id) {
        this.host_id = host_id;
    }

    public String getVisitorInfo() {
        return visitorInfo;
    }

    public void setVisitorInfo(String visitorInfo) {
        this.visitorInfo = visitorInfo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(LocalDate bookingDate) {
        this.bookingDate = bookingDate;
    }

    public int getAccId() {
        return accId;
    }

    public void setAccId(int accId) {
        this.accId = accId;
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

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public int getCartId() {
        return cartId;
    }

    public void setCartId(int cartId) {
        this.cartId = cartId;
    }

    @Override
    public String toString() {
        return  id +
                "," + customer_id +
                "," + fromDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.US))+
                "," + toDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.US))+
                "," + host_id +
                "," + accId +
                "," + cartId +
                "," + visitorInfo +
                "," + email +
                "," + bookingDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.US))+
                "," + total
               ;
    }
    public String display(){
        return "ID:" +id +"|"+"CUSTOMER_ID:"+ customer_id+ "|HOST_ID: "+host_id+"|ACC_ID:"+accId+"|CART_ID: "+cartId+" FROMDATE: "+ fromDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.US))+"| "+"TODATE: "+ toDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.US))+
                "| VISITOR INFO : "+ visitorInfo + "| Email: "+ email + "| BOOKING DATE : "+ bookingDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.US))+
                "| TOTAL: "+ total;
    }
}
