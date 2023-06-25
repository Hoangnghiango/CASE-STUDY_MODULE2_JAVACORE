package entity.user.abstraction;

import entity.Address;

public abstract class Account {
    private static int count = 0;

    private int id;
    private String username;
    private String email;

    private String password;
    private String phone;
    private double balance;

    public Account(String email, String password) {
        this.id = ++ count;
        this.email = email;
        this.password = password;
    }

    public Account(int id, String username, String email, String password, String phone, double balance) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.balance = balance;
    }

    public Account(String username, String email, String password, String phone, double balance) {
        this.id = ++count;
        this.username = username;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.balance = balance;
    }

    public int getId() {
        return id;
    }



    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return id + "," + username  +
                "," + email +
                "," + password +
                "," + phone +
                "," + balance ;
    }
}
