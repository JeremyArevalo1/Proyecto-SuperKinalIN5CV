/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jeremyarevalo.controller;

import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.jeremyarevalo.dao.Conexion;
import org.jeremyarevalo.model.Compras;
import org.jeremyarevalo.system.Main;

/**
 * FXML Controller class
 *
 * @author jerem
 */
public class MenuComprasController implements Initializable {

    private Main stage;
    
    private static Connection conexion = null;
    private static PreparedStatement statement = null;
    private static ResultSet resultset = null;
    
    @FXML
    TableView tblCompras;
    
    @FXML
    TableColumn colCompraId, colFechaCompra, colTotalCompra;
    
    @FXML
    TextField tfCompraId, tfFechaCompra, tfTotalCompra, tfBuscarId;
    
    @FXML
    Button btnGuardar, btnEliminar, btnRegresar, btnVaciar, btnBuscar;
    
    public ObservableList<Compras> listarCompra(){
        ArrayList<Compras> compras = new ArrayList<>();
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_listarCompras()";
            statement = conexion.prepareStatement(sql);
            resultset = statement.executeQuery();
            
            while(resultset.next()){
                int compraId = resultset.getInt("compraId");
                Date fecha = resultset.getDate("fechaCompra");
                double total = resultset.getDouble("totalCompra");
                
                compras.add(new Compras(compraId, fecha.toLocalDate(), total));
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }finally{
            try{
                if(resultset != null){
                    resultset.close();
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
        return FXCollections.observableList(compras);
    }
    
    public void cargarLista(){
        tblCompras.setItems(listarCompra());
        colCompraId.setCellValueFactory(new PropertyValueFactory<Compras, Integer>("compraId"));
        colFechaCompra.setCellValueFactory(new PropertyValueFactory<Compras, Date>("fechaCompra"));
        colTotalCompra.setCellValueFactory(new PropertyValueFactory<Compras, Double>("totalCompra"));
    }
    
    public void eliminarCompras(int comId){
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_eliminarCompras(?)";
            statement = conexion.prepareStatement(sql);
            statement.setInt(1, comId);
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
    
    public void cargarDatosEditar(){
        Compras cr = (Compras)tblCompras.getSelectionModel().getSelectedItem();
        if(cr != null){
            tfCompraId.setText(Integer.toString(cr.getCompraId()));
            tfFechaCompra.setText(LocalDate.now().toString());
            tfTotalCompra.setText(Double.toString(cr.getTotalCompra()));
        }
    }
    
    public Compras buscarCompra(){
        Compras compra = null;
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_buscarCompras(?)";
            statement = conexion.prepareStatement(sql);
            statement.setInt(1 ,Integer.parseInt(tfBuscarId.getText()));
            resultset = statement.executeQuery();
            
            if(resultset.next()){
                int compraId = resultset.getInt("compraId");
                Date fecha = resultset.getDate("fechaCompra");
                Double total = resultset.getDouble("totalCompra");
                
                compra = (new Compras(compraId, fecha.toLocalDate(), total));
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }finally{
            try{
                if(resultset != null){
                    resultset.close();
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
        
        return compra;
    }
     
    
    public void agregarCompra(){
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_agregarCompras(?, ?)";
            statement = conexion.prepareStatement(sql);
            statement.setString(1, tfFechaCompra.getText());
            statement.setDouble(2, Double.parseDouble(tfTotalCompra.getText()));
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
    
    
    public void editarCompra(){
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_editarCompras(?, ?, ?)";
            statement = conexion.prepareStatement(sql);
            statement.setInt(1, Integer.parseInt(tfCompraId.getText()));
            statement.setDate(2, Date.valueOf(tfFechaCompra.getText()));
            statement.setDouble(3, Double.parseDouble(tfTotalCompra.getText()));
            statement.execute();
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }finally{
            try{
                if(conexion != null){
                conexion.close();
                }
                if(statement != null){
                    statement.close();
                }
            }catch(SQLException e){
                System.out.println(e.getMessage());
            }
        }
    }
    
    public void vaciarCampos(){
        tfCompraId.clear();
        tfFechaCompra.clear();
        tfTotalCompra.clear();
    }
    
    @FXML
    public void handleButtonAction(ActionEvent event){
        if(event.getSource() == btnRegresar){
            stage.menuPrincipalView();
        }else if(event.getSource() == btnGuardar){
            if(tfCompraId.getText().equals("")){
                agregarCompra();
                cargarLista();
            }else{
                editarCompra();
            }
        }else if(event.getSource() == btnVaciar){
            vaciarCampos();
        }else if(event.getSource() == btnEliminar){
            int comId = ((Compras)tblCompras.getSelectionModel().getSelectedItem()).getCompraId();
            eliminarCompras(comId);
            cargarLista();
        }else if (event.getSource() == btnBuscar){
            tblCompras.getItems().clear();
            if(tfCompraId.getText().equals("")){
                cargarLista();
            }else{
                tblCompras.getItems().add(buscarCompra());
                colCompraId.setCellValueFactory(new PropertyValueFactory<Compras, Integer>("compraId"));
                colFechaCompra.setCellValueFactory(new PropertyValueFactory<Compras, Date>("fechaCompra"));
                colTotalCompra.setCellValueFactory(new PropertyValueFactory<Compras, Double>("totalCompra"));
            }
        }
            
    }       

    public Main getStage() {
        return stage;
    }

    public void setStage(Main stage) {
        this.stage = stage;
    }
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarLista();
        tfFechaCompra.setText(LocalDate.now().toString());
    }   
    
    
    
}
