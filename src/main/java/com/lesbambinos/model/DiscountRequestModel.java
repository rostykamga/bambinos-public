package com.lesbambinos.model;

import com.lesbambinos.HibernateUtil;
import com.lesbambinos.entity.AbstractEntity;
import com.lesbambinos.entity.Student;
import com.lesbambinos.entity.DiscountRequest;
import static com.lesbambinos.model.AbstractEntityModel.session;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.hibernate.criterion.Restrictions;

public class DiscountRequestModel extends AbstractEntityModel<DiscountRequest> {
    
    public DiscountRequestModel(){
        super();
    }
    
    public ObservableList<DiscountRequest> getDiscountRequestByStudent(Student student){
        
        ObservableList<DiscountRequest> list = FXCollections.observableArrayList();

        session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();

        List<DiscountRequest> classes = (List<DiscountRequest>) session.createCriteria(DiscountRequest.class)
                .add(Restrictions.eq("student.id", student.getId())).list();

        session.beginTransaction().commit();
        classes.stream().forEach(list::add);

        return list;
    }
    
    @Override
    protected void performUpdate(AbstractEntity prev, AbstractEntity upd) {
        DiscountRequest previous = (DiscountRequest)prev;
        DiscountRequest updated = (DiscountRequest)upd;
        
        previous.setDescription(updated.getDescription());
        previous.setClassroom(updated.getClassroom());
        previous.setFixedAmount(updated.getFixedAmount());
        previous.setRequestDate(updated.getRequestDate());
        previous.setStatus(updated.getStatus());
        previous.setStudent(updated.getStudent());
        previous.setValidationDate(updated.getValidationDate());
        previous.setValidator(updated.getValidator());
        previous.setFinalized(updated.isFinalized());
    }

    @Override
    public String getEntityClassName() {
        return "DiscountRequest";
    }

    @Override
    public Class getEntityClass() {
        return DiscountRequest.class;
    }

    @Override
    public String getSortField() {
        return "requestDate DESC";
    }




}
