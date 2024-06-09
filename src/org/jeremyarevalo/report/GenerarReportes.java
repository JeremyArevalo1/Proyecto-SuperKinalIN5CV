/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jeremyarevalo.report;

import java.io.InputStream;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import org.jeremyarevalo.dao.Conexion;
import win.zqxu.jrviewer.JRViewerFX;

/**
 *
 * @author jerem
 */
public class GenerarReportes {
    private static GenerarReportes instance;
    
    private static Connection conexion = null;

    
    public GenerarReportes() {
    }
    
    
    
    public static GenerarReportes getInstance(){
        if(instance == null){
            instance = new GenerarReportes();
        }
        return instance;
    }
    
    public void generarFactura(int factId){
        try{
            
            //paso 1: abrir la conexion a la DB
            conexion = Conexion.getInstance().obtenerConexion();
            
            //paso 2: defino los parametros del reporte
            Map<String, Object>parametros = new HashMap<>();
            parametros.put("factId", factId);
            
            //paso 3: crear el reporte
            InputStream jasperPath = GenerarReportes.class.getResourceAsStream("/org/jeremyarevalo/report/Factura.jasper");
            JasperPrint reporte = JasperFillManager.fillReport(jasperPath, parametros, conexion);
            
            //paso4: crear la ventana de javaFX para mostrar el reporte
            Stage reportStage = new Stage();
            
            //paso 5: llenar el stage con el reporte
            JRViewerFX reportViewer = new JRViewerFX(reporte);
            
            Pane root = new Pane();
            
            root.getChildren().add(reportViewer);
            
            reportViewer.setPrefSize(800, 600);
            
            Scene scene = new Scene(root);
            reportStage.setScene(scene);
            reportStage.setTitle("Factura");
            reportStage.show();
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    
    public void generarClientes(){
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            Map<String, Object>parametros = new HashMap<>();
            InputStream jasperPath = GenerarReportes.class.getResourceAsStream("/org/jeremyarevalo/report/Clientes.jasper");
            JasperPrint reporte = JasperFillManager.fillReport(jasperPath, parametros, conexion);
            Stage reportStage = new Stage();
            JRViewerFX reportViewer = new JRViewerFX(reporte);
            Pane root = new Pane();
            root.getChildren().add(reportViewer);
            reportViewer.setPrefSize(800, 600);
            
            Scene scene = new Scene(root);
            reportStage.setScene(scene);
            reportStage.setTitle("Clientes");
            reportStage.show();
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    
    public void generarProductos(){
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            Map<String, Object>parametros = new HashMap<>();
            InputStream jasperPath = GenerarReportes.class.getResourceAsStream("/org/jeremyarevalo/report/Productos.jasper");
            JasperPrint reporte = JasperFillManager.fillReport(jasperPath, parametros, conexion);
            Stage reportStage = new Stage();
            JRViewerFX reportViewer = new JRViewerFX(reporte);
            Pane root = new Pane();
            root.getChildren().add(reportViewer);
            reportViewer.setPrefSize(800, 600);
            Scene scene = new Scene(root);
            reportStage.setScene(scene);
            reportStage.setTitle("Productos");
            reportStage.show();
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
}
