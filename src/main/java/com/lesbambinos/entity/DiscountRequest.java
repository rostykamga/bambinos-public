/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lesbambinos.entity;

import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Rostand
 */
@Entity
@Table(name = "discountrequests")
public class DiscountRequest implements AbstractEntity{

    public static class STATUS{
       
        public static final String PENDING = "En Attente";
        public static final String ACCEPTED = "Accordé";
        public static final String REJECTED = "Refusé";
    }
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    
    @Column(name = "description")
    private String description;
    
    @Column(name = "status")
    private String status;
    
    @OneToOne
    @JoinColumn(name = "studentId")
    private Student student;
    
    @JoinColumn(name = "classeId")
    @ManyToOne()
    private Classe classroom;
    
    @JoinColumn(name = "creatorId")
    @ManyToOne()
    private Employee creator;
    
    @JoinColumn(name = "validatorId")
    @ManyToOne()
    private Employee validator;
    
    @Column(name = "fixedAmount")
    private double fixedAmount;
    
    @Temporal(TemporalType.DATE)
    @Column(name = "validationDate")
    private Date validationDate;
    
    @Temporal(TemporalType.DATE)
    @Column(name = "requestDate")
    private Date requestDate;

    public DiscountRequest() {
    }

    public DiscountRequest(String description, String status, Student student, Classe classroom, Employee creator, Employee validator, double fixedAmount, Date validationDate, Date requestDate) {
        this.description = description;
        this.status = status;
        this.student = student;
        this.classroom = classroom;
        this.creator = creator;
        this.validator = validator;
        this.fixedAmount = fixedAmount;
        this.validationDate = validationDate;
        this.requestDate = requestDate;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Classe getClassroom() {
        return classroom;
    }

    public void setClassroom(Classe classroom) {
        this.classroom = classroom;
    }

    public Employee getCreator() {
        return creator;
    }

    public void setCreator(Employee creator) {
        this.creator = creator;
    }
    
    public Employee getValidator() {
        return validator;
    }

    public void setValidator(Employee validator) {
        this.validator = validator;
    }

    public double getFixedAmount() {
        return fixedAmount;
    }

    public void setFixedAmount(double fixedAmount) {
        this.fixedAmount = fixedAmount;
    }

    public Date getValidationDate() {
        return validationDate;
    }

    public void setValidationDate(Date validationDate) {
        this.validationDate = validationDate;
    }

    public Date getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(Date requestDate) {
        this.requestDate = requestDate;
    }

    @Override
    public String asString() {
        return "DiscountRequest{" + "id=" + id + ", description=" + description + ", status=" + status + ", student=" + student + ", classroom=" + classroom + ", validator=" + validator + ", fixedAmount=" + fixedAmount + ", validationDate=" + validationDate + ", requestDate=" + requestDate + '}';
    }

}
