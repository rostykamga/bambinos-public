/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lesbambinos.controller;

import com.lesbambinos.entity.AutocompletedEntity;
import java.util.List;

/**
 *
 * @author Rostand
 * @param <T>
 */
public interface AutocompleteProvider<T extends AutocompletedEntity> {
    
    public List<T> search(String query);
    
    public int getMinSearcheableLength();
    
    public int getMaxResultSize();
}
