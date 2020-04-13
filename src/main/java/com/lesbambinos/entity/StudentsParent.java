/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lesbambinos.entity;

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

/**
 *
 * @author Rostand
 */
@Entity
@Table(name = "students_parents")
public class StudentsParent implements AbstractEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    
    @JoinColumn(name = "parentId", referencedColumnName = "id")
    @ManyToOne(cascade = CascadeType.MERGE)
    private Parent parent;
    
    @JoinColumn(name = "studentId", referencedColumnName = "id")
    @ManyToOne(cascade = CascadeType.MERGE)
    private Student student;
    
    @Basic(optional = false)
    @Column(name = "parentRole")
    private String parentRole;
    
    public StudentsParent() {
    }

    public StudentsParent(Parent parent, Student student, String parentRole) {
        this.parent = parent;
        this.student = student;
        this.parentRole = parentRole;
    }

    
    public StudentsParent(Long id, Parent parent, Student student, String parentRole) {
        this.id = id;
        this.parent = parent;
        this.student = student;
        this.parentRole = parentRole;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public Parent getParent() {
        return parent;
    }

    public void setParent(Parent parent) {
        this.parent = parent;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public String getParentRole() {
        return parentRole;
    }

    public void setParentRole(String parentRole) {
        this.parentRole = parentRole;
    }

    @Override
    public String toString() {
        return "StudentsParent{" + "id=" + id + ", parent=" + parent + ", student=" + student + ", parentRole=" + parentRole + '}';
    }
    
    @Override
    public String asString() {
        return "StudentsParent{" + "id=" + id + ", parent=" + parent + ", student=" + student + ", parentRole=" + parentRole + '}';
    }
    
}
