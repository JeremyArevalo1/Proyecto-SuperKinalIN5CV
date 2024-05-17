/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jeremyarevalo.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.jeremyarevalo.system.Main;

/**
 * FXML Controller class
 *
 * @author jerem
 */
public class MenuLoginController implements Initializable {
    private Main stage;
    
    @FXML
    TextField tfUsuario;
    @FXML
    Button btnIniciar, btnRegistrarU;
    @FXML
    PasswordField pfContrasenia;
    
    @FXML
    public void handleButtonAction(ActionEvent event){
        if(event.getSource() == btnIniciar){
            
        }
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // TODO
    }  

    public void setStage(Main stage) {
        this.stage = stage;
    }
    
    
    
}
