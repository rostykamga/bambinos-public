/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lesbambinos.entity;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

/**
 *
 * @author Rostand
 */
@Embeddable
public class InstallmentPerLevelPK implements Serializable{
    
    private static final long serialVersionUID = 1L;
    
    @JoinColumn(name = "levelId")
    @OneToOne(cascade = CascadeType.MERGE)
    private Level level;
    
    @JoinColumn(name = "installmentId")
    @OneToOne(cascade = CascadeType.MERGE)
    private Installment installment;

    public InstallmentPerLevelPK() {
    }

    public InstallmentPerLevelPK(Level level, Installment installment) {
        this.level = level;
        this.installment = installment;
    }
    
    
    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public Installment getInstallment() {
        return installment;
    }

    public void setInstallment(Installment tuition) {
        this.installment = tuition;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + Objects.hashCode(this.level);
        hash = 41 * hash + Objects.hashCode(this.installment);
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
        final InstallmentPerLevelPK other = (InstallmentPerLevelPK) obj;
        if (!Objects.equals(this.level, other.level)) {
            return false;
        }
        if (!Objects.equals(this.installment, other.installment)) {
            return false;
        }
        return true;
    }
    
    
}
