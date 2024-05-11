    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jeremyarevalo.controller;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import org.jeremyarevalo.dao.Conexion;
import org.jeremyarevalo.dto.EmpleadoDTO;
import org.jeremyarevalo.model.Empleados;
import org.jeremyarevalo.system.Main;
import org.jeremyarevalo.utils.SuperKinalAlert;

/**
 * FXML Controller class
 *
 * @author jerem
 */
public class FormEmpleadosController implements Initializable {
    private Main stage;
    private int op;
    
    private static Connection conexion;
    private static PreparedStatement statement;
    
    @FXML
    TextField tfEmpleadoId, tfNomEmpleado, tfApeEmpleado, tfSueldo, tfHoraEntrada, tfHoraSalida, tfCargoId, tfEncargadoId;
    @FXML
    Button btnGuardar5, btnCancelar5;
    @FXML
    public void handleButtonAction(ActionEvent event){
        if(event.getSource() == btnCancelar5){
            stage.menuEmpleadosView();
            EmpleadoDTO.getEmpleadoDTO().setEmpleados(null);
        }else if(event.getSource() == btnGuardar5){
            if(op == 1){
                if(!tfNomEmpleado.getText().equals("") && !tfApeEmpleado.getText().equals("") && !tfSueldo.getText().equals("") && !tfHoraEntrada.getText().equals("") && !tfHoraSalida.getText().equals("")){
                agregarEmpleado();
                SuperKinalAlert.getInstance().mostrarAlertaInfo(401);
                stage.menuEmpleadosView(); 
                }else{
                    SuperKinalAlert.getInstance().mostrarAlertaInfo(400);
                    tfNomEmpleado.requestFocus();
                    return;
                }  
            }else if(op == 2){
                if(!tfNomEmpleado.getText().equals("") && !tfApeEmpleado.getText().equals("") && !tfSueldo.getText().equals("") && !tfHoraEntrada.getText().equals("") && !tfHoraSalida.getText().equals("")){
                    if(SuperKinalAlert.getInstance().mostrarAlertaConfirmacion(106).get() == ButtonType.OK){
                        editarEmpleado();
                        EmpleadoDTO.getEmpleadoDTO().setEmpleados(null);
                        stage.menuEmpleadosView();   
                    }
                }else{
                    SuperKinalAlert.getInstance().mostrarAlertaInfo(400);
                    tfNomEmpleado.requestFocus();
                    return;
                }
                
            }

        }
    }
    
    public void cargarDatos(Empleados empleados){
        tfEmpleadoId.setText(Integer.toString(empleados.getEmpleadoId()));
        tfNomEmpleado.setText(empleados.getNombreEmpleado());
        tfApeEmpleado.setText(empleados.getApellidoEmpleado());
        tfSueldo.setText(Double.toString(empleados.getSueldo()));
        tfHoraEntrada.setText(empleados.getHoraEntrada().toString());
        tfHoraSalida.setText(empleados.getHoraSalida().toString());
        tfCargoId.setText(empleados.getCargos().toString());
        tfEncargadoId.setText(empleados.getEncargado().toString());
        
    }
    private String formatoHora(Time hora) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        return sdf.format(hora);
    
    }
    
    public void agregarEmpleado(){
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_agregarEmpleados(?,?,?,?,?,?,?)";
            statement = conexion.prepareStatement(sql);
            statement.setString(1, tfNomEmpleado.getText());
            statement.setString(2, tfApeEmpleado.getText());
            statement.setDouble(3, Double.valueOf(tfSueldo.getText()));
            statement.setTime(4, Time.valueOf(tfHoraEntrada.getText()));
            statement.setTime(5, Time.valueOf(tfHoraSalida.getText()));
            statement.setString(6, tfCargoId.getText());
            statement.setString(7, tfEncargadoId.getText());
            statement.execute();

        }catch(SQLException e){
            System.out.println(e.getMessage());
            e.printStackTrace();
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
                e.printStackTrace();
            }
        }
    }
    
    public void editarEmpleado(){
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_editarEmpleados(?,?,?,?,?,?,?,?)";
            statement = conexion.prepareStatement(sql);
            statement.setInt(1, Integer.parseInt(tfEmpleadoId.getText()));
            statement.setString(2, tfNomEmpleado.getText());
            statement.setString(3, tfApeEmpleado.getText());
            statement.setDouble(4, Double.valueOf(tfSueldo.getText()));
            statement.setTime(5, Time.valueOf(tfHoraEntrada.getText()));
            statement.setTime(6, Time.valueOf(tfHoraSalida.getText()));
            statement.setString(7, tfCargoId.getText());
            statement.setString(8, tfEncargadoId.getText());
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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if(EmpleadoDTO.getEmpleadoDTO().getEmpleados() != null){
            cargarDatos(EmpleadoDTO.getEmpleadoDTO().getEmpleados());
        }
        
        
    }    

    public Main getStage() {
        return stage;
    }

    public void setStage(Main stage) {
        this.stage = stage;
    }

    public void setOp(int op) {
        this.op = op;
    }
    
    
    
}
