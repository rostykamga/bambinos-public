/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lesbambinos.entity;

import java.io.Serializable;
import java.util.Objects;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author Rostand
 */
@Entity
@Table(name = "cycles")
public class Cycle implements Serializable,AbstractEntity {

    public static final transient ObservableList<Cycle> CYCLES = FXCollections.observableArrayList();
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    
    @Basic(optional = false)
    @Column(name = "cyclename")
    private String cyclename;
    @Column(name = "description")
    private String description;
    
    @JoinColumn(name = "subsystemId")
//    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    @OneToOne(cascade = CascadeType.MERGE)
    private Subsystem subsystem;


    public Cycle() {
    }

    public Cycle(String cyclename, String description, Subsystem subsystem) {
        this.cyclename = cyclename;
        this.description = description;
        this.subsystem = subsystem;
    }

    public Cycle(Long id, String cyclename, String description, Subsystem subsystem) {
        this.id = id;
        this.cyclename = cyclename;
        this.description = description;
        this.subsystem = subsystem;
    }

    

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getCyclename() {
        return cyclename;
    }

    public void setCyclename(String cyclename) {
        this.cyclename = cyclename;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    

    public Subsystem getSubsystem() {
        return subsystem;
    }

    public void setSubsystem(Subsystem subsystem) {
        this.subsystem = subsystem;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 59 * hash + Objects.hashCode(this.id);
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
        final Cycle other = (Cycle) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
    
    @Override
    public String toString(){
        return cyclename+ " ("+subsystem.getSubsystemname()+")";
    }

    
    @Override
    public String asString() {
        return "Cycle{" + "id=" + id + ", cyclename=" + cyclename + ", description=" + description + ", subsystem=" + subsystem + '}';
    }
    
}
