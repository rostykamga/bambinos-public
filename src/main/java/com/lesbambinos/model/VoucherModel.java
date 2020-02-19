/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lesbambinos.model;

import com.lesbambinos.HibernateUtil;
import com.lesbambinos.entity.AbstractEntity;
import com.lesbambinos.entity.Voucher;
import org.hibernate.Query;

/**
 *
 * @author rostand
 */
public class VoucherModel extends AbstractEntityModel<Voucher>{

    

    public Voucher getVoucherByCode(String voucherCode) {
        session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        Query query = session.createQuery("from Voucher where code=:code");
        query.setParameter("code", voucherCode);
        Voucher voucher = (Voucher) query.uniqueResult();
        
        return voucher;
    }

   

    @Override
    protected void performUpdate(AbstractEntity previous, AbstractEntity updated) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected Class getEntityClass() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected String getSortField() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected String getEntityClassName() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
