/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jeremyarevalo.controller;

import java.net.URL;
import java.sql.Blob;
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
import org.jeremyarevalo.model.Productos;
import org.jeremyarevalo.model.Promociones;
import org.jeremyarevalo.system.Main;

/**
 * FXML Controller class
 *
 * @author jerem
 */
public class MenuPromocionesController implements Initializable {
    private Main stage;
    
    private static Connection conexion = null;
    private static PreparedStatement statement = null;
    private static ResultSet resultSet = null;
    
    @FXML
    TextField tfPromocionId, tfPrecio, tfInicio, tfFinalizacion;
    
    @FXML
    TextArea taDescripcion;
    
    @FXML
    ComboBox cmbProductos;
    
    @FXML
    TableView tblPromociones;
    
    @FXML
    TableColumn colPromocionId, colPrecio, colDescripcion, colInicio, colFinalizacion, colProducto;
    
    @FXML
    Button btnGuardar, btnEliminar, btnRegresar, btnVaciar;
    
    @FXML
    public void handleButtonAction(ActionEvent event){
        if(event.getSource() == btnRegresar){
            stage.menuPrincipalView();
        }else if(event.getSource() == btnGuardar){
            if(tfPromocionId.getText().equals("")){
                agregarPromociones();
                cargarDatos();
            }else{
                editarPromociones();
            }
        }else if(event.getSource() == btnVaciar){
            vaciarCampos();
        }
    }
    
    public void cargarDatos(){
        tblPromociones.setItems(listarPromociones());
        colPromocionId.setCellValueFactory(new PropertyValueFactory<Promociones, Integer>("promocionId"));
        colPrecio.setCellValueFactory(new PropertyValueFactory<Promociones, Double>("precioPromocion"));
        colDescripcion.setCellValueFactory(new PropertyValueFactory<Promociones, String>("descripcionPromocion"));
        colInicio.setCellValueFactory(new PropertyValueFactory<Promociones, String>("fechaInicio"));
        colFinalizacion.setCellValueFactory(new PropertyValueFactory<Promociones, String>("fechaFinalizacion"));
        colProducto.setCellValueFactory(new PropertyValueFactory<Promociones, String>("producto"));
        tblPromociones.getSortOrder().add(colPromocionId);


    }
    
    public void cargarDatosEditar(){
        Promociones pr = (Promociones)tblPromociones.getSelectionModel().getSelectedItem();
        if(pr != null){
            tfPromocionId.setText(Integer.toString(pr.getPromocionId()));
            tfPrecio.setText(Double.toString(pr.getPrecioPromocion()));
            taDescripcion.setText(pr.getDescripcionPromocion());
            tfInicio.setText(pr.getFechaInicio());
            tfFinalizacion.setText(pr.getFechaFinalizacion());
            cmbProductos.getSelectionModel().select(obtenerIndexProducto());
        }
    }
    
    public int obtenerIndexProducto(){
        int index = 0;
        for(int i = 0 ; i <= cmbProductos.getItems().size() ; i++){
            String productoCmb = cmbProductos.getItems().get(i).toString();
            String productoTbl = ((Promociones)tblPromociones.getSelectionModel().getSelectedItems()).getProducto();
            if(productoCmb.equals(productoTbl)){
                index = i;
                break;
            }
            
        }
        return index;
    }


    
    public ObservableList<Promociones> listarPromociones(){
        ArrayList<Promociones> promociones = new ArrayList<>();
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_listarPromociones()";
            statement = conexion.prepareStatement(sql);
            resultSet = statement.executeQuery();
            
            while(resultSet.next()){
                int promocionId = resultSet.getInt("promocionId");
                double precioPromocion = resultSet.getDouble("precioPromocion");
                String descripcionPromocion = resultSet.getString("descripcionPromocion");
                String fechaInicio = resultSet.getString("fechaInicio");
                String fechaFinalizacion = resultSet.getString("fechaFInalizacion");
                String nombreProducto = resultSet.getString("producto");
                
                promociones.add(new Promociones(promocionId, precioPromocion, descripcionPromocion, fechaInicio, fechaFinalizacion, nombreProducto));
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
                
            }
        }
        
        return FXCollections.observableList(promociones);
    }
    
    
    
    public void agregarPromociones(){
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_agregarPromociones(?, ?, ?, ?, ?)";
            statement = conexion.prepareStatement(sql);
            statement.setDouble(1, Double.parseDouble(tfPrecio.getText()));
            statement.setString(2, taDescripcion.getText());
            statement.setString(3, tfInicio.getText());
            statement.setString(4, tfFinalizacion.getText());
            statement.setInt(5, ((Productos)cmbProductos.getSelectionModel().getSelectedItem()).getProductoId());
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
    
    public void editarPromociones(){
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_editarPromociones(?, ?, ?, ?, ?)";
            statement = conexion.prepareStatement(sql);
            statement.setInt(1, Integer.parseInt(tfPromocionId.getText()));
            statement.setDouble(2, Double.parseDouble(tfPrecio.getText()));
            statement.setString(3, taDescripcion.getText());
            statement.setString(4, tfInicio.getText());
            statement.setString(5, tfFinalizacion.getText());
            statement.setInt(6, 0);
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
    
    public ObservableList<Productos> listarProductos() {
        ArrayList<Productos> productos = new ArrayList<>();
        try {
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_listarProductos()";
            statement = conexion.prepareStatement(sql);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int productoId = resultSet.getInt("productoId");
                String nombreProducto = resultSet.getString("nombreProducto");
                String descripcionProducto = resultSet.getString("descripcionProducto");
                int cantidadStock = resultSet.getInt("cantidadStock");
                double precioVentaUnitario = resultSet.getDouble("precioVentaUnitario");
                double precioVentaMayor = resultSet.getDouble("precioVentaMayor");
                double precioCompra = resultSet.getDouble("precioCompra");
                Blob imagenProducto = resultSet.getBlob("imagenProducto");
                String distribuidor = resultSet.getString("nombreDistribuidor");
                String categoriaProducto = resultSet.getString("nombreCategoria");

                productos.add(new Productos (productoId, nombreProducto, descripcionProducto, cantidadStock, precioVentaUnitario, precioVentaMayor, precioCompra, imagenProducto, distribuidor, categoriaProducto));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (statement != null) {
                    statement.close();
                }
                if (conexion != null) {
                    conexion.close();
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
        return FXCollections.observableList(productos);
    }
    
    public void vaciarCampos(){
        tfPromocionId.clear();
        tfPrecio.clear();
        taDescripcion.clear();
        tfInicio.clear();
        tfFinalizacion.clear();
        cmbProductos.getSelectionModel().clearSelection();
        
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
        cargarDatos();
        cmbProductos.setItems(listarProductos());
        
    }    
    
}
