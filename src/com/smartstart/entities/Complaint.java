/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smartstart.entities;

import java.sql.Date;

/**
 *
 * @author dytcha
 */
public class Complaint {
    private int id_complaint;
    private fos_user user;
    private String mail_user;
    private String subject;
    private String content;
    private Date added_date;
    private Opportunity opportunity;
    

   public Complaint(){
    user =new fos_user();
    opportunity =new Opportunity();
}
public Complaint(int id_complaint,fos_user user,String mail_user,String subject,String content,Date added_date,Opportunity opportunity){
    id_complaint=this.id_complaint;
    user=this.user;
    mail_user=this.mail_user;
    subject=this.subject;
    content=this.content;
    added_date=this.added_date;
    opportunity=this.opportunity;
}

    public int getId_complaint() {
        return id_complaint;
    }

    public void setId_complaint(int id_complaint) {
        this.id_complaint = id_complaint;
    }

    public fos_user getUser() {
        return user;
    }

    public void setUser(fos_user user) {
        this.user = user;
    }

    public String getMail_user() {
        return mail_user;
    }

    public void setMail_user(String mail_user) {
        this.mail_user = mail_user;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getAdded_date() {
        return added_date;
    }

    public void setAdded_date(Date added_date) {
        this.added_date = added_date;
    }

    public Opportunity getOpportunity() {
        return opportunity;
    }

    public void setOpportunity(Opportunity opportunity) {
        this.opportunity = opportunity;
    }

}