package service;

import entity.DateBoard;
import entity.accomodation.abstraction.Accommodation;
import entity.room.abstraction.Room;
import entity.user.Host;
import untils.Inputs;
import untils.ReadAndWriteFile;
import view.AdminView;

import java.util.ArrayList;
import java.util.List;

public class AccommodationService {
    private static String notification;
    private static final AccommodationService accommodationService = new AccommodationService();
    private AccommodationService(){

    }
    public static AccommodationService getInstance(){ return accommodationService; }
    private static final String ACCOMMODATION_PATH ="/Users/macbook/Desktop/HoangNghia_Module2_CaseStudy/src/file/accommodation.txt";
    private static final String ROOM_PATH = "/Users/macbook/Desktop/HoangNghia_Module2_CaseStudy/src/file/room.txt";
    private static final String DATE_PATH = "/Users/macbook/Desktop/HoangNghia_Module2_CaseStudy/src/file/date.txt";
    private static final HostService hostService = HostService.getInstance();


    private static List<Accommodation> accommodationList = new ArrayList<>();
    private static List<Room> roomList = new ArrayList<>();
    private static List<DateBoard> availableDates = new ArrayList<>();


    static {
        accommodationList.addAll(ReadAndWriteFile.ReadFileAccommodation(ACCOMMODATION_PATH));
        ReadAndWriteFile.writeAccommodationToFile(ACCOMMODATION_PATH, accommodationList);
        roomList.addAll(ReadAndWriteFile.ReadFileRoom(ROOM_PATH));
        availableDates.addAll(ReadAndWriteFile.readDateOfRoomFromFile(DATE_PATH));
    }

