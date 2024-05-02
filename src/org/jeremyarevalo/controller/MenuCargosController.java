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
import org.jeremyarevalo.dto.CargoDTO;
import org.jeremyarevalo.model.Cargos;
import org.jeremyarevalo.system.Main;
import org.jeremyarevalo.utils.SuperKinalAlert;

/**
 * FXML Controller class
 *
 * @author jerem
 */
public class MenuCargosController implements Initializable {
    private Main stage;
    
    private static Connection conexion = null;
    private static PreparedStatement statement = null;
    private static ResultSet resultSet = null;
    
    @FXML
    TableView tblCargos;
    @FXML
    TableColumn colCargoId, colNomCargo, colDesCargo;
    @FXML
    Button btnAgregar1, btnEditar1, btnRegresar1, btnEliminar1, btnBuscar1;
    @FXML
    TextField tfCargoId;
    
    @FXML
    public void handleButtonAction(ActionEvent event){
        if(event.getSource() == btnRegresar1){
           stage.menuPrincipalView(); 
        }
        else if(event.getSource() == btnAgregar1){
            stage.formCargosView(1);
        }
        else if(event.getSource() == btnEditar1){
            CargoDTO.getCargoDTO().setCargos((Cargos)tblCargos.getSelectionModel().getSelectedItem());
            stage.formCargosView(2);
        }
        else if(event.getSource() == btnEliminar1){
            if(SuperKinalAlert.getInstance().mostrarAlertaConfirmacion(405).get() == ButtonType.OK){
                int cliId = ((Cargos)tblCargos.getSelectionModel().getSelectedItem()).getCargoId();
                eliminarCargo(cliId);
                cargarListaa();
            }    
        }
        else if(event.getSource() == btnBuscar1){
            tblCargos.getItems().clear();
            if(tfCargoId.getText().equals("")){
                cargarListaa();
            }else{
                tblCargos.getItems().add(buscarCliente());
                colCargoId.setCellValueFactory(new PropertyValueFactory<Cargos, Integer>("cargoId"));
                colNomCargo.setCellValueFactory(new PropertyValueFactory<Cargos, String>("nombreCargo"));
                colDesCargo.setCellValueFactory(new PropertyValueFactory<Cargos, String>("descripcionCargo"));
            }
        }
    }
    
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarListaa();
    }
    
    public void cargarListaa(){
        tblCargos.setItems(listarCargos());
        colCargoId.setCellValueFactory(new PropertyValueFactory<Cargos, Integer>("cargoId"));
        colNomCargo.setCellValueFactory(new PropertyValueFactory<Cargos, String>("nombreCargo"));
        colDesCargo.setCellValueFactory(new PropertyValueFactory<Cargos, String>("descripcionCargo"));
    }
    
    public ObservableList<Cargos> listarCargos(){
        ArrayList<Cargos> cargos = new ArrayList<>();
        
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_listarCargos()";
            statement = conexion.prepareStatement(sql);
            resultSet = statement.executeQuery();
            
            while(resultSet.next()){
                int cargoId = resultSet.getInt("cargoId");
                String nomCargo = resultSet.getString("nombreCargo");
                String desCargo = resultSet.getString("descripcionCargo");
                
                cargos.add(new Cargos(cargoId, nomCargo, desCargo));
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
        return FXCollections.observableList(cargos);
    }
    
    public void eliminarCargo(int carId){
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_eliminarCargos(?)";
            statement = conexion.prepareStatement(sql);
            statement.setInt(1, carId);
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
    
    public Cargos buscarCliente(){
        Cargos cargos = null;
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_buscarCargos(?)";
            statement = conexion.prepareStatement(sql);
            statement.setInt(1, Integer.parseInt(tfCargoId.getText()));
            resultSet = statement.executeQuery();
            
            if(resultSet.next()){
                int cargoId = resultSet.getInt("cargoId");
                String nomCargo = resultSet.getString("nombreCargo");
                String desCargo = resultSet.getString("descripcionCargo");
                
                cargos = (new Cargos(cargoId, nomCargo, desCargo));
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
        return cargos;
    }

    public Main getStage() {
        return stage;
    }

    public void setStage(Main stage) {
        this.stage = stage;
    }
    
    
}
