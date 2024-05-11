/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jeremyarevalo.dto;

import org.jeremyarevalo.model.Empleados;

/**
 *
 * @author jerem
 */
public class EmpleadoDTO {
    private static EmpleadoDTO instance;
    private Empleados empleados;
    
    private EmpleadoDTO(){
        
    }
    
    public static EmpleadoDTO getEmpleadoDTO(){
        if(instance == null){
            instance = new EmpleadoDTO();
        }
        return instance;
    }

    public Empleados getEmpleados() {
        return empleados;
    }

    public void setEmpleados(Empleados empleados) {
        this.empleados = empleados;
    }
    
    
    
}
