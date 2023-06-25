package service;

import entity.Booking;
import entity.Cart;
import entity.CartLine;
import entity.user.Admin;
import untils.Inputs;
import untils.ReadAndWriteFile;
import view.AdminView;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


public class BookingService {
    private static final BookingService bookingService = new BookingService();
    private static final CustomerService customerService = CustomerService.getInstance();
    private static final HostService hostService = HostService.getInstance();

    private static final CartService cartService = CartService.getInstance();// LAY CURRENT CART
    private static final String BOOKING_PATH = "/Users/macbook/Desktop/HoangNghia_Module2_CaseStudy/src/file/book.txt";
    private static final String CANCEL_BOOKING_PATH ="/Users/macbook/Desktop/HoangNghia_Module2_CaseStudy/src/file/cancelBooking.txt";
    private static final String CART_PATH = "/Users/macbook/Desktop/HoangNghia_Module2_CaseStudy/src/file/cart.txt";
    private static final String CARTLINE_PATH = "/Users/macbook/Desktop/HoangNghia_Module2_CaseStudy/src/file/cartLine.txt";
    private static List<Booking> bookingList = new ArrayList<>();
    private static List<Booking> cancelBookingList = new ArrayList<>();
    private static List<Cart> cartList = new ArrayList<>();
    private static List<CartLine> cartLineItemList = new ArrayList<>();

    static {
        cartList.addAll(ReadAndWriteFile.readCartFromFile(CART_PATH));
        ReadAndWriteFile.WriteCartToFile(CART_PATH,cartList);
        cartLineItemList.addAll(ReadAndWriteFile.readCartLineFromFile(CARTLINE_PATH));
        ReadAndWriteFile.writeCartLineItemToFile(CARTLINE_PATH, cartLineItemList);
        bookingList.addAll(ReadAndWriteFile.ReadBookingFromFile(BOOKING_PATH));
        ReadAndWriteFile.WriteBookingListToFile(BOOKING_PATH,bookingList);
        cancelBookingList.addAll(ReadAndWriteFile.ReadCancelBookingFromFile(CANCEL_BOOKING_PATH));
        ReadAndWriteFile.WriteCancelBookingListToFile(CANCEL_BOOKING_PATH,cancelBookingList);

    }
    private BookingService(){

    }
    public static BookingService getInstance(){ return bookingService ;}

