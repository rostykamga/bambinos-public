package com.lesbambinos.model;

import com.lesbambinos.HibernateUtil;
import com.lesbambinos.entity.AbstractEntity;
import com.lesbambinos.entity.Installment;
import com.lesbambinos.entity.Payment;
import com.lesbambinos.entity.Student;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.hibernate.criterion.Restrictions;

public class PaymentModel extends AbstractEntityModel<Payment> {

    public PaymentModel(){
        super();
    }
    
    public ObservableList<Payment> getPaymentByStudent(Student student){
        ObservableList<Payment> list = FXCollections.observableArrayList();
//
        session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();

        List<Payment> payments = (List<Payment>) session.createCriteria(Payment.class)
                .add(Restrictions.eq("pk.student.id", student.getId())).list();

        session.beginTransaction().commit();
        payments.stream().forEach(list::add);

        return list;
    }
    
    
    public Payment getPaymentByInstallementAndStudent(Installment inst, Student student){
        session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();

        Payment payment = (Payment) session.createCriteria(Payment.class)
                .add(Restrictions.eq("pk.student.id", student.getId()))
                .add(Restrictions.eq("pk.installment.id", inst.getId())).uniqueResult();

        session.beginTransaction().commit();
        

        return payment;
    }
    
//    public void saveOrUpdate(Payment payment){
//        session = HibernateUtil.getSessionFactory().getCurrentSession();
//        session.beginTransaction();
//        session.saveOrUpdate(payment);
//        session.getTransaction().commit();
//    }
    
    @Override
    protected void performUpdate(AbstractEntity prev, AbstractEntity upd) {
        Payment previous = (Payment)prev;
        Payment updated = (Payment)upd;
        
        previous.setPaidAmount(updated.getPaidAmount());
        previous.setPaymentDate(updated.getPaymentDate());
        previous.setPk(updated.getPk());
        previous.setEmployee(updated.getEmployee());
        previous.setInvoice(updated.getInvoice());
    }

    @Override
    public String getEntityClassName() {
        return "Payment";
    }

    @Override
    public Class getEntityClass() {
        return Payment.class;
    }

    @Override
    public String getSortField() {
        return "studentId";
    }

}
