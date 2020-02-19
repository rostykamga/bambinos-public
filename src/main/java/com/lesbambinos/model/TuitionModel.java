package com.lesbambinos.model;

import com.lesbambinos.entity.AbstractEntity;
import com.lesbambinos.entity.Tuition;

public class TuitionModel extends AbstractEntityModel<Tuition> {
    
    @Override
    protected void performUpdate(AbstractEntity prev, AbstractEntity upd) {
        Tuition previous = (Tuition)prev;
        Tuition updated = (Tuition)upd;
        
        previous.setTitle(updated.getTitle());
        previous.setDescription(updated.getDescription());
        previous.setNbInstallment(updated.getNbInstallment());
    }

    @Override
    public String getEntityClassName() {
        return "Tuition";
    }

    @Override
    public Class getEntityClass() {
        return Tuition.class;
    }

    @Override
    public String getSortField() {
        return "title";
    }




}
