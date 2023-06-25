package service;

import entity.user.Host;
import factory.UserFactory;
import factory.UserType;
import untils.Inputs;
import untils.ReadAndWriteFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class HostService {
    private static Host currentHost;
    private static String notification;
    private static final HostService hostService = new HostService();
    private HostService(){};
    public static HostService getInstance(){ return hostService;}
    private static final String HOST_PATH = "/Users/macbook/Desktop/HoangNghia_Module2_CaseStudy/src/file/host.txt";
    private static List<Host> hostList = new ArrayList<>();
    static {
        hostList.addAll(ReadAndWriteFile.ReadFileHost(HOST_PATH));
        ReadAndWriteFile.WriteHostListToFile(HOST_PATH,hostList);
    }

    public static Host getCurrentHost() {
        return currentHost;
    }

    public static void setCurrentHost(Host currentHost) {
        HostService.currentHost = currentHost;
    }

    public static String getNotification() {
        return notification;
    }

    public static void setNotification(String notification) {
        HostService.notification = notification;
    }
    public static void register() {
        System.out.println("---------------------------- HOST REGISTER -------------------------------");
        String loginName = Inputs.prompt("Enter your username: ","FULL_NAME");
        String phone = Inputs.prompt("Enter your phone number: ", "PHONE_NUMBER");
        String email = Inputs.prompt("Enter your email ", "EMAIL");
        String password = Inputs.prompt("Enter your password", "PASSWORD");
        double balance = Inputs.IntegerPrompt("Enter your balance");
        HostService hostService = HostService.getInstance();
        hostService.createHost(UserType.HOST, loginName,phone,email,password,balance);
        System.out.println(hostService.getNotification());
    }
    public static void createHost(UserType userType, String username, String phone, String email, String password, double balance){
        if(checkEmailAndPhone(email, phone)){
            notification = "This email or phone is registered before.";
        } else {
            int id;
            if(hostList.size() ==0){
                id = 0;
            } else {
                Host host = hostList.get(hostList.size()-1);
                id = host.getId();
            }
            Host host = (Host) UserFactory.getUserType(userType,++id,username, email, password, phone, balance);
            hostList.add(host);
            ReadAndWriteFile.WriteHostListToFile(HOST_PATH,hostList);
            notification = "Register successful. Welcome" + username + "To Booking.com";
        }
    }

    private static boolean checkEmailAndPhone(String email, String phone) {
        for(Host host : hostList){
            if(host.getEmail().equals(email) || host.getPhone().equals(phone)){
                return true;
            }
        }
        return false;
    }
    public static void login() {
        System.out.println("----------------------------HOST LOGIN-------------------------------");
        String EmailOrPhone = Inputs.prompt("Enter the email or phone: ");
        String Password = Inputs.prompt("Enter the password:  ");
        HostService hostService = HostService.getInstance();
        if(checkingEmailOrPhone(EmailOrPhone)){
            if(Password.equals(currentHost.getPassword())){
                System.out.println("Login Successful !!!.Hello " + currentHost.getUsername()+ " Welcome Back To Booking.com ");
            } else {
                HostService.setCurrentHost(null);// currentHost = null;
                System.out.println("Wrong password ! Please Login again !!! ");
                login();
            }
        } else {
            System.out.println("Email or phone number is not registered before . ");
            register();
        }

    }

    private static boolean checkingEmailOrPhone(String emailOrPhone) {
        for (Host host : hostList){
            if(emailOrPhone.equals(host.getEmail()) || emailOrPhone.equals(host.getPhone())){
                currentHost = host;
                return true;
            }
        }
        return false;
    }
    // LOGIN, sửa thông tin khách , nạp tiền, tham khảo code nhu
    public static void editHostInformation() {
        System.out.println("Cập nhật thông tin của bạn để chúng tôi liên hệ về Business của bạn \n" +
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
                    HostService.editUserName();
                    editHostInformation();
                case 2:
                    HostService.editEmail();
                    editHostInformation();

                case 3:
                    HostService.editPhoneNumber();
                    editHostInformation();
                case 4:
                    HostService.editPassword();
                    editHostInformation();
                case 5:
                    HostService.deposit();
                    editHostInformation();
                case 6:
                    break;

                default:
                    System.out.println("Vui lòng nhập từ 1 đến 5 để lựa chọn chọn thông tin chỉnh sửa của bạn !!!");

            }
        } while (true);

    }

    private static void deposit() {
        if(currentHost == null){
            System.out.println("Vui lòng đăng nhập để thực hiện chức năng chỉnh sửa thông tin này !!!");
            login();
        } else {
            System.out.println("Xin chào " + currentHost.getUsername() + "Nạp thêm tiền vào tài khoản để ting ting booking dễ dàng và nhanh chóng hơn.");
            double number = Inputs.DoublePrompt("Số tiền bạn muốn nạp vào tài khoản");
            double currentBalance = currentHost.getBalance();
            double newBalance = currentBalance + number;
            currentHost.setBalance(newBalance);
            System.out.println("Số dư hiện tại của bạn: " + currentHost.getBalance());
        }
    }

    private static void editPassword() {
        if(currentHost == null){
            System.out.println("Vui lòng đăng nhập để thực hiện chức năng chỉnh sửa thông tin này !!!");
            login();
        } else {
            String oldVerifyPassword = Inputs.prompt("Nhap mat khau cu: ", "PASSWORD");
            if(oldVerifyPassword == currentHost.getPassword()){
                System.out.println("Xin chào " + currentHost.getUsername() + ".Vui lòng nhập mật khẩu mới : ");
                String password = Inputs.prompt("Vui lòng nhập password mới :", "PASSWORD");
                currentHost.setPassword(password);
                System.out.println("Bạn đã chỉnh sửa thành công");
            } else {
                System.out.println("Mật khẩu không trùng khớp. Vui lòng nhập lại ! ");
                editPassword();
            }
        }
    }

    private static void editPhoneNumber() {
        if(currentHost == null){
            System.out.println("Vui lòng đăng nhập để thực hiện chức năng chỉnh sửa thông tin này !!!");
            login();
        } else {
            System.out.println("Xin chào " + currentHost.getUsername() + ".Vui lòng nhập số điện thoại mới : ");
            String phone = Inputs.prompt("Vui lòng nhập số điện thoại mới :", "PHONE");
            currentHost.setPhone(phone);
        }
    }

    private static void editEmail() {
        if(currentHost == null){
            System.out.println("Vui lòng đăng nhập để thực hiện chức năng chỉnh sửa thông tin này !!!");
            login();
        } else {
            System.out.println("Xin chào " + currentHost.getUsername() + ".Vui lòng nhập email mới : ");
            String email = Inputs.prompt("Vui lòng nhập email mới :", "EMAIL");
            currentHost.setEmail(email);
        }
    }

    private static void editUserName() {
        if(currentHost == null){
            System.out.println("Vui lòng đăng nhập để thực hiện chức năng chỉnh sửa thông tin này !!!");
            login();
        } else {
            System.out.println("Xin chào " + currentHost.getUsername() + ".Vui lòng nhập tên mới : ");
            String userName = Inputs.prompt("Vui lòng nhập tên mới :", "USER_NAME");
            currentHost.setUsername(userName);
        }
    }
    public static Host getCurrentHostByHostId(int hostId){
        Host output = null;
        for(Host ele : hostList){
            if(ele.getId() == hostId){
                output = ele;
                break;
            }
        }
        return output;
    }
    public static void addFee(int hostId,double total){
        for(int i=0; i< hostList.size();i++){
            if(hostList.get(i).getId() == hostId){
                double wallet = hostList.get(i).getBalance();
                double fee = total * 85 / 100;
                hostList.get(i).setBalance(wallet+fee);
                ReadAndWriteFile.WriteHostListToFile(HOST_PATH,hostList);
                break;
            }
        }
    }
    public static void refunds(int hostId,double total){
        for(int i=0; i< hostList.size();i++){
            if(hostList.get(i).getId() == hostId){
                double wallet = hostList.get(i).getBalance();
                double fee = total * 85 / 100;
                hostList.get(i).setBalance(wallet - fee);
                ReadAndWriteFile.WriteHostListToFile(HOST_PATH,hostList);
                break;
            }
        }
    }
    public static void getBalance(){
        System.out.println("Số dư tài khoản của bạn: "+currentHost.getBalance());
    }

    public static void logout() {
        currentHost = null;
        System.out.println("Ban da dang xuat");
    }

    public static void getAllHost() {
        for(Host ele: hostList){
            System.out.println(ele);
        }
    }
}