    public static void createNewBooking(){
        int id;
        if(bookingList.size() ==0){
            id = 0;
        } else {
            Booking booking = bookingList.get(bookingList.size()-1);
            id = booking.getId();
        }
        Cart currentCart = cartService.getCurrentCart();
        int hostId = AccommodationService.getHostIdByAccId(currentCart.getAccId());
        int cartId = currentCart.getCart_Id();
        String visitorInfo = Inputs.prompt("Nhập họ tên khách lưu trú: ");
        String email = Inputs.prompt("Nhập email liên hệ: ","EMAIL");
        LocalDate fromDate = null;
        LocalDate toDate = null;
        for(CartLine ele : cartLineItemList){
            if(ele.getCartId() == currentCart.getCart_Id()){
                fromDate = ele.getFromDate();
                toDate = ele.getToDate();
                break;
            }
        }
        //fromdate-todate,total,cartid
        double total = currentCart.getTotal();
//        if(customerService.getCurrentCustomer().getBalance() < total){
//            System.out.println("Số dư hiện tại của bạn không đủ để thanh toán !. Nạp tối thiểu" +minMoneyHaveToDeposit +" để thanh toán đặt phòng này" );
//            customerService.deposit();
//        }
        do {
            double minMoneyHaveToDeposit = total - customerService.getCurrentCustomer().getBalance();
            if(customerService.getCurrentCustomer().getBalance() < total){
                System.out.println("Số dư hiện tại của bạn không đủ để thanh toán !. Nạp tối thiểu : " + minMoneyHaveToDeposit +" để thanh toán đặt phòng này" );
                customerService.deposit();
            }

        } while(customerService.getCurrentCustomer().getBalance() < total);

        if(customerService.getCurrentCustomer().getBalance() >= total){
            System.out.println("Đang thanh toán......");
            CustomerService.payment(customerService.getCurrentCustomer().getId(),total);


            // chia hoa hong cho Booking.com
            double commission = total * 15 / 100;
            AdminService.addCommission(commission);
            System.out.println("Tiền hoa hồng đến tay Booking.com");

            //chia hoa hong cho chu nha
            HostService.addFee(hostId,total);
            System.out.println("Tiền phòng để về đến ví của chủ chổ nghỉ của bạn");

            // thay đổi trang thái phòng : giảm số lượng phòng hiện tại // dựa vào roomId,quantity
            for(CartLine ele : cartLineItemList){
                if(ele.getCartId() == currentCart.getCart_Id()){
                    System.out.println(ele);
                    for(LocalDate date = fromDate ; date.isBefore(toDate);date = date.plusDays(1)){
                        RoomAvailabilityManager.updateAvailableDateWhenBooking(ele.getRoomId(),date,ele.getQuantity());
                    }
                }
            }

            Booking booking = new Booking(++id,customerService.getCurrentCustomer().getId(),fromDate,toDate,hostId,currentCart.getAccId(),cartId,visitorInfo,email,LocalDate.now(),total);
            bookingList.add(booking);
            ReadAndWriteFile.WriteBookingListToFile(BOOKING_PATH,bookingList);
            System.out.println("Booking thành công. Cảm ơn bạn đã tin tưởng Booking.com");

            //chua xu li nhan tien admin voi host
        }



    }
    public static void cancelBooking(){

        if(customerService.getCurrentCustomer() == null){
            CustomerService.login();
        }
        if(customerService.getCurrentCustomer() != null) {
            System.out.println("Booking hien tai cua ban");
            for (int j = 0; j < bookingList.size(); j++) {
                if (bookingList.get(j).getCustomer_id() == customerService.getCurrentCustomer().getId()) {
                    System.out.println(bookingList.get(j).display());
                }
            }
            int bookingId = Inputs.IntegerPrompt("Chon booking id muon xoa:");
            for (int i = 0; i < bookingList.size(); i++) {
                if (bookingList.get(i).getId() == bookingId) {
                    if (bookingList.get(i).getFromDate().minusDays(1).isAfter(LocalDate.now())) {
                        System.out.println("Miễn phí huỷ booking trước ngày nhận phòng 1 ngày. Bạn sẽ nhận lại khoản tiền hoàn ngay lập tức sau khi huỷ đơn booking này");
                        double total = 0;
                        total = bookingList.get(i).getTotal();

                        // tra phi hoan tu Booking.com
                        double commission = total * 15 / 100;
                        AdminService.subCommission(commission);
                        System.out.println(" Booking.com hoàn tiền thành công ");

                        // tra phi hoan tu host
                        int hostId = bookingList.get(i).getHost_id();
                        HostService.refunds(hostId, total);
                        System.out.println("Host hoàn tiền thành công ");

                        // nhận lại phí hoàn tiền
                        int customerId = customerService.getCurrentCustomer().getId();
                        CustomerService.getRefund(customerId, total);

                        // cập nhật vao bảng ngày phòng
                        for (int j = 0; j < cartList.size(); j++) {
                            if (cartList.get(j).getCart_Id() == bookingList.get(i).getCartId()) {
                                for (CartLine ele : cartLineItemList) {
                                    if (ele.getCartId() == bookingList.get(i).getCartId()) {
                                        for (LocalDate date = bookingList.get(i).getFromDate(); date.isBefore(bookingList.get(i).getToDate()); date = date.plusDays(1)) {
                                            RoomAvailabilityManager.updateAvailableDateWhenCancelBooking(ele.getRoomId(), date, ele.getQuantity());
                                        }
                                    }
                                }
                                break;
                            }
                        }

                        // huỷ booking này
                        bookingList.remove(i);
                        ReadAndWriteFile.WriteBookingListToFile(BOOKING_PATH, bookingList);

                        // ghi vao file cancelBooking
                        break;


                        //set lai phong trong cua booking do
                        //id +
                        //                "," + customer_id +
                        //                "," + fromDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.US))+
                        //                "," + toDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.US))+
                        //                "," + host_id +
                        //                "," + accId +
                        //                "," + cartId +
                        //                "," + visitorInfo +
                        //                "," + email +
                        //                "," + bookingDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.US))+
                        //                "," + total
                        //               ;


                    }
                    if (bookingList.get(i).getFromDate().minusDays(1).isBefore(LocalDate.now())) {
                        System.out.println("Booking của Quý khách không thể được huỷ.Quý khách sẽ không nhận lại được khoản phí hoàn trả vì sau ngày quy định của chúng tôi");
                        break;
                    }
                }
            }
        }
    }
    public static void BookingListInAdminView(){
        System.out.println("""
              1.Xem danh sách Booking - theo ngày - theo tháng - theo năm - từ ngày đến ngày
              2.Xem danh sách Booking sắp xếp từ thời điểm gần nhất đến xa nhất
              3.Xem danh sách booking của 1 chủ nhà cụ thể
              4.Xem danh sách Booking của 1 khách hàng cụ thể
              6.Quay lai
              """);
        do {
            int choice = Inputs.IntegerPrompt("");
            switch(choice){
                case 1 :getAllBooking();
                    BookingListInAdminView();
                case 2 :sortBooking();
                    BookingListInAdminView();

                case 3 :viewSpecificHostBooking();
                    BookingListInAdminView();

                case 4 :viewSpecificCustomerBooking();
                    BookingListInAdminView();

                case 6 :
                    AdminView.getInstance().displayAdminHomepage();
                default:
                    System.out.println("Nhap tu 1 -6");
            }
        }while(true);
    }

