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
import java.sql.Time;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.jeremyarevalo.dao.Conexion;
import org.jeremyarevalo.dto.EmpleadoDTO;
import org.jeremyarevalo.model.Cargos;
import org.jeremyarevalo.model.Empleados;
import org.jeremyarevalo.system.Main;
import org.jeremyarevalo.utils.SuperKinalAlert;

/**
 * FXML Controller class
 *
 * @author jerem
 */
public class MenuEmpleadosController implements Initializable {
    private Main stage;
    
    private static Connection conexion = null;
    private static PreparedStatement statement = null;
    private static ResultSet resultSet = null;
    
    @FXML
    ComboBox cmbCargos, cmbEncargados;
    @FXML
    Button btnAgregar3, btnEditar3, btnGuardarr, btnRegresar3, btnEliminar3, btnVaciar2, btnBuscar3;
    @FXML
    TableView tblEmpleados;
    @FXML
    TableColumn colEmpleadoId, colNomEmpleado, colApeEmpleado, colSueldo, colHorEntrada, colHorSalida, colCargoId, colEncargadoId;
    @FXML
    TextField tfEmpleadoId;
    
    public void handleButtonAction(ActionEvent event){
        if(event.getSource() == btnRegresar3){
           stage.menuPrincipalView(); 
        }
        else if(event.getSource() == btnAgregar3){
            stage.formEmpleadosView(1);
        }
        else if(event.getSource() == btnEditar3){
            EmpleadoDTO.getEmpleadoDTO().setEmpleados((Empleados)tblEmpleados.getSelectionModel().getSelectedItem());
            stage.formEmpleadosView(2);
        }
        else if(event.getSource() == btnEliminar3){
            if(SuperKinalAlert.getInstance().mostrarAlertaConfirmacion(405).get() == ButtonType.OK){
                int empId = ((Empleados)tblEmpleados.getSelectionModel().getSelectedItem()).getEmpleadoId();
                eliminarEmpleado(empId);
                cargarrDatos();
            }    
        }
        else if(event.getSource() == btnBuscar3){
            tblEmpleados.getItems().clear();
            if(tfEmpleadoId.getText().equals("")){
                cargarrDatos();
            }else{
                tblEmpleados.getItems().add(buscarEmpleado());
                colEmpleadoId.setCellValueFactory(new PropertyValueFactory<Empleados, Integer>("empleadoId"));
                colNomEmpleado.setCellValueFactory(new PropertyValueFactory<Empleados, String>("nombreEmpleado"));
                colApeEmpleado.setCellValueFactory(new PropertyValueFactory<Empleados, String>("apellidoEmpleado"));
                colSueldo.setCellValueFactory(new PropertyValueFactory<Empleados, Double>("sueldo"));
                colHorEntrada.setCellValueFactory(new PropertyValueFactory<Empleados, String>("horaEntrada"));
                colHorSalida.setCellValueFactory(new PropertyValueFactory<Empleados, String>("horaSalida"));
                colCargoId.setCellValueFactory(new PropertyValueFactory<Empleados, String>("cargos"));
                colEncargadoId.setCellValueFactory(new PropertyValueFactory<Empleados, String>("encargado"));
            }
        }
        else if(event.getSource() == btnVaciar2){
            vaciarCamposss();
        }
    }
    
    public void cargarrDatos(){
        tblEmpleados.setItems(listarEmpleados());
        colEmpleadoId.setCellValueFactory(new PropertyValueFactory<Empleados, Integer>("empleadoId"));
        colNomEmpleado.setCellValueFactory(new PropertyValueFactory<Empleados, String>("nombreEmpleado"));
        colApeEmpleado.setCellValueFactory(new PropertyValueFactory<Empleados, String>("apellidoEmpleado"));
        colSueldo.setCellValueFactory(new PropertyValueFactory<Empleados, Double>("sueldo"));
        colHorEntrada.setCellValueFactory(new PropertyValueFactory<Empleados, Time>("horaEntrada"));
        colHorSalida.setCellValueFactory(new PropertyValueFactory<Empleados, Time>("horaSalida"));
        colCargoId.setCellValueFactory(new PropertyValueFactory<Empleados, String>("cargos"));
        colEncargadoId.setCellValueFactory(new PropertyValueFactory<Empleados, String>("encargado"));
        tblEmpleados.getSortOrder().add(colEmpleadoId);
    }
    
