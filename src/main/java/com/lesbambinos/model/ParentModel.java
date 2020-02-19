package com.lesbambinos.model;

import com.lesbambinos.HibernateUtil;
import com.lesbambinos.controller.AutocompleteProvider;
import com.lesbambinos.entity.AbstractEntity;
import com.lesbambinos.entity.Parent;
import static com.lesbambinos.model.AbstractEntityModel.session;
import java.util.List;
import org.hibernate.Query;

public class ParentModel extends AbstractEntityModel<Parent> implements  AutocompleteProvider<Parent>{
    
    public ParentModel(){
        super();
    }
    
    @Override
    protected void performUpdate(AbstractEntity prev, AbstractEntity upd) {
        Parent previous = (Parent)prev;
        Parent updated = (Parent)upd;
        
        previous.setFullname(updated.getFullname());
        previous.setPhone1(updated.getPhone1());
        previous.setPhone2(updated.getPhone2());
        previous.setProfession(updated.getProfession());
        previous.setReligion(updated.getReligion());
        previous.setResidence(updated.getResidence());
    }

    @Override
    public String getEntityClassName() {
        return "Parent";
    }

    @Override
    public Class getEntityClass() {
        return Parent.class;
    }

    @Override
    public String getSortField() {
        return "fullname";
    }

    @Override
    public List<Parent> search(String query) {
        session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        Query qry = session.createQuery("From Parent as p where p.fullname like :fullname");
        qry.setString("fullname", "%"+query+"%");
        qry.setMaxResults(getMaxResultSize());
        List<Parent> list = qry.list();
        session.getTransaction().commit();
        
        return  list;
    }

    @Override
    public int getMinSearcheableLength() {
        return 3;
    }

    @Override
    public int getMaxResultSize() {
        return 10;
    }

}
