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
@Table(name = "uniformorders")
public class UniformOrder implements AbstractEntity{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    
    @JoinColumn(name = "uniformId", referencedColumnName = "id")
    @ManyToOne(cascade = CascadeType.MERGE)
    private Uniform uniform;
    
    @JoinColumn(name = "studentId", referencedColumnName = "id")
    @ManyToOne(cascade = CascadeType.MERGE)
    private Student student;
    
    @Column(name = "quantity")
    private int quantity;
    
    @Column(name = "orderPrice")
    private double orderPrice;
    
    @Column(name = "paid")
    private boolean paid;
    
    @Column(name = "delivered")
    private boolean delivered;
    
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    @Column(name = "orderDate")
    private Date orderDate;
    
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    @Column(name = "deliveryDate")
    private Date deliveryDate;
    
    @JoinColumn(name = "creatorId", referencedColumnName = "id")
    @ManyToOne(cascade = CascadeType.MERGE)
    private Employee creator;
    
    @JoinColumn(name = "delivererId", referencedColumnName = "id")
    @ManyToOne(cascade = CascadeType.MERGE)
    private Employee deliverer;

    public UniformOrder() {
    }

    public UniformOrder(Uniform uniform, Student student, int quantity, double orderPrice, boolean paid, boolean delivered, Date orderDate, Date deliveryDate, Employee creator, Employee deliverer) {
        this.uniform = uniform;
        this.student = student;
        this.quantity = quantity;
        this.orderPrice = orderPrice;
        this.paid = paid;
        this.delivered = delivered;
        this.orderDate = orderDate;
        this.deliveryDate = deliveryDate;
        this.creator = creator;
        this.deliverer = deliverer;
    }

    public UniformOrder(Long id, Uniform uniform, Student student, int quantity, double orderPrice, boolean paid, boolean delivered, Date orderDate, Date deliveryDate, Employee creator, Employee deliverer) {
        this.id = id;
        this.uniform = uniform;
        this.student = student;
        this.quantity = quantity;
        this.orderPrice = orderPrice;
        this.paid = paid;
        this.delivered = delivered;
        this.orderDate = orderDate;
        this.deliveryDate = deliveryDate;
        this.creator = creator;
        this.deliverer = deliverer;
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

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(double orderPrice) {
        this.orderPrice = orderPrice;
    }

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    public boolean isDelivered() {
        return delivered;
    }

    public void setDelivered(boolean delivered) {
        this.delivered = delivered;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public Employee getCreator() {
        return creator;
    }

    public void setCreator(Employee creator) {
        this.creator = creator;
    }

    public Employee getDeliverer() {
        return deliverer;
    }

    public void setDeliverer(Employee deliverer) {
        this.deliverer = deliverer;
    }

    @Override
    public String asString() {
        return "UniformOrder{" + "id=" + id + ", uniform=" + uniform + ", student=" + student + ", quantity=" + quantity + ", orderPrice=" + orderPrice + ", paid=" + paid + ", delivered=" + delivered + ", orderDate=" + orderDate + ", deliveryDate=" + deliveryDate + ", creator=" + creator + ", deliverer=" + deliverer + '}';
    }
    
}
