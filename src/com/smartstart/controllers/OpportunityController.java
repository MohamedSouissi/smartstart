/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smartstart.controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.sql.Date;
import com.smartstart.entities.Opportunity;
import com.smartstart.services.OpportunityService;

/**
 *
 * @author acmou
 */
public class OpportunityController {
     public static void main(String[] args) throws ParseException {
         OpportunityService p1=new OpportunityService();
       p1.Display_Opportunity();
       LocalDate today = LocalDate.now();
       SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
       
       java.util.Date utilDate = new java.util.Date();
 java.sql.Date date = new java.sql.Date(utilDate.getTime());

    
       Opportunity O=new Opportunity(1,"AddedJob","Mobile","android studio",400,0,date,date,date,1);
       Opportunity O1=new Opportunity(1,"fathi","larou","larou",400,0,date,date,date,1);
      // p1.create_Opportunity(O);
       p1.delete_opporunity(5);
       p1.update_opportunity(O1,2);
        
    }
    
}
