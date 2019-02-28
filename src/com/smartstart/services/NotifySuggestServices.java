package com.smartstart.services;

import com.smartstart.util.ConnectionDb;
import com.smartstart.controllers.AlertBox;
import com.smartstart.entities.fos_user;
import com.smartstart.entities.Notification;
import com.smartstart.entities.Question;
import com.smartstart.entities.Reply;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author mohamed
 */
public class NotifySuggestServices {

    private Connection connexion;

    public NotifySuggestServices() {
        connexion = ConnectionDb.getInstance().getCnx();
    }

    public void sendNotification(fos_user who, Question q) {
        try {
            String req = "INSERT INTO `notification`(`id_user`, `content`, `id_declancheur`, `dateDeclanched`, `dateViwed`)"
                    + " VALUES (?,?,?,?,?)";
            PreparedStatement pstm = connexion.prepareStatement(req);
            pstm.setInt(1, who.getId());
            pstm.setString(2, "questions");
            pstm.setInt(3, q.getId());
            pstm.setDate(4, new java.sql.Date(q.getDate().getTime()));
            pstm.setDate(5, null);
            pstm.executeUpdate();
        } catch (SQLException ex) {
            AlertBox.displayError("QuestionServices", "void envoyerNotification(FosUser who, Question q)");
            ex.printStackTrace();
        }
    }

    public void sendNotification(Reply r) {
        try {
            String req = "INSERT INTO `notification`(`id_user`, `content`, `id_declancheur`, `dateDeclanched`, `dateViwed`)"
                    + " VALUES (?,?,?,?,?)";
            PreparedStatement pstm = connexion.prepareStatement(req);
            pstm.setInt(1, r.getQuestion().getUser().getId());
            pstm.setString(2, "reply");
            pstm.setInt(3, r.getId());
            pstm.setDate(4, new java.sql.Date(r.getDate().getTime()));
            pstm.setDate(5, null);
            pstm.executeUpdate();
        } catch (SQLException ex) {
            AlertBox.displayError("NotifySuggestServices", "void envoyerNotification(FosUser who, Reponse r)");
            ex.printStackTrace();
        }
    }

    public void deleteNotification(Notification n) {
        try {
            String req = "DELETE FROM `notification` WHERE 'id_notif'=" + n.getId();

            PreparedStatement pstm = connexion.prepareStatement(req);
            pstm.executeUpdate();
        } catch (SQLException ex) {
            AlertBox.displayError("NotifySuggestServices", "void supprimerNotification(Notification n)");
            ex.printStackTrace();
        }
    }

    public void setViwedNotification(Notification n) {
        try {
            String req = "UPDATE `notification` SET `dateViwed` = ? WHERE `notification`.`id_notif` = " + n.getId();
            PreparedStatement pstm = connexion.prepareStatement(req);
            pstm.setDate(6, new java.sql.Date(n.getDateViwed().getTime()));

            pstm.executeUpdate();
        } catch (SQLException ex) {
            AlertBox.displayError("NotifySuggestServices", "void setViwedNotification(Notification n)");
            ex.printStackTrace();
        }
    }

    public List<Notification> getNotViewedNotifications(fos_user f) {
        List<Notification> listReponses = new ArrayList<>();
        try {
            Statement stm = connexion.createStatement();
            String req = "SELECT * FROM `notification` WHERE 'id_user'=" + f.getId() + " AND 'dateViwed' is null";
            ResultSet resultat = stm.executeQuery(req);
            while (resultat.next()) {
                int id_notif = resultat.getInt("id_notif");
                int id_user = resultat.getInt("id_user");
                String content = resultat.getString("content");
                int id_declancheur = resultat.getInt("id_declancheur");
                Date dateDeclanched = resultat.getDate("dateDeclanched");
                Date dateViwed = resultat.getDate("dateViwed");

                listReponses.add(new Notification(id_notif, f, content, id_declancheur, dateDeclanched, dateViwed));
            }
            return listReponses;
        } catch (SQLException ex) {
            AlertBox.displayError("NotifySuggestServices", "List<Notification> getNotViewedNotifications(FosUser f)");
            ex.printStackTrace();
        }
        return listReponses;
    }
    
       public List<Notification> getNotifications(fos_user f) {
        List<Notification> listReponses = new ArrayList<>();
        try {
            Statement stm = connexion.createStatement();
            String req = "SELECT * FROM `notification` WHERE 'id_user'=" + f.getId();
            ResultSet resultat = stm.executeQuery(req);
            while (resultat.next()) {
                int id_notif = resultat.getInt("id_notif");
                int id_user = resultat.getInt("id_user");
                String content = resultat.getString("content");
                int id_declancheur = resultat.getInt("id_declancheur");
                Date dateDeclanched = resultat.getDate("dateDeclanched");
                Date dateViwed = resultat.getDate("dateViwed");

                listReponses.add(new Notification(id_notif, f, content, id_declancheur, dateDeclanched, dateViwed));
            }
            return listReponses;
        } catch (SQLException ex) {
            AlertBox.displayError("NotifySuggestServices", "List<Notification> getNotViewedNotifications(FosUser f)");
            ex.printStackTrace();
        }
        return listReponses;
    }
     public Map<String,Notification> getNotViewed(fos_user f) {
        Map<String,Notification> listReponses = new HashMap<>();
        try {
            Statement stm = connexion.createStatement();
            String req = "SELECT * FROM `notification` WHERE 'id_user'=" + f.getId() + " AND 'dateViwed' is null";
            ResultSet resultat = stm.executeQuery(req);
            while (resultat.next()) {
                int id_notif = resultat.getInt("id_notif");
                int id_user = resultat.getInt("id_user");
                String content = resultat.getString("content");
                int id_declancheur = resultat.getInt("id_declancheur");
                Date dateDeclanched = resultat.getDate("dateDeclanched");
                Date dateViwed = resultat.getDate("dateViwed");
                Notification n=new Notification(id_notif, f, content, id_declancheur, dateDeclanched, dateViwed);
                listReponses.put(n.toString(),n);
            }
            return listReponses;
        } catch (SQLException ex) {
            AlertBox.displayError("NotifySuggestServices", "List<Notification> getNotViewedNotifications(FosUser f)");
            ex.printStackTrace();
        }
        return listReponses;
    }

    public void suggestQuestionTo(Question q, fos_user to) {
        try {
            String req = "INSERT INTO `suggestion`(`idQuestion`, `idUserTo`, `sendingDate`, `viewedDate`)"
                    + " VALUES (?,?,?,?)";
            PreparedStatement pstm = connexion.prepareStatement(req);
            pstm.setInt(1, q.getId());
            pstm.setInt(2, to.getId());
            pstm.setDate(3, new java.sql.Date(q.getDate().getTime()));
            pstm.setDate(4, null);
            pstm.executeUpdate();
        } catch (SQLException ex) {
            AlertBox.displayError("NotifySuggestServices", "void suggestQuestionTo(Question q, FosUser to)");
            ex.printStackTrace();
        }
    }
}
