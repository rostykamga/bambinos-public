/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lesbambinos.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 *
 * @author Rostand
 */
@Entity
@Table(name = "students_parents")
public class StudentsParent implements AbstractEntity {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    private StudentsParentPK pk;
    @Basic(optional = false)
    @Column(name = "parentRole")
    private String parentRole;
    @Column(name = "fulldescription")
    private String fulldescription;
    

    public StudentsParent() {
        pk = new StudentsParentPK();
    }

    public StudentsParent(String parentRole, String fulldescription, Parent parent, Student student) {
        pk = new StudentsParentPK(parent, student);
        this.parentRole = parentRole;
        this.fulldescription = fulldescription;
    }

    public StudentsParentPK getPk() {
        return pk;
    }

    public void setPk(StudentsParentPK pk) {
        this.pk = pk;
    }
    
    

    @Override
    public Long getId() {
        return 0L;
    }

    @Override
    public void setId(Long id) {
    }

    public String getParentRole() {
        return parentRole;
    }

    public void setParentRole(String parentRole) {
        this.parentRole = parentRole;
    }

    public String getFulldescription() {
        return fulldescription;
    }

    public void setFulldescription(String fulldescription) {
        this.fulldescription = fulldescription;
    }

    public Parent getParent() {
        return pk.getParent();
    }

    public void setParent(Parent parent) {
        this.pk.setParent(parent);
    }

    public Student getStudent() {
        return pk.getStudent();
    }

    public void setStudent(Student student) {
        this.pk.setStudent(student);
    }

    @Override
    public String asString() {
        return "StudentsParent{" + "pk=" + pk + ", parentRole=" + parentRole + ", fulldescription=" + fulldescription + '}';
    }
    
}
