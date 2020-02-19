/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lesbambinos.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

/**
 *
 * @author Rostand
 */
@Entity
@Table(name = "installments")
public class Installment implements Serializable, AbstractEntity {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    
    @Basic(optional = false)
    @Column(name = "installmentNumber")
    private int installmentNumber;

    @Basic(optional = false)
    @Column(name = "alertdate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date alertDate;
    @Basic(optional = false)
    @Column(name = "deadline")
    @Temporal(TemporalType.TIMESTAMP)
    private Date deadline;
    @JoinColumn(name = "tuitionId")
    @OneToOne(cascade = CascadeType.MERGE)
    private Tuition tuition;

    public Installment() {
    }

    public Installment(int installmentNumber, Date alertDate, Date deadline, Tuition tuition) {
        this.installmentNumber = installmentNumber;
        this.alertDate = alertDate;
        this.deadline = deadline;
        this.tuition = tuition;
    }

    public Installment(Long id, int installmentNumber,  Date alertDate, Date deadline, Tuition tuition) {
        this.id = id;
        this.installmentNumber = installmentNumber;
        this.alertDate = alertDate;
        this.deadline = deadline;
        this.tuition = tuition;
    }
    

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public int getInstallmentNumber() {
        return installmentNumber;
    }

    public void setInstallmentNumber(int installmentNumber) {
        this.installmentNumber = installmentNumber;
    }
    


    public Date getAlertDate() {
        return alertDate;
    }

    public void setAlertDate(Date alertDate) {
        this.alertDate = alertDate;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }


    public Tuition getTuition() {
        return tuition;
    }

    public void setTuition(Tuition tuition) {
        this.tuition = tuition;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.id);
        hash = 97 * hash + Objects.hashCode(this.tuition);
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
        final Installment other = (Installment) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.tuition, other.tuition)) {
            return false;
        }
        return true;
    }

    

    

    @Override
    public String asString() {
        return "Installment{" + "id=" + id + ", installmentNumber=" + installmentNumber + ", alertDate=" + alertDate + ", deadline=" + deadline + ", tuition=" + tuition + '}';
    }   
    
}
