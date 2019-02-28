/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smartstart.services;

import com.smartstart.entities.Opportunity;
import com.smartstart.util.ConnectionDb;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import com.smartstart.entities.fos_user;
//import com.smartstart.entities.has_skill; //A AJOUTER
import com.smartstart.services.HasSkillService;
import java.security.NoSuchAlgorithmException;
import java.security.Security;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Base64;
import java.util.Properties;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import smartstart.FXMLDocumentController;
import javafx.collections.ObservableListBase;
import javafx.scene.input.KeyCode;
import static javafx.scene.input.KeyCode.ALPHANUMERIC;
//mail lib
import javax.mail.*;
import javax.mail.internet.*;
import javax.mail.internet.MimeMessage;
//encrypt
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.crypto.*;


/**
 *
 * @author HP
 */
public class fos_userService {
    ConnectionDb db = ConnectionDb.getInstance();
                Connection cn = db.getCnx();
                    public  ObservableList<fos_user> listusers;
    public void create_user(fos_user u)
    {
        
        
        try {
            String query ="insert into fos_user (id,username,username_canonical, email,email_canonical,"
                    + "enabled,salt,password,last_login,confirmation_token,password_requested_at,roles,"
                    + "name,last_name,birth_date,bio,location,register_date,earnings,expenses, budget, field_company)"
                    + "values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement ps=cn.prepareStatement(query) ;
             ps.setInt(1, u.getId() );
             ps.setString(2, u.getUsername());
             ps.setString(3, u.getUsername_canonical());
             ps.setString(4, u.getEmail());
             ps.setString(5, u.getEmail_canonical());
             ps.setInt(6, u.getEnabled());
             ps.setString(7, u.getSalt());  
             ps.setString(8, u.getPassword());  
             ps.setDate(9, u.getLast_login());  
             ps.setString(10, u.getConfirmation_token());  
             ps.setDate(11, u.getPassword_requested_at());  
             ps.setString(12, u.getRoles());  
             ps.setString(13, u.getName());  
             ps.setString(14, u.getLast_name());  
             ps.setDate(15, u.getBirth_date());  
             ps.setString(16, u.getBio());  
             ps.setString(17, u.getLocation());  
             ps.setDate(18, u.getRegister_date());  
             ps.setFloat(19, u.getEarnings()); 
             ps.setFloat(20, u.getExpenses()); 
             ps.setFloat(21, u.getBudget()); 
             ps.setString(22, u.getField_company()); 
             
              ps.executeUpdate();
               System.out.println("ci bon ");
        } catch (Exception e){
            System.out.println(e);
        }
        
    }
    
Date convertToDateViaInstant(LocalDateTime dateToConvert) {
    return (Date) java.util.Date
      .from(dateToConvert.atZone(ZoneId.systemDefault())
      .toInstant());
}
    
  public   ObservableList<fos_user>  read_all_users() throws SQLException{
           
         ObservableList <fos_user> retour = FXCollections.observableArrayList() ;
          Statement stm = cn.createStatement() ;
          String req = "SELECT * FROM fos_user ";
        ResultSet resultat = stm.executeQuery(req);
          
        while(resultat.next()){
           int id= resultat.getInt("id"); //kenet (1)
            String username = resultat.getString("username");
           String username_canonical= resultat.getString("username_canonical");
            String email= resultat.getString("email");
           String email_canonical= resultat.getString("email_canonical");
           int enabled = resultat.getInt("enabled");
           String salt= resultat.getString("salt");
           String password= resultat.getString("password");
           Date last_login = resultat.getDate("last_login");
           String confirmation_token= resultat.getString("confirmation_token");
           Date Password_requested_at= resultat.getDate("password_requested_at");
           String roles= resultat.getString("roles");
           String name= resultat.getString("name");
           String last_name= resultat.getString("last_name");
           String Bio= resultat.getString("Bio");
            Date Birth_date = resultat.getDate("Birth_date");
           String location= resultat.getString("location");
            Date Register_Date = resultat.getDate("Register_Date");
           float Earnings = resultat.getFloat("Earnings");
           float Expenses = resultat.getFloat("Expenses");
           float Budget = resultat.getFloat("Budget");
           String field_company= resultat.getString("field_company");
             retour.add(new fos_user( id,username,username_canonical,email, email_canonical,enabled,salt,password,
                   last_login, confirmation_token, Password_requested_at , roles, name, last_name, Birth_date, 
                   Bio,location,  Register_Date, Earnings,  Expenses,  Budget,  field_company));
            
        }
        return retour ;
    }
  
