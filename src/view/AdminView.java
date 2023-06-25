package view;

import service.*;
import untils.Inputs;

public class AdminView {
    private static final AdminView adminView = new AdminView();
    private AdminView(){};
    public static final AdminView getInstance(){ return adminView ;}
    public void displayAdminHomepage(){
        System.out.println("TRANG QUAN TRI BOOKING.COM");
        System.out.println("""
                1.Xem thông tin người quản trị
                2.Xem thông tin khách hàng
                3.Xem thông tin chủ nhà
                4.Xem thông tin chổ nghỉ được đăng kí
                5.Xem danh sách đặt phòng 
                6.Xem danh sach dat phong da huy 
                7.Dang xuat""");
        do {
            int choice = Inputs.IntegerPrompt("nhap lua chon:");
            switch(choice){
                case 1:
                    AdminService.showAdminInformation();
                    displayAdminHomepage();
                case 2:
                    CustomerService.getAllCustomer();
                    displayAdminHomepage();
                case 3:
                    HostService.getAllHost();
                    displayAdminHomepage();
                case 4:
                    AccommodationService.displayAllAccommodation();

                case 5:
                    BookingService.BookingListInAdminView();
                    displayAdminHomepage();
                case 6:
                    BookingService.viewAllCancelBooking();
                    displayAdminHomepage();
                case 7:
                    HomePageView.getInstance().displayMainMenu();
                default:
                    System.out.println("Nhap tu 1 - 7 ");
            }
        }while(true);

    }
}
