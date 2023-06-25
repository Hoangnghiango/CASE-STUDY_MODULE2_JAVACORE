package entity;

import java.util.List;

public class Cart {
    private int cart_Id;
    private int customer_id;
    private int accId;
    private double total;
    private static int count = 0;

    public Cart() {
    }

    public Cart(int cart_Id, int customer_id, double total) {
        this.cart_Id = cart_Id;
        this.customer_id = customer_id;
        this.total = total;
    }

    public Cart(int cart_Id, int customer_id, int accId, double total) {
        this.cart_Id = cart_Id;
        this.customer_id = customer_id;
        this.accId = accId;
        this.total = total;
    }

    public Cart(int customer_id, double total) {
        this.cart_Id = ++count;
        this.customer_id = customer_id;
        this.total = total;
    }

    public int getCart_Id() {
        return cart_Id;
    }

    public void setCart_Id(int cart_Id) {
        this.cart_Id = cart_Id;
    }

    public int getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }

    public int getAccId() {
        return accId;
    }

    public void setAccId(int accId) {
        this.accId = accId;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public static int getCount() {
        return count;
    }

    public static void setCount(int count) {
        Cart.count = count;
    }

    @Override
    public String toString() {
        return cart_Id + "," + customer_id + "," + accId + "," + total ;
    }
}
