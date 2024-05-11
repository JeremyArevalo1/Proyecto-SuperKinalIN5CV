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
import org.jeremyarevalo.dto.CategoriaProductoDTO;
import org.jeremyarevalo.model.CategoriaProductos;
import org.jeremyarevalo.system.Main;
import org.jeremyarevalo.utils.SuperKinalAlert;

/**
 * FXML Controller class
 *
 * @author jerem
 */
public class FormCategoriasProductosController implements Initializable {
    private Main stage;
    private int op;
    
    private static Connection conexion;
    private static PreparedStatement statement;
    
    @FXML
    TextField tfCatProId, tfNomCatergoria, tfDesCategoria;
    @FXML
    Button btnGuardar6, btnCancelar6;
    @FXML
    public void handleButtonAction(ActionEvent event){
        if(event.getSource() == btnCancelar6){
            stage.menuCategoriasProductosView();
            CategoriaProductoDTO.getCategoriaProductoDTO().setCategoriaProductos(null);
        }else if(event.getSource() == btnGuardar6){
            if(op == 1){
                if(!tfNomCatergoria.getText().equals("") && !tfDesCategoria.getText().equals("")){
                agregarCliente();
                SuperKinalAlert.getInstance().mostrarAlertaInfo(401);
                stage.menuCategoriasProductosView(); 
                }else{
                    SuperKinalAlert.getInstance().mostrarAlertaInfo(400);
                    tfNomCatergoria.requestFocus();
                    return;
                }  
            }else if(op == 2){
                if(!tfNomCatergoria.getText().equals("") && !tfDesCategoria.getText().equals("")){
                    if(SuperKinalAlert.getInstance().mostrarAlertaConfirmacion(106).get() == ButtonType.OK){
                        editarCliente();
                        CategoriaProductoDTO.getCategoriaProductoDTO().setCategoriaProductos(null);
                        stage.menuCategoriasProductosView();   
                    }
                }else{
                    SuperKinalAlert.getInstance().mostrarAlertaInfo(400);
                    tfNomCatergoria.requestFocus();
                    return;
                }
                
            }

        }
    }
    
    public void cargaarDatos(CategoriaProductos categoriaProductos){
        tfCatProId.setText(Integer.toString(categoriaProductos.getCategoriaProductoId()));
        tfNomCatergoria.setText(categoriaProductos.getNombreCategoria());
        tfDesCategoria.setText(categoriaProductos.getDescripcionCategoria());
    }
    
    public void agregarCliente(){
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_agregarCategoriaProductos(?,?)";
            statement = conexion.prepareStatement(sql);
            statement.setString(1, tfNomCatergoria.getText());
            statement.setString(2, tfDesCategoria.getText());
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
    
    public void editarCliente(){
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_editarCategoriaProductos(?,?,?)";
            statement = conexion.prepareStatement(sql);
            statement.setInt(1, Integer.parseInt(tfCatProId.getText()));
            statement.setString(2, tfNomCatergoria.getText());
            statement.setString(3, tfDesCategoria.getText());
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
        if(CategoriaProductoDTO.getCategoriaProductoDTO().getCategoriaProductos() != null){
            cargaarDatos(CategoriaProductoDTO.getCategoriaProductoDTO().getCategoriaProductos());
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
