/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lesbambinos.entity;

import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Rostand
 */
@Entity
@Table(name = "registrations")
public class Registration implements AbstractEntity {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    private RegistrationPK pk;
    @Basic(optional = false)
    @Column(name = "registrationDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date registrationDate;


    public Registration() {
        pk = new RegistrationPK();
    }

    public Registration(Date registrationDate, Classe classe, Student student) {
        this.registrationDate = registrationDate;
        this.pk = new RegistrationPK(classe, student);
    }

    public Registration(RegistrationPK id, Date registrationDate) {
        this.pk = id;
        this.registrationDate = registrationDate;
    }

    @Override
    public Long getId() {
        return 0L;
    }

    @Override
    public void setId(Long id) {
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    public Classe getClasse() {
        return pk.getClasse();
    }

    public void setClasse(Classe classe) {
        pk.setClasse(classe);
    }

    public Student getStudent() {
        return pk.getStudent();
    }

    public void setStudent(Student student) {
        pk.setStudent(student);
    }

    @Override
    public String asString() {
        return "Registration{" + "pk=" + pk + ", registrationDate=" + registrationDate + '}';
    }
    
}
