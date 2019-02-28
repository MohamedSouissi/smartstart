/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smartstart.services;
import com.smartstart.entities.Message;
import com.smartstart.entities.fos_user;
import com.smartstart.util.ConnectionDb;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author diabl
 */
public class ChatServiceImpl implements ChatServiceInterface {
	
	private Session session;
        
    
    @Override
    public void addMsg(Message m)   {
       
            try {
                ConnectionDb db = ConnectionDb.getInstance();
                Connection cn = db.getCnx();
                String query = "INSERT INTO `messages`(`message_from`, `message_to`, `content`, `attachement` , `date_message` , `viewed`) VALUES (?,?,?,?,?,?)";
                PreparedStatement st  = cn.prepareStatement(query);
                st.setInt(1, m.getMessage_from().getId());
                st.setInt(2,m.getMessage_to().getId());
                st.setString(3,m.getContent());
                st.setString(4,m.getAttachment());
                java.sql.Date date = new java.sql.Date(m.getDate_message().getTime());
                st.setDate(5,date );
                st.setInt(6,0);
                st.execute();
            } catch (SQLException ex) {
                System.err.println(ex.getMessage()+" =================");
            }
                
           

    }

    @Override
    public void sendMsgOnline() throws SQLException{
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void sendMsgOffline(Message m) throws SQLException {
        ConnectionDb db = ConnectionDb.getInstance();
                Connection cn = db.getCnx();
                String query = "SELECT * FROM `fos_user`,`message` WHERE `message.message_from` = "+m.getMessage_to();
		Statement st  = cn.createStatement();
                ResultSet rs = st.executeQuery(query);
                while(rs.next()){
                    String host="smtp.gmail.com";
		String from="SmartStartApplication@gmail.com" ;
		String pwd="smart123456@" ;
		String to=rs.getString("email") ;
		Transport t = null;
		Properties props = System.getProperties();
		props.put("mail.smtp.host", host);
		Session session = Session.getDefaultInstance(props, null);
		MimeMessage msg = new MimeMessage(session);
		try {
			msg.setFrom(new InternetAddress(from));
			msg.addRecipients(javax.mail.Message.RecipientType.TO,to);
			msg.setSubject("Vous avez reçu un message sur SmartStartApp");
			msg.setText("Vous avez reçu un message de la part de "+"session from"+"sur SmartStartApp");
			t = session.getTransport("smtps");
			t.connect(host,from,pwd);
			t.sendMessage(msg, msg.getAllRecipients());
                        addMsg(m);
		}
		catch (Exception ex ) {ex.printStackTrace();}
		
		}
		
		
	}   

    @Override
    public ObservableList<fos_user> discussionList(int id) throws SQLException{
        ConnectionDb db = ConnectionDb.getInstance();
                Connection cn = db.getCnx();
                String query = "SELECT * FROM `messages` WHERE ((`message_from` = "+id+") OR (`message_to` = "+id+"))";
		Statement st  = cn.createStatement();
                ResultSet rs = st.executeQuery(query);
                List<Integer> l_id = new ArrayList<Integer>();
                while(rs.next()){
                    l_id.add(rs.getInt("message_from"));
                    l_id.add(rs.getInt("message_to"));                  
                }
                l_id =  l_id.stream().distinct().filter(i->i!=id).collect(Collectors.toList());
                fos_userService us = new fos_userService();
                List<fos_user> l_u = new ArrayList<fos_user>();
                
                for(int i=0;i<l_id.size();i++){
                fos_user u = new fos_user();
                u=us.get_user_by_id(l_id.get(i));
                l_u.add(u);
                }
                ObservableList l_u_f = FXCollections.observableArrayList(l_u);
                return l_u_f;
                
    }

    @Override
    public ObservableList<Message> getMessages(int id_user1, int id_user2) throws SQLException {
        ConnectionDb db = ConnectionDb.getInstance();
                Connection cn = db.getCnx();
                String query = "SELECT * FROM `messages` WHERE (((`message_from` = "+id_user1+") AND (`message_to` = "+id_user2+")) OR ((`message_from` = "+id_user2+") AND (`message_to` = "+id_user1+")))";
		Statement st  = cn.createStatement();
                ResultSet rs = st.executeQuery(query);
                List<Message> l_m = new ArrayList<Message>();
                fos_userService fs = new fos_userService();
                while(rs.next()){
                    Message m = new Message();  
                    m.setContent(rs.getString("content"));
                    m.setDate_message(rs.getDate("date_message"));
                    m.setId_message(rs.getInt("id_message"));
                    m.setMessage_from(fs.get_user_by_id(rs.getInt("message_from")));
                    m.setMessage_to(fs.get_user_by_id(rs.getInt("message_to")));
                    m.setViewed(rs.getInt("viewed"));
                    m.setAttachment(rs.getString("attachement"));
                    l_m.add(m);
                }
                ObservableList l_m_f = FXCollections.observableArrayList(l_m);
                return l_m_f;
        
    }

}