    public static void createNewAccommodation(){
        if(hostService.getCurrentHost() == null){
            System.out.println("Bạn chưa đăng nhập !! Đăng nhập để tạo mới chỗ nghỉ");
            HostService.login();
        }
        if(hostService.getCurrentHost() != null){
            AccommodationService.getAccommodationListByCurrentHost();
            System.out.println("Đăng chỗ nghỉ của Quý vị trên Booking.com và bắt đầu đón tiếp khách thật nhanh chóng!\n" +
                    "Để bắt đầu, chọn loại chỗ nghỉ Quý vị muốn đăng trên Booking.com");
            System.out.println(""" 
                    |Bạn muốn tạo loại ?|
                    -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
                    BUNGALOW  : mô hình lưu trú được xây dựng hài hòa với thiên nhiên
                    HOTEL     : mô hình lưu trú  nằm trong một tòa nhà hoặc nằm trong một dãy nhiều phòng với nhau
                    RESORT    : mô hình lưu trú theo cụm như (villa, bungalow…), với những cảnh quan vô cùng hòan hảo và có tính thẩm mỹ cáo nhằm mục đích phục vụ nhu cầu nghỉ dưỡng
                    VILLA     : mô hình lưu trú được xây dựng trên một khoảng đất có diện tích lớn. Tất cả những căn villa đều có quy mô lớn, chú trọng sự thông thoáng với không gian được thiết kế hài hòa và gần gũi với thiên nhiên.
                    APARTMENT : Chỗ nghỉ tự nấu nướng, đầy đủ nội thất mà khách thuê nguyên căn.
                    CRUISE    : Các chỗ nghỉ như tàu thuyền, khu cắm trại, lều trại sang trọng, v.v.
                    """);
            String accommodation_type = Inputs.prompt("Nhập loại chỗ nghỉ: ");
            String accommodation_name = Inputs.prompt("Nhập tên chỗ lưu trú của bạn (Tên chỗ lưu trú này sẽ hiển thị với khách hàng) ");
            int star = Inputs.IntegerPrompt("Nhập số sao ");
            String description = Inputs.prompt("Nhập mô tả gây ấn tượng: ");
            int numberOfAddress = Inputs.IntegerPrompt(" Nhập số nhà : ");
            String street = Inputs.prompt("Nhập tên đường : ");
            String ward = Inputs.prompt("Nhập tên phường :  ");
            String district =  Inputs.prompt("Nhập tên quận/ huyện : ");
            String city =  Inputs.prompt("Nhập thành phố/ tỉnh");
            int accid;
            if(accommodationList.size() == 0){
                accid = 0;
            } else {
                Accommodation acc = accommodationList.get(accommodationList.size()-1);
                accid = acc.getId();
            }
            Accommodation accommodation = new Accommodation(++accid,hostService.getCurrentHost().getId(), accommodation_name,accommodation_type,star,description,numberOfAddress,street,ward,district,city,0);
            System.out.println("Chúc mừng " + hostService.getCurrentHost().getUsername() + "đã tạo mới chỗ nghỉ " + accommodation.getAccommodation_name());
            accommodationList.add(accommodation);
            ReadAndWriteFile.writeAccommodationToFile(ACCOMMODATION_PATH, accommodationList);
            System.out.println("Tạo phòng cho chổ nghỉ này ");
            do{
                RoomService.addRoom();
                String input = Inputs.prompt("them moi. yes/no/y/n");
                if("no".toLowerCase().contains(input.toLowerCase())){
                   break;
                }
            }while(true);

        }
    }
    private static void editAccommodation() {
        Host host = HostService.getInstance().getCurrentHost();
        if(host == null){
            System.out.println("Bạn chưa đăng nhập !! Đăng nhập để chỉnh sửa chỗ nghỉ");
            CustomerService.getInstance().login();
        }
        System.out.println(" Chỉnh sửa cơ sở lưu trú của bạn ");
        do {
            System.out.println("""
                         1.Chỉnh sửa tên lưu trú
                         2.Chỉnh sửa số sao
                         3.Chỉnh sửa mô tả
                         4.Chỉnh sửa địa chỉ
                         5.Tiep tuc
                         
                        """);
            int choice = Inputs.IntegerPrompt("Nhập lựa chọn của bạn: ");
            switch (choice) {
                case 1:
                    editAccommodationName();
                    break;
                case 2:
                    editAccommodationStar();
                    break;
                case 3:
                    editAccommodationDescription();
                    break;
                case 4:
                    editAccommodationAddress();
                    break;
                default:
                    System.out.println("Vui long nhap tu 1 den 5 ");

            }
        } while (true);

    }
    private static void editAccommodationAddress() {
        Host host = HostService.getCurrentHost();
        boolean found = false;
        for(Accommodation acc : accommodationList){
            if(host.getId() == acc.getHost_id()){
                System.out.println(acc + "\n");
                found = true;
            } else {
                System.out.println("Bạn chưa có chỗ lưu trú nào cả !!! ");
            }
        }
        if(found){
            int acc_id = Inputs.IntegerPrompt("Nhập accommodation_id bạn muốn chỉnh sửa thông tin: ");
            int numberOfAddress = Inputs.IntegerPrompt(" Nhập số nhà : ");
            String street = Inputs.prompt("Nhập tên đường : ");
            String ward = Inputs.prompt("Nhập tên phường :  ");
            String district =  Inputs.prompt("Nhập tên quận/ huyện : ");
            String city =  Inputs.prompt("Nhập thành phố/ tỉnh");
            for( Accommodation acc: accommodationList){
                if(acc.getId() == acc_id){
                    acc.setNumberOfAddress(numberOfAddress);
                    acc.setStreet(street);
                    acc.setWard(ward);
                    acc.setDistrict(district);
                    acc.setCity(city);
                    System.out.println("Cho nghi được sửa thành công.");
                    break;
                }
            }
        }
    }

    private static void editAccommodationDescription() {
        Host host = HostService.getCurrentHost();
        boolean found = false;
        for(Accommodation acc : accommodationList){
            if(host.getId() == acc.getHost_id()){
                System.out.println(acc + "\n");
                found = true;
            } else {
                System.out.println("Bạn chưa có chỗ lưu trú nào cả !!! ");
            }
        }
        if(found){
            int acc_id = Inputs.IntegerPrompt("Nhập accommodation_id bạn muốn chỉnh sửa thông tin: ");
            String description =  Inputs.prompt("Nhập mo ta");
            for( Accommodation acc: accommodationList){
                if(acc.getId() == acc_id){
                    acc.setDescription(description);
                    System.out.println("Mo ta được sửa thành công.");
                    break;
                }
            }
        }
    }

