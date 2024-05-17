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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import org.jeremyarevalo.dao.Conexion;
import org.jeremyarevalo.model.Empleados;
import org.jeremyarevalo.model.NivelAcceso;
import org.jeremyarevalo.system.Main;
import org.jeremyarevalo.utils.PasswordUtils;

/**
 * FXML Controller class
 *
 * @author jerem
 */
public class MenuRegistroController implements Initializable {
    private Main stage;
    
    private static Connection conexion = null;
    private static PreparedStatement statement = null;
    private static ResultSet resultSet = null;
    
    @FXML
    TextField tfUser, tfPassword;
    @FXML
    Button btnRegistrar;
    @FXML
    ComboBox cmbNivelA, cmbEmpleadoo;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cmbEmpleadoo.setItems(listarEmpleados());
        cmbNivelA.setItems(listarNivelesAcceso());
    }    
    
    @FXML
    public void handleButtonAction(ActionEvent event){
        if(event.getSource() == btnRegistrar){
            agregarUsuario();
        }
    }
    
    public ObservableList<NivelAcceso> listarNivelesAcceso(){
        ArrayList<NivelAcceso> nivelesAcceso = new ArrayList<>();
        
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_listarNivelAcceso()";
            statement = conexion.prepareStatement(sql);
            resultSet = statement.executeQuery();
            
            while(resultSet.next()){
                int nivelAccesoId = resultSet.getInt("nivelAccesoId");
                String nivelAcceso = resultSet.getString("nivelAcceso");
                
                nivelesAcceso.add(new NivelAcceso(nivelAccesoId, nivelAcceso));
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }finally{
            try{
                if(statement != null){
                    statement.close();
                }
                if(resultSet != null){
                    resultSet.close();
                }
                if(conexion != null){
                    conexion.close();
                }
            }catch(SQLException e){
                System.out.println(e.getMessage());
            }
        }
        
        return FXCollections.observableArrayList(nivelesAcceso);
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
    
    public void agregarUsuario(){
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_sp_agregarUsuarios(?,?,?,?)";
            statement = conexion.prepareStatement(sql);
            statement.setString(1, tfUser.getText());
            statement.setString(2, PasswordUtils.getInstance().encrytedPassword(tfPassword.getText()));
            statement.setInt(3, ((NivelAcceso)cmbNivelA.getSelectionModel().getSelectedItem()).getNivelAccesoId());
            statement.setInt(4, ((Empleados)cmbEmpleadoo.getSelectionModel().getSelectedItem()).getEmpleadoId());
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
    
    public void setStage(Main stage) {
        this.stage = stage;
    }
    
    
}
