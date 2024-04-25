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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.jeremyarevalo.dao.Conexion;
import org.jeremyarevalo.model.Cliente;
import org.jeremyarevalo.model.TicketSoporte;
import org.jeremyarevalo.system.Main;

/**
 * FXML Controller class
 *
 * @author jerem
 */
public class MenuTicketSoporteController implements Initializable {
    private Main stage;
    
    private static Connection conexion = null;
    private static PreparedStatement statement = null;
    private static ResultSet resultSet = null;

    /**
     * Initializes the controller class.
     */
    @FXML
    ComboBox cmbEstatus, cmbCliente;
    @FXML
    Button btnCancelar2;
    @FXML
    TableView tblTickets;
    @FXML
    TableColumn colTicketId, colDescripcion, colEstatus, colCliente, colFactura;
    
    @FXML
    public void handleButtonAction(ActionEvent event){
        if(event.getSource() == btnCancelar2){
            stage.menuPrincipalView();
        }
    }
    
    public void vaciarCampos(){
        tfTicketId.clear();
        taDescripcion.clear();
        cmbEstatus.getSelectionModel().
    }
    
    public void cargarDatos(){
        tblTickets.setItems(listarTickets());
        colTicketId.setCellValueFactory
    }
    
    public void cargarDatosEditar(){
        TicketSoporte ts = (TicketSoporte)tblTickets.getSelectionModel().getSelectedItem();
        if(ts != null){
            tfTicketId.setText(Integer.toString(ts.getTicketSoporteId()));
            taDescripcion.setText(ts.getDescripcionTicket());
            cmbEstatus.getSelectionModel().select(0);
            cmbCliente.getSelectionModel().select(obtenerIndexCliente());
            
        }
    }
    
    public int obtenerIndexCliente(){
        int index = 0;
        forint(int 1 = 0 ; <= cmbCliente.getItems().size() ; i++){
        String clienteCmb = cmbCliente.getItems().select(0);
            
        }
    }
    
    public void cargarCmdEstatus(){
        cmbEstatus.getItems().add("En Proceso");
        cmbEstatus.getItems().add("Finalizado");
    }
    
    public ObservableList<TicketSoporte> listarTickets(){
        ArrayList<TicketSoporte> ticketSoporte = new ArrayList<>();
        
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_listarTicketSoportes()";
            statement = conexion.prepareStatement(sql);
            resultSet = statement.executeQuery();
            
            while)(){
            int ticketSoporteId = resultSet.getInt("ticketSoporteId()";
            String 
            }
            
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }finally{
            
        }
        return FXCollections.observableList(ticketSoporte);
    }
    
    public ObservableList<Cliente> listarClientes(){
        ArrayList<Cliente> clientes = new ArrayList<>();
        
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_listarClientes()";
            statement = conexion.prepareStatement(sql);
            resultSet = statement.executeQuery();
            
            while(resultSet.next()){
                int clienteId = resultSet.getInt("clienteId");
                String nombre = resultSet.getString("nombre");
                String apellido = resultSet.getString("apellido");
                String telefono = resultSet.getString("telefono");
                String direccion = resultSet.getString("direccion");
                String nit = resultSet.getString("nit");
                
                clientes.add(new Cliente(clienteId, nombre, apellido, telefono, direccion, nit));
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
        return FXCollections.observableList(clientes);
    }
    
    public void agregarTicket(){
        try{
            
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }finally{
            
        }
        
    }
    
    public void editarTicket(){
        try{
            
        }catch(){
            
        }finally{
            
        }
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // TODO
        cargarCmdEstatus();
        cmbCliente.setItems(listarClientes());
    }

    public Main getStage() {
        return stage;
    }

    public void setStage(Main stage) {
        this.stage = stage;
    }
    
    
    
}
