/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smartstart.services;

import com.smartstart.entities.Contract;
import com.smartstart.entities.Feedback;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author dytcha
 */
public interface FeedbackServiceInterface {
    
    public void addFeedback(Feedback f) throws SQLException;
    public List<Feedback> listerFeedback (int id) throws SQLException;
    public List<Feedback> listerFeedbackByApp (int id) throws SQLException;
    public void removeFeedback(int id) throws SQLException ;
      public void updateFeedback(Feedback f) throws SQLException ;
          public int CountFeedbacks(int id_user);


   
    
    
    
    
}
