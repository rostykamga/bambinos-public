package com.lesbambinos.model;

import com.lesbambinos.BambinosSecurityManager;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.hibernate.Session;

import com.lesbambinos.HibernateUtil;
import com.lesbambinos.entity.AbstractEntity;
import com.lesbambinos.entity.Employee;
import com.lesbambinos.entity.EmployeeLog;
import java.util.Date;

public abstract class AbstractEntityModel<T extends AbstractEntity> {

    protected static Session session;
    protected final EmployeeLogModel employeeLogModel;
    protected final Employee currentEmployee;
    
    protected AbstractEntityModel(){
        employeeLogModel = new EmployeeLogModel();
        currentEmployee = BambinosSecurityManager.getAuthenticatedEmployee().getEmployeeEntity();
    }
    

    public ObservableList<T> getAll() {
        
        ObservableList<T> list = FXCollections.observableArrayList();

        session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        
        String query = "from "+getEntityClassName();
        String sortField;
        if((sortField= getSortField())!=null){
            query+= "  ORDER BY "+sortField;
        }
        
        List<T> entities = session.createQuery(query).list();
        session.beginTransaction().commit();
        entities.stream().forEach(list::add);

        return list;
    }
    
    public AbstractEntity getById(long id) {

        session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        AbstractEntity entity = (AbstractEntity)session.get(getEntityClass(), id);
        session.getTransaction().commit();

        return entity;
    }
    
    protected abstract void performUpdate(AbstractEntity previous, AbstractEntity updated);
    

    public void update(AbstractEntity updatedEntity) {
        //copyProperties(Object, Object)
        session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        AbstractEntity previousEntity = (AbstractEntity)session.get(getEntityClass(), updatedEntity.getId());
        performUpdate(previousEntity, updatedEntity);
        session.getTransaction().commit();
        employeeLogModel.saveEmployeeLog(new EmployeeLog(EmployeeLogModel.ACTION_UPDATE, updatedEntity.asString(), new Date(), currentEmployee));
    }


    public void save(AbstractEntity entity) {
        session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        session.save(entity);
        session.getTransaction().commit();
        employeeLogModel.saveEmployeeLog(new EmployeeLog(EmployeeLogModel.ACTION_CREATE, entity.asString(), new Date(), currentEmployee));
    }


    public void delete(AbstractEntity entity) {
        session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        AbstractEntity c = (AbstractEntity)session.get(getEntityClass(), entity.getId());
        session.delete(c);
        session.getTransaction().commit();
        employeeLogModel.saveEmployeeLog(new EmployeeLog(EmployeeLogModel.ACTION_DELETE, entity.asString(), new Date(), currentEmployee));
    }
    
    protected abstract Class getEntityClass();
    protected abstract String getSortField();
    protected abstract String getEntityClassName();

}
