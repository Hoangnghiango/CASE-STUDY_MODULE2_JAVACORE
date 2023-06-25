package view;

import service.*;
import untils.Inputs;

public class HomePageView {
    // dựa vào instanceOf trỏ đến trang Customer homepage hay Host Homepage
    private static final HomePageView homePageView = new HomePageView();
    private HomePageView(){};
    public static  HomePageView getInstance(){return homePageView;}

    private static final AccommodationService accommodationService = AccommodationService.getInstance();
    private static final RoomService roomService = RoomService.getInstance();
    private static final RoomAvailabilityManager roomAvailabilityManager = RoomAvailabilityManager.getInstance();
    public void displayMainMenu(){
        do {
            try {
                System.out.println("""
                        1.Nhập 1 để tìm chỗ nghỉ tiếp theo...Tìm ưu đãi khách sạn, căn hộ đa dạng và nhiều hơn nữa...
                        2.Nhập 2 để trở thành đối tác của chúng tôi. Đăng chỗ nghỉ của Quý vị 
                        3.Nhập 3 để truy cập vào trang quản trị của Booking.com""");

                int choice = Inputs.IntegerPrompt("Nhap:");
                switch (choice){
                    case 1:
                        CustomerView customerView = CustomerView.getInstance();
                        customerView.displayCustomerHomePage();
                        displayMainMenu();
                    case 2:
                        HostView hostView = HostView.getInstance();
                        hostView.displayHostHomePage();
                        displayMainMenu();
                    case 3:
                        AdminView adminView = AdminView.getInstance();
                        adminView.displayAdminHomepage();
                        displayMainMenu();
                    default:
                        System.out.println("Vui lòng nhập từ 1 đến 3");
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } while(true);

    }
}
