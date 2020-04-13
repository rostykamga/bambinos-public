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
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

/**
 * Attachement represent a document that might be tied to an entity, be it a physicall entity like a person, or a 
 * virtual entity like a registration
 * @author Rostand
 */
@Entity
@Table(name = "attachments")
public class Attachment implements AbstractEntity{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    
    @Column(name = "ownerId")
    private Long ownerId;
    
    @Column(name = "ownerType")
    private String ownerType;
    
    @Column(name = "description")
    private String description;
    
    @Column(name = "filename")
    private String filename;
    
    
    @Lob @Basic(fetch=FetchType.LAZY)
    @Column(name = "content")
    private byte[] content;

    public Attachment() {
    }

    public Attachment(Long ownerId, String ownerType, String description, String filename, byte[] content) {
        this.ownerId = ownerId;
        this.ownerType = ownerType;
        this.description = description;
        this.filename = filename;
        this.content = content;
    }

    public Attachment(Long id, Long ownerId, String ownerType, String description, String filename, byte[] content) {
        this.id = id;
        this.ownerId = ownerId;
        this.ownerType = ownerType;
        this.description = description;
        this.filename = filename;
        this.content = content;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public String getOwnerType() {
        return ownerType;
    }

    public void setOwnerType(String ownerType) {
        this.ownerType = ownerType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }
    
    

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 23 * hash + Objects.hashCode(this.id);
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
        final Attachment other = (Attachment) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }


    @Override
    public String toString() {
        return description;
    }

    @Override
    public String asString() {
        return "Attachment{" + "id=" + id + ", ownerId=" + ownerId + ", ownerType=" + ownerType + ", description=" + description + ", filename=" + filename + ", content=" + content + '}';
    }
    
    

   
    
}
