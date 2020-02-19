/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lesbambinos.entity;

import java.util.Date;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Rostand
 */
@Entity
@Table(name = "payments")
public class Payment implements AbstractEntity {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    private PaymentPK pk;
    
    @Basic(optional = false)
    @Column(name = "paidAmount")
    private double paidAmount;
    
    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "employeeId")
    private Employee employee;
    
    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "invoiceId")
    private Invoice invoice;
    
    @Basic(optional = false)
    @Column(name = "paymentDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date paymentDate;

    
    public Payment() {
    }
    
    public Payment(PaymentPK pk, double paidAmount, Employee employee, Invoice invoice, Date paymentDate) {
        this.pk = pk;
        this.paidAmount = paidAmount;
        this.employee = employee;
        this.invoice = invoice;
        this.paymentDate = paymentDate;
    }

    
    @Override
    public Long getId() {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public void setId(Long id) {
        throw new UnsupportedOperationException("Not implemented");
    }

    public PaymentPK getPk() {
        return pk;
    }

    public void setPk(PaymentPK pk) {
        this.pk = pk;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    public double getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(double paidAmount) {
        this.paidAmount = paidAmount;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

   

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 37 * hash + Objects.hashCode(this.pk);
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
        final Payment other = (Payment) obj;
        if (!Objects.equals(this.pk, other.pk)) {
            return false;
        }
        return true;
    }


    @Override
    public String asString() {
        return "Payment{" + "pk=" + pk + ", paidAmount=" + paidAmount + ", employee=" + employee + ", invoice=" + invoice + ", paymentDate=" + paymentDate + '}';
    }
    
    
    
}
