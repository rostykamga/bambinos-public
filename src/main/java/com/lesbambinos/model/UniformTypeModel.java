package com.lesbambinos.model;

import com.lesbambinos.entity.UniformType;
import com.lesbambinos.entity.AbstractEntity;

public class UniformTypeModel extends AbstractEntityModel<UniformType> {

    @Override
    protected void performUpdate(AbstractEntity previous, AbstractEntity updated) {
        UniformType existing = (UniformType)previous;
        UniformType upd = (UniformType)updated;
        
        existing.setId(upd.getId());
        existing.setType(upd.getType());
    }

    @Override
    public String getEntityClassName() {
        return "UniformType";
    }

    @Override
    public Class getEntityClass() {
        return UniformType.class;
    }

    @Override
    public String getSortField() {
        return "type";
    }

}
