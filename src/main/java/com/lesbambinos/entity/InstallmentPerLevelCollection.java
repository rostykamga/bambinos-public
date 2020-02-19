/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lesbambinos.entity;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Rostand
 */
public class InstallmentPerLevelCollection implements AbstractEntity{
    
    private Level level;
    private double total=0.0;
    private List<InstallmentPerLevel> installmentsPerLevels = new ArrayList<>();
    private Long id = null;

    /**
     * 
     * @param lvl
     * @param installements 
     */
    public InstallmentPerLevelCollection(Level lvl, List<Installment> installements) {
        level = lvl;
        installements.forEach((Installment inst) -> {
            installmentsPerLevels.add(new InstallmentPerLevel(new InstallmentPerLevelPK(level, inst), 0.0));
        });
        calculation();
    }
    public InstallmentPerLevelCollection(List<InstallmentPerLevel> installmentsPerLevels) {
        this.installmentsPerLevels.clear();
        this.installmentsPerLevels.addAll(installmentsPerLevels);
        if(!installmentsPerLevels.isEmpty()){
            level = installmentsPerLevels.get(0).getInstPk().getLevel();
        }
        calculation();
    }

    public List<InstallmentPerLevel> getInstallmentsPerLevels() {
        return installmentsPerLevels;
    }
    
    public InstallmentPerLevelCollection copy(){
        List<InstallmentPerLevel> nList= new ArrayList<>();
        installmentsPerLevels.forEach((ipl) -> {
            nList.add(new InstallmentPerLevel(ipl.getInstPk(), ipl.getAmount()));
        });
        
        return new InstallmentPerLevelCollection(nList);
    }
    
    private void calculation(){
        double t = 0;
        t = installmentsPerLevels.stream()
                .map((ipl) -> ipl.getAmount())
                .reduce(t, (accumulator, _item) -> accumulator + _item);
        setTotal(t);
    }
    
    public boolean isValid(){
        return installmentsPerLevels.stream().noneMatch((ipl) -> (ipl.getAmount() <=0));
    }
    
    public int size(){
        return installmentsPerLevels.size();
    }

    public String getLevel() {
        return level.getLevelname();
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public double getAmount1() {
        return installmentsPerLevels.size()>0? installmentsPerLevels.get(0).getAmount() : 0.0;
    }

    public void setAmount1(double amount1) {
        if(installmentsPerLevels.size()>0){
            installmentsPerLevels.get(0).setAmount(amount1);
        }
        calculation();
    }

    public double getAmount2() {
       return installmentsPerLevels.size()>1? installmentsPerLevels.get(1).getAmount() : 0.0;
    }

    public void setAmount2(double amount2) {
        if(installmentsPerLevels.size()>1){
            installmentsPerLevels.get(1).setAmount(amount2);
        }
        calculation();
    }

    public double getAmount3() {
        return installmentsPerLevels.size()>2? installmentsPerLevels.get(2).getAmount() : 0.0;
    }

    public void setAmount3(double amount3) {
        if(installmentsPerLevels.size()>2){
            installmentsPerLevels.get(2).setAmount(amount3);
        }
        calculation();
    }

    public double getAmount4() {
        return installmentsPerLevels.size()>3? installmentsPerLevels.get(3).getAmount() : 0.0;
    }

    public void setAmount4(double amount4) {
        if(installmentsPerLevels.size()>3){
            installmentsPerLevels.get(3).setAmount(amount4);
        }
        calculation();
    }

    public double getAmount5() {
       return installmentsPerLevels.size()>4? installmentsPerLevels.get(4).getAmount() : 0.0;
    }

    public void setAmount5(double amount5) {
        if(installmentsPerLevels.size()>4){
            installmentsPerLevels.get(4).setAmount(amount5);
        }
        calculation();
    }

    public double getAmount6() {
        return installmentsPerLevels.size()>5? installmentsPerLevels.get(5).getAmount() : 0.0;
    }

    public void setAmount6(double amount6) {
        if(installmentsPerLevels.size()>5){
            installmentsPerLevels.get(5).setAmount(amount6);
        }
        calculation();
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String asString() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
