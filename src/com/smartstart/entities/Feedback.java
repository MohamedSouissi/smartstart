/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smartstart.entities;

import java.util.Date;

/**
 *
 * @author dytcha
 */
public class Feedback {
    private int id_feedback;
    private fos_user user;
    private float Rating;
    private String comment;
    private Date added_date;
    private Application application;
    

   public Feedback(){
   application=new Application();
   user = new fos_user();
   added_date = new Date();
   }
   public Feedback(int id_feedback,fos_user user ,float Rating ,String comment,Date added_date,Application application){
       this.id_feedback=id_feedback;
       this.user=user;
       this.Rating=Rating;
       this.comment=comment;
       this.added_date=added_date;
       this.application=application;
        
       
   
   }

    public int getId_feedback() {
        return id_feedback;
    }

    public void setId_feedback(int id_feedback) {
        this.id_feedback = id_feedback;
    }

    public fos_user getUser() {
        return user;
    }

    public void setUser(fos_user user) {
        this.user = user;
    }

    public float getRating() {
        return Rating;
    }

    public void setRating(float Rating) {
        this.Rating = Rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getAdded_date() {
        return added_date;
    }

    public void setAdded_date(Date added_date) {
        this.added_date = added_date;
    }

    public Application getApplication() {
        return application;
    }

    public void setApplication(Application application) {
        this.application = application;
    }
    
    
    
    
    
}

