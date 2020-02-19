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
@Table(name = "levels")
public class Level implements AbstractEntity {

    public static final transient ObservableList<Level> LEVELS = FXCollections.observableArrayList();
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    
    @Basic(optional = false)
    @Column(name = "levelname")
    private String levelname;
    @Basic(optional = false)
    @Column(name = "level")
    private int level;
    @Column(name = "description")
    private String description;

    @JoinColumn(name = "cycleId", referencedColumnName = "id")
    @OneToOne(cascade = CascadeType.MERGE)
//    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    private Cycle cycle;

    public Level() {
    }

    public Level(String levelname, int level, String description, Cycle cycle) {
        this.levelname = levelname;
        this.level = level;
        this.description = description;
        this.cycle = cycle;
    }

    public Level(Long id, String levelname, int level, String description, Cycle cycle) {
        this.id = id;
        this.levelname = levelname;
        this.level = level;
        this.description = description;
        this.cycle = cycle;
    }

    

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLevelname() {
        return levelname;
    }

    public void setLevelname(String levelname) {
        this.levelname = levelname;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
    

    public Cycle getCycle() {
        return cycle;
    }

    public void setCycle(Cycle cycle) {
        this.cycle = cycle;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + Objects.hashCode(this.id);
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
        final Level other = (Level) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
    
    @Override
    public String toString(){
        
        return levelname+ " ("+cycle.getCyclename()+", "+cycle.getSubsystem().getSubsystemname()+")";
    }

    @Override
    public String asString() {
        return "Level{" + "id=" + id + ", levelname=" + levelname + ", level=" + level + ", description=" + description + ", cycle=" + cycle + '}';
    }
}
