/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lesbambinos.entity;

import java.sql.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Rostand
 */
@Entity
@Table(name = "students")
public class Student implements AutocompletedEntity {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Basic(optional = false)
    @Column(name = "registrationNumber")
    private String registrationNumber;
    @Basic(optional = false)
    @Column(name = "names")
    private String names;
    @Basic(optional = false)
    @Column(name = "surnames")
    private String surnames;
    @Basic(optional = false)
    @Column(name = "sex")
    private boolean sex;
    @Basic(optional = false)
    @Column(name = "dateOfBirth")
    //@Temporal(TemporalType.)
    private Date dateOfBirth;
    @Basic(optional = false)
    @Column(name = "placeOfBirth")
    private String placeOfBirth;
    @Lob
    @Column(name = "health1")
    private String health1;
    @Lob
    @Column(name = "health2")
    private String health2;
    
    @JoinColumn(name = "classroomId", referencedColumnName = "id")
    @ManyToOne(cascade = CascadeType.MERGE)
    private Classe classroom;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "registrationDate")
    private java.util.Date registrationDate;
    
   
    @Lob
    @Column(name = "picture")
    private byte[] picture;

    public Student() {
    }

    public Student(Long id, String registrationNumber, String names, String surnames, boolean sex, Date dateOfBirth, String placeOfBirth, String health1, String health2, java.util.Date registrationDate, Classe classroom, byte[] picture) {
        this.id = id;
        this.registrationNumber = registrationNumber;
        this.names = names;
        this.surnames = surnames;
        this.sex = sex;
        this.dateOfBirth = dateOfBirth;
        this.placeOfBirth = placeOfBirth;
        this.health1 = health1;
        this.health2 = health2;
        this.registrationDate = registrationDate;
        this.classroom = classroom;
        this.picture = picture;
    }

    public Student(String registrationNumber, String names, String surnames, boolean sex, Date dateOfBirth, String placeOfBirth, String health1, String health2, java.util.Date registrationDate, Classe classroom, byte[] picture) {
        this.registrationNumber = registrationNumber;
        this.names = names;
        this.surnames = surnames;
        this.sex = sex;
        this.dateOfBirth = dateOfBirth;
        this.placeOfBirth = placeOfBirth;
        this.health1 = health1;
        this.health2 = health2;
        this.registrationDate = registrationDate;
        this.classroom = classroom;
        this.picture = picture;
    }

    

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public String getNames() {
        return names;
    }
    
    public String getFullname() {
        return names + " " + surnames;
    }

    public void setNames(String names) {
        this.names = names;
    }

    public String getSurnames() {
        return surnames;
    }

    public void setSurnames(String surnames) {
        this.surnames = surnames;
    }

    public boolean getSex() {
        return sex;
    }

    public void setSex(boolean sex) {
        this.sex = sex;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getPlaceOfBirth() {
        return placeOfBirth;
    }

    public void setPlaceOfBirth(String placeOfBirth) {
        this.placeOfBirth = placeOfBirth;
    }

    public String getHealth1() {
        return health1;
    }

    public void setHealth1(String health1) {
        this.health1 = health1;
    }

    public String getHealth2() {
        return health2;
    }

    public void setHealth2(String health2) {
        this.health2 = health2;
    }

    public byte[] getPicture() {
        return picture;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }

    public Classe getClassroom() {
        return classroom;
    }

    public void setClassroom(Classe classroom) {
        this.classroom = classroom;
    }

    public java.util.Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(java.util.Date registrationDate) {
        this.registrationDate = registrationDate;
    }
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Student)) {
            return false;
        }
        Student other = (Student) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }


    @Override
    public String getAutocompleteValue() {
        return this.names+" "+this.surnames;
    }


    @Override
    public String toString() {
        return "Student{" + "id=" + id + ", registrationNumber=" + registrationNumber + ", names=" + names + ", surnames=" + surnames + ", sex=" + sex + ", dateOfBirth=" + dateOfBirth + ", placeOfBirth=" + placeOfBirth + ", health1=" + health1 + ", health2=" + health2 + ", registrationDate=" + registrationDate + ", classroom=" + classroom + ", picture=" + picture + '}';
    }
    
    @Override
    public String asString() {
        return "Student{" + "id=" + id + ", registrationNumber=" + registrationNumber + ", names=" + names + ", surnames=" + surnames + ", sex=" + sex + ", dateOfBirth=" + dateOfBirth + ", placeOfBirth=" + placeOfBirth + ", health1=" + health1 + ", health2=" + health2 + ", registrationDate=" + registrationDate + ", classroom=" + classroom + ", picture=" + picture + '}';
    }
    
}
