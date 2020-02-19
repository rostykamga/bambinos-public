package com.lesbambinos.model;

import com.lesbambinos.HibernateUtil;
import com.lesbambinos.entity.AbstractEntity;
import com.lesbambinos.entity.Classe;
import static com.lesbambinos.model.AbstractEntityModel.session;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.hibernate.criterion.Restrictions;

public class ClasseModel extends AbstractEntityModel<Classe> {
    
    public ClasseModel(){
        super();
    }
    
    public ObservableList<Classe> getClasseByLevelId(long levelId){
        
        ObservableList<Classe> list = FXCollections.observableArrayList();

        session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();

        List<Classe> classes = (List<Classe>) session.createCriteria(Classe.class)
                .add(Restrictions.eq("level.id", levelId)).list();

        session.beginTransaction().commit();
        classes.stream().forEach(list::add);

        return list;
    }
    
    @Override
    protected void performUpdate(AbstractEntity prev, AbstractEntity upd) {
        Classe previous = (Classe)prev;
        Classe updated = (Classe)upd;
        
        previous.setDescription(updated.getDescription());
        previous.setFullname(updated.getFullname());
        previous.setLevel(updated.getLevel());
        previous.setShortname(updated.getShortname());
    }

    @Override
    public String getEntityClassName() {
        return "Classe";
    }

    @Override
    public Class getEntityClass() {
        return Classe.class;
    }

    @Override
    public String getSortField() {
        return "shortname";
    }




}
