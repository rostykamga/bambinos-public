package com.lesbambinos.model;

import org.hibernate.Query;

import com.lesbambinos.HibernateUtil;
import com.lesbambinos.entity.Employee;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.hibernate.Session;

public class EmployeeModel{

   
    public boolean checkUser(String username) {

        session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        Query query = session.createQuery("from Employee where userName = :username");
        query.setParameter("username", username);
        Employee employee = (Employee) query.uniqueResult();
        session.getTransaction().commit();

        return employee != null;
    }
    

    public Employee getEmployeeByUsername(String username){
        session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        Query query = session.createQuery("from Employee where userName = :username");
        query.setParameter("username", username);
        Employee employee = (Employee) query.uniqueResult();
        session.getTransaction().commit();

        return employee;
     }
    

    public boolean checkPassword(String username, String password) {

        session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        Query query = session.createQuery("from Employee where userName = :username");
        query.setParameter("username", username);
        Employee employee = (Employee) query.uniqueResult();
        session.getTransaction().commit();

        return employee.getPassword().equals(password);
    }

//    @Override
//    protected void performUpdate(AbstractEntity prev, AbstractEntity upd) {
//        Employee previous = (Employee)prev;
//        Employee updated = (Employee)upd;
//        
//        previous.setAddress(updated.getAddress());
//        previous.setFirstName(updated.getFirstName());
//        previous.setId(updated.getId());
//        previous.setLastName(updated.getLastName());
//        previous.setPassword(updated.getPassword());
//        previous.setPhone(updated.getPhone());
//        previous.setUserName(updated.getUserName());
//        previous.setLastconnection(updated.getLastconnection());
//        previous.setEmployeeRole(updated.getEmployeeRole());
//    }

//    @Override
//    public String getEntityClassName() {
//        return "Employee";
//    }
//
//    @Override
//    public String getSortField() {
//        return "firstName";
//    }
//
//    @Override
//    public Class getEntityClass() {
//        return Employee.class;
//    }
    
     private static Session session;

    public ObservableList<Employee> getEmployees() {

        ObservableList<Employee> list = FXCollections.observableArrayList();

        session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        List<Employee> employeelogs = session.createQuery("from Employee").list();
        session.beginTransaction().commit();
        employeelogs.stream().forEach(list::add);

        return list;
    }

    public Employee getEmployee(Long id) {

        session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        Employee employee = session.get(Employee.class, id);
        session.getTransaction().commit();

        return employee;
    }

    public void saveEmployee(Employee employee) {

        session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        session.save(employee);
        session.getTransaction().commit();
    }

    public void deleteEmployee(Employee employee) {

        session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        Employee i = session.get(Employee.class, employee.getId());
        session.delete(i);
        session.getTransaction().commit();
    }

}
