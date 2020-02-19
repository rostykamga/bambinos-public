package com.lesbambinos.model;

import com.lesbambinos.HibernateUtil;
import com.lesbambinos.entity.AbstractEntity;
import com.lesbambinos.entity.Cycle;
import com.lesbambinos.entity.EmployeeLog;
import com.lesbambinos.entity.Installment;
import com.lesbambinos.entity.InstallmentPerLevel;
import com.lesbambinos.entity.InstallmentPerLevelCollection;
import com.lesbambinos.entity.InstallmentPerLevelPK;
import com.lesbambinos.entity.Level;
import com.lesbambinos.entity.Tuition;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.hibernate.criterion.Restrictions;

public class InstallmentPerLevelModel extends AbstractEntityModel<InstallmentPerLevel> {

    public InstallmentPerLevelModel(){
        super();
    }
    
    public ObservableList<InstallmentPerLevelCollection> getInstallmentPerCycle(Tuition tuition, Cycle cycle){
        
        LevelModel levelModel = new LevelModel();
        ObservableList<Level> levels = levelModel.getLevelsByCycleId(cycle.getId());
        InstallmentModel installmentModel = new InstallmentModel();
        ObservableList<Installment> installments = installmentModel.getInstallmentsByTuitionId(tuition.getId());
        
        ObservableList<InstallmentPerLevelCollection> result = FXCollections.observableArrayList();
        
        session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        
        for(Level level : levels){
            List<InstallmentPerLevel> instPerLevel = new ArrayList<>();
            for(Installment inst : installments){
                InstallmentPerLevel installment = (InstallmentPerLevel) session.createCriteria(InstallmentPerLevel.class)
                        .add(Restrictions.eq("instPk.installment.id", inst.getId()))
                        .add(Restrictions.eq("instPk.level.id", level.getId())).uniqueResult();
                if(installment ==null){
                    installment = new InstallmentPerLevel(new InstallmentPerLevelPK(level, inst), 0);
                }
                instPerLevel.add(installment);
            }
            result.add(new InstallmentPerLevelCollection(instPerLevel));
        }
        session.beginTransaction().commit();
        return result;
    }
    
    public ObservableList<InstallmentPerLevel> getInstallmentsByLevel(Level level){
        ObservableList<InstallmentPerLevel> list = FXCollections.observableArrayList();
//
        session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();

        List<InstallmentPerLevel> levels = (List<InstallmentPerLevel>) session.createCriteria(InstallmentPerLevel.class)
                .add(Restrictions.eq("instPk.level.id", level.getId())).list();

        session.beginTransaction().commit();
        levels.stream().forEach(list::add);

        return list;
    }
    
    
    public ObservableList<Installment> getInstallmentsForLevel(Level level){
        ObservableList<Installment> result = FXCollections.observableArrayList();
        ObservableList<InstallmentPerLevel> list = getInstallmentsByLevel(level);
        list.forEach(instpl->result.add(instpl.getInstPk().getInstallment()));

        return result;
    }
    
    public void saveOrUpdate(InstallmentPerLevel ipl){
        session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        session.saveOrUpdate(ipl);
        session.getTransaction().commit();
        employeeLogModel.saveEmployeeLog(new EmployeeLog(EmployeeLogModel.ACTION_UPSERT, ipl.asString(), new Date(), currentEmployee));
    }
    
    @Override
    protected void performUpdate(AbstractEntity prev, AbstractEntity upd) {
        InstallmentPerLevel previous = (InstallmentPerLevel)prev;
        InstallmentPerLevel updated = (InstallmentPerLevel)upd;
        
        previous.setAmount(updated.getAmount());
        previous.setInstPk(updated.getInstPk());
    }

    @Override
    public String getEntityClassName() {
        return "InstallmentPerLevel";
    }

    @Override
    public Class getEntityClass() {
        return InstallmentPerLevel.class;
    }

    @Override
    public String getSortField() {
        return "id";
    }

}
