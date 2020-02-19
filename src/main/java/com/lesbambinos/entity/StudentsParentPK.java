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
public class StudentsParentPK implements Serializable {
    
    private static final long serialVersionUID = 1L;
        
    @JoinColumn(name = "parents_id", referencedColumnName = "id")
    @OneToOne(cascade = CascadeType.MERGE)
    private Parent parent;
    @JoinColumn(name = "students_id", referencedColumnName = "id")
    @OneToOne(cascade = CascadeType.MERGE)
    private Student student;

    public StudentsParentPK() {
    }

    public StudentsParentPK(Parent parent, Student student) {
        this.parent = parent;
        this.student = student;
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

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.parent);
        hash = 53 * hash + Objects.hashCode(this.student);
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
        final StudentsParentPK other = (StudentsParentPK) obj;
        if (!Objects.equals(this.parent, other.parent)) {
            return false;
        }
        if (!Objects.equals(this.student, other.student)) {
            return false;
        }
        return true;
    }

    
    
    
}
