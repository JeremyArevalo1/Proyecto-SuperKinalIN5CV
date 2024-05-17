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
import javafx.scene.control.MenuItem;
import org.jeremyarevalo.system.Main;
/**
 *
 * @author jerem
 */
public class MenuPrincipalController implements Initializable{
    private Main stage;
    
    @FXML
    MenuItem btnMenuClientes, btnMenuTickets, btnMenuCargos, btnMenuDistribuidores, btnMenuEmpleados, btnMenuFacturas, btnMenuCategoriaProductos, btnMenuProductos, btnCompras, btnPromociones;
    @FXML
    public void handleButtonAction(ActionEvent event){
        if(event.getSource() == btnMenuClientes){
            stage.menuClientesView();
        }else if(event.getSource() == btnMenuTickets){
            stage.menuTicketSoporteView();
        }else if(event.getSource() == btnMenuCargos){
            stage.menuCargosView();
        }else if(event.getSource() == btnMenuDistribuidores){
            stage.menuDistribuidoresView();
        }else if(event.getSource() == btnMenuEmpleados){
            stage.menuEmpleadosView();
        }else if(event.getSource() == btnMenuFacturas){
            stage.menuFacturasView();
        }else if(event.getSource() == btnMenuCategoriaProductos){
            stage.menuCategoriasProductosView();
        }else if(event.getSource() == btnMenuProductos){
            stage.menuProductosView();
        }else if(event.getSource() == btnCompras){
            stage.menuComprasView();
        }else if(event.getSource() == btnPromociones){
            stage.menuPromocionesView();
        }
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources){
        
    }

    public Main getStage() {
        return stage;
    }

    public void setStage(Main stage) {
        this.stage = stage;
    }
    
}
