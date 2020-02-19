package com.lesbambinos.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;

import com.lesbambinos.HibernateUtil;
import com.lesbambinos.entity.Subsystem;
import com.lesbambinos.entity.AbstractEntity;

public class SubsystemModel extends AbstractEntityModel<Subsystem> {

    private static Session session;

    public ObservableList<String> getTypes() {
        
        session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        Criteria criteria = session.createCriteria(Subsystem.class);
        criteria.setProjection(Projections.property("subsystemname"));
        ObservableList<String> list = FXCollections.observableArrayList(criteria.list());
        session.getTransaction().commit();
        
        return list;
    }
    

    @Override
    protected void performUpdate(AbstractEntity previous, AbstractEntity updated) {
        Subsystem existing = (Subsystem)previous;
        Subsystem upd = (Subsystem)updated;
        
        existing.setRegprefix(upd.getRegprefix());
        existing.setDescription(upd.getDescription());
        existing.setSubsystemname(upd.getSubsystemname());
    }

    @Override
    public String getEntityClassName() {
        return "Subsystem";
    }

    @Override
    public Class getEntityClass() {
        return Subsystem.class;
    }

    @Override
    public String getSortField() {
        return "subsystemname";
    }

}
