/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smartstart.util;

import com.smartstart.services.OpportunityService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
 
public class FxChartUtil 
{
    static OpportunityService s=new OpportunityService();
    static int f,e,o,a;
    // Create PieChart Data
    public static ObservableList<PieChart.Data> getChartData() 
    { a=s.CountALLApplication();
    f=s.CountALLFreelancer();
    o=s.CountALLOpportunties();
    e=s.CountALLEntreprises();
        ObservableList<PieChart.Data> data = FXCollections. observableArrayList();
        data.add(new PieChart.Data("Freelancer",f ));
        data.add(new PieChart.Data("Entreprise",e));
        data.add(new PieChart.Data("Opportunities",o));
        data.add(new PieChart.Data("Applications",a));
       
        return data;
    }
     
   
}