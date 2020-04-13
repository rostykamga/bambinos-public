package com.lesbambinos.model;

import com.lesbambinos.entity.UniformSize;
import com.lesbambinos.entity.AbstractEntity;

public class UniformSizeModel extends AbstractEntityModel<UniformSize> {

    @Override
    protected void performUpdate(AbstractEntity previous, AbstractEntity updated) {
        UniformSize existing = (UniformSize)previous;
        UniformSize upd = (UniformSize)updated;
        
        existing.setId(upd.getId());
        existing.setUniformSize(upd.getUniformSize());
    }

    @Override
    public String getEntityClassName() {
        return "UniformSize";
    }

    @Override
    public Class getEntityClass() {
        return UniformSize.class;
    }

    @Override
    public String getSortField() {
        return "uniformSize";
    }

}
