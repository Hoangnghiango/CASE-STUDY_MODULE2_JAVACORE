package service;

import com.sun.security.jgss.GSSUtil;
import entity.Cart;
import entity.CartLine;
import entity.DateBoard;
import untils.Inputs;
import untils.ReadAndWriteFile;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CartService {
    //    addToCart(){
    //    // b1: lay productid
    //    //b2 tinh subtotal
    //    //b3: tao ra 1 cai cartline
    //    //tinh total va update total  }
    //    //changeCart(){ tính subtotal và tính total}
    private static final CartService cartService = new CartService();
    private static final CustomerService customerService = CustomerService.getInstance();
    private static Cart currentCart; // khi login

    public static Cart getCurrentCart() {
        return currentCart;
    }

    public static void setCurrentCart(Cart currentCart) {
        CartService.currentCart = currentCart;
    }

    private static final String CART_PATH = "/Users/macbook/Desktop/HoangNghia_Module2_CaseStudy/src/file/cart.txt";
    private static final String CARTLINE_PATH = "/Users/macbook/Desktop/HoangNghia_Module2_CaseStudy/src/file/cartLine.txt";

    private static List<Cart> cartList = new ArrayList<>();
    private static List<CartLine> cartLineItemList = new ArrayList<>();
    static {
        cartList.addAll(ReadAndWriteFile.readCartFromFile(CART_PATH));
        ReadAndWriteFile.WriteCartToFile(CART_PATH,cartList);
        cartLineItemList.addAll(ReadAndWriteFile.readCartLineFromFile(CARTLINE_PATH));
        ReadAndWriteFile.writeCartLineItemToFile(CARTLINE_PATH, cartLineItemList);
    }

    private CartService(){

    }
    public static CartService getInstance(){ return cartService ;}
    //    // b1: lay productid
    //    //b2 tinh subtotal
    //    //b3: tao ra 1 cai cartline
    //    //tinh total va update total  }
    //    //changeCart(){ tính subtotal và tính total}
    // nhập thành phố, thời gian đến và thời gian đi, so nguoi => hiển thị ra nơi lưu trú,  phòng phu


    // kiểm tra xem customer đó đã có cart chưa, nếu chưa thì tạo mới, chưa có thì for để lấy ra cart của khách đó
    // CartLineList = cart.getCartLineList( thường thì rỗng)
    // lấy product cần mua bằng ProductService.getById() => product
    // tính subtotal
    // CartLineList.add(new CartLine(productId,quantity,subtotalPrice)

    public static Cart createCart(int customerId,int accId){
        int id;
        if(cartList.size() ==0){
            id = 0;
        } else {
            Cart cart = cartList.get(cartList.size()-1);
            id = cart.getCart_Id();
        }

        Cart cart = new Cart(++id,customerId,accId,0);
        cartList.add(cart);
        ReadAndWriteFile.WriteCartToFile(CART_PATH,cartList);
        return cart;
    }
    public static void addToCart(List<DateBoard> ListAfterSearch,String FromDate, String ToDate){
        //getRoom
        if(customerService.getCurrentCustomer() == null){
            CustomerService.login();
        }
        if(customerService.getCurrentCustomer() != null){
            int accID = Inputs.IntegerPrompt("Chon noi luu tru");
            currentCart = createCart(customerService.getCurrentCustomer().getId(),accID);
                // chon roomdate, so luong: cần chỉnh sửa vì trả ra roomdate theo ngày cơ
            int quantity = 0;
            List<DateBoard> RoomListByAcc = new ArrayList<>();
            for(DateBoard element : ListAfterSearch){
                if(element.getAccId() == accID){
                        System.out.println(element.display());
                        RoomListByAcc.add(element);
                }
            }

            do{
                RoomListByAcc.forEach(dateBoard -> dateBoard.display());
                int roomId = Inputs.IntegerPrompt("Chon id phong");

                List<DateBoard> RoomListByRoom = new ArrayList<>();

                for(DateBoard element : RoomListByAcc){
                    if(element.getRoomId() == roomId){
                        RoomListByRoom.add(element);
                    }
                }
                double subtotal = 0;
                do {
                    quantity = Inputs.IntegerPrompt("Nhập số lượng phòng: ");
                    int count =0;
                    for(DateBoard e : RoomListByRoom){
                        if(e.getRoomId() == roomId){
                            if(quantity <= e.getQuantity()){
                                ++count;
                            }
                        }
                    }
                    if(count == RoomListByRoom.size()){
                        break;
                    }else {
                        System.out.println("Nhập quá số lượng phòng có sẳn. Chọn lại số lượng ");
                    }
                } while (true);

                for(DateBoard ele: RoomListByRoom){
                    subtotal += ele.getPrice() * quantity ;
                    System.out.println(ele.display());
                }

                // tuy chon sua xoa cartline vua chon
                boolean flag = false;
                for(int i=0; i< cartLineItemList.size(); i++){
                    if(cartLineItemList.get(i).getCartId() == currentCart.getCart_Id() && cartLineItemList.get(i).getRoomId() == roomId){
                        System.out.println("Bạn muốn chỉnh sửa số lượng phòng này hay xoá? 1: xoá 2:sửa");
                        do {
                            int input = Inputs.IntegerPrompt("Chon 1 hoac 2 :");
                            switch(input){
                                case 1 :
                                    cartLineItemList.remove(i);
                                    ReadAndWriteFile.writeCartLineItemToFile(CARTLINE_PATH,cartLineItemList);
                                    flag = true;
                                    break;
                                case 2 :
                                    cartLineItemList.get(i).setQuantity(quantity);
                                    cartLineItemList.get(i).setSubtotal(subtotal);
                                    ReadAndWriteFile.writeCartLineItemToFile(CARTLINE_PATH,cartLineItemList);
                                    flag = true;
                                    break;
                                default:
                                    System.out.println(" nhap dang hoang coi  1 / 2 ");
                            }
                            if(flag == true){
                                break;
                            }
                        } while(true);
                        break;

                    }
                }

                if(!flag){
                    //1,3,7,2,4000000.016/06/202319/06/2023 check xem thử roomId chọn có hay chưa,chưa có thì tạo mới còn không thì sửa chứ ko cho add tiếp
                    createCartLine(currentCart.getCart_Id(),accID,roomId,quantity,subtotal,FromDate,ToDate);
                }

                String input = Inputs.prompt("Them phong tiep ? y/n/yes/no");
                if("no".toLowerCase().contains(input.toLowerCase())){
                        break;
                }
            } while (true);
                System.out.println("""
                        Mới trên Booking.com_________________________________________Chúng tôi luôn khớp giá!
                        """+
                        "................................."+AccommodationService.getAccommodationNameByAccId(accID).toUpperCase()+"..............................."+ "\n"+
                        "..................."+AccommodationService.getAddressByAccId(accID)+"....................."+ "\n" +
                        "Ngày nhận phòng______________________________________________________  Ngày trả phòng \n "+
                        FromDate + "_________________________________________________________" + ToDate+ "\n"+
                        "________________________________Phòng đã chọn________________________________________ \n"+
                        "     Loại phòng                                Giá/ tổng số đêm         Số lượng     |\n"+
                                showCartLineList() + "\n"
                        );
                double total = 0;
                for(CartLine ele : cartLineItemList){
                    if(ele.getCartId() == currentCart.getCart_Id()){
                        total += ele.getSubtotal();

                    }
                }
                currentCart.setTotal(total);
                ReadAndWriteFile.WriteCartToFile(CART_PATH,cartList);
                System.out.println("-----------------Tổng tiền: " +currentCart.getTotal()+"---------------------");
                System.out.println(" Đặt phòng\n"+
                        "Bạn sẽ được chuyển sang bước kế tiếp\n" +
                                "Xác nhận tức thời\n" );

        }
    }
    public static void createCartLine(int cartId,int accId, int roomId,int quantity,double subtotal,String FromDate, String ToDate){
        //int id, int cartId, int roomId, int quantity, double subtotal
        int id;
        if(cartLineItemList.size() ==0){
            id = 0;
        } else {
            CartLine cartline = cartLineItemList.get(cartLineItemList.size()-1);
            id = cartline.getId();
        }
        CartLine newCartLine = new CartLine(++id,cartId,roomId,quantity,subtotal, LocalDate.parse(FromDate, DateTimeFormatter.ofPattern("dd/MM/yyyy")),LocalDate.parse(ToDate, DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        System.out.println(newCartLine);
        cartLineItemList.add(newCartLine);
        ReadAndWriteFile.writeCartLineItemToFile(CARTLINE_PATH,cartLineItemList);
        System.out.println("Chọn phòng thành công");


    }
    public static void createCartLine1(int roomId, int quantity){
        double price = RoomAvailabilityManager.getPriceByRoomDateId(roomId);
        double subtotal = price * quantity;
        int id;
        if(cartLineItemList.size() ==0){
            id = 0;
        } else {
            CartLine cartline = cartLineItemList.get(cartLineItemList.size()-1);
            id = cartline.getId();
        }

        CartLine element = new CartLine(++id,currentCart.getCart_Id(),roomId,quantity,subtotal);
        System.out.println(element);
        cartLineItemList.add(element);
        ReadAndWriteFile.writeCartLineItemToFile(CARTLINE_PATH, cartLineItemList);
    }
    public static void DeleteCartLine(){


    }
    public static String showCartLineList(){
        String output = "";
        for(CartLine ele : cartLineItemList){
            if(ele.getCartId() == currentCart.getCart_Id()){
                output += ele.display();

            }
        }
        return output;
    }

}