    private static void getAllBooking() {
        System.out.println("""
                1.Xem tất cả Booking
                2.Xem Booking theo ngày
                3.Xem Booking từ ngày đến ngày 
                4.Xem Booking theo tháng
                5.Xem Booking theo năm
                6.Quay lại""");
        int choice = Inputs.IntegerPrompt("Nhap lua chon:");
        do {
            switch(choice){
                case 1:
                    viewAllBooking();
                    getAllBooking();
                case 2:
                    viewBookingByDate();
                    getAllBooking();
                case 3:viewBookingFromDateToDate();
                    getAllBooking();

                case 4:
                    viewBookingByMonthYear();
                    getAllBooking();

                case 5:viewBookingByYear();
                    getAllBooking();
                case 6:
                    BookingListInAdminView();
                default:
                    System.out.println("Nhap tu 1 -6 ");

            }
        } while(true);
    }
    public static void viewAllBooking(){
        for(Booking ele : bookingList){
            System.out.println(ele.display());
        }
    }

    public static void viewCustomerBooking() {
        List<Booking> BOOKINGLISTCUSTOMER = new ArrayList<>();
        System.out.println("Booking hien tai cua ban");
        int count = 0;
        for(int j=0;j<bookingList.size();j++) {
            if (bookingList.get(j).getCustomer_id() == customerService.getCurrentCustomer().getId()) {
                System.out.println(bookingList.get(j).display());
                BOOKINGLISTCUSTOMER.add(bookingList.get(j));
                count++;
            }
        }
        if(count ==0){
            System.out.println("Chua co booking nao");
        }else {
            System.out.println("Co" + count + " Booking ");
            System.out.println("Sort");
            BOOKINGLISTCUSTOMER.sort(Comparator.comparing(Booking::getBookingDate));
            BOOKINGLISTCUSTOMER.forEach(booking -> System.out.println(booking.display()));
        }

    }
    public static void viewHostBooking() {
        List<Booking> BOOKINGLISTHOST = new ArrayList<>();
        System.out.println("Booking hien tai cua ban");
        int count = 0;
        for(int j=0;j<bookingList.size();j++) {
            if (bookingList.get(j).getHost_id() == hostService.getCurrentHost().getId()) {
                BOOKINGLISTHOST.add(bookingList.get(j));
            }
        }
        if(count ==0){
            System.out.println("Chua co booking nao");
        }else {
            System.out.println("Co" + count + " Booking ");
            System.out.println("After sort");
            BOOKINGLISTHOST.sort(Comparator.comparing(Booking::getBookingDate));
            BOOKINGLISTHOST.forEach(booking -> System.out.println(booking.display()));
        }

    }
    public static void viewBookingByDate(){
        String date = Inputs.prompt("Nhap ngay", "DATE");
        LocalDate findDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        int count = 0;
        for(int j=0;j<bookingList.size();j++) {
            if (bookingList.get(j).getBookingDate().equals(findDate)) {
                System.out.println(bookingList.get(j).display());
                count++;
            }
        }
        System.out.println("Có "+ count +" Booking vào ngày "+ date);

    }
    public static void viewBookingFromDateToDate(){
        String FromDate = Inputs.prompt("Tu ngay", "DATE");
        String ToDate = Inputs.prompt("Den ngay", "DATE");
        LocalDate FindFromDate = LocalDate.parse(FromDate, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        LocalDate FindToDate = LocalDate.parse(ToDate, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        int count = 0;
        System.out.println("Danh sách Booking tu ngay "+ FromDate +" Den ngay : "+ ToDate + ":");

        for(int j=0;j<bookingList.size();j++){
            if(bookingList.get(j).getBookingDate().equals(FindFromDate)
                    || bookingList.get(j).getBookingDate().isBefore(FindToDate)
                    || bookingList.get(j).getBookingDate().equals(FindToDate) ) {
                System.out.println(bookingList.get(j).display());
                count++;

            }
        }
        System.out.println("Có "+ count +" Booking tu ngay "+ FromDate +" Den ngay : "+ ToDate);
    }
    public static void viewBookingByMonthYear(){
        int month;
        do {
            month = Inputs.IntegerPrompt("Nhap thang");
            if(month<=12 && month >=1){
                break;
            }else {
                System.out.println("Chon sai tháng. Nhập lại đi");
            }
        } while(true);
        int year;
        do {
            year = Inputs.IntegerPrompt("Nhap nam");
            if(year<=LocalDate.now().getYear() && year >= 1996){
                break;
            }else {
                System.out.println("Chọn năm trong khoảng 1996 - LocalDate().now().getYear()");
            }
        } while(true);
        int count = 0;
        System.out.println("Danh sách Booking trong thang "+ month +" Nam : "+ year + ":");

        for(int j=0;j<bookingList.size();j++){
            if(bookingList.get(j).getBookingDate().getMonthValue() == month
                    && bookingList.get(j).getBookingDate().getYear() == year) {

                System.out.println(bookingList.get(j).display());
                count++;
            }
        }
        System.out.println("Có "+ count +" Booking ");

    }
    public static void viewBookingByYear(){
        int year;
        do {
            year = Inputs.IntegerPrompt("Nhap nam");
            if(year<=LocalDate.now().getYear() && year >= 1996){
                break;
            }else {
                System.out.println("Chọn năm trong khoảng 1996 - LocalDate().now().getYear()");
            }
        } while(true);
        int count = 0;
        System.out.println("Danh sách Booking trong nam: "+ year);

        for(int j=0;j<bookingList.size();j++) {
            if(bookingList.get(j).getBookingDate().getYear() == year) {
                System.out.println(bookingList.get(j).display());
                count++;
            }
        }
        System.out.println("Có "+ count +" Booking ");

    }
    public static void sortBooking(){
        bookingList.sort(Comparator.comparing(Booking::getBookingDate));
        bookingList.forEach(booking -> System.out.println(booking.display()));
                //l.forEach((a)->System.out.println(a));
        //Collections.sort(listofemployee, new Comparator<Employee>(){
        //
        //            @Override
        //            public int compare(Employee emp1, Employee emp2) {
        //                if(emp1.getDOJ().isBefore(emp2.getDOJ()))
        //                    return +1;
        //                else if (emp1.getDOJ().isAfter(emp2.getDOJ)))
        //                    return -1
        //                else
        //                    return 0;
        //            }
        //
        //        });
    }
    public static void viewSpecificHostBooking(){
        viewAllBooking();
        int hostid = Inputs.IntegerPrompt("Nhap Id cua host ");
        int count = 0;
        for(Booking ele : bookingList){
            if(ele.getHost_id() == hostid){
                System.out.println(ele.display());
                count++;
            }
        }
        if(count ==0){
            System.out.println("Chua co booking nao");
        }else {
            System.out.println("Co" + count + " Booking ");
        }

    }
    public static void viewSpecificCustomerBooking(){
        viewAllBooking();
        int customerId = Inputs.IntegerPrompt("Nhap Id cua Khach hang ");
        int count = 0;
        for(Booking ele : bookingList){
            if(ele.getCustomer_id() == customerId){
                System.out.println(ele.display());
                count++;
            }
        }
        if(count ==0){
            System.out.println("Chua co booking nao");
        }else {
            System.out.println("Co" + count + " Booking ");
        }

    }


    public static void viewAllCancelBooking() {
        cancelBookingList.sort(Comparator.comparing(Booking::getBookingDate));
        cancelBookingList.forEach(booking -> System.out.println(booking.display()));
    }
}
