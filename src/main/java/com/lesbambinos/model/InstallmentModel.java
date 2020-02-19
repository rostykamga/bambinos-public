package com.lesbambinos.model;

import com.lesbambinos.HibernateUtil;
import com.lesbambinos.entity.AbstractEntity;
import com.lesbambinos.entity.Installment;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.hibernate.criterion.Restrictions;

public class InstallmentModel extends AbstractEntityModel<Installment> {
    
    public InstallmentModel(){
        super();
    }
    
    public ObservableList<Installment> getInstallmentsByTuitionId(long tuitionID){
        ObservableList<Installment> list = FXCollections.observableArrayList();
//
        session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();

        List<Installment> levels = (List<Installment>) session.createCriteria(Installment.class)
                .add(Restrictions.eq("tuition.id", tuitionID)).list();

        session.beginTransaction().commit();
        levels.stream().forEach(list::add);

        return list;
    }
    
    @Override
    protected void performUpdate(AbstractEntity prev, AbstractEntity upd) {
        Installment previous = (Installment)prev;
        Installment updated = (Installment)upd;
        
        previous.setAlertDate(updated.getAlertDate());
        previous.setInstallmentNumber(updated.getInstallmentNumber());
        previous.setDeadline(updated.getDeadline());
        previous.setTuition(updated.getTuition());
    }

    @Override
    public String getEntityClassName() {
        return "Installment";
    }

    @Override
    public Class getEntityClass() {
        return Installment.class;
    }

    @Override
    public String getSortField() {
        return "installmentNumber";
    }

}
