/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lesbambinos.entity;

import java.util.Objects;
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
@Table(name = "uniforms")
public class Uniform implements AbstractEntity{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    
    @JoinColumn(name = "uniformType", referencedColumnName = "id")
    @ManyToOne(cascade = CascadeType.MERGE)
    private UniformType type;
    
    @JoinColumn(name = "uniformSize", referencedColumnName = "id")
    @ManyToOne(cascade = CascadeType.MERGE)
    private UniformSize size;
    
    @Column(name = "gender")
    private boolean gender;
    
    @Column(name = "quantityInStock")
    private int quantityInStock;
    
    @Column(name = "purchasePrice")
    private int purchasePrice;
    
    @Column(name = "unitPrice")
    private int unitPrice;
    
    @Column(name = "upStock")
    private int upStock;
    
    @Column(name = "downStock")
    private int downStock;

    public Uniform() {
    }

    public Uniform(UniformType type, UniformSize size, boolean gender, int quantityInStock, int purchasePrice, int unitPrice, int upStock, int downStock) {
        this.type = type;
        this.size = size;
        this.gender = gender;
        this.quantityInStock = quantityInStock;
        this.purchasePrice = purchasePrice;
        this.unitPrice = unitPrice;
        this.upStock = upStock;
        this.downStock = downStock;
    }

    public Uniform(Long id, UniformType type, UniformSize size, boolean gender, int quantityInStock, int purchasePrice, int unitPrice, int upStock, int downStock) {
        this.id = id;
        this.type = type;
        this.size = size;
        this.gender = gender;
        this.quantityInStock = quantityInStock;
        this.purchasePrice = purchasePrice;
        this.unitPrice = unitPrice;
        this.upStock = upStock;
        this.downStock = downStock;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public UniformType getType() {
        return type;
    }

    public void setType(UniformType type) {
        this.type = type;
    }

    public UniformSize getSize() {
        return size;
    }

    public void setSize(UniformSize size) {
        this.size = size;
    }

    public boolean getGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public int getQuantityInStock() {
        return quantityInStock;
    }

    public void setQuantityInStock(int quantityInStock) {
        this.quantityInStock = quantityInStock;
    }

    public int getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(int purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public int getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(int unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getUpStock() {
        return upStock;
    }

    public void setUpStock(int upStock) {
        this.upStock = upStock;
    }

    public int getDownStock() {
        return downStock;
    }

    public void setDownStock(int downStock) {
        this.downStock = downStock;
    }

    @Override
    public String asString() {
        return "Uniform{" + "id=" + id + ", type=" + type + ", size=" + size + ", gender=" + gender + ", quantityInStock=" + quantityInStock + ", purchasePrice=" + purchasePrice + ", unitPrice=" + unitPrice + ", upStock=" + upStock + ", downStock=" + downStock + '}';
    }

    @Override
    public String toString() {
        return type + " / " + size + " / " + gender;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.id);
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
        final Uniform other = (Uniform) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
    
    
    
}
