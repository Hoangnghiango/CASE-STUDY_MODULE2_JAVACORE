package view;

import entity.DateBoard;
import service.BookingService;
import service.CartService;
import service.CustomerService;
import service.RoomAvailabilityManager;
import untils.Inputs;

import java.util.List;

public class CustomerView {
    private static final CustomerView customerView = new CustomerView();
    private CustomerView(){}
    public static CustomerView getInstance(){return customerView ;}
    public void displayCustomerHomePage(){
        System.out.println("Tìm chỗ nghỉ nhanh chóng trên Booking.com " );
        System.out.println("""
                1.Đăng ký 
                2.Đăng nhập
                3.Tìm kiếm chỗ nghỉ -> Thêm vào giỏ hàng -> Đặt phòng
                4.Xem booking
                5.Xem số dư
                6.Huỷ Booking
                7.Chinh sua thong tin
                8.Dang xuat""");
        do {
            int choice = Inputs.IntegerPrompt("nhap lua chon:");
            switch(choice){
                case 1:
                    CustomerService.register();
                    displayCustomerHomePage();
                case 2:
                    CustomerService.login();
                    displayCustomerHomePage();
                case 3:
                    RoomAvailabilityManager.findAvailableRoomByCriteria();
                    BookingService.createNewBooking();
                case 4:
                    BookingService.viewCustomerBooking();
                    displayCustomerHomePage();

                case 5:
                    CustomerService.displayYourBalance();
                    displayCustomerHomePage();

                case 6:
                    BookingService.cancelBooking();
                    displayCustomerHomePage();
                case 7:
                    CustomerService.editCustomerInformation();
                case 8:
                    CustomerService.logout();
                    HomePageView.getInstance().displayMainMenu();
                default:
                    System.out.println("Nhập từ 1 đến 7. Nhập sai nữa quánh chớt");
            }
        } while (true);
    }
}
