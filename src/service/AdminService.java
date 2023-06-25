package service;

import entity.user.Admin;
import untils.ReadAndWriteFile;

import java.util.ArrayList;
import java.util.List;

public class AdminService {
    private static final AdminService adminService = new AdminService();
    private AdminService(){};
    public static final AdminService getInstance(){ return adminService ;}
    private static final String ADMIN_PATH ="/Users/macbook/Desktop/HoangNghia_Module2_CaseStudy/src/file/admin.txt";

    private static List<Admin> adminList = new ArrayList<>();

    static {
        adminList.addAll(ReadAndWriteFile.readAdminFromFile(ADMIN_PATH));
        ReadAndWriteFile.writeAdminToFile(ADMIN_PATH,adminList);

    }

    public static void showAdminInformation(){
        System.out.println("USERNAME: "+adminList.get(0).getUsername()+ "\n"+
                "PASSWORD: "+adminList.get(0).getPassword() +"\n" +
                "PHONE: "+adminList.get(0).getPhone()+"\n"+
                "EMAIL: "+adminList.get(0).getEmail()+"\n"+
                "BALANCE: "+adminList.get(0).getBalance());
    }

    public static void addCommission(double commission) {
        double currentBalance = adminList.get(0).getBalance();
        adminList.get(0).setBalance(currentBalance + commission);
        System.out.println("So du hien tai cua admin la: "+ adminList.get(0).getBalance());
        ReadAndWriteFile.writeAdminToFile(ADMIN_PATH,adminList);
    }
    public static void subCommission(double commission) {
        double currentBalance = adminList.get(0).getBalance();
        adminList.get(0).setBalance(currentBalance - commission);
        System.out.println("So du hien tai cua admin la: "+ adminList.get(0).getBalance());
        ReadAndWriteFile.writeAdminToFile(ADMIN_PATH,adminList);
    }
}
