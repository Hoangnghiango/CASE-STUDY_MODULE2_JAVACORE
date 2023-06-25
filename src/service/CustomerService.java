package service;

import entity.user.Customer;
import entity.user.Host;
import factory.UserFactory;
import factory.UserType;
import untils.Inputs;
import untils.ReadAndWriteFile;
import untils.Validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CustomerService {
    static Scanner scanner = new Scanner(System.in);
    private static Customer currentCustomer; // khi login
    private static String notification;
    private static final CustomerService customerService = new CustomerService();
    private CustomerService(){

    }
    public static CustomerService getInstance() { return customerService; }
    private static final String CUSTOMER_PATH = "/Users/macbook/Desktop/HoangNghia_Module2_CaseStudy/src/file/customer.txt";
    private static List<Customer> customerList = new ArrayList<>();

    static {
        List<Customer> list = ReadAndWriteFile.ReadFileCustomer(CUSTOMER_PATH);
        customerList.addAll(list);
        ReadAndWriteFile.WriteCustomerListToFile(CUSTOMER_PATH,customerList);
    }

    public static void getAllCustomer() {
        for(Customer ele : customerList){
            System.out.println(ele.toString());
        }
    }


    public String getNotification() {
        return notification;
    }

    public void setNotification(String notification) {
        this.notification = notification;
    }

    public static Customer getCurrentCustomer() {
        return currentCustomer;
    }

    public static void setCurrentCustomer(Customer currentCustomer) {
        CustomerService.currentCustomer = currentCustomer;
    }

    //     register
    public static void register1() {
        System.out.println("--------Let's Register Now To Become Our Customer-------");
        String userName;
        do {
            System.out.println("Enter userName: ");
            userName = scanner.nextLine();
        } while (!Validation.checkRegexUserName(userName));

        String emailInput;
        do {
            System.out.println("Enter email:  ");
            emailInput = scanner.nextLine();

        } while (!Validation.validate(emailInput, "EMAIL"));
        String password;
        do {
            System.out.println("Enter password");
            password = scanner.nextLine();
        } while (!Validation.validate(password, "PASSWORD"));
        String phone;
        do {
            System.out.println("Enter phone: ");
            phone = scanner.nextLine();
        } while (!Validation.validate(phone, "PHONE_NUMBER"));
        double balance;
        System.out.println("Enter balance");
        balance = scanner.nextDouble();
        scanner.nextLine();
        for (Customer customer : customerList) {
            if (emailInput.equals(customer.getEmail())) {
                System.out.println("Email nay da duoc dang ky truoc do !! ");
            } else {
                Customer newCustomer = new Customer(userName, emailInput, password, phone, balance);
                customerList.add(newCustomer);
                ReadAndWriteFile.WriteCustomerListToFile(CUSTOMER_PATH, customerList);
                System.out.println("Register successful !!! Welcome " + userName + " to Booking.com ! ");
            }
        }

    }
    public static void register() {
        System.out.println("---------------------------- CUSTOMER REGISTER -------------------------------");
        String loginName = Inputs.prompt("Enter your name: ","FULL_NAME");
        String phone = Inputs.prompt("Enter your phone: ", "PHONE_NUMBER");
        String email = Inputs.prompt("Enter your email", "EMAIL");
        String password = Inputs.prompt("Enter your password", "PASSWORD");
        double balance = Inputs.IntegerPrompt("Enter your balance");
        CustomerService customerService = CustomerService.getInstance();
        customerService.createCustomer(UserType.CUSTOMER, loginName,phone,email,password,balance);
        System.out.println(customerService.getNotification());
    }
    public static void createCustomer(UserType userType, String username, String phone, String email, String password, double balance){
        if(checkEmailAndPhone(email, phone)){
            notification = "This email or phone is registered before.";
        } else {
            int id;
            if(customerList.size() ==0){
                id = 0;
            } else {
                Customer customer = customerList.get(customerList.size()-1);
                id = customer.getId();
            }
            Customer customer = (Customer) UserFactory.getUserType(userType,++id,username, email, password, phone, balance);
            customerList.add(customer);
            ReadAndWriteFile.WriteCustomerListToFile(CUSTOMER_PATH,customerList);
            notification = "Register successful. Welcome" + username + "To Booking.com";
        }

    }

    private static boolean checkEmailAndPhone(String email, String phone) {
        for(Customer customer : customerList){
            if(customer.getEmail().equals(email) || customer.getPhone().equals(phone)){
                return true;
            }
        }
        return false;
    }
    public static void login() {
        System.out.println("----------------------------CUSTOMER LOGIN-------------------------------");
        String EmailOrPhone = Inputs.prompt("Enter the email or phone:  ");
        String Password = Inputs.prompt("Enter the password: ","PASSWORD");
        CustomerService customerService = CustomerService.getInstance();
        if(checkingEmailOrPhone(EmailOrPhone)){
            if(Password.equals(currentCustomer.getPassword())){
                System.out.println("Login Successful !!!.Hello " + currentCustomer.getUsername()+ " Welcome Back To Booking.com ");
            } else {
                CustomerService.setCurrentCustomer(null);// currentCustomer = null;
                System.out.println("Wrong password ! Please Login again !!! ");
                login();
            }
        } else {
            System.out.println("Email or phone number is not registered before . ");
            register();
        }

    }

    private static boolean checkingEmailOrPhone(String emailOrPhone) {
        for (Customer customer : customerList){
            if(emailOrPhone.equals(customer.getEmail()) || emailOrPhone.equals(customer.getPhone())){
                currentCustomer = customer;
                return true;
            }
        }
        return false;
    }
    // LOGIN, sửa thông tin khách , nạp tiền, tham khảo code nhu
    public static void editCustomerInformation() {
        System.out.println("Cập nhật thông tin của bạn và tìm hiểu các thông tin này được sử dụng ra sao.\n" +
                "\n");
        do {
            System.out.println("""
                    1.Chỉnh sửa tên
                    2.Chỉnh sửa địa chỉ email
                    3.Chỉnh sửa số điện thoaị
                    4.Chỉnh sửa password
                    5.Nap thêm số dư vào tài khoản của bạn
                    6.Trở về màn hình chính của bạn 
                    """);
            int choice = Inputs.IntegerPrompt("Nhập tuỳ chọn để chỉnh sửa");
            switch(choice){
                case 1:
                    CustomerService.editUserName();
                    editCustomerInformation();
                case 2:
                    CustomerService.editEmail();
                    editCustomerInformation();

                case 3:
                    CustomerService.editPhoneNumber();
                    editCustomerInformation();
                case 4:
                    CustomerService.editPassword();
                    editCustomerInformation();
                case 5:
                    CustomerService.deposit();
                    editCustomerInformation();
                case 6:
                    return;

                default:
                    throw new IllegalArgumentException("Vui lòng nhập từ 1 đến 5 để lựa chọn chọn thông tin chỉnh sửa của bạn !!!");

            }
        } while (true);

    }

    public static void deposit() {
        if(currentCustomer == null){
            System.out.println("Vui lòng đăng nhập để thực hiện chức năng chỉnh sửa thông tin này !!!");
            login();
        } else {
            System.out.println("Xin chào " + currentCustomer.getUsername() + " .Nạp thêm tiền vào tài khoản để thanh toán dễ dàng và nhanh chóng hơn.");
            double number = Inputs.DoublePrompt("Số tiền bạn muốn nạp vào tài khoản");
            double currentBalance = currentCustomer.getBalance();
            double newBalance = currentBalance + number;
            currentCustomer.setBalance(newBalance);
            System.out.println("Bạn đã nap tien thành công.Số dư hiện tại của bạn: " + currentCustomer.getBalance());
            ReadAndWriteFile.WriteCustomerListToFile(CUSTOMER_PATH,customerList);

        }
    }
    public static void payment(int customerId, double total){
        for(int i=0; i<customerList.size();i++){
            if(customerList.get(i).getId()==customerId){
                double wallet = customerList.get(i).getBalance();
                customerList.get(i).setBalance(wallet - total);
                ReadAndWriteFile.WriteCustomerListToFile(CUSTOMER_PATH,customerList);
                System.out.println("Đã trừ tiền từ ví của bạn :V");
                break;
            }
        }
    }
    public static void getRefund(int customerId, double total) {
        for(int i=0; i<customerList.size();i++){
            if(customerList.get(i).getId()==customerId){
                double wallet = customerList.get(i).getBalance();
                customerList.get(i).setBalance(wallet + total);
                ReadAndWriteFile.WriteCustomerListToFile(CUSTOMER_PATH,customerList);
                System.out.println("Hoàn tất hoàn tiền");
                break;
            }
        }
    }

    private static void editPassword() {
        if(currentCustomer == null){
            System.out.println("Vui lòng đăng nhập để thực hiện chức năng chỉnh sửa thông tin này !!!");
            login();
        } else {
            String oldVerifyPassword = Inputs.prompt("Nhap mat khau cu: ", "PASSWORD");
            if(oldVerifyPassword.equals(currentCustomer.getPassword())){
                System.out.println("Xin chào " + currentCustomer.getUsername() + ".Vui lòng nhập mật khẩu mới : ");
                String password = Inputs.prompt("Vui lòng nhập password mới :", "PASSWORD");
                currentCustomer.setPassword(password);
                ReadAndWriteFile.WriteCustomerListToFile(CUSTOMER_PATH,customerList);
                System.out.println("Bạn đã chỉnh sửa thành công");
            } else {
                System.out.println("Mật khẩu không trùng khớp. Vui lòng nhập lại ! ");
                editPassword();
            }
        }
    }

    private static void editPhoneNumber() {
        if(currentCustomer == null){
            System.out.println("Vui lòng đăng nhập để thực hiện chức năng chỉnh sửa thông tin này !!!");
            login();
        } else {
            System.out.println("Xin chào " + currentCustomer.getUsername() + ".Vui lòng nhập số điện thoại mới : ");
            String phone = Inputs.prompt("Vui lòng nhập số điện thoại mới :", "PHONE");
            currentCustomer.setPhone(phone);
            ReadAndWriteFile.WriteCustomerListToFile(CUSTOMER_PATH,customerList);
            System.out.println("Bạn đã chỉnh sửa thành công");

        }
    }

    private static void editEmail() {
        if(currentCustomer == null){
            System.out.println("Vui lòng đăng nhập để thực hiện chức năng chỉnh sửa thông tin này !!!");
            login();
        } else {
            System.out.println("Xin chào " + currentCustomer.getUsername() + ".Vui lòng nhập email mới : ");
            String email = Inputs.prompt("Vui lòng nhập email mới :", "EMAIL");
            currentCustomer.setEmail(email);
            ReadAndWriteFile.WriteCustomerListToFile(CUSTOMER_PATH,customerList);
            System.out.println("Bạn đã chỉnh sửa thành công");

        }
    }

    private static void editUserName() {
        if(currentCustomer == null){
            System.out.println("Vui lòng đăng nhập để thực hiện chức năng chỉnh sửa thông tin này !!!");
            login();
        } else {
            System.out.println("Xin chào " + currentCustomer.getUsername() + ".Vui lòng nhập tên mới : ");
            String userName = Inputs.prompt("Vui lòng nhập tên mới :", "FULL_NAME");
            currentCustomer.setUsername(userName);
            ReadAndWriteFile.WriteCustomerListToFile(CUSTOMER_PATH,customerList);
            System.out.println("Bạn đã chỉnh sửa thành công");

        }
    }
    public static void displayYourBalance(){
        if(currentCustomer == null){
            System.out.println("Vui lòng đăng nhập để thực hiện chức năng chỉnh sửa thông tin này !!!");
            login();
        } else {
            System.out.println("So du cua ban la: ");
            System.out.println(currentCustomer.getBalance());;
        }
    }
    public static void logout() {
        currentCustomer = null;
        System.out.println("Dang xuat");
    }



}
