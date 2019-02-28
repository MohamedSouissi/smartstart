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
import com.smartstart.entities.fos_user;
import com.smartstart.util.ConnectionDb;
import java.io.File;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import static java.time.LocalDate.now;
import java.time.Period;
import java.util.Properties;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author acmou
 */
public class OpportunityService implements OpporunityServiceInterface {

    public ConnectionDb cnx = ConnectionDb.getInstance();
    Connection connection = cnx.getCnx();
    

    public OpportunityService() {
    }
    Opportunity o = new Opportunity();
    private ObservableList<Opportunity> Listeopp;
    private ObservableList<fos_user> ListeUser;

    @Override
    public ObservableList<Opportunity> Display_Opportunity() {

        PreparedStatement ps = null;
        ResultSet rs = null;
        Listeopp = FXCollections.observableArrayList();
        try {
            String query = "select * from opportunity";
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
                Listeopp.add(new Opportunity(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getFloat(5), rs.getInt(6)));
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        Listeopp.forEach(e -> System.out.println(e));
        System.out.println("com.smartstart.services.OpportunityService.Display_Opportunity()");
        return Listeopp;

    }

    public Opportunity Display_One_Opportunity(int id_opp) {

        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            String query = "select * from opportunity where id_opp=" + id_opp;
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
                return (new Opportunity(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getFloat(5), rs.getInt(6), rs.getString(7), rs.getDate(8), rs.getDate(9)));

            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return o;

    }

    public ObservableList<Opportunity> DisplayMy_Opportunities(int id_user) {

        PreparedStatement ps = null;
        ResultSet rs = null;
        Listeopp = FXCollections.observableArrayList();
        try {
            String query = "select * from opportunity where id_entreprise=" + id_user+" AND job_Draft="+0;
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
                Listeopp.add(new Opportunity(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getFloat(5), rs.getInt(6), rs.getString(7), rs.getDate(8), rs.getDate(9)));

            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return Listeopp;

    }
    public ObservableList<Opportunity> DisplayMy_OpportunitiesDrafts(int id_user) {

        PreparedStatement ps = null;
        ResultSet rs = null;
        Listeopp = FXCollections.observableArrayList();
        try {
            String query = "select * from opportunity where id_entreprise=" + id_user+" AND job_Draft="+1;
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
                Listeopp.add(new Opportunity(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getFloat(5), rs.getInt(6), rs.getString(7), rs.getDate(8), rs.getDate(9), rs.getInt(10)));

            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return Listeopp;

    }

    public void create_Opportunity(Opportunity o, int id_entreprise) {
       

        java.util.Date utilDate = new java.util.Date();
        java.sql.Date date = new java.sql.Date(utilDate.getTime());
        PreparedStatement ps = null;
       
        try {
            String query = "INSERT INTO `opportunity`(`job_title`, `job_category`, `job_description`, `Budget`, `job_Draft`, `job_Duration`, `Expiry_date`, `added_date`, `id_entreprise`) VALUES (?,?,?,?,?,?,?,?,?)";
            ps = connection.prepareStatement(query);
             Period period = Period.between(date.toLocalDate(),o.getExpiry_date().toLocalDate());
            int diffY = period.getYears();
            int diffm=period.getMonths();
            int diffD=period.getDays();
            
            System.out.println(diffY);
            
            ps.setString(1, o.getJob_title());
            ps.setString(2, o.getJob_category());
            ps.setString(3, o.getJob_description());
            ps.setFloat(4, o.getBudget());
            ps.setInt(5, 0);
            ps.setString(6, ""+diffY+" years "+diffm+" Months "+diffD+" Days");
            ps.setDate(7, (Date) o.getExpiry_date());
            ps.setDate(8, (Date) date);
            ps.setInt(9, id_entreprise);
            
            

            System.out.println(ps);
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void create_Opportunitydraft(Opportunity o,int id_Entreprise) {
java.util.Date utilDate = new java.util.Date();
        java.sql.Date date = new java.sql.Date(utilDate.getTime());
        PreparedStatement ps = null;
        try {
            String query = "INSERT INTO `opportunity`(`job_title`, `job_category`, `job_description`, `Budget`, `job_Draft`, `job_Duration`, `Expiry_date`, `added_date`, `id_entreprise`) VALUES (?,?,?,?,?,?,?,?,?)";
            ps = connection.prepareStatement(query);
            Period period = Period.between(date.toLocalDate(),o.getExpiry_date().toLocalDate());
            int diffY = period.getYears();
            int diffm=period.getMonths();
            int diffD=period.getDays();
            ps.setString(1, o.getJob_title());
            ps.setString(2, o.getJob_category());
            ps.setString(3, o.getJob_description());
            ps.setFloat(4, o.getBudget());
            ps.setInt(5, 1);
            ps.setString(6, ""+diffY+" years "+diffm+" Months "+diffD+" Days");
            ps.setDate(7, (Date) o.getExpiry_date());
            ps.setDate(8, (Date) date);
            ps.setInt(9, id_Entreprise);

            System.out.println(ps);
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void delete_opporunity(int id_opp) {

        PreparedStatement ps = null;
        try {
            String query = "delete from opportunity where id_opp=?";
            ps = connection.prepareStatement(query);
            ps.setInt(1, id_opp);
            System.out.println(ps);
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }

    }

    public void update_opportunity(Opportunity o, int id_opportunityC) {
        PreparedStatement ps = null;
        try {
            String query = "UPDATE `opportunity` SET `job_title`=?,`job_category`=?,`job_description`=?,`Budget`=?,`Expiry_date`=? WHERE id_opp=" + id_opportunityC;
            ps = connection.prepareStatement(query);
            System.out.println(query);
            ps.setString(1, o.getJob_title());
            ps.setString(2, o.getJob_category());
            ps.setString(3, o.getJob_description());
            ps.setFloat(4, o.getBudget());
       
         
            ps.setDate(5, (Date) o.getExpiry_date());
            

            System.out.println(ps);
            ps.executeUpdate();
            

        } catch (Exception e) {
            System.out.println(e);
        }

    }
     public void SetDraftTo_Opp(Opportunity o) {
        PreparedStatement ps = null;
        try {
            String query = "UPDATE `opportunity` SET `job_Draft`=0 WHERE id_opp=" + o.getId_Opp();
            ps = connection.prepareStatement(query);
           System.out.println(ps);
            ps.executeUpdate();

        } catch (Exception e) {
            System.out.println(e);
        }

    }
     public int CountOpportunities (int id_user)
     { int toretrun=0;
         PreparedStatement ps=null;
         try
         {
             String query="Select Count(*) from opportunity where id_entreprise="+id_user+" AND job_Draft=0";
             ps=connection.prepareStatement(query);
             System.out.println(ps);
            ResultSet rs= ps.executeQuery();
             while(rs.next())
             {
                 toretrun= rs.getInt(1);
             }
             
         } catch(Exception e)
         {
             System.out.println(e);
         }
         return toretrun;
         
     }
    @Override
     public int CountDraft (int id_user)
     { int toretrun=0;
         PreparedStatement ps=null;
         try
         {
             String query="Select Count(*) from opportunity where id_entreprise="+id_user+" AND job_Draft=1";
             ps=connection.prepareStatement(query);
             System.out.println(ps);
            ResultSet rs= ps.executeQuery();
             while(rs.next())
             {
                 toretrun= rs.getInt(1);
             }
             
         } catch(Exception e)
         {
             System.out.println(e);
         }
         return toretrun;
         
     }
      public ObservableList<Opportunity> getOpportunitiesFromApplications(int id_opp) {

        PreparedStatement ps = null;
        PreparedStatement ps2 = null;
        ResultSet rs = null;
        ResultSet rs2 = null;
        Listeopp = FXCollections.observableArrayList();
        try {
            String query = "select * from opportunity where id_opp=" +id_opp;
            ps = connection.prepareStatement(query);
            System.out.println(ps);
            rs = ps.executeQuery();
            while (rs.next()) {

                 Listeopp.add(new Opportunity(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getFloat(5), rs.getInt(6), rs.getString(7), rs.getDate(8), rs.getDate(9), rs.getInt(10)));

            }
        } catch (Exception e) {
            System.out.println(e);
        }
        Listeopp.forEach(e -> System.out.println(e));
        return Listeopp;

    }
      public Opportunity getOpportunityById(int id_opp) {

        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            String query = "select * from opportunity where id_opp=" + id_opp;
            ps = connection.prepareStatement(query);
            //ps.setString(1, sl_no);
            System.out.println(ps);
            rs = ps.executeQuery();
            while (rs.next()) {

        
                Opportunity o = new Opportunity(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getFloat(5), rs.getInt(6), rs.getString(7), rs.getDate(8), rs.getDate(9), rs.getInt(10));

            }
        } catch (Exception e) {
           
        }
        return o;

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
                      
			msg.setText("Your Application has been : "+appName);
			t = session.getTransport("smtps");
			t.connect(host,from,pwd);
			t.sendMessage(msg, msg.getAllRecipients());
                  
		}
		catch (Exception ex ) {ex.printStackTrace();}
		
		}
         public Opportunity LastOpportunity() {

        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            String query = "SELECT * FROM opportunity ORDER BY id_opp DESC LIMIT 0, 1";
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
                return new Opportunity(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getFloat(5), rs.getInt(6), rs.getString(7), rs.getDate(8), rs.getDate(9), rs.getInt(10));

            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return o;

    }
           public fos_user getUserByIdOpp(int id_free) {

        PreparedStatement ps = null;
        PreparedStatement ps2 = null;
        ResultSet rs = null;
        ResultSet rs2 = null;
        ListeUser = FXCollections.observableArrayList();
        try {
            String query = "select * from application,fos_user where ((id_freelancer=" +id_free+
                    ") AND (application.id_freelancer=fos_user.id))";
            ps = connection.prepareStatement(query);
            System.out.println(ps);
            rs = ps.executeQuery();
            while (rs.next()) { //job title job category
                
                
                fos_user opp=new fos_user();
                 opp.setUsername(rs.getString("username"));
                 opp.setEmail(rs.getString("email"));
                  
                 
                return new fos_user(rs.getString(6),rs.getString(8));

            }
        } catch (Exception e) {
            System.out.println(e);
        }
     return new fos_user();

    }
           public fos_user getCompanyNameById_opp(int id_opp) {

        PreparedStatement ps = null;
        PreparedStatement ps2 = null;
        ResultSet rs = null;
        ResultSet rs2 = null;
        ListeUser = FXCollections.observableArrayList();
        try {
            String query = "select * from opportunity,fos_user where ((id_opp=" +id_opp+
                    ") AND (opportunity.id_entreprise=fos_user.id))";
            ps = connection.prepareStatement(query);
            System.out.println(ps);
            rs = ps.executeQuery();
            while (rs.next()) { //job title job category
                
                
                fos_user opp=new fos_user();
                 opp.setUsername(rs.getString("username"));
                 opp.setEmail(rs.getString("email"));
                 opp.setName(rs.getString("name"));
                  
                 
                return new fos_user(rs.getString(6),rs.getString(8),rs.getString(12));

            }
        } catch (Exception e) {
            System.out.println(e);
        }
     return new fos_user();

    }
    @Override
           public int CountApplication (int id_app)
     { int toretrun=0;
         PreparedStatement ps=null;
         try
         {
             String query="Select Count(*) from application where id_opportunity="+id_app;
             ps=connection.prepareStatement(query);
             System.out.println(ps);
            ResultSet rs= ps.executeQuery();
             while(rs.next())
             {
                 toretrun= rs.getInt(1);
             }
             
         } catch(Exception e)
         {
             System.out.println(e);
         }
         return toretrun;
         
     }
    @Override
           public int CountAppliedApp (int id_user)
     { int toretrun=0;
         PreparedStatement ps=null;
         try
         {
             String query="Select count(*) from application,opportunity where "
                     + "(opportunity.id_entreprise="+id_user+" AND application.state=\"APPLIED\" AND opportunity.id_opp=application.id_opportunity)";
             ps=connection.prepareStatement(query);
             System.out.println(ps);
            ResultSet rs= ps.executeQuery();
             while(rs.next())
             {
                 toretrun= rs.getInt(1);
             }
             
         } catch(Exception e)
         {
             System.out.println(e);
         }
         return toretrun;
         
     }
            public int CountALLApplication ()
     { int toretrun=0;
         PreparedStatement ps=null;
         try
         {
             String query="Select Count(*) from application";
             ps=connection.prepareStatement(query);
             System.out.println(ps);
            ResultSet rs= ps.executeQuery();
             while(rs.next())
             {
                 toretrun= rs.getInt(1);
             }
             
         } catch(Exception e)
         {
             System.out.println(e);
         }
         return toretrun;
         
     }
           public int CountALLOpportunties ()
     { int toretrun=0;
         PreparedStatement ps=null;
         try
         {
             String query="Select Count(*) from opportunity";
             ps=connection.prepareStatement(query);
             System.out.println(ps);
            ResultSet rs= ps.executeQuery();
             while(rs.next())
             {
                 toretrun= rs.getInt(1);
             }
             
         } catch(Exception e)
         {
             System.out.println(e);
         }
         return toretrun;
         
     }
            public int CountALLEntreprises ()
     { int toretrun=0;
         PreparedStatement ps=null;
         try
         {
             String query="Select Count(*) from fos_user where roles=\"entreprise\"";
             ps=connection.prepareStatement(query);
             System.out.println(ps);
            ResultSet rs= ps.executeQuery();
             while(rs.next())
             {
                 toretrun= rs.getInt(1);
             }
             
         } catch(Exception e)
         {
             System.out.println(e);
         }
         return toretrun;
         
     }
            public int CountALLFreelancer ()
     { int toretrun=0;
         PreparedStatement ps=null;
         try
         {
             String query="Select Count(*) from fos_user where roles=\"freelancer\"";
             ps=connection.prepareStatement(query);
             System.out.println(ps);
            ResultSet rs= ps.executeQuery();
             while(rs.next())
             {
                 toretrun= rs.getInt(1);
             }
             
         } catch(Exception e)
         {
             System.out.println(e);
         }
         return toretrun;
         
     }
           
      
  

}
