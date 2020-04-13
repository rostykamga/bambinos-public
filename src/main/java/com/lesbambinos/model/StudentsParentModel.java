package com.lesbambinos.model;

import com.lesbambinos.HibernateUtil;
import com.lesbambinos.entity.AbstractEntity;
import com.lesbambinos.entity.StudentsParent;
import com.lesbambinos.entity.Parent;
import com.lesbambinos.entity.Student;
import static com.lesbambinos.model.AbstractEntityModel.session;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.hibernate.criterion.Restrictions;

public class StudentsParentModel extends AbstractEntityModel<StudentsParent> {
    
    public ObservableList<StudentsParent> getParentsByStudent(Student student){
        
        ObservableList<StudentsParent> list = FXCollections.observableArrayList();

        session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();

        List<StudentsParent> studentsParents = (List<StudentsParent>) session.createCriteria(StudentsParent.class)
                .add(Restrictions.eq("student.id", student.getId())).list();

        session.beginTransaction().commit();
        studentsParents.stream().forEach(list::add);

        return list;
    }
    
    public ObservableList<StudentsParent> getStudentsOf(Parent parent){
        
        ObservableList<StudentsParent> list = FXCollections.observableArrayList();

        session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();

        List<StudentsParent> studentsParents = (List<StudentsParent>) session.createCriteria(StudentsParent.class)
                .add(Restrictions.eq("parent.id", parent.getId())).list();

        session.beginTransaction().commit();
        studentsParents.stream().forEach(list::add);

        return list;
    }
    
    @Override
    protected void performUpdate(AbstractEntity prev, AbstractEntity upd) {
        StudentsParent previous = (StudentsParent)prev;
        StudentsParent updated = (StudentsParent)upd;
        
        previous.setParent(updated.getParent());
        previous.setParentRole(updated.getParentRole());
        previous.setStudent(updated.getStudent());
    }

    @Override
    public String getEntityClassName() {
        return "StudentsParent";
    }

    @Override
    public Class getEntityClass() {
        return StudentsParent.class;
    }

    @Override
    public String getSortField() {
        return "studentsParentRole";
    }




}