    public void vaciarCamposss(){
        tfEmpleadoId.clear();
        cmbCargos.getSelectionModel().clearSelection();
        cmbEncargados.getSelectionModel().clearSelection();
    }
    
    public void cargarDatosEditarr(){
        Empleados em = (Empleados)tblEmpleados.getSelectionModel().getSelectedItem();
        if(em != null){
            tfEmpleadoId.setText(Integer.toString(em.getEmpleadoId()));
            cmbEncargados.getSelectionModel().select(0);
            cmbCargos.getSelectionModel().select(obtenerIndexCargos());
            
        }
    }
    
    public int obtenerIndexCargos(){
        int index = 0;
        for(int i = 0 ; i <= cmbCargos.getItems().size() ; i++){
        String cargosCmb = cmbCargos.getItems().get(i).toString();
        String cargosTbl =((Empleados)tblEmpleados.getSelectionModel().getSelectedItem()).getCargos();
        if(cargosCmb.equals(cargosTbl)){
            index = i;
            break;
        }
            
        }
        return index;   
    }
    
    public ObservableList<Empleados> listarEmpleados(){
        ArrayList<Empleados> empleados = new ArrayList<>();
        
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_listarEmpleados()";
            statement = conexion.prepareStatement(sql);
            resultSet = statement.executeQuery();
            
            while(resultSet.next()){
                int empleadoId = resultSet.getInt("empleadoId");
                String nombreEmpleado = resultSet.getString("nombreEmpleado");
                String apellidoEmpleado = resultSet.getString("apellidoEmpleado");
                Double sueldo = resultSet.getDouble("sueldo");
                String horaEntrada = resultSet.getString("horaEntrada");
                String horaSalida = resultSet.getString("horaSalida");
                String cargos = resultSet.getString("cargo");
                String encargadoId = resultSet.getString("encargado");
                
                empleados.add(new Empleados(empleadoId, nombreEmpleado, apellidoEmpleado, sueldo, horaEntrada, horaSalida, cargos, encargadoId));
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
        return FXCollections.observableList(empleados);
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
                String nombreCargo = resultSet.getString("nombreCargo");
                String descripcionCargo = resultSet.getString("descripcionCargo");
                
                cargos.add(new Cargos(cargoId, nombreCargo, descripcionCargo));
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
    
    public void eliminarEmpleado(int empId){
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_eliminarEmpleados(?)";
            statement = conexion.prepareStatement(sql);
            statement.setInt(1, empId);
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
    
   
    
    public Empleados buscarEmpleado(){
        Empleados empleados = null;
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_buscarEmpleados(?)";
            statement = conexion.prepareStatement(sql);
            statement.setInt(1, Integer.parseInt(tfEmpleadoId.getText()));
            resultSet = statement.executeQuery();
            
            if(resultSet.next()){
                int empleadoId = resultSet.getInt("empleadoId");
                String nombreEmpleado = resultSet.getString("nombreEmpleado");
                String apellidoEmpleado = resultSet.getString("apellidoEmpleado");
                Double sueldo = resultSet.getDouble("sueldo");
                String horaEntrada = resultSet.getString("horaEntrada");
                String horaSalida = resultSet.getString("horaSalida");
                String cargos = resultSet.getString("cargo");
                String encargadoId = resultSet.getString("encargado");
                
                empleados = (new Empleados(empleadoId, nombreEmpleado, apellidoEmpleado, sueldo, horaEntrada, horaSalida, cargos, encargadoId));
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
        return empleados;
    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarrDatos();
        cmbCargos.setItems(listarCargos());
        cmbEncargados.setItems(listarEmpleados());
    }    

    public Main getStage() {
        return stage;
    }

    public void setStage(Main stage) {
        this.stage = stage;
    }
    
    
    
}
