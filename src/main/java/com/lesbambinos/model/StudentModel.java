package com.lesbambinos.model;

import com.lesbambinos.HibernateUtil;
import com.lesbambinos.controller.AutocompleteProvider;
import com.lesbambinos.entity.AbstractEntity;
import com.lesbambinos.entity.Classe;
import com.lesbambinos.entity.EmployeeLog;
import com.lesbambinos.entity.Student;
import com.lesbambinos.entity.Subsystem;
import static com.lesbambinos.model.AbstractEntityModel.session;
import com.lesbambinos.util.AppUtils;
import java.util.Date;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

public class StudentModel extends AbstractEntityModel<Student> implements AutocompleteProvider<Student>{
    
    
    public ObservableList<Student> getStudentByClass(Classe classroom){
        ObservableList<Student> list = FXCollections.observableArrayList();
//
        session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();

        List<Student> students = (List<Student>) session.createCriteria(Student.class)
                .add(Restrictions.eq("classroom.id", classroom.getId())).list();

        session.beginTransaction().commit();
        students.stream().forEach(list::add);

        return list;
    }
    
        public ObservableList<Student> find(String q){
        ObservableList<Student> list = FXCollections.observableArrayList();
//
        session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        
        String hql = "FROM Student "
                + "WHERE registrationNumber like :query "
                + "OR names like :query "
                + "OR surnames like :query "
                + "OR dateOfBirth like :query "
                + "OR placeOfBirth like :query "
                + "OR health1 like :query "
                + "OR health2 like :query "
                + "OR shortname like :query "
                + "OR classroom.fullname like :query "
                + "OR classroom.description like :query ";
       
        Query query = session.createQuery(hql);
        query.setParameter("query", "%"+q+"%");
        

        List<Student> students = (List<Student>)query.list();

        session.beginTransaction().commit();
        students.stream().forEach(list::add);

        return list;
    }
    
    public int getStudentCount(){
        session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        Criteria criteria = session.createCriteria(Student.class);
        criteria.setProjection(Projections.rowCount());

        List l = criteria.list();
        if (l!=null) {
            session.getTransaction().commit();
            Integer rowCount = (Integer) l.get(0);
            return rowCount;
        }

        session.getTransaction().commit();
        return 0;
    }
    

    public void saveStudent(Student std, Subsystem section) {
        
       // RegistrationModel regModel = new RegistrationModel();
        session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
       
        if(AppUtils.isEmptyOrNullString(std.getRegistrationNumber())){
            Criteria criteria = session.createCriteria(Student.class);
            criteria.setProjection(Projections.max("id"));
            Long id = (Long)criteria.uniqueResult();
            std.setRegistrationNumber(AppUtils.generateRegistrationNumber(section, id + 1));
        }
        session.save(std);
        session.getTransaction().commit();
        employeeLogModel.saveEmployeeLog(new EmployeeLog(EmployeeLogModel.ACTION_CREATE, std.asString(), new Date(), currentEmployee));
    }
    
    @Override
    protected void performUpdate(AbstractEntity prev, AbstractEntity upd) {
        Student previous = (Student)prev;
        Student updated = (Student)upd;
        
        previous.setDateOfBirth(updated.getDateOfBirth());
        previous.setHealth1(updated.getHealth1());
        previous.setHealth2(updated.getHealth2());
        previous.setNames(updated.getNames());
        previous.setPicture(updated.getPicture());
        previous.setPlaceOfBirth(updated.getPlaceOfBirth());
        previous.setRegistrationNumber(updated.getRegistrationNumber());
        previous.setSex(updated.getSex());
        previous.setSurnames(updated.getSurnames());
    }

    @Override
    public String getEntityClassName() {
        return "Student";
    }

    @Override
    public Class getEntityClass() {
        return Student.class;
    }

    @Override
    public String getSortField() {
        return "names";
    }

    @Override
    public List<Student> search(String query) {
        session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        Query qry = session.createQuery("From Student as st where st.names like :firstname or st.surnames like :lastname");
        qry.setString("firstname", "%"+query+"%");
        qry.setString("lastname", "%"+query+"%");
        qry.setMaxResults(getMaxResultSize());
        List<Student> list = qry.list();
        session.getTransaction().commit();
        
        return  list;
    }

    @Override
    public int getMinSearcheableLength() {
        return 3;
    }
    
    @Override
    public int getMaxResultSize(){
        return 12;
    }

}
