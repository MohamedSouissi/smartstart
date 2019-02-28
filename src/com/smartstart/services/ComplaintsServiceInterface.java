/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smartstart.services;

import com.smartstart.entities.Complaint;
import com.smartstart.entities.Feedback;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author dytcha
 */
public interface ComplaintsServiceInterface {
    public void addComplaint(Feedback f) throws SQLException;
    public List<Complaint> listerComplaint (int id) throws SQLException;
    public void removeComplaint(int id) throws SQLException ;
      public void updateComplaint(Feedback f) throws SQLException ;
          public int CountComplaint(int id_user);
    
}
