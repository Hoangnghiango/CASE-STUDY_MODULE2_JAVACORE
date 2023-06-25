package service;

import entity.DateBoard;
import entity.accomodation.abstraction.Accommodation;
import entity.room.abstraction.Room;
import untils.Inputs;
import untils.ReadAndWriteFile;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RoomAvailabilityManager {
    private static final String DATE_PATH = "/Users/macbook/Desktop/HoangNghia_Module2_CaseStudy/src/file/date.txt";
    private static final String ACCOMMODATION_PATH ="/Users/macbook/Desktop/HoangNghia_Module2_CaseStudy/src/file/accommodation.txt";
    private static final String ROOM_PATH = "/Users/macbook/Desktop/HoangNghia_Module2_CaseStudy/src/file/room.txt";

    private RoomAvailabilityManager(){
    }
    public static RoomAvailabilityManager getInstance() {return roomAvailabilityManager ; }
    private static final RoomAvailabilityManager roomAvailabilityManager = new RoomAvailabilityManager();
    private static final HostService hostService = HostService.getInstance();

    private static List<Accommodation> accommodationList = new ArrayList<>();
    private static List<Room> roomList = new ArrayList<>();
    private static List<DateBoard> availableDates = new ArrayList<>();

    static {
        availableDates.addAll(ReadAndWriteFile.readDateOfRoomFromFile(DATE_PATH));
        ReadAndWriteFile.writeDateToFile(DATE_PATH, availableDates);
        accommodationList.addAll(ReadAndWriteFile.ReadFileAccommodation(ACCOMMODATION_PATH));
        roomList.addAll(ReadAndWriteFile.ReadFileRoom(ROOM_PATH));
    }

    public static void setRoomAvailability(){
        if(hostService.getCurrentHost() == null){
            System.out.println("Bạn chưa đăng nhập !! Đăng nhập để tạo mới chỗ nghỉ");
            HostService.login();
        }
        if(hostService.getCurrentHost() != null){}
        List<Accommodation> accommodationListFound = AccommodationService.displayAccommodationList();
        for( Accommodation acc: accommodationListFound){
            System.out.println("--------------------");
            System.out.println(acc.display());

        }
        int accId = Inputs.IntegerPrompt("Chọn id chỗ nghỉ để thêm phòng trong: ");
        List<Room> roomListFound = RoomService.getRoomListByAccommodationId(accId);
        for(Room room : roomListFound){
            System.out.println("---------------------");
            System.out.println(room.display());

        }

        int roomId = Inputs.IntegerPrompt("Chọn id phòng để thêm ngày phòng");
        String date;
        do {
             date = Inputs.prompt("Nhập ngày phòng theo format dd/MM/YYYY", "DATE");
             if(LocalDate.parse(date, DateTimeFormatter.ofPattern("dd/MM/yyyy")).isBefore(LocalDate.now())){
                 System.out.println("Bạn không thể thêm phòng vào thời điểm trong quá khứ ");
             }else {
                 break;
             }
        } while (true);
        int numberOfRoom = Inputs.IntegerPrompt("Nhap so luong phong co san");
        double price = Inputs.DoublePrompt("Nhập giá phòng linh hoạt : ");

        boolean flag = false;
        for(int i=0; i<availableDates.size();i++){
            if(availableDates.get(i).getRoomId() == roomId && availableDates.get(i).getDate().equals(LocalDate.parse(date, DateTimeFormatter.ofPattern("dd/MM/yyyy")))){
                flag = true;
                System.out.println("Đã set lịch phòng " + roomId + " vao ngay : " + date +".Ban co muon chinh sua phong nay khong ? y/n");
                String input = Inputs.prompt("Yes or No? y/n/yes/no");
                if("Yes".toLowerCase().contains(input.toLowerCase())){
                    editRoomDate(date,roomId,numberOfRoom,price);
                }
                String input2 = Inputs.prompt("Them moi: yes/no/y/n");
                if("Yes".toLowerCase().contains(input2.toLowerCase())){
                    setRoomAvailability();
                }else {

                }
            }
        }
        if(flag == false){
            System.out.println("Set lịch mới:");
            int id;
            if(availableDates.size() ==0){
                id = 0;
            } else {
                DateBoard room = availableDates.get(availableDates.size()-1);
                id = room.getId();
            }
                DateBoard newDate = new DateBoard(++id,LocalDate.parse(date, DateTimeFormatter.ofPattern("dd/MM/yyyy")),accId,hostService.getCurrentHost().getId(), roomId, numberOfRoom, price);
                String AccName = AccommodationService.getAccommodationNameByAccId(accId);
                String roomName = RoomService.getRoomNameByRoomId(roomId);
                System.out.println("Thêm thành công "+ numberOfRoom + " phòng: " + roomName + " taị chỗ nghỉ: "+ AccName + " của bạn.");
                availableDates.add(newDate);
                ReadAndWriteFile.writeDateToFile(DATE_PATH,availableDates);
                String input = Inputs.prompt("Them moi: yes/no/y/n");
                if("Yes".toLowerCase().contains(input.toLowerCase())){
                    setRoomAvailability();
                }

        }
    }

    private static void editRoomDate(String date, int roomId, int newNumber, double price) {
        for(DateBoard element : availableDates){
            if(element.getDate().equals(LocalDate.parse(date, DateTimeFormatter.ofPattern("dd/MM/yyyy"))) && element.getRoomId() == roomId){
                element.setQuantity(newNumber);
                element.setPrice(price);
                System.out.println("Đã chỉnh sửa số phòng !!");
            }
        }
        ReadAndWriteFile.writeDateToFile(DATE_PATH,availableDates);
    }

    public static List<DateBoard> findAvailableRoomByCriteria(){ // mới làm đến trả danh sách phòng date phù hợp theo mỗi ngày
        String city;
        city = Inputs.prompt("Ten thanh pho");
        int maxOccupancy;
        maxOccupancy = Inputs.IntegerPrompt("Nguoi lon: ");
        int SoPhong;
        SoPhong = Inputs.IntegerPrompt("So phong: ");

        //DATE
        String FromDate = null;
        String ToDate = null;
        try {
            do {
                FromDate = Inputs.prompt(" NGAY DEN:", "DATE");
                if(LocalDate.parse(FromDate, DateTimeFormatter.ofPattern("dd/MM/yyyy")).isBefore(LocalDate.now())){
                    System.out.println("Bạn không thể đến vào thời điểm trong quá khứ. Chọn ngày lại ");
                }else {
                    break;
                }
            } while (true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            do {
                ToDate = Inputs.prompt(" NGAY DI:", "DATE");
                if(LocalDate.parse(ToDate, DateTimeFormatter.ofPattern("dd/MM/yyyy")).isBefore(LocalDate.parse(FromDate, DateTimeFormatter.ofPattern("dd/MM/yyyy")))){
                    System.out.println("Nhap lai");
                } else {
                    break;
                }
            } while(true);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        double minPrice;
        double maxPrice;
        minPrice = Inputs.DoublePrompt("Mininum Price");
        maxPrice = Inputs.DoublePrompt("Max Price");
        String accommodationType;
        accommodationType = Inputs.prompt("SELECT TYPE OF ACCOMMODATION");


//        List<Accommodation> ACCLISTFOUND = AccommodationService.getAccommodationListByCity(city);
//        List<Room> ROOMLISTFOUND = new ArrayList<>();
//        for( Accommodation acc: ACCLISTFOUND){
//            List<Room> R = RoomService.getRoomListByAccommodationId(acc.getId());
//            for(Room element : R){
//                ROOMLISTFOUND.add(element);
//            }
//        }
//        for(int i =0; i <= ROOMLISTFOUND.size(); i++){
//            if(ROOMLISTFOUND.get(i).getMaxPerson() != maxOccupancy){
//                ROOMLISTFOUND.remove(i);
//            }
//        }
        List<DateBoard> FOUND = new ArrayList<>();
        // Search danh sách phòng trống theo city, số phòng, tối đa người ở, khoảng giá,loại nơi lưu trú
        for(DateBoard element : availableDates){
            if(AccommodationService.getCityByAccId(element.getAccId()).toLowerCase().contains(city.toLowerCase())
            && element.getQuantity() >= SoPhong && RoomService.MaxOccupancyByRoomID(element.getRoomId(),maxOccupancy)
            && element.getPrice() <= maxPrice && element.getPrice() >= minPrice
            && AccommodationService.getAccommodationTypeByAccId(element.getAccId()).toLowerCase().contains(accommodationType.toLowerCase())){
                FOUND.add(element);
            }
        }
        LocalDate startDate;
        LocalDate endDate;
        try {
             startDate = LocalDate.parse(FromDate, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
             endDate = LocalDate.parse(ToDate, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        //for (LocalDate date = startDate; date.isBefore(endDate); date = date.plusDays(1))
        //{
        //    ...
        //}
        // xử lí phòng trống fromdate -> toDate
        List<DateBoard> ListDateRoomAfterFromDateAndToDate = new ArrayList<>();

        for(int i = 0; i < FOUND.size(); i++){
            boolean flag = false;
            for(LocalDate date = startDate ; date.isBefore(endDate);date = date.plusDays(1)) {
                if(FOUND.get(i).getDate().equals(date)){
                    flag = true;
                    ListDateRoomAfterFromDateAndToDate.add(FOUND.get(i));
                    break;
                }
            }
        }
        // tìm danh sách phòng ngày startDate;
        List<DateBoard> AvailableRoomInStartDate = getDateBoardListByStarDate(ListDateRoomAfterFromDateAndToDate,startDate);

        // tìm danh sách phòng khớp tất cả điều kiện , có sẵn trong khoảng ngày khách chọn
        List<DateBoard> listAfterSearching = new ArrayList<>();

        for(DateBoard item : AvailableRoomInStartDate){
            Period different = Period.between(startDate, endDate);
            int days = different.getDays();
            List<DateBoard> ROOMLIST = getRoomListRangeTimeByRoomId(item,ListDateRoomAfterFromDateAndToDate);
            if(ROOMLIST.size() == days){
                for(DateBoard room : ROOMLIST){
                    listAfterSearching.add(room);
                }
            }
        }
        for (DateBoard ele: listAfterSearching) {
            System.out.println(ele.display());

        }
        if(listAfterSearching.size() ==0){
            System.out.println("Xui ghê !!! Hem cho chổ nghỉ nào luôn .Tìm lại đyyyyyyyy");
            RoomAvailabilityManager.findAvailableRoomByCriteria();
        }
        CartService.addToCart(listAfterSearching,FromDate,ToDate);


        return listAfterSearching;
    }

    private static List<DateBoard> getRoomListRangeTimeByRoomId(DateBoard item, List<DateBoard> found) {
        List<DateBoard> ROOMLIST = new ArrayList<>();
        for(DateBoard ele : found){
            if(ele.getRoomId() == item.getRoomId()){
                ROOMLIST.add(ele);
            }
        }

        return ROOMLIST;
    }

    public static void SearchAndBooking(){
        String city;
        city = Inputs.prompt("Ten thanh pho");
        int maxOccupancy;
        maxOccupancy = Inputs.IntegerPrompt("Nguoi lon: ");
        int quantity;
        quantity = Inputs.IntegerPrompt("So phong: ");
        //DATE
        String FromDate;
        String ToDate;
        do {
            FromDate = Inputs.prompt(" NGAY DEN:", "DATE");
            if(LocalDate.parse(FromDate, DateTimeFormatter.ofPattern("dd/MM/yyyy")).isBefore(LocalDate.now())){
                System.out.println("Bạn không thể đến vào thời điểm trong quá khứ. Chọn ngày lại ");
            }else {
                break;
            }
        } while (true);
        do {
            ToDate = Inputs.prompt(" NGAY DI:", "DATE");
            if(LocalDate.parse(ToDate, DateTimeFormatter.ofPattern("dd/MM/yyyy")).isBefore(LocalDate.parse(FromDate, DateTimeFormatter.ofPattern("dd/MM/yyyy")))){
                System.out.println("Nhap lai");
            } else {
                break;
            }
        } while(true);

        List<Accommodation> matchingAccommodations = searchAccommodations(accommodationList, roomList, availableDates, city, LocalDate.parse(FromDate, DateTimeFormatter.ofPattern("dd/MM/yyyy")), LocalDate.parse(ToDate, DateTimeFormatter.ofPattern("dd/MM/yyyy")), maxOccupancy, quantity);

    }
    public static List<Accommodation> searchAccommodations(List<Accommodation> accommodations, List<Room> rooms,
                                                           List<DateBoard> availableDates, String city, LocalDate fromDate,
                                                           LocalDate toDate, int maxOccupancy, int quantity){
        List<Accommodation> matchingAccommodations = new ArrayList<>();


        return matchingAccommodations ;
    }
    public static double getPriceByRoomDateId(int availableId){
        double price = 0;
        for(DateBoard element : availableDates){
            if(element.getId()==availableId){
                price = element.getPrice();
            }
        }
        return price;
    }
    public static List<DateBoard> getDateBoardListByStarDate(List<DateBoard> list, LocalDate startDate){
        List<DateBoard> ListInStartDay = new ArrayList<>();
        for(DateBoard element : list){
            if(element.getDate().equals(startDate)){
                ListInStartDay.add(element);
            }
        }
        return ListInStartDay;
    }
    public static void updateAvailableDateWhenBooking(int roomId, LocalDate date, int quantity){
        for(int i = 0; i < availableDates.size(); i++){
            if(availableDates.get(i).getRoomId() == roomId && availableDates.get(i).getDate().equals(date)){
                int currentQuantity = availableDates.get(i).getQuantity();
                availableDates.get(i).setQuantity(currentQuantity - quantity );
                ReadAndWriteFile.writeDateToFile(DATE_PATH,availableDates);
                break;
            }
        }
    }
    public static void updateAvailableDateWhenCancelBooking(int roomId, LocalDate date, int quantity){
        for(int i = 0; i < availableDates.size(); i++){
            if(availableDates.get(i).getRoomId() == roomId && availableDates.get(i).getDate().equals(date)){
                int currentQuantity = availableDates.get(i).getQuantity();
                availableDates.get(i).setQuantity(currentQuantity + quantity );
                ReadAndWriteFile.writeDateToFile(DATE_PATH,availableDates);
                break;
            }
        }
    }



}
