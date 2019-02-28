/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smartstart.services;

import com.smartstart.entities.Application;
import com.smartstart.entities.Feedback;
import com.smartstart.entities.fos_user;
import com.smartstart.util.ConnectionDb;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author dytcha
 */
public class FeedbackServiceImpl  implements FeedbackServiceInterface{

   @Override
    public void addFeedback(Feedback c) throws SQLException {
        ConnectionDb db = ConnectionDb.getInstance();
        Connection cn = db.getCnx();
        String query = "INSERT INTO `feedback`(id_user,Rating,comment,added_date,id_application) VALUES (?,?,?,?,?)";
        PreparedStatement st = cn.prepareStatement(query);
        st.setInt(1, c.getUser().getId());
        st.setDouble(2,c.getRating());
        st.setString(3,c.getComment());

        java.sql.Date dateS = new java.sql.Date(c.getAdded_date().getTime());
        st.setDate(4,dateS);
        st.setInt(5,c.getApplication().getId());

        st.executeUpdate();
    }   
    


    @Override
    public ObservableList<Feedback> listerFeedback(int id) throws SQLException {


                ConnectionDb db = ConnectionDb.getInstance();
                Connection cn = db.getCnx();
                String query = "SELECT * FROM `feedback`,`fos_user`,`application`,`opportunity` WHERE ((feedback.id_user=fos_user.id) AND (feedback.id_application=application.id_application) AND (application.id_opportunity=opportunity.id_opp) AND (feedback.id_user = "+id+"))";
		Statement st  = cn.createStatement();
                ResultSet rs = st.executeQuery(query);
                List<Feedback> lf = new ArrayList<Feedback>();
                while(rs.next()){
                     Feedback f = new Feedback();

                    fos_user u =new fos_user();
                            u.setId(rs.getInt("fos_user.id"));
                            u.setUsername(rs.getString("fos_user.username"));
                            f.setUser(u);
                    f.setId_feedback(rs.getInt("id_feedback"));
                    f.setRating(rs.getFloat("Rating"));                    
                    f.setComment(rs.getString("comment"));
                    f.setAdded_date(rs.getDate("added_date"));
                    
                    Application a=new Application();
                   
                    a.getOpportunity().setJob_title(rs.getString("opportunity.job_title"));
                    a.setId(rs.getInt("application.id_application"));
                    a.getOpportunity().setJob_description("job_description");
                    f.setApplication(a);
                    
                    lf.add(f);
                }
                ObservableList lf_final = FXCollections.observableArrayList(lf);
                return lf_final;
    }
    
        @Override
    public ObservableList<Feedback> listerFeedbackByApp(int id) throws SQLException {


                ConnectionDb db = ConnectionDb.getInstance();
                Connection cn = db.getCnx();
                String query = "SELECT * FROM `feedback`,`fos_user`,`application`,`opportunity` WHERE ((feedback.id_user=fos_user.id) AND (feedback.id_application=application.id_application) AND (application.id_opportunity=opportunity.id_opp) AND (feedback.id_application = "+id+"))";
		Statement st  = cn.createStatement();
                ResultSet rs = st.executeQuery(query);
                List<Feedback> lf = new ArrayList<Feedback>();
                while(rs.next()){
                     Feedback f = new Feedback();

                    fos_user u =new fos_user();
                            u.setId(rs.getInt("fos_user.id"));
                            u.setUsername(rs.getString("fos_user.username"));
                            f.setUser(u);
                    f.setId_feedback(rs.getInt("id_feedback"));
                    f.setRating(rs.getFloat("Rating"));                    
                    f.setComment(rs.getString("comment"));
                    f.setAdded_date(rs.getDate("added_date"));
                    
                    Application a=new Application();
                   
                    a.getOpportunity().setJob_title(rs.getString("opportunity.job_title"));
                    a.setId(rs.getInt("application.id_application"));
                    f.setApplication(a);
                    
                    lf.add(f);
                }
                ObservableList lf_final = FXCollections.observableArrayList(lf);
                return lf_final;
    }

    
    
    

    @Override
    public void removeFeedback(int id) throws SQLException {

                ConnectionDb db = ConnectionDb.getInstance();
                Connection cn = db.getCnx();
                String query = "DELETE FROM `feedback` WHERE `id_feedback` = "+id;
		PreparedStatement st  = cn.prepareStatement(query);  
                    st.execute();


    }

    @Override
    public void updateFeedback(Feedback f) throws SQLException {

                ConnectionDb db = ConnectionDb.getInstance();
                Connection cn = db.getCnx();
                String query = "UPDATE `feedback` SET `Rating`=?,`comment`=?,`added_date`=? WHERE `id_feedback` =? ";
                       
		PreparedStatement st  = cn.prepareStatement(query);
                st.setFloat(1, f.getRating());
                st.setString(2, f.getComment());
                java.sql.Date d1 = new java.sql.Date((new java.util.Date()).getTime());
                st.setDate(3,d1);
                st.setInt(4, f.getId_feedback()); 
                st.execute();

        

    }
    
    
    @Override
    public int CountFeedbacks(int id_user) {
        int toretrun = 0;
        PreparedStatement ps = null;
        try {
            ConnectionDb db = ConnectionDb.getInstance();
            Connection cn = db.getCnx();
            String query = "SELECT count(*) FROM feedback WHERE `id_user` = "+id_user;
            ps = cn.prepareStatement(query);
            System.out.println(ps);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                toretrun = rs.getInt(1);
            }

        } catch (Exception e) {
            System.out.println(e);
        }
        return toretrun;
    }
    
    
    public boolean Test(int u ,int a) throws SQLException{
     Feedback f=new Feedback();
      ConnectionDb db = ConnectionDb.getInstance();
                Connection cn = db.getCnx();
                String query = "SELECT * FROM `feedback` WHERE ((id_user= "+u+") AND (id_application="+a+"))";
		Statement st  = cn.createStatement();
                ResultSet rs = st.executeQuery(query);
               
while(rs.next()){return true;}        
        
        return false;
    }
    
}

    
    

