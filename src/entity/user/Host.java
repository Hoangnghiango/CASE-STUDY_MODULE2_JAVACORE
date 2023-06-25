package entity.user;

import entity.user.abstraction.Account;

public class Host extends Account  {

    public Host(String email, String password) {
        super(email, password);
    }

    public Host(String username, String email, String password, String phone, double balance) {
        super(username, email, password, phone, balance);
    }

    public Host(int id, String username, String email, String password, String phone, double balance) {
        super(id, username, email, password, phone, balance);
    }
}
