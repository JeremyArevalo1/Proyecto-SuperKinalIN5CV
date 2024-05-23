/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jeremyarevalo.utils;

import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

/**
 *
 * @author jerem
 */
public class SuperKinalAlert {
    
    private static SuperKinalAlert instance;
    
    private SuperKinalAlert(){   
    }
    
    public static SuperKinalAlert getInstance(){
        if(instance == null){
            instance = new SuperKinalAlert();
        }
        return instance;
    }
    
    public void mostrarAlertaInfo(int code){
        if(code == 400){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Campos pendientes");
            alert.setHeaderText("Campos pendientes");
            alert.setContentText("Algunos campos necesarios para el registro estan pendientes");
            alert.showAndWait();
        }else if(code == 401){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Confirmacion de registro");
            alert.setHeaderText("Confirmacion de registro");
            alert.setContentText("El registro se ha creado con exito!");
            alert.showAndWait();
        }else if(code == 805){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("usuario Incorrecto");
            alert.setHeaderText("usuario Incorrecto");
            alert.setContentText("verifique el usuario");
            alert.showAndWait();
        }else if(code == 815){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("contraseña Incorrecta");
            alert.setHeaderText("contraseña Incorrecta");
            alert.setContentText("verifique la contraseña");
            alert.showAndWait();
                
        }
    }
    
    public Optional<ButtonType> mostrarAlertaConfirmacion(int code){
        Optional<ButtonType> action = null;
       if(code == 405){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Eliminacion de registro");
            alert.setHeaderText("Eliminacion de registro");
            alert.setContentText("¿Desea Eliminar el registro?");
            action = alert.showAndWait();
        }else if(code == 106){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Edicion de registro");
            alert.setHeaderText("Edicion de registro");
            alert.setContentText("¿Desea confirmar la edicion de este registro?");
            action = alert.showAndWait();
        }
        return action;
    }
    
    public void alertaSaludo(String usuario){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Bienvenido");
        alert.setHeaderText("Bienvenido");
        alert.setContentText("Bienvenido " + usuario);
        alert.showAndWait();
    }
    
}
