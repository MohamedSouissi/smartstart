/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smartstart.entities;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author diabl
 */
public class Message implements Serializable {
	
	protected static final long serialVersionUID = 1112122200L;

    
    private int id_message;
    private fos_user message_from;
    private fos_user message_to;
    private String content;
    private String attachment;
    private Date date_message;
    private int viewed;
    private int type;

    // The different types of message sent by the Client
	// WHOISIN to receive the list of the users connected
	// MESSAGE an ordinary message
	// LOGOUT to disconnect from the Server
    public static final int WHOISIN = 0, MESSAGE = 1, LOGOUT = 2;

    public void setType(int type) {
        this.type = type;
    }

    
    
    public int getType() {
		return type;
	}
    
    public Message(int type, String message) {
		this.type = type;
		this.content = message;
	}


    public Message() {
    message_from=new fos_user();
    message_to=new fos_user();
    }
    public Message(int id_message,fos_user message_from,fos_user message_to,String content,String attachment,Date date_message,int viewed){
    this.id_message = id_message;
    this.message_from = message_from;
    this.message_to = message_to;
    this.content = content;
    this.attachment = attachment;
    this.date_message = date_message;
    this.viewed = 0;
    
    }

    public int getId_message() {
        return id_message;
    }

    public void setId_message(int id_message) {
        this.id_message = id_message;
    }

    public fos_user getMessage_from() {
        return message_from;
    }

    public void setMessage_from(fos_user message_from) {
        this.message_from = message_from;
    }

    public fos_user getMessage_to() {
        return message_to;
    }

    public void setMessage_to(fos_user message_to) {
        this.message_to = message_to;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAttachment() {
        return attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }

    public Date getDate_message() {
        return date_message;
    }

    public void setDate_message(Date date_message) {
        this.date_message = date_message;
    }

    public int getViewed() {
        return viewed;
    }

    public void setViewed(int viewed) {
        this.viewed = viewed;
    }

    @Override
    public String toString() {
        return this.getMessage_from().getUsername()+" : "+this.getContent();
    }
    
    

    
    
    
    
}
