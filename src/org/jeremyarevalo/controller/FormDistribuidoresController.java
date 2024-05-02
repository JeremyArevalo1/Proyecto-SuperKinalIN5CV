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
import org.jeremyarevalo.dto.DistribuidorDTO;
import org.jeremyarevalo.model.Distribuidores;
import org.jeremyarevalo.system.Main;
import org.jeremyarevalo.utils.SuperKinalAlert;

/**
 * FXML Controller class
 *
 * @author jerem
 */
public class FormDistribuidoresController implements Initializable {
    private Main stage;
    private int op;
    
    private static Connection conexion;
    private static PreparedStatement statement;
    
    @FXML
    TextField tfDistribuidorId, tfNomDistribuidor, tfDirDistribuidor, tfNitDistribuidor, tfTelDistribuidor, tfWeb;
    @FXML
    Button btnGuardar4, btnCancelar4;
    
    @FXML
    public void handleButtonAction(ActionEvent event){
        if(event.getSource() == btnCancelar4){
            stage.menuDistribuidoresView();
            DistribuidorDTO.getDistribuidorDTO().setDistribuidores(null);
        }else if(event.getSource() == btnGuardar4){
            if(op == 1){
                if(!tfNomDistribuidor.getText().equals("") && !tfDirDistribuidor.getText().equals("") && !tfNitDistribuidor.getText().equals("") && !tfTelDistribuidor.getText().equals("")){
                agregarDistribuidor();
                SuperKinalAlert.getInstance().mostrarAlertaInfo(401);
                stage.menuDistribuidoresView(); 
                }else{
                    SuperKinalAlert.getInstance().mostrarAlertaInfo(400);
                    tfNomDistribuidor.requestFocus();
                    return;
                }  
            }else if(op == 2){
                if(!tfNomDistribuidor.getText().equals("") && !tfDirDistribuidor.getText().equals("") && !tfNitDistribuidor.getText().equals("") && !tfTelDistribuidor.getText().equals("")){
                    if(SuperKinalAlert.getInstance().mostrarAlertaConfirmacion(106).get() == ButtonType.OK){
                        editarDistribuidor();
                        DistribuidorDTO.getDistribuidorDTO().setDistribuidores(null);
                        stage.menuDistribuidoresView();   
                    }
                }else{
                    SuperKinalAlert.getInstance().mostrarAlertaInfo(400);
                    tfNomDistribuidor.requestFocus();
                    return;
                }
                
            }

        }
    }
    
    public void cargarDatosss(Distribuidores distribuidores){
        tfDistribuidorId.setText(Integer.toString(distribuidores.getDistribuidorId()));
        tfNomDistribuidor.setText(distribuidores.getNombreDistribuidor());
        tfDirDistribuidor.setText(distribuidores.getDireccionDistribuidor());
        tfNitDistribuidor.setText(distribuidores.getNitDistribuidor());
        tfTelDistribuidor.setText(distribuidores.getTelefonoDistribuidor());
        tfWeb.setText(distribuidores.getWeb());
        
    }
    
    public void agregarDistribuidor(){
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_agregarDistribuidores(?,?,?,?,?)";
            statement = conexion.prepareStatement(sql);
            statement.setString(1, tfNomDistribuidor.getText());
            statement.setString(2, tfDirDistribuidor.getText());
            statement.setString(3, tfNitDistribuidor.getText());
            statement.setString(4, tfTelDistribuidor.getText());
            statement.setString(5, tfWeb.getText());
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
    
    public void editarDistribuidor(){
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_editarDistribuidores(?,?,?,?,?,?)";
            statement = conexion.prepareStatement(sql);
            statement.setInt(1, Integer.parseInt(tfDistribuidorId.getText()));
            statement.setString(2, tfNomDistribuidor.getText());
            statement.setString(3, tfDirDistribuidor.getText());
            statement.setString(4, tfNitDistribuidor.getText());
            statement.setString(5, tfTelDistribuidor.getText());
            statement.setString(6, tfWeb.getText());
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
        // TODO
        if(DistribuidorDTO.getDistribuidorDTO().getDistribuidores() != null){
            cargarDatosss(DistribuidorDTO.getDistribuidorDTO().getDistribuidores());
        }
        
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
