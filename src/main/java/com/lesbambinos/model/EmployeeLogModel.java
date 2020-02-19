package com.lesbambinos.model;

import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.hibernate.Session;

import com.lesbambinos.HibernateUtil;
import com.lesbambinos.entity.EmployeeLog;

public class EmployeeLogModel{
    
    public static final String ACTION_CREATE = "Create";
    public static final String ACTION_DELETE = "Delete";
    public static final String ACTION_UPDATE = "Update";
    public static final String ACTION_UPSERT = "Update";

    private static Session session;

    public ObservableList<EmployeeLog> getEmployeeLogs() {

        ObservableList<EmployeeLog> list = FXCollections.observableArrayList();

        session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        List<EmployeeLog> employeelogs = session.createQuery("from EmployeeLog").list();
        session.beginTransaction().commit();
        employeelogs.stream().forEach(list::add);

        return list;
    }

    public EmployeeLog getEmployeeLog(Long id) {

        session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        EmployeeLog employeeLog = session.get(EmployeeLog.class, id);
        session.getTransaction().commit();

        return employeeLog;
    }

    public void saveEmployeeLog(EmployeeLog employeeLog) {

        session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        session.save(employeeLog);
        session.getTransaction().commit();
    }

    public void deleteEmployeeLog(EmployeeLog employeeLog) {

        session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        EmployeeLog i = session.get(EmployeeLog.class, employeeLog.getId());
        session.delete(i);
        session.getTransaction().commit();
    }

}
