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
import org.jeremyarevalo.dto.CategoriaProductoDTO;
import org.jeremyarevalo.model.CategoriaProductos;
import org.jeremyarevalo.system.Main;
import org.jeremyarevalo.utils.SuperKinalAlert;

/**
 * FXML Controller class
 *
 * @author jerem
 */
public class MenuCategoriasProductosController implements Initializable {
    private Main stage;
    
    private static Connection conexion = null;
    private static PreparedStatement statement = null;
    private static ResultSet resultSet = null;
    
    @FXML
    TableView tblCategoriaProductos;
    @FXML
    TableColumn colCatProId, colNomCat, colDesCat;
    @FXML
    Button btnAgregar4, btnEditar4, btnRegresar4, btnEliminar4, btnBuscar4;
    @FXML
    TextField tfCatProId;
    
    @FXML
    public void handleButtonAction(ActionEvent event){
        if(event.getSource() == btnRegresar4){
           stage.menuPrincipalView(); 
        }
        else if(event.getSource() == btnAgregar4){
            stage.formCategoriaProductosView(1);
        }
        else if(event.getSource() == btnEditar4){
            CategoriaProductoDTO.getCategoriaProductoDTO().setCategoriaProductos((CategoriaProductos)tblCategoriaProductos.getSelectionModel().getSelectedItem());
            stage.formCategoriaProductosView(2);
        }
        else if(event.getSource() == btnEliminar4){
            if(SuperKinalAlert.getInstance().mostrarAlertaConfirmacion(405).get() == ButtonType.OK){
                int cliId = ((CategoriaProductos)tblCategoriaProductos.getSelectionModel().getSelectedItem()).getCategoriaProductoId();
                eliminarCategoriaProductos(cliId);
                cargaarListaa();
            }    
        }
        else if(event.getSource() == btnBuscar4){
            tblCategoriaProductos.getItems().clear();
            if(tfCatProId.getText().equals("")){
                cargaarListaa();
            }else{
                tblCategoriaProductos.getItems().add(buscarCategoriaProductos());
                colCatProId.setCellValueFactory(new PropertyValueFactory<CategoriaProductos, Integer>("categoriaProductoId"));
                colNomCat.setCellValueFactory(new PropertyValueFactory<CategoriaProductos, String>("nombreCategoria"));
                colDesCat.setCellValueFactory(new PropertyValueFactory<CategoriaProductos, String>("descripcionCategoria"));
            }
        }
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargaarListaa();
    }    
    
    public void cargaarListaa(){
        tblCategoriaProductos.setItems(listarCategoriaProductos());
        colCatProId.setCellValueFactory(new PropertyValueFactory<CategoriaProductos, Integer>("categoriaProductoId"));
        colNomCat.setCellValueFactory(new PropertyValueFactory<CategoriaProductos, String>("nombreCategoria"));
        colDesCat.setCellValueFactory(new PropertyValueFactory<CategoriaProductos, String>("descripcionCategoria"));
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
    
    public void eliminarCategoriaProductos(int catProId){
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_eliminarCategoriaProductos(?)";
            statement = conexion.prepareStatement(sql);
            statement.setInt(1, catProId);
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
    
    public CategoriaProductos buscarCategoriaProductos(){
        CategoriaProductos categoriaProductos = null;
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_buscarCategoriaProductos(?)";
            statement = conexion.prepareStatement(sql);
            statement.setInt(1, Integer.parseInt(tfCatProId.getText()));
            resultSet = statement.executeQuery();
            
            if(resultSet.next()){
                int categoriaProductoId = resultSet.getInt("categoriaProductoId");
                String nombreCategoria = resultSet.getString("nombreCategoria");
                String descripcionCategoria = resultSet.getString("descripcionCategoria");
                
                categoriaProductos = (new CategoriaProductos(categoriaProductoId, nombreCategoria, descripcionCategoria));
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
        return categoriaProductos;
    }

    public Main getStage() {
        return stage;
    }

    public void setStage(Main stage) {
        this.stage = stage;
    }
    
    
}
