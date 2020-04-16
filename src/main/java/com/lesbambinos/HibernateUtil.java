package com.lesbambinos;


import com.lesbambinos.util.AppUtils;
import java.util.Properties;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {

    private static SessionFactory sessionFactory;
    
    /**
     * Tries to load all the entities classes to check database version
     */
    private static void testLoadEntities(){
        
//        new SubsystemModel().getCategory(1);
//        new EmployeeModel().getEmployee(1);
//        new InvoiceModel().getInvoice("");
//        new PictureModel().getPicture(1);
//        new ProductModel().getProduct(1);
//        new PurchaseCheckModel().getPurchaseCheck(1);
//        new PurchaseModel().getPurchase(1);
//        new PurchaseProductModel().getPurchaseProduct(1);
//        new SalesModel().getSale(1);
//        new SupplierModel().getSupplier(1);
//        new TicketPrintSettingModel().getUniqueTicketPrintSetting();
//        new VoucherModel().getVoucher(1);
        
        
    }

    public static boolean setSessionFactory() {
        try {
            Properties properties = new Properties();
            
            properties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
            properties.setProperty("hibernate.connection.driver_class", "com.mysql.jdbc.Driver");
            
            properties.setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3306/bambinos");
            
            properties.setProperty("hibernate.connection.username", "root");
            
           
            properties.setProperty("hibernate.connection.password", "rostand");
            
            sessionFactory = new Configuration()
                    .addProperties(properties)
                    .configure()
                    .buildSessionFactory();
            
            testLoadEntities();
        } catch (Exception ex) {
        	ex.printStackTrace();
            return false;
            
        }
        
        return true;
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
