package untils;

import entity.*;
import entity.accomodation.abstraction.Accommodation;
import entity.room.abstraction.Room;
import entity.user.Admin;
import entity.user.Customer;
import entity.user.Host;
import service.AccommodationService;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ReadAndWriteFile {
    public static List<Customer> ReadFileCustomer(String path){
        //  ghi vào id
        // khi tạo customer mới dùng constructor có id;
        // public Customer(int id, String username, String email, String password, String phone, Address address, double balance) {
        //     super(id, username, email, password, phone, address, balance);
        //    }
        List<Customer> customerList = new ArrayList<>();
        try {
            File file = new File(path);
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            List<String> customers = new ArrayList<>();
            String line;
            while((line = bufferedReader.readLine())!=null){
                customers.add(line);
            }
            for(String element : customers){
                String[] arr = element.split(",");
                Customer customer = new Customer(Integer.parseInt(arr[0]), arr[1], arr[2], arr[3],arr[4], Double.parseDouble(arr[5]));
                customerList.add(customer);
            }

        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return customerList;
    }
    public static void WriteCustomerListToFile(String path, List<Customer> customerList){
        try {
            FileWriter fileWriter = new FileWriter(path);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            for( Customer element : customerList){
                bufferedWriter.write(element.toString() +"\n");
            }
            bufferedWriter.close();
            fileWriter.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    public static List<Host> ReadFileHost(String path){
        //  ghi vào id
        // khi tạo customer mới dùng constructor có id;
        // public Customer(int id, String username, String email, String password, String phone, Address address, double balance) {
        //     super(id, username, email, password, phone, address, balance);
        //    }
        List<Host> hostList = new ArrayList<>();
        try {
            File file = new File(path);
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            List<String> hosts = new ArrayList<>();
            String line;
            while((line = bufferedReader.readLine())!=null){
                hosts.add(line);
            }
            for(String element : hosts){
                String[] arr = element.split(",");
                Host host = new Host(Integer.parseInt(arr[0]), arr[1], arr[2], arr[3],arr[4], Double.parseDouble(arr[5]));
                hostList.add(host);
            }

        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return hostList;
    }

    public static void WriteHostListToFile(String path, List<Host> hostList){
        try {
            FileWriter fileWriter = new FileWriter(path);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            for( Host element : hostList){
                bufferedWriter.write(element.toString() + "\n");
            }
            bufferedWriter.close();
            fileWriter.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public static List<Accommodation> ReadFileAccommodation(String path){
        //  ghi vào id
        // khi tạo customer mới dùng constructor có id;
        // public Customer(int id, String username, String email, String password, String phone, Address address, double balance) {
        //     super(id, username, email, password, phone, address, balance);
        //    }
        List<Accommodation> accommodationList = new ArrayList<>();
        try {
            File file = new File(path);
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            List<String> accommodations = new ArrayList<>();
            String line;
            while((line = bufferedReader.readLine())!=null){
                accommodations.add(line);
            }
            for(String element : accommodations){
                String[] arr = element.split(",");
                Accommodation accommodation = new Accommodation(Integer.parseInt(arr[0]),Integer.parseInt(arr[1]), arr[2], arr[3],Integer.parseInt(arr[4]), arr[5],Integer.parseInt(arr[6]), arr[7], arr[8], arr[9], arr[10],Double.parseDouble(arr[11]));
                accommodationList.add(accommodation);
            }

        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return accommodationList;
    }
    public static void writeAccommodationToFile(String path, List<Accommodation> accommodationList){
        try {
            FileWriter fileWriter = new FileWriter(path);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            for( Accommodation element : accommodationList){
                bufferedWriter.write(element.toString() + "\n");
            }
            bufferedWriter.close();
            fileWriter.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    public static List<Room> ReadFileRoom(String path){
        //  ghi vào id
        // khi tạo customer mới dùng constructor có id;
        // public Customer(int id, String username, String email, String password, String phone, Address address, double balance) {
        //     super(id, username, email, password, phone, address, balance);
        //    }
        List<Room> roomList = new ArrayList<>();
        try {
            File f = new File(path);
            FileReader fileReader = new FileReader(f);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            List<String> rooms = new ArrayList<>();
            String line;
            while((line = bufferedReader.readLine())!=null){
                rooms.add(line);
            }
            for(String element : rooms){
                String[] arr = element.split(",");
                Room room = new Room(Integer.parseInt(arr[0]), Integer.parseInt(arr[1]), arr[2],arr[3],arr[4],Double.parseDouble(arr[5]),Integer.parseInt(arr[6]),Integer.parseInt(arr[7]));
                roomList.add(room);
            }

        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return roomList;
    }
    public static void writeRoomToFile(String path, List<Room> roomList){
        try {
            FileWriter fileWriter = new FileWriter(path);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            for( Room element : roomList){
                bufferedWriter.write(element.toString() + "\n");
            }
            bufferedWriter.close();
            fileWriter.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public static List<Cart> readCartFromFile(String path) {
        List<Cart> cartList = new ArrayList<>();
        try {
            File f = new File(path);
            FileReader fileReader = new FileReader(f);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            List<String> rooms = new ArrayList<>();
            String line;
            while((line = bufferedReader.readLine())!=null){
                rooms.add(line);
            }
            for(String element : rooms){
                String[] arr = element.split(",");
                Cart cart = new Cart(Integer.parseInt(arr[0]),Integer.parseInt(arr[1]),Integer.parseInt(arr[2]),Double.parseDouble(arr[3]));
                cartList.add(cart);
            }

        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return cartList;
    }

    public static void WriteCartToFile(String cartPath, List<Cart> cartList) {
        try {
            FileWriter fileWriter = new FileWriter(cartPath);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            for( Cart element : cartList){
                bufferedWriter.write(element.toString() + "\n");
            }
            bufferedWriter.close();
            fileWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<DateBoard> readDateOfRoomFromFile(String path) {
        List<DateBoard> dateOfRoomList = new ArrayList<>();
        try {
            File f = new File(path);
            FileReader fileReader = new FileReader(f);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            List<String> dates = new ArrayList<>();
            String line;
            while((line = bufferedReader.readLine())!=null){
                dates.add(line);
            }
            for(String element : dates){
                String[] arr = element.split(",");
                DateBoard dateOfRoom = new DateBoard(Integer.parseInt(arr[0]),LocalDate.parse(arr[1], DateTimeFormatter.ofPattern("dd/MM/yyyy")), Integer.parseInt(arr[2]), Integer.parseInt(arr[3]),Integer.parseInt(arr[4]),Integer.parseInt(arr[5]),Double.parseDouble(arr[6]));
                dateOfRoomList.add(dateOfRoom);
            }

        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return dateOfRoomList;
    }
    public static void writeDateToFile(String path, List<DateBoard> dateOfRoomList){
        try {
            FileWriter fileWriter = new FileWriter(path);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            for( DateBoard element : dateOfRoomList){
                bufferedWriter.write(element.toString() + "\n");
            }
            bufferedWriter.close();
            fileWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void writeCartLineItemToFile(String path, List<CartLine> cartLineItemList) {
        try {
            FileWriter fileWriter = new FileWriter(path);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            for( CartLine element : cartLineItemList){
                bufferedWriter.write(element.toString() + "\n");
            }
            bufferedWriter.close();
            fileWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<CartLine> readCartLineFromFile(String path) {
        List<CartLine> cartLineItemList = new ArrayList<>();
        try {
            File f = new File(path);
            FileReader fileReader = new FileReader(f);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            List<String> cartlines = new ArrayList<>();
            String line;
            while((line = bufferedReader.readLine())!=null){
                cartlines.add(line);
            }
            for(String element : cartlines){
                String[] arr = element.split(",");
                CartLine item = new CartLine(Integer.parseInt(arr[0]), Integer.parseInt(arr[1]), Integer.parseInt(arr[2]),Integer.parseInt(arr[3]),Double.parseDouble(arr[4]),LocalDate.parse(arr[5], DateTimeFormatter.ofPattern("dd/MM/yyyy")),LocalDate.parse(arr[6], DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                cartLineItemList.add(item);
            }

        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return cartLineItemList;
    }

    public static List<Booking> ReadBookingFromFile(String path) {
        List<Booking> bookingList = new ArrayList<>();
        try {
            File f = new File(path);
            FileReader fileReader = new FileReader(f);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            List<String> cartlines = new ArrayList<>();
            String line;
            while((line = bufferedReader.readLine())!=null){
                cartlines.add(line);
            }
            for(String element : cartlines){
                String[] arr = element.split(",");
                Booking booking = new Booking(Integer.parseInt(arr[0]), Integer.parseInt(arr[1]),LocalDate.parse(arr[2], DateTimeFormatter.ofPattern("dd/MM/yyyy")),LocalDate.parse(arr[3], DateTimeFormatter.ofPattern("dd/MM/yyyy")), Integer.parseInt(arr[4]),Integer.parseInt(arr[5]),Integer.parseInt(arr[6]),arr[7],arr[8],LocalDate.parse(arr[9], DateTimeFormatter.ofPattern("dd/MM/yyyy")),Double.parseDouble(arr[10]));
                bookingList.add(booking);
            }

        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return bookingList;
    }

    public static void WriteBookingListToFile(String path, List<Booking> bookingList) {
        try {
            FileWriter fileWriter = new FileWriter(path);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            for( Booking element : bookingList){
                bufferedWriter.write(element.toString() + "\n");
            }
            bufferedWriter.close();
            fileWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static List<Booking> ReadCancelBookingFromFile(String path) {
        List<Booking> bookingList = new ArrayList<>();
        try {
            File f = new File(path);
            FileReader fileReader = new FileReader(f);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            List<String> cartlines = new ArrayList<>();
            String line;
            while((line = bufferedReader.readLine())!=null){
                cartlines.add(line);
            }
            for(String element : cartlines){
                String[] arr = element.split(",");
                Booking booking = new Booking(Integer.parseInt(arr[0]), Integer.parseInt(arr[1]),LocalDate.parse(arr[2], DateTimeFormatter.ofPattern("dd/MM/yyyy")),LocalDate.parse(arr[3], DateTimeFormatter.ofPattern("dd/MM/yyyy")), Integer.parseInt(arr[4]),Integer.parseInt(arr[5]),Integer.parseInt(arr[6]),arr[7],arr[8],LocalDate.parse(arr[9], DateTimeFormatter.ofPattern("dd/MM/yyyy")),Double.parseDouble(arr[10]));
                bookingList.add(booking);
            }

        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return bookingList;
    }

    public static void WriteCancelBookingListToFile(String path, List<Booking> bookingList) {
        try {
            FileWriter fileWriter = new FileWriter(path);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            for( Booking element : bookingList){
                bufferedWriter.write(element.toString() + "\n");
            }
            bufferedWriter.close();
            fileWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<Admin> readAdminFromFile(String path) {
        List<Admin> adminList = new ArrayList<>();
        try {
            File f = new File(path);
            FileReader fileReader = new FileReader(f);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            List<String> admins = new ArrayList<>();
            String line;
            while((line = bufferedReader.readLine())!=null){
                admins.add(line);
            }
            for(String element : admins){
                String[] arr = element.split(",");
                Admin admin = new Admin( arr[0], arr[1], arr[2],arr[3], Double.parseDouble(arr[4]));
                adminList.add(admin);
            }

        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return adminList;
    }

    public static void writeAdminToFile(String path, List<Admin> adminList) {
        try {
            FileWriter fileWriter = new FileWriter(path);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            for( Admin element : adminList){
                bufferedWriter.write(element.toString() +"\n");
            }
            bufferedWriter.close();
            fileWriter.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