      public ObservableList<fos_user> DisplayMy_Users(int id_user) {

        PreparedStatement ps = null;
        ResultSet rs = null;
        listusers = FXCollections.observableArrayList();
        try {
            String query = "select * from fos_users where id="+id_user+" AND enabled="+0;
            ps = cn.prepareStatement(query);
            //ps.setString(1, sl_no);
            System.out.println(ps);
            rs = ps.executeQuery();
            while (rs.next()) {
                listusers.add(new fos_user(rs.getInt(1), rs.getString(2),rs.getString(3), rs.getString(4), rs.getString(5),rs.getInt(6), rs.getString(7), rs.getString(8), rs.getDate(9), rs.getString(10),
                        rs.getDate(11),rs.getString(12),rs.getString(13),rs.getString(14),rs.getDate(15),rs.getString(16),rs.getString(17),
                        rs.getDate(18),rs.getFloat(19),rs.getFloat(20),rs.getFloat(21),rs.getString(21)));

            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return listusers;

    }
      
  public int CountUsers () throws SQLException
     { Statement stmt = null;
    ResultSet rs = null;
    int rowCount = -1;
    try {
      stmt = cn.createStatement();
      rs = stmt.executeQuery("SELECT COUNT(*) FROM fos_user " );
      // get the number of rows from the result set
      rs.next();
      rowCount = rs.getInt(1);
    } finally {
      rs.close();
      stmt.close();
    }
    return rowCount;
         
     }
  
  
  
  
 public void delete_user(int id){
	
	PreparedStatement ps=null;
	try {
		String query="delete from fos_user where id=?";
		ps=cn.prepareStatement(query);
		ps.setInt(1,id);
		System.out.println(ps);
		ps.executeUpdate();
	} catch (Exception e) {
		System.out.println(e);
	}

}
   
 public void update_Username(int id, String newUsername) {
        PreparedStatement ps = null;
        try {
            String query = "UPDATE fos_user SET `username`=? WHERE id=" + id;
            ps = cn.prepareStatement(query);
            ps.setString(1, newUsername);
            System.out.println(ps);
            ps.executeUpdate();

        } catch (Exception e) {
            System.out.println(e);
        }
    }
 public void update_password(int id, String newpassword) {
        PreparedStatement ps = null;
        try {
            String query = "UPDATE fos_user SET `password`=? WHERE id=" + id;
            ps = cn.prepareStatement(query);
            ps.setString(1, newpassword);
            System.out.println(ps);
            ps.executeUpdate();

        } catch (Exception e) {
            System.out.println(e);
        }
    }
  public void update_passwordviamail(String mail, String newpassword) {
        PreparedStatement ps = null;
        try {
            String query = "UPDATE fos_user SET `password`=? WHERE email='" + mail+"'";
            ps = cn.prepareStatement(query);
            ps.setString(1, newpassword);
            System.out.println(ps);
            ps.executeUpdate();

        } catch (Exception e) {
            System.out.println(e);
        }
    }
 public void Ajouter_skills(int idfreelancer, int idskill)
 { HasSkillService p1=new HasSkillService();
       p1.addHasSkill(idfreelancer,idskill);
 }
 public boolean Authentification (String username, String password) throws SQLException
 {
     
             Statement statement = cn.createStatement();
                      String sql ="SELECT * FROM `fos_user` WHERE username = '"+username+"' and password = '"+password+"'";
                      System.out.println(sql);
                   //   ps=cn.prepareStatement(sql);
                      ResultSet rs = statement.executeQuery(sql);
                      // console requete System.out.println(rs);
                      if (rs.next())
                      {
                          System.out.println("User trouvé ");
                          return true ;
                      }
                    else  System.out.println("User introuveable");
                      return false ;
                     //isconnected.setText("LAAAAAAA");
                  
 }

 
  public fos_user get_user_by_id(int searched ) 
  {        fos_user newus = new fos_user() ;
   try {
      //zid thabet
      PreparedStatement ps=null;
      ResultSet rs=null;
      newus.setId(searched);
      
      ps = cn.prepareStatement("select * from fos_user where id="+searched);
      rs = ps.executeQuery();
      System.out.println("Execute statement");
      while(rs.next())
      {
          newus.setUsername(rs.getString("username"));
          newus.setUsername_canonical(rs.getString("username_canonical"));
          newus.setEmail(rs.getString("email"));
          newus.setEmail_canonical(rs.getString("email_canonical"));
          newus.setEnabled(rs.getInt("enabled"));
          newus.setSalt(rs.getString("salt"));
          newus.setPassword(rs.getString("password"));
          newus.setLast_login(rs.getDate("last_login"));
          newus.setConfirmation_token(rs.getString("confirmation_token"));
          newus.setPassword_requested_at(rs.getDate("password_requested_at"));
          newus.setRoles(rs.getString("roles"));
          newus.setName(rs.getString("name"));
          newus.setLast_name(rs.getString("last_name"));
          newus.setBirth_date(rs.getDate("Birth_date"));
          newus.setBio(rs.getString("Bio"));
          newus.setLocation(rs.getString("location"));
          newus.setRegister_date(rs.getDate("Register_Date"));
          newus.setEarnings(rs.getFloat("Earnings"));
          newus.setExpenses(rs.getFloat("Expenses"));
          newus.setBudget(rs.getFloat("Budget"));
          newus.setField_company(rs.getString("field_company"));
          //      Date birth_date, String bio,* String location, Date register_date, float earnings, float expenses, float budget, String field_company) {
          
          return newus;
      }
      //     cb.setLastName(rs.getString(3));
      
      
        } catch (SQLException ex) {
            Logger.getLogger(fos_userService.class.getName()).log(Level.SEVERE, null, ex);
        }return newus;
      
  }
public boolean checkverif(fos_user u )
{
    if (u.getEnabled()==0)
    {
        return false;
    }
    else return true ;
    
}
public boolean confirmAccount(fos_user u ,String code) throws SQLException
{
     PreparedStatement ps = null;
    if (u.getConfirmation_token() == code)
    {
        String query = "UPDATE fos_user SET `enabled`=? WHERE id=" + u.getId();
            ps = cn.prepareStatement(query);
            ps.setInt(1, 1);
            System.out.println(ps);
            ps.executeUpdate();   
            System.out.println("confirmed");
        return true ;
    }
    else return false ;
}

  public fos_user get_user_by_username(String searched ) throws SQLException
  {//zid thabet
      PreparedStatement ps=null;
	ResultSet rs=null;
        fos_user newus = new fos_user() ;
        newus.setUsername(searched);
       
        ps = cn.prepareStatement("select * from fos_user where username='"+searched+"'");
        rs = ps.executeQuery();
        System.out.println("Execute statement");
        System.out.println(ps);
       while(rs.next()) 
       {
           newus.setId(rs.getInt("id"));
           newus.setUsername(rs.getString("username"));
           newus.setUsername_canonical(rs.getString("username_canonical"));
           newus.setEmail(rs.getString("email"));
           newus.setEmail_canonical(rs.getString("email_canonical"));
           newus.setEnabled(rs.getInt("enabled"));
           newus.setSalt(rs.getString("salt"));
           newus.setPassword(rs.getString("password"));
           newus.setLast_login(rs.getDate("last_login"));
           newus.setConfirmation_token(rs.getString("confirmation_token"));
           newus.setPassword_requested_at(rs.getDate("password_requested_at"));
           newus.setRoles(rs.getString("roles"));
           newus.setName(rs.getString("name"));
           newus.setLast_name(rs.getString("last_name"));
           newus.setBirth_date(rs.getDate("Birth_date"));
           newus.setBio(rs.getString("Bio"));
           newus.setLocation(rs.getString("location"));
           newus.setRegister_date(rs.getDate("Register_Date"));
           newus.setEarnings(rs.getFloat("Earnings"));
           newus.setExpenses(rs.getFloat("Expenses"));
           newus.setBudget(rs.getFloat("Budget"));
           newus.setField_company(rs.getString("field_company"));
           return newus;
       }
   //     cb.setLastName(rs.getString(3));
        
       return newus;
      
  }
    public ObservableList<fos_user> Display_users() {

        PreparedStatement ps = null;
        ResultSet rs = null;
        listusers = FXCollections.observableArrayList();
        try {
            String query = "select * from fos_users";
            ps = cn.prepareStatement(query);
            //ps.setString(1, sl_no);
            System.out.println(ps);
            rs = ps.executeQuery();
            while (rs.next()) {
                listusers.add(new fos_user());

            }
        } catch (Exception e) {
            System.out.println(e);
        }
        listusers.forEach(e -> System.out.println(e));
        return listusers;

    }
    
   public void sendmail(String to,String verifcode)
    {  
        try{
            String host ="smtp.gmail.com" ;
            String user = "smartstart1941@gmail.com";
            String pass = "azerty1941";
            
            String from = "smartstart1941@gmail.com";
            String subject = "Welcome to smartstart";
            String messageText = "Your confirmation code is"+verifcode;
            boolean sessionDebug = false;

            Properties props = System.getProperties();

            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host",host);
         //option   props.put("mail.smtp.user", user);
            props.put("mail.smtp.port", "587");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.required", "true");
            
            java.security.Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
            Session mailSession = Session.getDefaultInstance(props, null);
            mailSession.setDebug(sessionDebug);
            Message msg = new MimeMessage(mailSession);
            msg.setFrom(new InternetAddress(from));
           
            InternetAddress[] address = {new InternetAddress(to)};
           
            msg.setRecipients(Message.RecipientType.TO, address);
            msg.setSubject(subject); msg.setSentDate(new java.util.Date());
            msg.setText(messageText);

           Transport transport=mailSession.getTransport("smtp"); //serveur
           transport.connect(host, user, pass);
           transport.sendMessage(msg, msg.getAllRecipients());
           transport.close();
           System.out.println("message send successfully");
        }catch(Exception ex)
        {
            System.out.println(ex);
        }

    }
   public String random_code(int length)
   {
        String LETTERS ="abcdefghijklmnopqrstuvwxyz" ;
        char[] ALPHANUMERIC =(LETTERS + LETTERS.toUpperCase()+"1234567890").toCharArray();
       StringBuilder result = new StringBuilder();
       for (int i=0; i <10 ;i++)
       {
           result.append(ALPHANUMERIC[new Random().nextInt(ALPHANUMERIC.length)]);
       }
       return result.toString();
   }
    public void sendmail_password(String to,String password)
    {  
        try{
            String host ="smtp.gmail.com" ;
            String user = "smartstart1941@gmail.com";
            String pass = "azerty1941";
            
            String from = "smartstart1941@gmail.com";
            String subject = "Welcome to smartstart";
            String messageText = "Your password is"+password;
            boolean sessionDebug = false;

            Properties props = System.getProperties();

            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host",host);
         //option   props.put("mail.smtp.user", user);
            props.put("mail.smtp.port", "587");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.required", "true");
            
            java.security.Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
            Session mailSession = Session.getDefaultInstance(props, null);
            mailSession.setDebug(sessionDebug);
            Message msg = new MimeMessage(mailSession);
            msg.setFrom(new InternetAddress(from));
           
            InternetAddress[] address = {new InternetAddress(to)};
           
            msg.setRecipients(Message.RecipientType.TO, address);
            msg.setSubject(subject); msg.setSentDate(new java.util.Date());
            msg.setText(messageText);

           Transport transport=mailSession.getTransport("smtp"); //serveur
           transport.connect(host, user, pass);
           transport.sendMessage(msg, msg.getAllRecipients());
           transport.close();
           System.out.println("message send successfully");
        }catch(Exception ex)
        {
            System.out.println(ex);
        }

    }
}
   
   
    
    


