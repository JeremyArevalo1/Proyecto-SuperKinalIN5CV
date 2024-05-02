/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jeremyarevalo.controller;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import org.jeremyarevalo.dao.Conexion;
import org.jeremyarevalo.dto.CargoDTO;
import org.jeremyarevalo.model.Cargos;
import org.jeremyarevalo.system.Main;
import org.jeremyarevalo.utils.SuperKinalAlert;

/**
 * FXML Controller class
 *
 * @author jerem
 */
public class FormCargosController implements Initializable {
    private Main stage;
    private int op;
    
    private static Connection conexion;
    private static PreparedStatement statement;
    
    @FXML
    TextField tfCargoId, tfNomCargo, tfDesCargo;
    
    @FXML
    Button btnGuardar3, btnCancelar3;
    
    @FXML
    public void handleButtonAction(ActionEvent event){
        if(event.getSource() == btnCancelar3){
            stage.menuCargosView();
            CargoDTO.getCargoDTO().setCargos(null);
        }else if(event.getSource() == btnGuardar3){
            if(op == 1){
                if(!tfNomCargo.getText().equals("") && !tfDesCargo.getText().equals("")){
                agregarCargos();
                SuperKinalAlert.getInstance().mostrarAlertaInfo(401);
                stage.menuCargosView(); 
                }else{
                    SuperKinalAlert.getInstance().mostrarAlertaInfo(400);
                    tfNomCargo.requestFocus();
                    return;
                }  
            }else if(op == 2){
                if(!tfNomCargo.getText().equals("") && !tfDesCargo.getText().equals("")){
                    if(SuperKinalAlert.getInstance().mostrarAlertaConfirmacion(106).get() == ButtonType.OK){
                        editarCargos();
                        CargoDTO.getCargoDTO().setCargos(null);
                        stage.menuCargosView();   
                    }
                }else{
                    SuperKinalAlert.getInstance().mostrarAlertaInfo(400);
                    tfNomCargo.requestFocus();
                    return;
                }
                
            }

        }
    }
    
    public void cargarDatoss(Cargos cargos){
        tfCargoId.setText(Integer.toString(cargos.getCargoId()));
        tfNomCargo.setText(cargos.getNombreCargo());
        tfDesCargo.setText(cargos.getDescripcionCargo());
    }
    
    public void agregarCargos(){
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_agregarCargos(?,?)";
            statement = conexion.prepareStatement(sql);
            statement.setString(1, tfNomCargo.getText());
            statement.setString(2, tfDesCargo.getText());
            statement.execute();

        }catch(SQLException e){
            System.out.println(e.getMessage());
        }finally{
            try{
                if(statement != null){
                    statement.close();
                }
                if(conexion != null){
                    conexion.close();
                }
            }catch(SQLException e){
                System.out.println(e.getMessage());
            }
        }
    }
    
    public void editarCargos(){
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_editarCargos(?,?,?)";
            statement = conexion.prepareStatement(sql);
            statement.setInt(1, Integer.parseInt(tfCargoId.getText()));
            statement.setString(2, tfNomCargo.getText());
            statement.setString(3, tfDesCargo.getText());
            statement.execute();
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }finally{
            try{
                if(statement != null){
                    statement.close();
                }
                if(conexion != null){
                    conexion.close();
                }
            }catch(SQLException e){
                System.out.println(e.getMessage());
            }
        }
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if(CargoDTO.getCargoDTO().getCargos() != null){
            cargarDatoss(CargoDTO.getCargoDTO().getCargos());
        }
        // TODO
    }

    public Main getStage() {
        return stage;
    }

    public void setStage(Main stage) {
        this.stage = stage;
    }

    public void setOp(int op) {
        this.op = op;
    }
    
    
    
}
