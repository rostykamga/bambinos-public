/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lesbambinos.entity;

import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 *
 * @author Rostand
 */
@Entity
@Table(name = "installmentsperlevels")
public class InstallmentPerLevel implements AbstractEntity{
    
    private static final long serialVersionUID = 1L;
    
    
    @EmbeddedId
    private InstallmentPerLevelPK instPk;
    
    @Basic(optional = false)
    @Column(name = "amount")
    private double amount;

    public InstallmentPerLevel(){
    }
    
    
    public InstallmentPerLevel(InstallmentPerLevelPK id, double amount) {
        this.instPk = id;
        this.amount = amount;
    }

    public InstallmentPerLevelPK getInstPk() {
        return instPk;
    }

    public void setInstPk(InstallmentPerLevelPK instPk) {
        this.instPk = instPk;
    }
    

    @Override
    public Long getId() {
        return 0L;
    }

    @Override
    public void setId(Long id) {
    }
    

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 47 * hash + Objects.hashCode(this.instPk);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final InstallmentPerLevel other = (InstallmentPerLevel) obj;
        if (!Objects.equals(this.instPk, other.instPk)) {
            return false;
        }
        return true;
    }

    @Override
    public String asString() {
        return "InstallmentPerLevel{" + "instPk=" + instPk + ", amount=" + amount + '}';
    }

}
