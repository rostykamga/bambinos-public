package com.lesbambinos.model;

import com.lesbambinos.HibernateUtil;
import com.lesbambinos.entity.AbstractEntity;
import com.lesbambinos.entity.Student;
import com.lesbambinos.entity.Attachment;
import static com.lesbambinos.model.AbstractEntityModel.session;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.hibernate.criterion.Restrictions;

public class AttachmentModel extends AbstractEntityModel<Attachment> {
    
    public AttachmentModel(){
        super();
    }
    
    public ObservableList<Attachment> getAttachmentByStudent(Student student){
        
        ObservableList<Attachment> list = FXCollections.observableArrayList();

        session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();

        List<Attachment> classes = (List<Attachment>) session.createCriteria(Attachment.class)
                .add(Restrictions.eq("ownerId", student.getId()))
                .add(Restrictions.eq("ownerType", "Student")).list();

        session.beginTransaction().commit();
        classes.stream().forEach(list::add);

        return list;
    }
    
    @Override
    protected void performUpdate(AbstractEntity prev, AbstractEntity upd) {
        Attachment previous = (Attachment)prev;
        Attachment updated = (Attachment)upd;
        
        previous.setDescription(updated.getDescription());
        previous.setContent(updated.getContent());
        previous.setOwnerId(updated.getOwnerId());
        previous.setOwnerType(updated.getOwnerType());
    }

    @Override
    public String getEntityClassName() {
        return "Attachment";
    }

    @Override
    public Class getEntityClass() {
        return Attachment.class;
    }

    @Override
    public String getSortField() {
        return "description";
    }




}
