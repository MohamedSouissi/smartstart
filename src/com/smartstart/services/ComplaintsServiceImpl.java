/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smartstart.services;

import com.smartstart.entities.Application;
import com.smartstart.entities.Complaint;
import com.smartstart.entities.Feedback;
import com.smartstart.entities.Opportunity;
import com.smartstart.entities.fos_user;
import com.smartstart.util.ConnectionDb;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author dytcha
 */
public class ComplaintsServiceImpl {

    public void addComplaint(Complaint c) throws SQLException {
        ConnectionDb db = ConnectionDb.getInstance();
        Connection cn = db.getCnx();
        String query = "INSERT INTO `complaints`(id_user,mail_user,subject,content,added_date,id_opp) VALUES (?,?,?,?,?,?)";
        PreparedStatement st = cn.prepareStatement(query);
        st.setInt(1, c.getUser().getId());
        st.setString(2, c.getMail_user());
        st.setString(3, c.getSubject());
        st.setString(4, c.getContent());
        Date d1 = new Date();
        java.sql.Date dateS = new java.sql.Date(d1.getTime());
        st.setDate(5, dateS);
        st.setInt(6, c.getOpportunity().getId_Opp());

        st.executeUpdate();
    }

    public ObservableList<Complaint> listerComplaint(int id) throws SQLException {

        ConnectionDb db = ConnectionDb.getInstance();
        Connection cn = db.getCnx();
        String query = "SELECT * FROM `complaints`,`fos_user`,`opportunity` WHERE ((complaints.id_user=fos_user.id) AND (complaints.id_opp=opportunity.id_opp) AND (complaints.id_user = " + id + "))";
        Statement st = cn.createStatement();
        ResultSet rs = st.executeQuery(query);
        List<Complaint> lf = new ArrayList<Complaint>();
        while (rs.next()) {
            Complaint f = new Complaint();

            fos_user u = new fos_user();
            u.setId(rs.getInt("fos_user.id"));
            u.setUsername(rs.getString("fos_user.username"));
            f.setUser(u);
            f.setId_complaint(rs.getInt("id_complaint"));
            f.setMail_user(rs.getString("mail_user"));
            f.setSubject(rs.getString("subject"));
            f.setContent(rs.getString("content"));
            f.setAdded_date(rs.getDate("added_date"));
            Opportunity o = new Opportunity();
            o.setId(rs.getInt("complaints.id_opp"));
            o.setJob_title(rs.getString("opportunity.job_title"));
            f.setOpportunity(o);
            lf.add(f);
        }
        ObservableList lf_final = FXCollections.observableArrayList(lf);
        return lf_final;
    }

    
    public void removeComplaint(int id) throws SQLException {

                ConnectionDb db = ConnectionDb.getInstance();
                Connection cn = db.getCnx();
                String query = "DELETE FROM `complaints` WHERE `id_complaint` = "+id;
		PreparedStatement st  = cn.prepareStatement(query);  
                st.execute();
    }
    
    
    public void updateComplaint(Complaint f) throws SQLException {

                ConnectionDb db = ConnectionDb.getInstance();
                Connection cn = db.getCnx();
                String query = "UPDATE `complaints` SET `mail_user`=? ,`subject`=?,`content`=?,`added_date`=? WHERE `id_complaint` =? ";
		PreparedStatement st  = cn.prepareStatement(query);
                st.setString(1, f.getMail_user());
                st.setString(2, f.getSubject());
                st.setString(3, f.getContent());
                java.sql.Date d1 = new java.sql.Date((new java.util.Date()).getTime());
                st.setDate(4,d1);
                st.setInt(5, f.getId_complaint()); 
                st.executeUpdate();

    }
    
    public int CountComplaint(int id_user) {
        int toretrun = 0;
        PreparedStatement ps = null;
        try {
            ConnectionDb db = ConnectionDb.getInstance();
            Connection cn = db.getCnx();
            String query = "SELECT count(*) FROM `complaints` WHERE `id_user`= "+id_user;
            ps = cn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                toretrun = rs.getInt(1);
            }

        } catch (Exception e) {
        }
        return toretrun;
    }
}
