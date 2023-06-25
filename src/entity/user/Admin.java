package entity.user;

import entity.Address;
import entity.user.abstraction.Account;

public class Admin extends Account {


    public Admin(String username, String email, String password, String phone, double balance) {
        super(username, email, password, phone, balance);
    }


    public String display() {
        return "Username:"+getUsername()+"| Email: "+ getEmail()+"|Password: "+getPassword()+"Phone: "+getPhone()+"Balance: "+getBalance();
    }
    @Override
    public String toString() {
        return  getUsername()  +
                "," + getEmail() +
                "," + getPassword() +
                "," + getPhone() +
                "," + getBalance() ;
    }
}
