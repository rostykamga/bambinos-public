/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lesbambinos.model;

import com.lesbambinos.entity.AbstractEntity;
import com.lesbambinos.entity.TicketPrintSetting;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.hibernate.Session;

/**
 *
 * @author rostand
 */
public class TicketPrintSettingModel extends AbstractEntityModel<TicketPrintSetting>{

    private static Session session;
    
    
    public TicketPrintSetting getUniqueTicketPrintSetting() {
         ObservableList<TicketPrintSetting> list = getAll();
         return list.isEmpty()? null : list.get(0);
    }


//    @Override
//    public void updateTicketPrintSetting(TicketPrintSetting ticketPrintSetting) {
//        session = HibernateUtil.getSessionFactory().getCurrentSession();
//        session.beginTransaction();
//        TicketPrintSetting setting = session.get(TicketPrintSetting.class, ticketPrintSetting.getId());
//        
//        setting.setFooter(ticketPrintSetting.getFooter());
//        setting.setFooterFont(ticketPrintSetting.getFooterFont());
//        setting.setHeader(ticketPrintSetting.getHeader());
//        setting.setHeaderFont(ticketPrintSetting.getHeaderFont());
//        setting.setLogo(ticketPrintSetting.getLogo());
//        setting.setPrinter(ticketPrintSetting.getPrinter());
//        setting.setPrintOutput(ticketPrintSetting.getPrintOutput());
//        setting.setTitle(ticketPrintSetting.getTitle());
//        setting.setTitleFont(ticketPrintSetting.getTitleFont());
//        setting.setCutOption(ticketPrintSetting.getCutOption());
//        setting.setCashDrawerOption(ticketPrintSetting.getCashDrawerOption());
//        setting.setLogoHeight(ticketPrintSetting.getLogoHeight());
//        setting.setLogoWidth(ticketPrintSetting.getLogoWidth());
//        setting.setPaperWidth(ticketPrintSetting.getPaperWidth());
//        
//        session.getTransaction().commit();
//    }


    @Override
    protected void performUpdate(AbstractEntity previous, AbstractEntity updated) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getEntityClassName() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getSortField() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Class getEntityClass() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
