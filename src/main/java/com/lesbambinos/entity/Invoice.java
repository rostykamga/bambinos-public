package com.lesbambinos.entity;

import java.util.Date;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "invoices")
public class Invoice implements AbstractEntity {
    
    @Id
    @Column(name = "invoiceId")
    private String invoiceId;
    
    @Column(name = "total")
    private double total;
    @Column(name = "payable")
    private double payable;
    @Column(name = "paid")
    private double paid;
    @Column(name = "retail")
    private double retail;
    @Column(name = "voucher")
    private String voucher;
    @Column(name = "discount")
    private double discount;
    @Column(name = "cashIn")
    private boolean cashIn;
    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "employeeId")
    private Employee employee;
    @Column(name = "invoiceDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;
    
    public Invoice() {
        
    }


    public Invoice(String invoiceId, double total, double payable, double paid, double retail, String voucher, double discount, boolean cashIn, Employee employee, Date date) {
        this.invoiceId = invoiceId;
        this.total = total;
        this.payable = payable;
        this.paid = paid;
        this.retail = retail;
        this.voucher = voucher;
        this.discount = discount;
        this.cashIn = cashIn;
        this.employee = employee;
        this.date = date;
    }
    
    public Invoice(String invoiceId, double total, double payable, double paid, double retail, String voucher, double discount, boolean cashIn, Employee employee) {
        this.invoiceId = invoiceId;
        this.total = total;
        this.payable = payable;
        this.paid = paid;
        this.retail = retail;
        this.voucher = voucher;
        this.discount = discount;
        this.cashIn = cashIn;
        this.employee = employee;
    }


    @Override
    public Long getId() {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public void setId(Long id) {
        throw new UnsupportedOperationException("Not implemented");
    }

    public String getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(String invoiceId) {
        this.invoiceId = invoiceId;
    }
    
    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public double getPayable() {
        return payable;
    }

    public void setPayable(double payable) {
        this.payable = payable;
    }

    public double getPaid() {
        return paid;
    }

    public void setPaid(double paid) {
        this.paid = paid;
    }

    public double getRetail() {
        return retail;
    }

    public void setRetail(double retail) {
        this.retail = retail;
    }

    public String getVoucher() {
        return voucher;
    }

    public void setVoucher(String voucher) {
        this.voucher = voucher;
    }

    public boolean isCashIn() {
        return cashIn;
    }

    public void setCashIn(boolean cashIn) {
        this.cashIn = cashIn;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.invoiceId);
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
        final Invoice other = (Invoice) obj;
        if (!Objects.equals(this.invoiceId, other.invoiceId)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Invoice{" + "id=" + invoiceId + ", total=" + total + ", payable=" + payable + ", paid=" + paid + ", returned=" + retail + ", voucher=" + voucher + ", discount=" + discount + ", cashIn=" + cashIn + ", employee=" + employee + ", date=" + date + '}';
    }

    @Override
    public String asString() {
        return "Invoice{" + "id=" + invoiceId + ", total=" + total + ", payable=" + payable + ", paid=" + paid + ", returned=" + retail + ", voucher=" + voucher + ", discount=" + discount + ", cashIn=" + cashIn + ", employee=" + employee + ", date=" + date + '}';
    }
    
    
}
