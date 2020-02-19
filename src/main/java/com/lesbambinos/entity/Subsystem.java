/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lesbambinos.entity;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
@Table(name = "subsystems")
public class Subsystem implements AbstractEntity {
    
    public static final transient ObservableList<Subsystem> SUBSYSTEMS = FXCollections.observableArrayList();
    
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Basic(optional = false)
    @Column(name = "subsystemname")
    private String subsystemname;
    @Column(name = "regprefix")
    private String regprefix;
    @Column(name = "description")
    private String description;

    public Subsystem() {
    }

    public Subsystem(String subsystemname, String regprefix, String description) {
        this.subsystemname = subsystemname;
        this.regprefix = regprefix;
        this.description = description;
    }

    
    public Subsystem(Long id, String subsystemname, String regprefix, String description) {
        this.id = id;
        this.subsystemname = subsystemname;
        this.regprefix = regprefix;
        this.description = description;
    }


    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getSubsystemname() {
        return subsystemname;
    }

    public void setSubsystemname(String subsystemname) {
        this.subsystemname = subsystemname;
    }

    public String getRegprefix() {
        return regprefix;
    }

    public void setRegprefix(String regprefix) {
        this.regprefix = regprefix;
    }
    

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
        if (!(object instanceof Subsystem)) {
            return false;
        }
        Subsystem other = (Subsystem) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return getSubsystemname();
    }

    @Override
    public String asString() {
        return "Subsystem{" + "id=" + id + ", subsystemname=" + subsystemname + ", regprefix=" + regprefix + ", description=" + description + '}';
    }

    
}