    private static void editAccommodationStar() {
        Host host = HostService.getCurrentHost();
        boolean found = false;
        for(Accommodation acc : accommodationList){
            if(host.getId() == acc.getHost_id()){
                System.out.println(acc + "\n");
                found = true;
            } else {
                System.out.println("Bạn chưa có chỗ lưu trú nào cả !!! ");
            }
        }
        if(found){
            int acc_id = Inputs.IntegerPrompt("Nhập accommodation_id bạn muốn chỉnh sửa thông tin: ");
            int star = Inputs.IntegerPrompt(" Nhập số sao : ");

            for( Accommodation acc: accommodationList){
                if(acc.getId() == acc_id){
                   acc.setStar(star);
                    System.out.println("Cho nghi được sửa thành công.");
                    break;
                }
            }
        }
    }

    private static void editAccommodationName() {
        Host host = HostService.getCurrentHost();
        boolean found = false;
        for(Accommodation acc : accommodationList){
            if(host.getId() == acc.getHost_id()){
                System.out.println(acc + "\n");
                found = true;
            } else {
                System.out.println("Bạn chưa có chỗ lưu trú nào cả !!! ");
            }
        }
        if(found){
            int acc_id = Inputs.IntegerPrompt("Nhập accommodation_id bạn muốn chỉnh sửa thông tin: ");
            String accommodation_name = Inputs.prompt("Nhập tên chỗ lưu trú của bạn (Tên chỗ lưu trú này sẽ hiển thị với khách hàng) ");
            for( Accommodation acc: accommodationList){
                if(acc.getId() == acc_id){
                    acc.setAccommodation_name(accommodation_name);
                    System.out.println("Tên cho nghi được sửa thành công.");
                    break;
                }
            }
        }


    }

    public static int getHostIdByAccId(int accId) {
        int output = 0;
        for(Accommodation ele : accommodationList){
            if(ele.getId() == accId){
                output = ele.getHost_id();
                break;
            }
        }
        return output;
    }

