package service;

import entity.accomodation.abstraction.Accommodation;
import entity.room.abstraction.Room;
import entity.user.Host;
import factory.RoomFactory;
import untils.Inputs;
import untils.ReadAndWriteFile;

import java.util.ArrayList;
import java.util.List;

public class RoomService {
    private static final RoomService roomService = new RoomService();
    private RoomService(){

    }
    public static RoomService getInstance() { return roomService; }
    private static final String ROOM_PATH = "/Users/macbook/Desktop/HoangNghia_Module2_CaseStudy/src/file/room.txt";
    private static final String ACCOMMODATION_PATH ="/Users/macbook/Desktop/HoangNghia_Module2_CaseStudy/src/file/accommodation.txt";
    private static final HostService hostService = HostService.getInstance();

    private static List<Room> roomList = new ArrayList<>();
    private static final List<Accommodation> accommodationList = new ArrayList<>();
    static {
       roomList.addAll(ReadAndWriteFile.ReadFileRoom(ROOM_PATH));
       ReadAndWriteFile.writeRoomToFile(ROOM_PATH,roomList);
       accommodationList.addAll(ReadAndWriteFile.ReadFileAccommodation(ACCOMMODATION_PATH));
    }
    public static void addRoom(){
        if(hostService.getCurrentHost() == null){
            System.out.println("Please login as a host to add a new room.");
            HostService.login();
        }
        if(hostService.getCurrentHost() != null) {
            boolean flag = false;
            for(Accommodation acc : accommodationList){
                if(acc.getHost_id() == hostService.getCurrentHost().getId()){
                    System.out.println(acc.display() + "\n");
                    flag = true;
                }
            }
            if(!flag){
                System.out.println("Bạn chưa có chỗ nghỉ nào được tạo");
                System.out.println("Tạo chỗ nghỉ mới");
                AccommodationService.createNewAccommodation();
            } else {
                System.out.println("Chọn chỗ nghỉ của bạn");
                int roomID;
                if(roomList.size()==0){
                    roomID = 0;
                } else {
                    Room room = roomList.get(roomList.size()-1);
                    roomID = room.getId();
                }
                int accId = Inputs.IntegerPrompt("Enter the acc id:");
                System.out.println("-----------------------------ADD ROOM INTO YOUR ACCOMMODATION------------------------------");
                String roomType = Inputs.prompt("Enter Room Type You want to add: DELUXE - DOUBLE - EXECUTIVE - FAMILY - PENTHOUSE - SINGLE - SUITE  ");
                String roomName = Inputs.prompt("Enter The RoomName:  ");
                String bedInfo = Inputs.prompt("Enter The Bed Information: ");
                double price = Inputs.DoublePrompt("Enter the price");
                int quantity = Inputs.IntegerPrompt("Enter the quantity: ");
                int maxPerson = Inputs.IntegerPrompt("Enter The Max Person:");
                RoomService.createRoom(++roomID,accId, roomType, roomName, bedInfo, price, quantity, maxPerson); // da ghi file
//                String input = Inputs.prompt("Bạn muốn thêm phòng mới cho chỗ nghỉ này không ? yes/no ");
//                if ("YES".contains(input.toUpperCase())) {
//                    addRoom();
//                } else {
//                    return;
//                }
                System.out.println("**Thông tin phòng hiện tại ở chổ nghỉ của bạn**");
                getRoomByAccommodationId(accId);
            }
        }

    }
    public static void createRoom(int roomId,int accommodation_id, String roomType, String room_name, String bed_info, double price, int quantity,int maxPerson){
        Room room = RoomFactory.getRoom(roomId,accommodation_id, roomType, room_name, bed_info,price, quantity,maxPerson);
        roomList.add(room);
        ReadAndWriteFile.writeRoomToFile(ROOM_PATH,roomList);
        System.out.println("Add room successful !!! ");
    }
    public static void getRoomByAccommodationId(int accId){
        for(Room element : roomList){
            if(element.getAccommodation_id() == accId){
                System.out.println("ID: " + element.getId()+ " Tên phòng: "+element.getRoom_name() + "|" + " Loại phòng" + element.getRoomType() + "|"+ " Loại giường: "  + element.getBed_info()+ "|"+" Giá: " +  element.getPrice()+" |"+" Số lượng: "+ element.getQuantity()+ " |"+" Phòng dành cho tối đa : " + element.getMaxPerson());
            }
        }
    }

    public static List<Room> getRoomListByAccommodationId(int accId){
        List<Room> list = new ArrayList<>();
        for(Room ele : roomList){
            if(ele.getAccommodation_id() == accId){
                list.add(ele);
            }
        }
        return list;
    }
    public static Accommodation getAccommodationNameByRoomId(int roomId){
        Accommodation acc = null;
        boolean flag = false;
        for(Room ele : roomList){
            if(ele.getAccommodation_id() == roomId){
                flag = true;
                for(Accommodation element: accommodationList){
                    if(element.getId() == ele.getAccommodation_id()){
                        acc = element;
                    }
                }
            }
        }
        if(flag = false){
            System.out.println("Not find the accommodation By Room Id");
        }

        return acc;
    }


    public static String getRoomNameByRoomId(int roomId) {
        String roomName = null;
        for(Room room : roomList){
            if(room.getId() == roomId){
                roomName = room.getRoom_name();

            }
        }
        return roomName;
    }
    public static boolean MaxOccupancyByRoomID(int roomId, int maxOccupancy){
        for(Room element : roomList){
            if(element.getId()==roomId && element.getMaxPerson() >= maxOccupancy){
                return true;
            }
        }

        return false;
    }
}
//1,1,2,Deluxe,Phong Deluxe 2 nguoi,500000.0,1,1
//4,2,1,Double,Phong Double view ho boi,600000.0,2,2
//5,3,Suite,Phong Suite thượng hạng,1,2000000.0,5,2
//6,3,Double,phong duble,2,500000.0,5,2
//7,4,Suite,SUITE Sang trong danh cho 2 khach(Bao bua sang),2,2000000.0,5,2
//8,4,Suite,SUITE Sang trong dnah cho 2 khach (Bao bua sang),2,2000000.0,5,2
//9,3,Family,Phong gia dinh danh cho 4 nguoi 25m2,2,700000.0,5,4
