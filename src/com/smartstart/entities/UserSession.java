/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smartstart.entities;

import com.smartstart.services.NotifySuggestServices;
import com.smartstart.services.QApiServices;
import com.smartstart.services.QuestionServices;
import com.smartstart.services.ReplyServices;
import com.smartstart.services.ViewServices;
import com.smartstart.services.fos_userService;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


/**
 *
 * @author hatem
 */
public class UserSession  {
     private static QuestionServices questionService;
    private static NotifySuggestServices notifySuggestService;
    private static ReplyServices replyService;
    private static ViewServices viewService;
    private static fos_userService fus;
     private static QApiServices qapiServices;
  
    private static fos_user u ;
    private static UserSession instance=null;
    
    private UserSession (fos_user uu) throws ClassNotFoundException {
    
         

            u = uu;
        

       
    }
    
    public static UserSession getInstance(fos_user uu) throws ClassNotFoundException{
        if( instance == null)
            instance = new UserSession(uu);
        
        return instance;
    }
    
     public static UserSession SetInstance() throws ClassNotFoundException{
        if( instance != null)
            instance =null;
        
        return instance;
    }
    
    public fos_user getUser() {
        return u;
    }  
     public void SetUser(fos_user u) {
        this.u=u;
        
    }  
    public static void logout()
    {  Platform.exit();
            
   
        
                
    }
    public static QuestionServices getQuestionService() {
        if (questionService == null) {
            questionService = new QuestionServices();
        }
        return questionService;
    }

    public static ReplyServices getReplyService() {
        if (replyService == null) {
            replyService = new ReplyServices();
        }
        return replyService;
    }

    public static NotifySuggestServices getNotifSuggestService() {
        if (notifySuggestService == null) {
            notifySuggestService = new NotifySuggestServices();
        }
        return notifySuggestService;
    }

    public static ViewServices getViewService() {
        if (viewService == null) {
            viewService = new ViewServices();
        }
        return viewService;
    }
     public static fos_user getFosUser() {
        if (u == null) {
            u = new fos_user();
            // in this case we have to throw an Exception
        }
        return u;
    }
      public static fos_userService getfos_userService(){
    if (fus == null) {
            fus = new fos_userService();
        }
        return fus;
    }
      
    public static QApiServices getQApiServices(){
    if (qapiServices == null) {
            qapiServices = new QApiServices();
        }
        return qapiServices;
    }
   
    
}