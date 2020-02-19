/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lesbambinos.controller;

import com.lesbambinos.entity.AutocompletedEntity;

/**
 *
 * @author Rostand
 * @param <T>
 */
public interface AutocompletableForm<T extends AutocompletedEntity> {
    public void handleAutocompletion(T element);
}
