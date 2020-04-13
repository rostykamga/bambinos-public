package com.lesbambinos;

import com.lesbambinos.config.AppConfig;

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
            AppConfig config = AppConfig.getCurrentConfig();
            if(config == null)
                return false;
            
            Properties properties = new Properties();
            
            properties.setProperty("hibernate.dialect", config.getDialect());
            properties.setProperty("hibernate.connection.driver_class", config.getDriver());
            
            properties.setProperty("hibernate.connection.url", config.getUrl());
            
            properties.setProperty("hibernate.connection.username", config.getUser());
            
            if(!AppUtils.isEmptyOrNullString(config.getPassword()))
                properties.setProperty("hibernate.connection.password", config.getPassword());
            
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
