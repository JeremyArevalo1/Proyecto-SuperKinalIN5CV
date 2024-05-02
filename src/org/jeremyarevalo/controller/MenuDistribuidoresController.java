/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jeremyarevalo.controller;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
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
public class MenuDistribuidoresController implements Initializable {
    private Main stage;
    
    private static Connection conexion = null;
    private static PreparedStatement statement = null;
    private static ResultSet resultSet = null;
    
    @FXML
    TableView tblDistribuidores;
    @FXML
    TableColumn colDistribuidorId, colNomDistribuidor, colDirDistribuidor, colNitDistribuidor, colTelDistribuidor, colWeb;
    @FXML
    Button btnAgregar2, btnEditar2, btnRegresar2, btnEliminar2, btnBuscar2;
    @FXML
    TextField tfDistribuidorId;
    
    @FXML
    public void handleButtonAction(ActionEvent event){
        if(event.getSource() == btnRegresar2){
           stage.menuPrincipalView(); 
        }
        else if(event.getSource() == btnAgregar2){
            stage.formDistribuidoresView(1);
        }
        else if(event.getSource() == btnEditar2){
            DistribuidorDTO.getDistribuidorDTO().setDistribuidores((Distribuidores)tblDistribuidores.getSelectionModel().getSelectedItem());
            stage.formDistribuidoresView(2);
        }
        else if(event.getSource() == btnEliminar2){
            if(SuperKinalAlert.getInstance().mostrarAlertaConfirmacion(405).get() == ButtonType.OK){
                int cliId = ((Distribuidores)tblDistribuidores.getSelectionModel().getSelectedItem()).getDistribuidorId();
                eliminarDistribuidores(cliId);
                cargarListaaa();
            }    
        }
        else if(event.getSource() == btnBuscar2){
            tblDistribuidores.getItems().clear();
            if(tfDistribuidorId.getText().equals("")){
                cargarListaaa();
            }else{
                tblDistribuidores.getItems().add(buscarDistribuidores());
                colDistribuidorId.setCellValueFactory(new PropertyValueFactory<Distribuidores, Integer>("distribuidorId"));
                colNomDistribuidor.setCellValueFactory(new PropertyValueFactory<Distribuidores, String>("nombreDistribuidor"));
                colDirDistribuidor.setCellValueFactory(new PropertyValueFactory<Distribuidores, String>("direccionDistribuidor"));
                colNitDistribuidor.setCellValueFactory(new PropertyValueFactory<Distribuidores, String>("nitDistribuidor"));
                colTelDistribuidor.setCellValueFactory(new PropertyValueFactory<Distribuidores, String>("telefonoDistribuidor"));
                colWeb.setCellValueFactory(new PropertyValueFactory<Distribuidores, String>("web"));
            }
        }
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarListaaa();
    }
    
    public void cargarListaaa(){
        tblDistribuidores.setItems(listarDistribuidores());
        colDistribuidorId.setCellValueFactory(new PropertyValueFactory<Distribuidores, Integer>("distribuidorId"));
        colNomDistribuidor.setCellValueFactory(new PropertyValueFactory<Distribuidores, String>("nombreDistribuidor"));
        colDirDistribuidor.setCellValueFactory(new PropertyValueFactory<Distribuidores, String>("direccionDistribuidor"));
        colNitDistribuidor.setCellValueFactory(new PropertyValueFactory<Distribuidores, String>("nitDistribuidor"));
        colTelDistribuidor.setCellValueFactory(new PropertyValueFactory<Distribuidores, String>("telefonoDistribuidor"));
        colWeb.setCellValueFactory(new PropertyValueFactory<Distribuidores, String>("web"));
    }
    
    public ObservableList<Distribuidores> listarDistribuidores(){
        ArrayList<Distribuidores> distribuidores = new ArrayList<>();
        
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_listarDistribuidores()";
            statement = conexion.prepareStatement(sql);
            resultSet = statement.executeQuery();
            
            while(resultSet.next()){
                int distribuidorId = resultSet.getInt("distribuidorId");
                String nombreDistribuidor = resultSet.getString("nombreDistribuidor");
                String direccionDistribuidor = resultSet.getString("direccionDistribuidor");
                String nitDistribuidor = resultSet.getString("nitDistribuidor");
                String telefonoDistribuidor = resultSet.getString("telefonoDistribuidor");
                String web = resultSet.getString("web");
                
                distribuidores.add(new Distribuidores(distribuidorId, nombreDistribuidor, direccionDistribuidor, nitDistribuidor, telefonoDistribuidor, web));
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }finally{
            try{
                if(resultSet != null){
                    resultSet.close();
                }
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
        return FXCollections.observableList(distribuidores);
    }
    
    public void eliminarDistribuidores(int dirId){
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_eliminarDistribuidores(?)";
            statement = conexion.prepareStatement(sql);
            statement.setInt(1, dirId);
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
    
    public Distribuidores buscarDistribuidores(){
        Distribuidores distribuidores = null;
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_buscarDistribuidores(?)";
            statement = conexion.prepareStatement(sql);
            statement.setInt(1, Integer.parseInt(tfDistribuidorId.getText()));
            resultSet = statement.executeQuery();
            
            if(resultSet.next()){
                int distribuidorId = resultSet.getInt("distribuidorId");
                String nombreDistribuidor = resultSet.getString("nombreDistribuidor");
                String direccionDistribuidor = resultSet.getString("direccionDistribuidor");
                String nitDistribuidor = resultSet.getString("nitDistribuidor");
                String telefonoDistribuidor = resultSet.getString("telefonoDistribuidor");
                String web = resultSet.getString("web");
                
                distribuidores = (new Distribuidores(distribuidorId, nombreDistribuidor, direccionDistribuidor, nitDistribuidor, telefonoDistribuidor, web));
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }finally{
            try{
                if(resultSet != null){
                    resultSet.close();
                }
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
        return distribuidores;
    }

    public Main getStage() {
        return stage;
    }

    public void setStage(Main stage) {
        this.stage = stage;
    }
    
    
    
}
