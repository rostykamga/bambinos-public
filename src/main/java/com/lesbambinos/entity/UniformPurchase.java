package com.lesbambinos.entity;

import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;

/**
 *
 * @author Rostand
 */
@Entity
@Table(name = "uniformpurchases")
public class UniformPurchase implements AbstractEntity{
    
    public static class Status{
        public static final int PENDING = 0;
        public static final int DELIVERED = 1;
        public static final int CANCELLED = 2;
        
    }
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    
    @JoinColumn(name = "uniformId", referencedColumnName = "id")
    @ManyToOne(cascade = CascadeType.MERGE)
    private Uniform uniform;
        
    @Column(name = "quantity")
    private int quantity;
    
    @Column(name = "unitPrice")
    private double unitPrice;
    
    @Column(name = "total")
    private int total;
    
    @Column(name = "status")
    private int status;
    
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    @Column(name = "purchaseDate")
    private Date purchaseDate;
    
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    @Column(name = "deliveryDate")
    private Date deliveryDate;
    
    @JoinColumn(name = "purchaserId", referencedColumnName = "id")
    @ManyToOne(cascade = CascadeType.MERGE)
    private Employee purchaser;
    
    @JoinColumn(name = "receptorId", referencedColumnName = "id")
    @ManyToOne(cascade = CascadeType.MERGE)
    private Employee receptor;

    public UniformPurchase() {
    }

    public UniformPurchase(Uniform uniform, int quantity, double unitPrice, int total, int status, Date purchaseDate, Date deliveryDate, Employee purchaser, Employee receptor) {
        this.uniform = uniform;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.total = total;
        this.status = status;
        this.purchaseDate = purchaseDate;
        this.deliveryDate = deliveryDate;
        this.purchaser = purchaser;
        this.receptor = receptor;
    }

    public UniformPurchase(Long id, Uniform uniform, int quantity, double unitPrice, int total, int status, Date purchaseDate, Date deliveryDate, Employee purchaser, Employee receptor) {
        this.id = id;
        this.uniform = uniform;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.total = total;
        this.status = status;
        this.purchaseDate = purchaseDate;
        this.deliveryDate = deliveryDate;
        this.purchaser = purchaser;
        this.receptor = receptor;
    }


    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public Uniform getUniform() {
        return uniform;
    }

    public void setUniform(Uniform uniform) {
        this.uniform = uniform;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Date getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(Date purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public Employee getPurchaser() {
        return purchaser;
    }

    public void setPurchaser(Employee purchaser) {
        this.purchaser = purchaser;
    }

    public Employee getReceptor() {
        return receptor;
    }

    public void setReceptor(Employee receptor) {
        this.receptor = receptor;
    }

    @Override
    public String asString() {
        return "UniformPurchase{" + "id=" + id + ", uniform=" + uniform + ", quantity=" + quantity + ", unitPrice=" + unitPrice + ", total=" + total + ", status=" + status + ", purchaseDate=" + purchaseDate + ", deliveryDate=" + deliveryDate + ", purchaser=" + purchaser + ", receptor=" + receptor + '}';
    }
    
}
