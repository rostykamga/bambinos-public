package com.lesbambinos.model;

import com.lesbambinos.HibernateUtil;
import com.lesbambinos.entity.AbstractEntity;
import com.lesbambinos.entity.Level;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.hibernate.criterion.Restrictions;

public class LevelModel extends AbstractEntityModel<Level> {

    public LevelModel(){
        super();
    }
    
    public ObservableList<Level> getLevelsByCycleId(long cycleId){
        
        ObservableList<Level> list = FXCollections.observableArrayList();

        session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();

        List<Level> levels = (List<Level>) session.createCriteria(Level.class)
                .add(Restrictions.eq("cycle.id", cycleId)).list();

        session.beginTransaction().commit();
        levels.stream().forEach(list::add);

        return list;
    }
    
    @Override
    protected void performUpdate(AbstractEntity prev, AbstractEntity upd) {
        Level previous = (Level)prev;
        Level updated = (Level)upd;
        
        previous.setLevelname(updated.getLevelname());
        previous.setLevel(updated.getLevel());
        previous.setDescription(updated.getDescription());
        previous.setCycle(updated.getCycle());
    }

    @Override
    public String getEntityClassName() {
        return "Level";
    }

    @Override
    public Class getEntityClass() {
        return Level.class;
    }

    @Override
    public String getSortField() {
        return "level";
    }

}
