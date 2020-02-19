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
@Table(name = "tuitions")
public class Tuition implements  AbstractEntity, Comparable<Tuition> {
    
    public static ObservableList<Tuition> TUITIONLIST = FXCollections.observableArrayList();

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Basic(optional = false)
    @Column(name = "title")
    private String title;
    @Basic(optional = false)
    @Column(name = "nbInstallment")
    private int nbInstallment;
    @Column(name = "description")
    private String description;


    public Tuition() {
    }

    public Tuition(Long id) {
        this.id = id;
    }

    public Tuition(String title, int totalAmount, String description) {
        this.title = title;
        this.nbInstallment = totalAmount;
        this.description = description;
    }

    public Tuition(Long id, String title, int totalAmount, String description) {
        this.id = id;
        this.title = title;
        this.nbInstallment = totalAmount;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getNbInstallment() {
        return nbInstallment;
    }

    public void setNbInstallment(int totalAmount) {
        this.nbInstallment = totalAmount;
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
        if (!(object instanceof Tuition)) {
            return false;
        }
        Tuition other = (Tuition) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return title;
    }

    @Override
    public int compareTo(Tuition o) {
        return this.title.compareTo(o.title);
    }

    @Override
    public String asString() {
        return "Tuition{" + "id=" + id + ", title=" + title + ", nbInstallment=" + nbInstallment + ", description=" + description + '}';
    }
    
}
