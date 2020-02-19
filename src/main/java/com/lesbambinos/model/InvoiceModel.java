package com.lesbambinos.model;


import com.lesbambinos.entity.AbstractEntity;
import com.lesbambinos.entity.Invoice;

public class InvoiceModel extends AbstractEntityModel<Invoice> {


    public InvoiceModel(){
        super();
    }
    
    @Override
    protected void performUpdate(AbstractEntity previous, AbstractEntity updated) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getEntityClassName() {
        return "Invoice";
    }

    @Override
    public String getSortField() {
        return "date DESC";
    }

    @Override
    public Class getEntityClass() {
        return Invoice.class;
    }

}
