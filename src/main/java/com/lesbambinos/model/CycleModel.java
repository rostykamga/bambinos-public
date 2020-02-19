package com.lesbambinos.model;

import com.lesbambinos.HibernateUtil;
import com.lesbambinos.entity.AbstractEntity;
import com.lesbambinos.entity.Cycle;
import static com.lesbambinos.model.AbstractEntityModel.session;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.hibernate.criterion.Restrictions;

public class CycleModel extends AbstractEntityModel<Cycle> {
    
    public CycleModel(){
        super();
    }
    
     public ObservableList<Cycle> getCyclesBySectionId(long sectionId){
        
        ObservableList<Cycle> list = FXCollections.observableArrayList();

        session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();

        List<Cycle> cycles = (List<Cycle>) session.createCriteria(Cycle.class)
                .add(Restrictions.eq("subsystem.id", sectionId)).list();

        session.beginTransaction().commit();
        cycles.stream().forEach(list::add);

        return list;
    }
    
    @Override
    protected void performUpdate(AbstractEntity prev, AbstractEntity upd) {
        Cycle previous = (Cycle)prev;
        Cycle updated = (Cycle)upd;
        
        previous.setCyclename(updated.getCyclename());
        previous.setDescription(updated.getDescription());
        previous.setSubsystem(updated.getSubsystem());
    }

    @Override
    public String getEntityClassName() {
        return "Cycle";
    }

    @Override
    public Class getEntityClass() {
        return Cycle.class;
    }

    @Override
    public String getSortField() {
        return "cyclename";
    }

}
