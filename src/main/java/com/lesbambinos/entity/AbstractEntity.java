/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lesbambinos.entity;

import java.io.Serializable;

/**
 *
 * @author Rostand
 */
public interface AbstractEntity extends Serializable{
    public Long getId();
    public void setId(Long id);
    public String asString();
}
