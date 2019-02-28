package com.smartstart.services;

import com.smartstart.controllers.AlertBox;
import com.smartstart.entities.UserSession;
import com.smartstart.util.ConnectionDb;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import static java.lang.ProcessBuilder.Redirect.to;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author mohamed
 */
public class QApiServices {
     private Connection connexion;
    
    public QApiServices(){        
        connexion = ConnectionDb.getInstance().getCnx();
    }
    
    public static Map<String, String> getMap(){
    Map<String, String> suggestMap = new HashMap<String, String>();
        suggestMap.put("mounir", "0021658978667");
        suggestMap.put("metwi", "0021699574161");
        suggestMap.put("marwen", "0021622209052");
        suggestMap.put("mehdi", "0021692156121");
        suggestMap.put("amine", "0021621986562");
        suggestMap.put("laroussi", "0021623383700");
        return suggestMap;
}
            
    
    
      public void sendSMS(String question,String number){
    try {
    // Construct data
    String apiKey = "apikey=" + "Co+szq+TxGw-9vVJpFqeLLK3PItNtSJsORDOdRRYQJ";
    String message = "&message=" + question;
    String sender = "&sender=" + UserSession.getFosUser().getUsername();
    String numbers = "&numbers=" + number;
    
    // Send data
    HttpURLConnection conn = (HttpURLConnection) new URL("https://api.txtlocal.com/send/?").openConnection();
    String data = apiKey + numbers + message + sender;
    conn.setDoOutput(true);
    conn.setRequestMethod("POST");
    conn.setRequestProperty("Content-Length", Integer.toString(data.length()));
    conn.getOutputStream().write(data.getBytes("UTF-8"));
    final BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
    final StringBuffer stringBuffer = new StringBuffer();
    String line;
    while ((line = rd.readLine()) != null) {
    AlertBox.displayError("sms sending", line);
    }
    rd.close();
    
    }catch(IOException ex){
    AlertBox.displayError("QApiServices", "sms not sent");
    
    }
    }
    
    
  /*  public void sendAction(String to,String question,String description) {
    try {
    
    String host = "smtp.gmail.com";
    String user = "smartstart1941@gmail.com";
    String pass = "azerty1941";
    String From = UserSession.getFosUser().getEmail_canonical();
    String To = this.getEmail(to);
    String Subject = question;
    String Message = description;
    boolean sessionDebug = false;
    
    Properties props = System.getProperties();
    
    props.put("mail.smtp.starttls.enable", "true");
    props.put("mail.smtp.host", host);
    props.put("mail.smtp.port", "587");
    props.put("mail.smtp.auth", "true");
    props.put("mail.smtp.starttls.required", "true");
    
    java.security.Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
    Session mailSession = Session.getDefaultInstance(props, null);
    mailSession.setDebug(sessionDebug);
    Message msg = new MimeMessage(mailSession);
    msg.setFrom(new InternetAddress(From));
    // InternetAddress[] address = {new InternetAddress(To)};
    msg.setRecipient(javax.mail.Message.RecipientType.TO, new InternetAddress(To));
    msg.setSubject(Subject);
    msg.setSentDate(new Date());
    msg.setText(Message);
    Transport transport = mailSession.getTransport("smtp");
    transport.connect(host, user, pass);
    
    transport.sendMessage(msg, msg.getAllRecipients());
    transport.close();
    System.out.println("message send successfully");
    } catch (Exception ex) {
    ex.printStackTrace();
    System.out.println(ex);
    }
    }*/
    
       public void sendQuestionToUser(String name,String question) {
        
                String host="smtp.gmail.com";
		String from="smartstart1941@gmail.com" ;
		String pwd="azerty1941" ;
		String to= this.getEmail(name) ;
		Transport t = null;
		Properties props = System.getProperties();
		props.put("mail.smtp.host", host);
		Session session = Session.getDefaultInstance(props, null);
		MimeMessage msg = new MimeMessage(session);
		try {
			msg.setFrom(new InternetAddress(from));
			msg.addRecipients(javax.mail.Message.RecipientType.TO,to);
			msg.setSubject("Someone from SmartStart asked you a question");
			msg.setText(question);
			t = session.getTransport("smtps");
			t.connect(host,from,pwd);
			t.sendMessage(msg, msg.getAllRecipients());
		}
		catch (Exception ex ) {ex.printStackTrace();}
		
		}
     
     
     
      public String getEmail(String value) {
              String mail="";
        try {
            Statement stm = connexion.createStatement();
            String req = "SELECT `email_canonical` FROM `fos_user` WHERE `username_canonical`='"+value+"'";
            ResultSet resultat = stm.executeQuery(req);
            while (resultat.next()) {
              return resultat.getString("email_canonical");
            }
            return mail;
        } catch (SQLException ex) {
            AlertBox.displayError("QuestionServices", "String getEmail()");
            ex.printStackTrace();
        }
        return mail;
    }
     
}
