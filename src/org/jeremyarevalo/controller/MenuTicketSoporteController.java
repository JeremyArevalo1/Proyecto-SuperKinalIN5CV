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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
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
    Button btnCancelar2, btnGuardar2;
    @FXML
    TableView tblTickets;
    @FXML
    TableColumn colTicketId, colDescripcion, colEstatus, colCliente, colFactura;
    @FXML
    TextArea taDescripcion;
    @FXML
    TextField tfTicketId;
    
    @FXML
    public void handleButtonAction(ActionEvent event){
        if(event.getSource() == btnCancelar2){
            stage.menuPrincipalView();
        }else if(event.getSource() == btnGuardar2){
            if(tfTicketId.getText().equals("")){
                agregarTicket();
                cargarDatos();
            }else{
                agregarTicket();
                cargarDatos();
            }
        }
    }
    
    public void vaciarCampos(){
        tfTicketId.clear();
        taDescripcion.clear();
        cmbEstatus.getSelectionModel();
    }
    
    public void cargarDatos(){
        tblTickets.setItems(listarTickets());
        colTicketId.setCellValueFactory(new PropertyValueFactory<TicketSoporte, Integer>("ticketSoporteId"));
        colDescripcion.setCellValueFactory(new PropertyValueFactory<TicketSoporte, String>("descripcionTicket"));
        colEstatus.setCellValueFactory(new PropertyValueFactory<TicketSoporte, Integer>("estatus"));
        colCliente.setCellValueFactory(new PropertyValueFactory<TicketSoporte, Integer>("cliente"));
        colFactura.setCellValueFactory(new PropertyValueFactory<TicketSoporte, Integer>("facturaId"));
        tblTickets.getSortOrder().add(colTicketId);
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
        for(int i = 0 ; i <= cmbCliente.getItems().size() ; i++){
        String clienteCmb = cmbCliente.getItems().get(i).toString();
        String clienteTbl =((TicketSoporte)tblTickets.getSelectionModel().getSelectedItem()).getCliente();
        if(clienteCmb.equals(clienteTbl)){
            index = i;
            break;
        }
            
        }
        return index;
    }
    
    public void cargarCmdEstatus(){
        cmbEstatus.getItems().add("En Proceso");
        cmbEstatus.getItems().add("Finalizado");
    }
    
    public ObservableList<TicketSoporte> listarTickets(){
        ArrayList<TicketSoporte> tickets = new ArrayList<>();
        
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_listarTicketSoportes()";
            statement = conexion.prepareStatement(sql);
            resultSet = statement.executeQuery();
            
            while(resultSet.next()){
                int ticketSoporteId = resultSet.getInt("ticketSoporteId");
                String descripcion = resultSet.getString("descripcionTicket");
                String estatus = resultSet.getString("estatus");
                String cliente = resultSet.getString("cliente");
                int facturaId = resultSet.getInt("facturaId");
                
                tickets.add(new TicketSoporte(ticketSoporteId, descripcion, estatus, cliente, facturaId));
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
        return FXCollections.observableList(tickets);
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
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_agregarTicketSoportes(?,?,?)";
            statement = conexion.prepareStatement(sql);
            statement.setString(1, taDescripcion.getText());
            statement.setInt(2, ((Cliente)cmbCliente.getSelectionModel().getSelectedItem()).getClienteId());
            statement.setInt(3, 1);
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
    
    public void editarTicket(){
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_editarTicketSoportes(?,?,?,?,?)";
            statement = conexion.prepareStatement(sql);
            statement.setInt(1, Integer.parseInt(tfTicketId.getText()));
            statement.setString(2, taDescripcion.getText());
            statement.setString(3, cmbEstatus.getSelectionModel().getSelectedItem().toString());
            statement.setInt(4, ((Cliente)cmbCliente.getSelectionModel().getSelectedItem()).getClienteId());
            statement.setInt(5, 1);
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
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // TODO
        cargarCmdEstatus();
        cmbCliente.setItems(listarClientes());
        cargarDatos();
    }

    public Main getStage() {
        return stage;
    }

    public void setStage(Main stage) {
        this.stage = stage;
    }
    
    
    
}
