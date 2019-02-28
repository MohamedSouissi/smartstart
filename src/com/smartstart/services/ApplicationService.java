/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smartstart.services;

import com.smartstart.controllers.OpportunityController;
import com.smartstart.entities.Application;
import com.smartstart.entities.Message;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.smartstart.entities.Opportunity;
import com.smartstart.util.ConnectionDb;
import java.sql.Statement;
import java.util.Properties;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author Marr
 */
public class ApplicationService {
 private ObservableList<Opportunity> ListOpp;
    private ObservableList<Application> ListeApp;
    private ObservableList<Opportunity> ListeOpp;

    public ConnectionDb cnx = ConnectionDb.getInstance();
    Connection connection = cnx.getCnx();

    public ApplicationService() {
    }
    Application app = new Application();
    List<Application> ListApp = new ArrayList<>();
     OpportunityService p=new OpportunityService();
        fos_userService p1=new fos_userService();

    public void Display_Application() {
       
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String query = "select * from application,opportunity,fos_user where ((application.id_opportunity=opportunity.id_opp) AND (application.id_freelancer=fos_user.id)) ";
            ps = connection.prepareStatement(query);

            System.out.println(ps);
            rs = ps.executeQuery();
            while (rs.next()) {

                ListApp.add(new Application(rs.getInt("id_application"), p.getOpportunityById(rs.getInt("id_opportunity")), p1.get_user_by_id(rs.getInt("id_freelancer")), rs.getString("state")));

            }
        } catch (Exception e) {
            System.out.println(e);
        }
        ListApp.forEach(e -> System.out.println(e));

    }

    public Application getApplicationById(int searchedapp) {

        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String query = "select * from application where id_application=" + searchedapp;
            ps = connection.prepareStatement(query);

            System.out.println(ps);
            rs = ps.executeQuery();
            if (rs.next()) {
                Application app = new Application(rs.getInt(1), p.getOpportunityById(rs.getInt(2)), p1.get_user_by_id(rs.getInt(3)), rs.getString(4));

            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return app;
    }

    public void create_application(Application app) {

        PreparedStatement ps = null;
        try {
            String query = "INSERT INTO `application`(`id_opportunity`, `id_freelancer`, `state`) VALUES (?,?,?)";
            ps = connection.prepareStatement(query);
            ps.setInt(1, app.getOpportunity().getId_Opp());
            ps.setInt(2, app.getFreelancer().getId());
            ps.setString(3, app.getState());

            System.out.println(ps);
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void delete_Application(int id) {

        PreparedStatement ps = null;
        try {
            String query = "delete from application where id_application=?";
            ps = connection.prepareStatement(query);
            ps.setInt(1, id);
            System.out.println(ps);
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }

    }

    public void update_application(int id, String newState) {  //Hedhi eli bech testa3melha bech tbadel state mta3 APP
        PreparedStatement ps = null;
        try {
            String query = "UPDATE `application` SET `state`=? WHERE id_application=" + id;
            ps = connection.prepareStatement(query);
            ps.setString(1, newState);
            System.out.println(ps);
            ps.executeUpdate();

        } catch (Exception e) {
            System.out.println(e);
        }

    }

    public void fillSuggestions() {

        PreparedStatement ps = null;
        ResultSet rs = null;
        ResultSet rsx = null;
        ResultSet rsy = null;

        try {
            String query = "select * from has_skill";
            ps = connection.prepareStatement(query);
            //ps.setString(1, sl_no);
            System.out.println(ps);
            rs = ps.executeQuery();
            while (rs.next()) {

                PreparedStatement psx = null;
                try {
                    String queryx = "select * from needed_skills where id_skill=" + rs.getInt("id_skill");
                    psx = connection.prepareStatement(queryx);
                    System.out.println(psx);
                    rsx = psx.executeQuery();
                    while (rsx.next()) {

                        PreparedStatement psz = null;
                        try {
                            String queryz = "INSERT INTO `suggestedopps`(`freelancerId`,`opportunityId`) VALUES (?,?)";
                            System.out.println(psz);
                            psz = connection.prepareStatement(queryz);
                            psz.setInt(1, rs.getInt("id_freelancer"));
                            psz.setInt(2, rsx.getInt("id_opp"));
                            System.out.println("ADDED");

                            System.out.println(psz);
                            psz.execute();

                        } catch (Exception ex) {
                            System.out.println(ex);
                        }

                    }

                } catch (Exception ex) {
                    System.out.println(ex);
                }

            }
        } catch (Exception e) {
            System.out.println(e);
        }

    }

    public void clearSuggestions() {

        PreparedStatement ps = null;
        try {
            String query = "delete from suggestedopps";
            ps = connection.prepareStatement(query);
            System.out.println(ps);
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }

    }

    public boolean isApt(int freelancerId, int oppId) {
        ApplicationService p1=new ApplicationService();
        p1.clearSuggestions();
        p1.fillSuggestions();
        int test = 0;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String query = "select * from suggestedopps where freelancerId=" + freelancerId;
            ps = connection.prepareStatement(query);

            System.out.println(ps);
            rs = ps.executeQuery();
            while (rs.next()) {
                if (rs.getInt(2) == oppId) {
                    test++;
                }

            }
        } catch (Exception e) {
            System.out.println(e);
        }

        if (test == 0) {
            return false;
        } else {
            return true;
        }

    }

    public boolean HasApplied(int freelancerId, int oppId) {
        int test = 0;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String query = "select * from application where id_freelancer=" + freelancerId;
            ps = connection.prepareStatement(query);

            System.out.println(ps);
            rs = ps.executeQuery();
            while (rs.next()) {
                if (rs.getInt(2) == oppId) {
                    test++;
                }

            }
        } catch (Exception e) {
            System.out.println(e);
        }

        if (test == 0) {
            return false;
        } else {
            return true;
        }

    }

    public ObservableList<Application> getApplicationsByEntrepriseId(int connectedEntreprise) {

        PreparedStatement ps = null;
        PreparedStatement ps2 = null;
        ResultSet rs = null;
        ResultSet rs2 = null;
        ListeApp = FXCollections.observableArrayList();
        try {
            String query = "select * from application,opportunity where ((id_entreprise=" + connectedEntreprise+
                    ") AND (application.id_opportunity=opportunity.id_opp))";
            ps = connection.prepareStatement(query);
            System.out.println(ps);
            rs = ps.executeQuery();
            while (rs.next()) { //job title job category
                
                
                Opportunity opp=new Opportunity();
                 opp.setJob_title(rs.getString("job_title"));
                 opp.setJob_category(rs.getString("job_category"));
                  opp.setAdded_date(rs.getDate("added_date"));
                   opp.setExpiry_date(rs.getDate("Expiry_date"));
                 
                ListeApp.add(new Application(rs.getInt(1), opp, p1.get_user_by_id(rs.getInt(3)), rs.getString(4)));

            }
        } catch (Exception e) {
            System.out.println(e);
        }
      ListeApp.forEach(e -> System.out.println(e));
        return ListeApp;

    }

    
    
    public ObservableList<Application> getApplicationsByFreelancerId(int connectedFreelancer) {

        PreparedStatement ps = null;
        PreparedStatement ps2 = null;
        ResultSet rs = null;
        ResultSet rs2 = null;
        ListeApp = FXCollections.observableArrayList();
        try {
            String query = "select * from application,opportunity where ((id_freelancer=" + connectedFreelancer+
                    ") AND (application.id_opportunity=opportunity.id_opp))";
            ps = connection.prepareStatement(query);
            System.out.println(ps);
            rs = ps.executeQuery();
            while (rs.next()) {
                
                
                Opportunity opp=new Opportunity();
                 opp.setJob_title(rs.getString("job_title"));
                  opp.setAdded_date(rs.getDate("added_date"));
                   opp.setExpiry_date(rs.getDate("Expiry_date"));
                   opp.setJob_category(rs.getString("job_category"));
                ListeApp.add(new Application(rs.getInt(1), opp, p1.get_user_by_id(rs.getInt(3)), rs.getString(4)));

            }
        } catch (Exception e) {
            System.out.println(e);
        }
      ListeApp.forEach(e -> System.out.println(e));
        return ListeApp;

    }

   public ObservableList<Opportunity> showSuggestedOpps(int connectedFreelancer) {
     ApplicationService p1=new ApplicationService();
        PreparedStatement ps = null;
        PreparedStatement ps2 = null;
        ResultSet rs = null;
        ResultSet rs2 = null;
        ListeOpp = FXCollections.observableArrayList();
        try {
            String query = "select * from opportunity ";
            ps = connection.prepareStatement(query);
            System.out.println(ps);
            rs = ps.executeQuery();
            while (rs.next()) {
                if (p1.isApt(connectedFreelancer, rs.getInt(1)))

                ListeOpp.add (new Opportunity(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getFloat(5), rs.getInt(6), rs.getString(7), rs.getDate(8), rs.getDate(9), rs.getInt(10)));

            }
        } catch (Exception e) {
            System.out.println(e);
        }
        ListeOpp.forEach(e -> System.out.println(e));
        return ListeOpp;

    }

    public void sendAcceptanceToUser(String mail,String appName) throws SQLException {
        
                String host="smtp.gmail.com";
		String from="smartstart1941@gmail.com" ;
		String pwd="azerty1941" ;
		String to=mail ;
		Transport t = null;
		Properties props = System.getProperties();
		props.put("mail.smtp.host", host);
		Session session = Session.getDefaultInstance(props, null);
		MimeMessage msg = new MimeMessage(session);
		try {
			msg.setFrom(new InternetAddress(from));
			msg.addRecipients(javax.mail.Message.RecipientType.TO,to);
			msg.setSubject("Vous avez reçu un message sur SmartStartApp");
                      
			msg.setText("CONGRATS YOU HAVE BEEN ACCEPTED IN AN APPLICATION : "+appName);
			t = session.getTransport("smtps");
			t.connect(host,from,pwd);
			t.sendMessage(msg, msg.getAllRecipients());
                  
		}
		catch (Exception ex ) {ex.printStackTrace();}
		
		}
    
    
    
    public void sendAppliedToUser(String mail,String appName) throws SQLException {
        
                String host="smtp.gmail.com";
		String from="smartstart1941@gmail.com" ;
		String pwd="azerty1941" ;
		String to=mail ;
		Transport t = null;
		Properties props = System.getProperties();
		props.put("mail.smtp.host", host);
		Session session = Session.getDefaultInstance(props, null);
		MimeMessage msg = new MimeMessage(session);
		try {
			msg.setFrom(new InternetAddress(from));
			msg.addRecipients(javax.mail.Message.RecipientType.TO,to);
			msg.setSubject("Vous avez reçu un message sur SmartStartApp");
                      
			msg.setText("WE HAVE RECEIVED YOUR APPLICATION IN THE PROJECT ': "+appName+"' AND IT'S NOW IN PROCESS");
			t = session.getTransport("smtps");
			t.connect(host,from,pwd);
			t.sendMessage(msg, msg.getAllRecipients());
                  
		}
		catch (Exception ex ) {ex.printStackTrace();}
		
		}
    
 public ObservableList<Opportunity> searchOpps(String searchedOpps) {

        PreparedStatement ps ;
        ResultSet rs;
         ListOpp = FXCollections.observableArrayList();
        try {
            String query = "select * from opportunity where `job_title`=? OR `job_category`=?" ;
            
            ps = connection.prepareStatement(query);
             ps.setString(1, searchedOpps);
             ps.setString(2, searchedOpps);
            System.out.println(ps);
            rs = ps.executeQuery();
            while (rs.next()) {
                Opportunity p = new Opportunity(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getFloat(5), rs.getInt(6), rs.getString(7), rs.getDate(8), rs.getDate(9), rs.getInt(10));
                ListOpp.add(p);

            }
        } catch (Exception e) {
            System.out.println(e);
        }
         ListOpp.forEach(e -> System.out.println(e));
        return ListOpp;
    }		
			
		
	
 public boolean CanWithdraw(String status) {
        if (status.equals("APPLIED"))
            return true;
        else return false;
    }


}   






