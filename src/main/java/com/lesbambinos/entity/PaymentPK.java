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
public class PaymentPK implements Serializable{
    
    private static final long serialVersionUID = 1L;
    
    @JoinColumn(name = "studentId")
    @OneToOne(cascade = CascadeType.MERGE)
    private Student student;
    
    @JoinColumn(name = "installmentId")
    @OneToOne(cascade = CascadeType.MERGE)
    private Installment installment;

    public PaymentPK() {
    }

    public PaymentPK(Student student, Installment installment) {
        this.student = student;
        this.installment = installment;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Installment getInstallment() {
        return installment;
    }

    public void setInstallment(Installment installment) {
        this.installment = installment;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 17 * hash + Objects.hashCode(this.student);
        hash = 17 * hash + Objects.hashCode(this.installment);
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
        final PaymentPK other = (PaymentPK) obj;
        if (!Objects.equals(this.student, other.student)) {
            return false;
        }
        if (!Objects.equals(this.installment, other.installment)) {
            return false;
        }
        return true;
    }

    
}
