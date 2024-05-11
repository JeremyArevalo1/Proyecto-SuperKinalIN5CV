/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jeremyarevalo.dto;

import org.jeremyarevalo.model.CategoriaProductos;

/**
 *
 * @author jerem
 */
public class CategoriaProductoDTO {
    private static CategoriaProductoDTO instance;
    private CategoriaProductos categoriaProductos;

    public CategoriaProductoDTO() {
    }
    
    public static CategoriaProductoDTO getCategoriaProductoDTO(){
        if(instance == null){
            instance = new CategoriaProductoDTO();
        }
        return instance;
    }

    public CategoriaProductos getCategoriaProductos() {
        return categoriaProductos;
    }

    public void setCategoriaProductos(CategoriaProductos categoriaProductos) {
        this.categoriaProductos = categoriaProductos;
    }
    
    
}
