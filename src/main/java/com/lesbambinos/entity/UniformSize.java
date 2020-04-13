/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lesbambinos.entity;

import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author Rostand
 */
@Entity
@Table(name = "uniformsizes")
public class UniformSize implements AbstractEntity{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    
    @Column(name = "uniformSize")
    private String uniformSize;

    public UniformSize() {
    }

    public UniformSize(Long id, String size) {
        this.id = id;
        this.uniformSize = size;
    }

    
    
    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getUniformSize() {
        return uniformSize;
    }

    public void setUniformSize(String size) {
        this.uniformSize = size;
    }
    

    @Override
    public String asString() {
        return "UniformSize{" + "id=" + id + ", size=" + uniformSize + '}';
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 71 * hash + Objects.hashCode(this.id);
        hash = 71 * hash + Objects.hashCode(this.uniformSize);
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
        final UniformSize other = (UniformSize) obj;
        if (!Objects.equals(this.uniformSize, other.uniformSize)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString(){
        return uniformSize;
    }
    
}
