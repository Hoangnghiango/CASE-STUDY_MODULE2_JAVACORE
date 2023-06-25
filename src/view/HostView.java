package view;

import service.AccommodationService;
import service.BookingService;
import service.HostService;
import service.RoomAvailabilityManager;
import untils.Inputs;

public class HostView {
    private static final HostView hostView = new HostView();
    private HostView(){}
    public static HostView getInstance(){ return hostView ;}
    private static final AccommodationService accommodationService = AccommodationService.getInstance();

    public void displayHostHomePage(){
        System.out.println("Đăng chỗ nghỉ của Quý vị trên Booking.com "+"\n" +
                "Đăng kí miễn phí và chỉ mất 15 phút để hoàn tất – hãy bắt đầu ngay hôm nay");
        System.out.println("""
                1.Đăng ký 
                2.Đăng nhập
                3.Tạo chỗ nghỉ mới
                4.Xem booking
                5.Xem số dư
                6.Chinh sua thong tin
                7.Đăng xuất """);
        do {
            int choice = Inputs.IntegerPrompt("nhap lua chon:");
            switch(choice){
                case 1:
                    HostService.register();
                    displayHostHomePage();
                case 2:
                    HostService.login();
                    displayHostHomePage();
                case 3:
                    AccommodationService.createNewAccommodation();
                    RoomAvailabilityManager.setRoomAvailability();
                    displayHostHomePage();

                case 4 :
                    BookingService.viewHostBooking(); // thong ke theo ngay tuan thang
                    displayHostHomePage();

                case 5:
                    HostService.getBalance();
                    displayHostHomePage();

                case 6:
                    HostService.editHostInformation();
                    displayHostHomePage();
                case 7:
                    HostService.logout();
                    HomePageView.getInstance().displayMainMenu();

                default:
                    System.out.println("sai");
            }
        } while (true);
    }
}
