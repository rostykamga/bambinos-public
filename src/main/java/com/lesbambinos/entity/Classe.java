/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lesbambinos.entity;

import java.io.Serializable;
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
@Table(name = "classes")
public class Classe implements AbstractEntity {

    public static final transient ObservableList<Classe> CLASSES = FXCollections.observableArrayList();
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    
    @Basic(optional = false)
    @Column(name = "shortname")
    private String shortname;
    @Column(name = "fullname")
    private String fullname;
    @Column(name = "description")
    private String description;

    @JoinColumn(name = "levelId", referencedColumnName = "id")
//    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    @OneToOne(cascade = CascadeType.MERGE)
    private Level level;

    public Classe() {
    }

    public Classe(String shortname, String fullname, String description, Level level) {
        this.shortname = shortname;
        this.fullname = fullname;
        this.description = description;
        this.level = level;
    }

    public Classe(Long id, String shortname, String fullname, String description, Level level) {
        this.id = id;
        this.shortname = shortname;
        this.fullname = fullname;
        this.description = description;
        this.level = level;
    }
    

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getShortname() {
        return shortname;
    }

    public void setShortname(String shortname) {
        this.shortname = shortname;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }
    
    @Override
    public String toString(){
        return shortname;
    }
//    @Override
//    public String toString(){
//        return shortname +" ("+level.getLevelname()+", "+level.getCycle().getCyclename()+", "+level.getCycle().getSubsystem().getSubsystemname()+")";
//    }

    @Override
    public String asString() {
        return "Classe{" + "id=" + id + ", shortname=" + shortname + ", fullname=" + fullname + ", description=" + description + ", level=" + level + '}';
    }

    
}
