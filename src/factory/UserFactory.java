package factory;


import entity.Address;
import entity.user.Customer;
import entity.user.Host;
import entity.user.abstraction.Account;

public class UserFactory {
    private UserFactory(){
    }
    public static final Account getUserType(UserType userType,int id, String username, String email, String password, String phone, double balance){
        switch (userType){
            case CUSTOMER:
                return new Customer(id, username, email, password, phone, balance);
            case HOST:
                return new Host(id,username, email, password, phone, balance);
            default:
                throw new IllegalArgumentException("This accountType is not support");
        }
    }
}
