package com.smartstart.services;

import com.smartstart.util.ConnectionDb;
import com.smartstart.controllers.AlertBox;
import com.smartstart.entities.fos_user;
import com.smartstart.entities.Question;
import com.smartstart.entities.Reply;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

/**
 *
 * @author mohamed
 */
public class ViewServices {

    private Connection connexion;

    public ViewServices() {
        connexion = ConnectionDb.getInstance().getCnx();
    }

    public void addViewQuestion(fos_user viewer, Question q) {
        try {
        String sql = "SELECT * FROM `views` WHERE `type`=1 AND`view_identification`="+q.getId()+" AND `id_user`="+q.getUser().getId();
            Statement st = connexion.createStatement();
            ResultSet res = st.executeQuery(sql);
            if (res.next()) { 
                return;
            }else{
            String req = "INSERT INTO `views`(`id_user`, `type`, `view_identification`, `dateView`) VALUES (?,?,?,?)";
            PreparedStatement pstm = connexion.prepareStatement(req);

            pstm.setInt(1, viewer.getId());
            pstm.setInt(2, 1);
            pstm.setInt(3, q.getId());
            pstm.setDate(4, new java.sql.Date(new Date().getTime()));
            pstm.executeUpdate();
            }
        } catch (SQLException ex) {
            AlertBox.displayError("ViewServices", "void addViewQuestion(FosUser viewer,Question q)");
            ex.printStackTrace();
        }
    }

    public void addViewReponse(fos_user viewer, Reply r) {
        try {
            String req = "INSERT INTO `views`(`id_user`, `type`, `view_identification`, `dateView`) VALUES (?,?,?,?)";
            PreparedStatement pstm = connexion.prepareStatement(req);

            pstm.setInt(1, viewer.getId());
            pstm.setInt(2, 2);
            pstm.setInt(3, r.getId());
            pstm.setDate(4, new java.sql.Date(new Date().getTime()));

            pstm.executeUpdate();

        } catch (SQLException ex) {
            AlertBox.displayError("ViewServices", "void addViewReponse(FosUser viewer,Reponse r)");
            ex.printStackTrace();
        }
    }

    /*  
    public void addViewOpportunity(fos_user viewer,Opportunity o, Views v) {
    try {
    String req = "INSERT INTO `views`(`id_user`, `type`, `view_identification`, `dateView`) VALUES (?,?,?,?)";
    PreparedStatement pstm = connexion.prepareStatement(req);
    
    pstm.setInt(1, viewer.getId());
    pstm.setInt(2, 3);
    pstm.setInt(3, o.id_opp());
    pstm.setDate(4, new java.sql.Date(v.getDateView().getTime()));
    
    pstm.executeUpdate();
    
    String query = "SELECT MAX(`id_view`) FROM `views`";
    Statement st = connexion.createStatement();
    ResultSet rs = st.executeQuery(query);
    if (rs.next()) {
    v.setId(rs.getInt(1));
    }
    } catch (SQLException ex) {
    AlertBox.displayError("ViewServices", "void addViewOpportunity(fos_user viewer,Opportunity o, Views v)");
    ex.printStackTrace();
    }
    }
    
    public void addViewApplication(fos_user viewer,Application a, Views v) {
    try {
    String req = "INSERT INTO `views`(`id_user`, `type`, `view_identification`, `dateView`) VALUES (?,?,?,?)";
    PreparedStatement pstm = connexion.prepareStatement(req);
    
    pstm.setInt(1, viewer.getId());
    pstm.setInt(2, 4);
    pstm.setInt(3, a.getId());
    pstm.setDate(4, new java.sql.Date(v.getDateView().getTime()));
    
    pstm.executeUpdate();
    
    String query = "SELECT MAX(`id_view`) FROM `views`";
    Statement st = connexion.createStatement();
    ResultSet rs = st.executeQuery(query);
    if (rs.next()) {
    v.setId(rs.getInt(1));
    }
    } catch (SQLException ex) {
    AlertBox.displayError("ViewServices", "void addViewApplication(fos_user viewer,Application a, Views v)");
    ex.printStackTrace();
    }
    }
    
     */
    public int CountViewedQuestions(Question q) {
        try {
            String req = "SELECT COUNT(`id_view`) FROM `views` WHERE `type`=1 AND `view_identification`=" + q.getId() + " AND `id_user`!=" + q.getUser().getId();
            Statement st = connexion.createStatement();
            ResultSet rs = st.executeQuery(req);
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException ex) {
            AlertBox.displayError("ViewServices", "int CountViewedQuestions(Question q)");
            ex.printStackTrace();
        }
        return 0;
    }

    public int CountViewedReplies(Reply r) {
        try {
            String req = "SELECT COUNT(`id_view`) FROM `views` WHERE `type`=2 AND `view_identification`=" + r.getId() + " AND `id_user`!=" + r.getUser().getId();
            Statement st = connexion.createStatement();
            ResultSet rs = st.executeQuery(req);
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException ex) {
            AlertBox.displayError("ViewServices", "int CountViewedReponses(Reponse r)");
            ex.printStackTrace();
        }
        return 0;
    }

    /*
    public int CountViewedOpportunities(Opportunity o) {
    o.id_opp();
    }
    public int CountViewedApplications(Question q) {
    }*/
    public void deleteAllViewedQuestionsBefore(Date before) {
        try {
            String req = "DELETE FROM `views` WHERE `type`=1 AND `dateView`< '" + before.getYear()
                    + "," + before.getMonth() + "," + before.getDate() + "'";

            PreparedStatement pstm = connexion.prepareStatement(req);
            pstm.executeUpdate();
        } catch (SQLException ex) {
            AlertBox.displayError("ViewServices", "void deleteAllQuestionsBefore(Question q, Date before)");
            ex.printStackTrace();
        }
    }

    public void deleteAllViewedRepliesBefore(Date before) {
        try {
            String req = "DELETE FROM `views` WHERE `type`=2 AND `dateView`< '" + before.getYear()
                    + "," + before.getMonth() + "," + before.getDate() + "'";

            PreparedStatement pstm = connexion.prepareStatement(req);
            pstm.executeUpdate();
        } catch (SQLException ex) {
            AlertBox.displayError("ViewServices", "void deleteAllReponsesBefore(Question q, Date before)");
            ex.printStackTrace();
        }
    }

    public void deleteViewedQuestionBefore(Question q, Date before) {
        try {
            String req = "DELETE FROM `views` WHERE `type`=1 AND `dateView`< '" + before.getYear()
                    + "," + before.getMonth() + "," + before.getDate() + "' AND `view_identification`=" + q.getId();

            PreparedStatement pstm = connexion.prepareStatement(req);
            pstm.executeUpdate();
        } catch (SQLException ex) {
            AlertBox.displayError("ViewServices", "void deleteAllQuestionsBefore(Question q, Date before)");
            ex.printStackTrace();
        }
    }
}
