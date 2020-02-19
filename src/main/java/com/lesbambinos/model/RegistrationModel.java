package com.lesbambinos.model;

import com.lesbambinos.HibernateUtil;
import com.lesbambinos.entity.AbstractEntity;
import com.lesbambinos.entity.Classe;
import com.lesbambinos.entity.Registration;
import com.lesbambinos.entity.Student;
import static com.lesbambinos.model.AbstractEntityModel.session;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

public class RegistrationModel extends AbstractEntityModel<Registration> {
    
    public RegistrationModel(){
        super();
    }
    
    public ObservableList<Registration> getRegistrationByClass(Classe classe){
        ObservableList<Registration> list = FXCollections.observableArrayList();
//
        session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();

        List<Registration> levels = (List<Registration>) session.createCriteria(Registration.class)
                .add(Restrictions.eq("classe.id", classe.getId())).list();

        session.beginTransaction().commit();
        levels.stream().forEach(list::add);

        return list;
    }
    
    public ObservableList<Registration> find(String q){
        ObservableList<Registration> list = FXCollections.observableArrayList();
//
        session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        
        String hql = "FROM Registration "
                + "WHERE pk.student.registrationNumber like :query "
                + "OR pk.student.names like :query "
                + "OR pk.student.surnames like :query "
                + "OR pk.student.dateOfBirth like :query "
                + "OR pk.student.placeOfBirth like :query "
                + "OR pk.student.health1 like :query "
                + "OR pk.student.health2 like :query "
                + "OR pk.classe.shortname like :query "
                + "OR pk.classe.fullname like :query "
                + "OR pk.classe.description like :query ";
       
        Query query = session.createQuery(hql);
        query.setParameter("query", "%"+q+"%");
        

        List<Registration> registrations = (List<Registration>)query.list();

        session.beginTransaction().commit();
        registrations.stream().forEach(list::add);

        return list;
    }
    
    public ObservableList<Registration> getRegistrationByStudent(Student std){
        ObservableList<Registration> list = FXCollections.observableArrayList();
//
        session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();

        List<Registration> levels = (List<Registration>) session.createCriteria(Registration.class)
                .add(Restrictions.eq("student.id", std.getId())).list();

        session.beginTransaction().commit();
        levels.stream().forEach(list::add);

        return list;
    }
    
    public Long getRegistrationCount(){
        session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        Criteria criteria = session.createCriteria(Registration.class);
        criteria.setProjection(Projections.rowCount());

        List l = criteria.list();
        if (l!=null) {
            Long rowCount = (Long) l.get(0);
            return rowCount;
        }

        session.getTransaction().commit();
        return 0L;
    }
    
    
    
    @Override
    protected void performUpdate(AbstractEntity prev, AbstractEntity upd) {
        Registration previous = (Registration)prev;
        Registration updated = (Registration)upd;
        
        previous.setClasse(updated.getClasse());
        previous.setRegistrationDate(updated.getRegistrationDate());
        previous.setStudent(updated.getStudent());
    }

    @Override
    public String getEntityClassName() {
        return "Registration";
    }

    @Override
    public Class getEntityClass() {
        return Registration.class;
    }

    @Override
    public String getSortField() {
        return "registrationDate";
    }


}