    // hiển thị tất cả các Chỗ nghỉ
    public static void displayAllAccommodation(){
        do {
            try {

                System.out.println("""
                        Hiển thị theo:
                        1.Thành phố 
                        2.Hạng sao 
                        3.Loại chổ nghỉ
                        4.Điểm đánh giá
                        5.Quay lai""");

                int choice  = Inputs.IntegerPrompt("nhap lua chon");
                switch(choice){
                    case 1 :
                        displayAccommodationByCity();
                        displayAllAccommodation();
                    case 2 :
                        displayAccommodationByStar();
                        displayAllAccommodation();
                        case 3:
                        displayAccommodationByType();
                        displayAllAccommodation();

                    case 4:
                        displayAccommodationByRating();
                        displayAllAccommodation();
                    case 5:
                        AdminView.getInstance().displayAdminHomepage();
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        } while (true);
    }

    public static void displayAccommodationByRating() {
        boolean flag = false;
        double RATING = Inputs.DoublePrompt("Diem danh gia: ");
        System.out.println("---------------------Loading----------------");
        for(Accommodation acc: accommodationList){
            if(acc.getRating() >= RATING){
                System.out.println(acc.display());
                flag = true;
            }

        }
        if(!flag){
            System.out.println("Không tìm thấy chỗ nghỉ nào với số sao bạn muốn");
        }
    }

    public static void displayAccommodationByType() {
        boolean flag = false;
        System.out.println("Loai cho nghi");
        String type = Inputs.prompt("Chọn loại chổ nghỉ: HOTEL- VILLA- APARTMENT-BUNGALOW-RESORT ");
        System.out.println("--------------Loading---------------");
        for(Accommodation acc: accommodationList){
            if(acc.getAccommodation_type().toLowerCase().contains(type.toLowerCase())){
                System.out.println(acc.display());
                flag = true;
            }

        }
        if(!flag){
            System.out.println("Só rì hiện tại chưa có loại phòng này ở Booking.com");
        }
    }

    public static void displayAccommodationByStar() {
        boolean flag = false;
        System.out.println("Mí sao");
        int star = Inputs.IntegerPrompt("tu may sao: ");
        System.out.println("-----------------------Loading----------------");
        for(Accommodation acc: accommodationList){
            if(acc.getStar() >= star){
                System.out.println(acc.display());
                flag = true;
            }

        }
        if(!flag){
            System.out.println("Không tìm thấy chỗ nghỉ nào với số sao bạn muốn");
        }
    }

    public static void displayAccommodationByCity() {
        boolean flag = false;
        System.out.println("O dau?");
        String city = Inputs.prompt("Nhập thành phố: ");
        System.out.println("-----------------------Loading----------------");
        for(Accommodation acc: accommodationList){
            if(acc.getCity().toLowerCase().contains(city.toLowerCase())){
                System.out.println(acc.display());
                flag = true;
            }
        }
        if(!flag){
            System.out.println("Không tìm thấy số nghỉ nào tại " + city);
        }
    }
    public void sort(){

    }
    public static List<Accommodation> displayAccommodationList(){
        List<Accommodation> arr = new ArrayList<>();
        for(Accommodation ele : accommodationList){
            if(ele.getHost_id() == hostService.getCurrentHost().getId()){
                arr.add(ele);
            }
        }
        if(arr.size() == 0){
            System.out.println("Hãy tạo chỗ nghỉ của bạn để bắt đầu kinh doanh ngay !!!");
            createNewAccommodation();
        }
        return arr;
    }
    public static String getAccommodationNameByAccId(int accId){
        String name = null;
        for(Accommodation acc: accommodationList){
            if(acc.getId() == accId){
                name = acc.getAccommodation_name();
            }
        }
        return name;
    }
    public static String getAccommodationTypeByAccId(int accId){
        String output = null;
        for(Accommodation acc : accommodationList){
            if(acc.getId() == accId){
                output = acc.getAccommodation_type();
                break;
            }
        }
        return output;
    }
    public static String getCityByAccId(int accId){
        String city = null;
        for(Accommodation acc : accommodationList){
            if(acc.getId() == accId){
                city =  acc.getCity();
                break;
            }
        }
        return city;
    }
    public static List<Accommodation> getAccommodationListByCity(String city){
        List<Accommodation> FOUND = new ArrayList<>();
        for( Accommodation ele : accommodationList){
            if(ele.getCity().contains(city)){
                FOUND.add(ele);
            }
        }
        return FOUND;
    }
    public static List<Accommodation> getAccommodationListAfterSearchFromDateAndToDate(){
        List<Accommodation> accommodationlist = new ArrayList<>();
        for(DateBoard element : availableDates){

        }

        return accommodationlist;
    }
    public static List<Accommodation> getAccommodationListAfterSearchMaxNumberPerson(int maxOccupancy){
        List<Accommodation> accommodationlist = new ArrayList<>();
        for(DateBoard element : availableDates){
           if(RoomService.MaxOccupancyByRoomID( element.getRoomId(),maxOccupancy)){
           };// iss true hay false

        }
        return accommodationlist;
    }
    public static void getAccommodationListByCurrentHost(){
        for(Accommodation element : accommodationList){
            if(element.getHost_id()==hostService.getCurrentHost().getId()){
                System.out.println(element.display());            }
        }


    }
    public static String getAddressByAccId(int accId){
        String output = null;
        for(Accommodation e : accommodationList){
            if(e.getId() == accId){
                output = e.getNumberOfAddress()+","+ e.getStreet() +"," + e.getWard()+"," +e.getDistrict()+","+ e.getCity();
                break;
            }
        }
        return output;
    }



}
