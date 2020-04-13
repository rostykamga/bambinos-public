package com.lesbambinos.model;

import com.lesbambinos.HibernateUtil;
import com.lesbambinos.entity.AbstractEntity;
import com.lesbambinos.entity.Uniform;
import com.lesbambinos.entity.UniformSize;
import com.lesbambinos.entity.UniformType;
import static com.lesbambinos.model.AbstractEntityModel.session;
import org.hibernate.criterion.Restrictions;

public class UniformModel extends AbstractEntityModel<Uniform> {

    public Uniform getUniformWith(UniformSize size, UniformType type, boolean gender){
        
        session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();

        Uniform uniform = (Uniform) session.createCriteria(Uniform.class)
                .add(Restrictions.eq("size.id", size.getId()))
                .add(Restrictions.eq("type.id", type.getId()))
                .add(Restrictions.eq("gender", gender)).uniqueResult();

        session.beginTransaction().commit();

        return uniform;
    }
    
    @Override
    protected void performUpdate(AbstractEntity previous, AbstractEntity updated) {
        Uniform existing = (Uniform)previous;
        Uniform upd = (Uniform)updated;
        
        existing.setId(upd.getId());
        existing.setGender(upd.getGender());
        existing.setPurchasePrice(upd.getPurchasePrice());
        existing.setQuantityInStock(upd.getQuantityInStock());
        existing.setSize(upd.getSize());
        existing.setType(upd.getType());
        existing.setUnitPrice(upd.getUnitPrice());
    }

    @Override
    public String getEntityClassName() {
        return "Uniform";
    }

    @Override
    public Class getEntityClass() {
        return Uniform.class;
    }

    @Override
    public String getSortField() {
        return "id";
    }

}
