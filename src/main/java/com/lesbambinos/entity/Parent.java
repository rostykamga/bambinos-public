/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lesbambinos.entity;

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
@Table(name = "parents")
public class Parent implements AutocompletedEntity {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Basic(optional = false)
    @Column(name = "fullname")
    private String fullname;
    @Column(name = "profession")
    private String profession;
    @Basic(optional = false)
    @Column(name = "phone1")
    private String phone1;
    @Column(name = "phone2")
    private String phone2;
    @Column(name = "religion")
    private String religion;
    @Column(name = "residence")
    private String residence;

    public Parent() {
    }

    public Parent(String fullname, String profession, String phone1, String phone2, String religion, String residence) {
        this.fullname = fullname;
        this.profession = profession;
        this.phone1 = phone1;
        this.phone2 = phone2;
        this.religion = religion;
        this.residence = residence;
    }

    public Parent(Long id, String fullname, String profession, String phone1, String phone2, String religion, String residence) {
        this.id = id;
        this.fullname = fullname;
        this.profession = profession;
        this.phone1 = phone1;
        this.phone2 = phone2;
        this.religion = religion;
        this.residence = residence;
    }

    

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getPhone1() {
        return phone1;
    }

    public void setPhone1(String phone1) {
        this.phone1 = phone1;
    }

    public String getPhone2() {
        return phone2;
    }

    public void setPhone2(String phone2) {
        this.phone2 = phone2;
    }

    public String getReligion() {
        return religion;
    }

    public void setReligion(String religion) {
        this.religion = religion;
    }

    public String getResidence() {
        return residence;
    }

    public void setResidence(String residence) {
        this.residence = residence;
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
        if (!(object instanceof Parent)) {
            return false;
        }
        Parent other = (Parent) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String getAutocompleteValue() {
        return fullname;
    }

    @Override
    public String toString() {
        return "Parent{" + "id=" + id + ", fullname=" + fullname + ", profession=" + profession + ", phone1=" + phone1 + ", phone2=" + phone2 + ", religion=" + religion + ", residence=" + residence + '}';
    }
    
    @Override
    public String asString() {
        return "Parent{" + "id=" + id + ", fullname=" + fullname + ", profession=" + profession + ", phone1=" + phone1 + ", phone2=" + phone2 + ", religion=" + religion + ", residence=" + residence + '}';
    }
    
}
