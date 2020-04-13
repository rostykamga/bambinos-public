package com.lesbambinos.model;

import com.lesbambinos.HibernateUtil;
import com.lesbambinos.entity.UniformOrder;
import com.lesbambinos.entity.AbstractEntity;
import com.lesbambinos.entity.UniformOrder;
import com.lesbambinos.entity.Student;
import static com.lesbambinos.model.AbstractEntityModel.session;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.hibernate.criterion.Restrictions;

public class UniformOrderModel extends AbstractEntityModel<UniformOrder> {

    public ObservableList<UniformOrder> getUniformOrdersByStudent(Student student){
        
        ObservableList<UniformOrder> list = FXCollections.observableArrayList();

        session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();

        List<UniformOrder> classes = (List<UniformOrder>) session.createCriteria(UniformOrder.class)
                .add(Restrictions.eq("student.id", student.getId())).list();

        session.beginTransaction().commit();
        classes.stream().forEach(list::add);

        return list;
    }
    
    @Override
    protected void performUpdate(AbstractEntity previous, AbstractEntity updated) {
        UniformOrder existing = (UniformOrder)previous;
        UniformOrder upd = (UniformOrder)updated;
        
        existing.setId(upd.getId());
        existing.setDelivered(upd.isDelivered());
        existing.setDeliveryDate(upd.getDeliveryDate());
        existing.setOrderDate(upd.getOrderDate());
        existing.setOrderPrice(upd.getOrderPrice());
        existing.setPaid(upd.isPaid());
        existing.setQuantity(upd.getQuantity());
        existing.setStudent(upd.getStudent());
        existing.setUniform(upd.getUniform());
    }

    @Override
    public String getEntityClassName() {
        return "UniformOrder";
    }

    @Override
    public Class getEntityClass() {
        return UniformOrder.class;
    }

    @Override
    public String getSortField() {
        return "orderDate DESC";
    }

}
