package com.lesbambinos.model;

import com.lesbambinos.entity.AbstractEntity;
import com.lesbambinos.entity.UniformPurchase;

public class UniformPurchaseModel extends AbstractEntityModel<UniformPurchase> {

  
    
    @Override
    protected void performUpdate(AbstractEntity previous, AbstractEntity updated) {
        UniformPurchase existing = (UniformPurchase)previous;
        UniformPurchase upd = (UniformPurchase)updated;
        
        existing.setId(upd.getId());
        existing.setDeliveryDate(upd.getDeliveryDate());
        existing.setPurchaseDate(upd.getPurchaseDate());
        existing.setPurchaser(upd.getPurchaser());
        existing.setQuantity(upd.getQuantity());
        existing.setReceptor(upd.getReceptor());
        existing.setStatus(upd.getStatus());
        existing.setTotal(upd.getTotal());
        existing.setUniform(upd.getUniform());
        existing.setUnitPrice(upd.getUnitPrice());
    }

    @Override
    public String getEntityClassName() {
        return "UniformPurchase";
    }

    @Override
    public Class getEntityClass() {
        return UniformPurchase.class;
    }

    @Override
    public String getSortField() {
        return "purchaseDate DESC";
    }

}
