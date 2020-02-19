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
public class RegistrationPK implements Serializable {
    
    private static final long serialVersionUID = 1L;
        
    @JoinColumn(name = "classId", referencedColumnName = "id")
    @OneToOne(cascade = CascadeType.MERGE)
    private Classe classe;
    @JoinColumn(name = "studentId", referencedColumnName = "id")
    @OneToOne(cascade = CascadeType.MERGE)
    private Student student;

    public RegistrationPK() {
    }

    public RegistrationPK(Classe classe, Student student) {
        this.classe = classe;
        this.student = student;
    }
    
    

    public Classe getClasse() {
        return classe;
    }

    public void setClasse(Classe classe) {
        this.classe = classe;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 17 * hash + Objects.hashCode(this.classe);
        hash = 17 * hash + Objects.hashCode(this.student);
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
        final RegistrationPK other = (RegistrationPK) obj;
        if (!Objects.equals(this.classe, other.classe)) {
            return false;
        }
        if (!Objects.equals(this.student, other.student)) {
            return false;
        }
        return true;
    }
    
    
    
}
