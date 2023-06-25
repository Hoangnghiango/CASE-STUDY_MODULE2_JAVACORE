package entity.user;


import entity.Address;
import entity.user.abstraction.Account;
public class Customer extends Account {
    public Customer(String email, String password) {
        super(email, password);
    }

    public Customer(String username, String email, String password, String phone, double balance) {
        super(username, email, password, phone, balance);
    }

    public Customer(int id, String username, String email, String password, String phone, double balance) {
        super(id, username, email, password, phone, balance);
    }


}
