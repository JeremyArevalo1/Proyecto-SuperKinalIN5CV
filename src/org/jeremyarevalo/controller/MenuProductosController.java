/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jeremyarevalo.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.TransferMode;
import org.jeremyarevalo.dao.Conexion;
import org.jeremyarevalo.model.CategoriaProductos;
import org.jeremyarevalo.model.Distribuidores;
import org.jeremyarevalo.model.Producto;
import org.jeremyarevalo.report.GenerarReportes;
import org.jeremyarevalo.system.Main;

/**
 * FXML Controller class
 *
 * @author jerem
 */
public class MenuProductosController implements Initializable {
    private Main stage;
    
    private static Connection conexion = null;
    private static PreparedStatement statement = null;
    private static ResultSet resultSet = null;
    
    private List<File> files = null;
    
    @FXML
    Button btnGuardar7, btnRegresar7, btnVaciar7, btnBuscar7, btnVerProductos;
    @FXML
    TextField tfProductoId, tfNomProducto, tfCanStock, tfPrecioVenUnitario, tfPrecioVenMayor, tfPrecioCompra, tfBuscar1;
    @FXML
    TextArea taDesProducto;
    @FXML
    TableView tblProductos;
    @FXML
    TableColumn colProId, colNomPro, colDesPro, colCanStock, colPreVenU, colPreVenM, colPreCompra, colDistribuidorId, colCatProductoId;
    @FXML
    ComboBox cmbDistribuidorId, cmbCatProducto;
    @FXML
    ImageView imgMostrar, imgCargar;
    @FXML
    Label lblNombreProducto;
    
    
    @FXML
    public void handleButtonAction(ActionEvent event){
        try{
            if(event.getSource() == btnRegresar7){
            stage.menuPrincipalView();
        }else if(event.getSource() == btnGuardar7){
            if(tfProductoId.getText().equals("")){
                agregarProducto();
                cargarDatossss();
            }else{
                editarProducto();
                cargarDatossss();
            }
        }else if(event.getSource() == btnVaciar7){
            vaciarCampossss();
        }else if(event.getSource() == btnBuscar7){
            Producto producto = buscarProductos();
            if(producto != null){
                tblProductos.getItems().clear();
                lblNombreProducto.setText(producto.getNombreProducto());
                InputStream file = producto.getImagenProducto().getBinaryStream();
                Image image = new Image(file);
                imgMostrar.setImage(image);
                tblProductos.getItems().add(buscarProductos());
                colProId.setCellValueFactory(new PropertyValueFactory<Producto, Integer>("productoId"));
                colNomPro.setCellValueFactory(new PropertyValueFactory<Producto, String>("nombreProducto"));
                colDesPro.setCellValueFactory(new PropertyValueFactory<Producto, String>("descripcionProducto"));
                colCanStock.setCellValueFactory(new PropertyValueFactory<Producto, Integer>("cantidadStock"));
                colPreVenU.setCellValueFactory(new PropertyValueFactory<Producto, Double>("precioVentaUnitario"));
                colPreVenM.setCellValueFactory(new PropertyValueFactory<Producto, Double>("precioVentaMayor"));
                colPreCompra.setCellValueFactory(new PropertyValueFactory<Producto, Double>("precioCompra"));
                colDistribuidorId.setCellValueFactory(new PropertyValueFactory<Producto, String>("nombreDistribuidor"));
                colCatProductoId.setCellValueFactory(new PropertyValueFactory<Producto, String>("nombreCategoria"));
            }
        }else if(event.getSource() == btnVerProductos){
            GenerarReportes.getInstance().generarProductos();
        }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarDatossss();
        cmbDistribuidorId.setItems(listarDistribuidores());
        cmbCatProducto.setItems(listarCategoriaProductos());
    }    
    
    @FXML
    public void handleOnDrag(DragEvent event){
        if(event.getDragboard().hasFiles()){
            event.acceptTransferModes(TransferMode.ANY);
        }
    }
    
    @FXML
    public void handleOnDrop(DragEvent event){
        try{
            files = event.getDragboard().getFiles();
            FileInputStream file = new FileInputStream(files.get(0));
            Image image = new Image(file);
            imgCargar.setImage(image);
        }catch(Exception e){
            e.printStackTrace();
        }
        
        
    }
    
    public void vaciarCampossss(){
        tfProductoId.clear();
        tfNomProducto.clear();
        tfCanStock.clear();
        tfPrecioVenUnitario.clear();
        tfPrecioVenMayor.clear();
        tfPrecioCompra.clear();
        tfBuscar1.clear();
        cmbDistribuidorId.getSelectionModel().clearSelection();
        cmbCatProducto.getSelectionModel().clearSelection();
    }
    
    public void cargarDatossss(){
        tblProductos.setItems(listarProductos());
        colProId.setCellValueFactory(new PropertyValueFactory<Producto, Integer>("productoId"));
        colNomPro.setCellValueFactory(new PropertyValueFactory<Producto, String>("nombreProducto"));
        colDesPro.setCellValueFactory(new PropertyValueFactory<Producto, String>("descripcionProducto"));
        colCanStock.setCellValueFactory(new PropertyValueFactory<Producto, Integer>("cantidadStock"));
        colPreVenU.setCellValueFactory(new PropertyValueFactory<Producto, Double>("precioVentaUnitario"));
        colPreVenM.setCellValueFactory(new PropertyValueFactory<Producto, Double>("precioVentaMayor"));
        colPreCompra.setCellValueFactory(new PropertyValueFactory<Producto, Double>("precioCompra"));
        colDistribuidorId.setCellValueFactory(new PropertyValueFactory<Producto, String>("nombreDistribuidor"));
        colCatProductoId.setCellValueFactory(new PropertyValueFactory<Producto, String>("nombreCategoria"));
    }
    
    public int obtenerIndexDistribuidores(){
        int index = 0;
        for(int i = 0 ; i <= cmbDistribuidorId.getItems().size() ; i++){
        String distribuidorCmb = cmbDistribuidorId.getItems().get(i).toString();
        String productosTbl =((Producto)tblProductos.getSelectionModel().getSelectedItem()).getNombreDistribuidor();
        if(distribuidorCmb.equals(productosTbl)){
            index = i;
            break;
        }
            
        }
        return index;
    }
    
    public int obtenerIndexCatProductos(){
        int index = 0;
        for(int i = 0 ; i <= cmbCatProducto.getItems().size() ; i++){
        String catProductosCmb = cmbCatProducto.getItems().get(i).toString();
        String catProductosTbl =((CategoriaProductos)tblProductos.getSelectionModel().getSelectedItem()).getNombreCategoria();
        if(catProductosCmb.equals(catProductosTbl)){
            index = i;
            break;
        }
            
        }
        return index;
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
    
    public ObservableList<CategoriaProductos> listarCategoriaProductos(){
        ArrayList<CategoriaProductos> categoriaProductos = new ArrayList<>();
        
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_listarCategoriaProductos()";
            statement = conexion.prepareStatement(sql);
            resultSet = statement.executeQuery();
            
            while(resultSet.next()){
                int categoriaProductoId = resultSet.getInt("categoriaProductoId");
                String nombreCategoria = resultSet.getString("nombreCategoria");
                String descripcionCategora = resultSet.getString("descripcionCategoria");
                
                categoriaProductos.add(new CategoriaProductos(categoriaProductoId, nombreCategoria, descripcionCategora));
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
        return FXCollections.observableList(categoriaProductos);
    }
    
    public ObservableList<Producto> listarProductos() {
        ArrayList<Producto> productos = new ArrayList<>();
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
                String distribuidor = resultSet.getString("Distribuidor");
                String categoriaProducto = resultSet.getString("Categoria");

                productos.add(new Producto (productoId, nombreProducto, descripcionProducto, cantidadStock, precioVentaUnitario, precioVentaMayor, precioCompra, imagenProducto, distribuidor, categoriaProducto));
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
    
    public void agregarProducto(){
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_agregarProductos(?,?,?,?,?,?,?,?,?)";
            statement = conexion.prepareStatement(sql);
            statement.setString(1, tfNomProducto.getText());
            statement.setString(2, taDesProducto.getText());
            statement.setInt(3, Integer.parseInt(tfCanStock.getText()));
            statement.setDouble(4, Double.valueOf(tfPrecioVenUnitario.getText()));
            statement.setDouble(5, Double.valueOf(tfPrecioVenMayor.getText()));
            statement.setDouble(6, Double.valueOf(tfPrecioCompra.getText()));
            InputStream img = new FileInputStream(files.get(0));
            statement.setBinaryStream(7, img);
            statement.setInt(8, ((Distribuidores)cmbDistribuidorId.getSelectionModel().getSelectedItem()).getDistribuidorId());
            statement.setInt(9, ((CategoriaProductos)cmbCatProducto.getSelectionModel().getSelectedItem()).getCategoriaProductoId());
            statement.execute();
        }catch(Exception e){
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
    
    public void editarProducto(){
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_editarProductos(?,?,?,?,?,?,?,?,?,?)";
            statement = conexion.prepareStatement(sql);
            statement.setInt(1, Integer.parseInt(tfProductoId.getText()));
            statement.setString(2, tfNomProducto.getText());
            statement.setString(3, taDesProducto.getText());
            statement.setInt(4, Integer.parseInt(tfCanStock.getText()));
            statement.setDouble(5, Double.valueOf(tfPrecioVenUnitario.getText()));
            statement.setDouble(6, Double.valueOf(tfPrecioVenMayor.getText()));
            statement.setDouble(7, Double.valueOf(tfPrecioCompra.getText()));
            InputStream img = new FileInputStream(files.get(0));
            statement.setBinaryStream(8, img);
            statement.setInt(9, ((Distribuidores)cmbDistribuidorId.getSelectionModel().getSelectedItem()).getDistribuidorId());
            statement.setInt(10, ((CategoriaProductos)cmbCatProducto.getSelectionModel().getSelectedItem()).getCategoriaProductoId());
            statement.execute();
        }catch(Exception e){
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
    
    public Producto buscarProductos(){
        Producto productos = null;
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_buscarProductos(?)";
            statement = conexion.prepareStatement(sql);
            statement.setInt(1, Integer.parseInt(tfBuscar1.getText()));
            resultSet = statement.executeQuery();
            
            if(resultSet.next()){
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
                
                productos = (new Producto(productoId, nombreProducto, descripcionProducto, cantidadStock, precioVentaUnitario, precioVentaMayor, precioCompra, imagenProducto, distribuidor, categoriaProducto));
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
        return productos;
    }

    public Main getStage() {
        return stage;
    }

    public void setStage(Main stage) {
        this.stage = stage;
    }
    
    
    
    
    
}
