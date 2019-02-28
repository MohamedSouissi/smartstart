/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smartstart.services;

import com.smartstart.entities.Application;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.smartstart.entities.Opportunity;
import com.smartstart.entities.Skill;
import com.smartstart.util.ConnectionDb;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Marrr
 */
public class SkillService {
     public ConnectionDb cnx = ConnectionDb.getInstance();
     Connection connection=cnx.getCnx();
     public SkillService(){}
     
    private ObservableList<Skill> ListeSkills;
    Skill s=new Skill();
    
 public void addNeededSkill (int connectedUserId,int selectedSkillId){ //na3tiwha el id mta3 el skill eli bech najoutiwha lel freelancer
	
     
     PreparedStatement psx=null;
	ResultSet s=null;
	
	try {
		String queryx="INSERT INTO `needed_skills`(`id_opp`,`id_skill`) VALUES (?,?)";
		psx=connection.prepareStatement(queryx);
		psx.setInt(1,connectedUserId);
                psx.setInt(2, selectedSkillId);
	        System.out.println(psx);
		psx.executeUpdate();
                
	} catch (Exception ex) {
		System.out.println(ex);
	}
                    
                    
                                  
       
     
     
     
     
   
	
	}
 
    public ObservableList<Skill> Display_Skills() {

        PreparedStatement ps = null;
        ResultSet rs = null;
        ListeSkills = FXCollections.observableArrayList();
        try {
            String query = "select * from skills";
            ps = connection.prepareStatement(query);
            //ps.setString(1, sl_no);
            System.out.println(ps);
            rs = ps.executeQuery();
            while (rs.next()) {

                /* o.setId(rs.getInt(1));
                 o.setJob_title(rs.getString(2));
                    
                    
                 o.setJob_category(rs.getString(3));
                 o.setJob_description(rs.getString(4));
                 o.setBudget(rs.getFloat(5));
                 o.setJob_draft(rs.getInt(6));
                 o.setJob_Duration(rs.getTimestamp(7));
                 o.setExpiry_date(rs.getDate(8));
                 o.setAdded_date(rs.getDate(9));
                 o.setIdEntreprise(rs.getInt(10));
                 System.out.println("bij");*/
                ListeSkills.add(new Skill(rs.getInt(1), rs.getString(2)));

            }
        } catch (Exception e) {
            System.out.println(e);
        }
        ListeSkills.forEach(e -> System.out.println(e));
        return ListeSkills;

    }
     public Skill findSkill (String selectedSkillId) throws SQLException{ //na3tiwha el id mta3 el skill eli bech najoutiwha lel freelancer
	
     
     PreparedStatement ps=null;
	ResultSet rs=null;

		String query="select * from skills where name_skill='"+selectedSkillId+"'";
		ps=connection.prepareStatement(query);
		
		System.out.println(ps);
		rs=ps.executeQuery();
		while(rs.next()){
                    return new Skill(rs.getInt(1),rs.getString(2));
                   
	
     
   
	
	}
         return s;
     }




 
 
 
}
