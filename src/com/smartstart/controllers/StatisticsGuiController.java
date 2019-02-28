/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smartstart.controllers;

import com.smartstart.util.FxChartUtil;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author acmou
 */
public class StatisticsGuiController implements Initializable {
    @FXML
    private PieChart pie;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        pie.setTitle("Population in Year 2000");
        // Place the legend on the left side
        pie.setLegendSide(Side.LEFT);
        // Set the Data for the Chart
        ObservableList<PieChart.Data> chartData = FxChartUtil.getChartData();
        pie.setData(chartData);
        // TODO
    }    

    @FXML
    public void Backtohome(ActionEvent event) throws IOException
    {
        
        Parent tableViewOpportunity=FXMLLoader.load(getClass().getResource("/com/smartstart/gui/AcceuilOpportunities.fxml"));
         Scene tableViewOpportunityScene=new Scene (tableViewOpportunity);
         Stage window=(Stage)((Node)event.getSource()).getScene().getWindow();
         window.setScene(tableViewOpportunityScene);
        
    }
    
}
