/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jeremyarevalo.dto;

import org.jeremyarevalo.model.Empleado;

/**
 *
 * @author jerem
 */
public class EmpleadoDTO {
    private static EmpleadoDTO instance;
    private Empleado empleados;
    
    private EmpleadoDTO(){
        
    }
    
    public static EmpleadoDTO getEmpleadoDTO(){
        if(instance == null){
            instance = new EmpleadoDTO();
        }
        return instance;
    }

    public Empleado getEmpleados() {
        return empleados;
    }

    public void setEmpleados(Empleado empleados) {
        this.empleados = empleados;
    }
    
    
    
}
