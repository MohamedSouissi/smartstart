package com.smartstart.services;

import com.smartstart.entities.UserSession;
import com.smartstart.util.ConnectionDb;
import com.smartstart.controllers.AlertBox;
import com.smartstart.entities.fos_user;
import com.smartstart.entities.Question;
import com.smartstart.entities.Reply;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author mohamed
 */
public class ReplyServices {

    private Connection connexion;

    public ReplyServices() {
        connexion = ConnectionDb.getInstance().getCnx();
    }

   public void replyQuestion(Reply r) {
       try { String query = "SELECT MAX(`id_rep`) FROM `reply`";
            Statement st = connexion.createStatement();
            ResultSet rs = st.executeQuery(query);
            if (rs.next()) {
                r.setId(rs.getInt(1));
            }
            String req = "INSERT INTO `reply`(`content`, `date_reply`, `id_question`, `id_user`, `likes`, `dislikes`) VALUES (?,?,?,?,?,?)";
            PreparedStatement pstm = connexion.prepareStatement(req);
            pstm.setString(1, r.getContent());
            pstm.setDate(2, new java.sql.Date(r.getDate().getTime()));
            pstm.setInt(3, r.getQuestion().getId());
            pstm.setInt(4, r.getUser().getId());
            pstm.setInt(5, r.getLikes());
            pstm.setInt(6, r.getDislikes());
            pstm.executeUpdate();
            UserSession.getNotifSuggestService().sendNotification(r);
           
        } catch (SQLException ex) {
           // AlertBox.displayError("ReponseServices", "void replyQuestion(Reponse r, FosUser rep)");
            ex.printStackTrace();
        }
    }

    public void deleteReply(Reply r) {
        try {
            String req = "DELETE FROM `reply` WHERE `id_rep`=" + r.getId();

            PreparedStatement pstm = connexion.prepareStatement(req);
            pstm.executeUpdate();
        } catch (SQLException ex) {
            AlertBox.displayError("ReponseServices", "void supprimerReponse(Reponse r)");
            ex.printStackTrace();
        }
    }

    public void updateReply(Reply r) {
        try {
          //  String req = "UPDATE `reply` SET `content`=? WHERE `id_rep`=" + r.getId();
            String req = "UPDATE `reply` SET `content`=?,`date_reply`=?,`likes`=?,`dislikes`=? WHERE `id_rep`=" + r.getId();

            PreparedStatement pstm = connexion.prepareStatement(req);
            pstm.setString(1, r.getContent());
            pstm.setDate(2, new java.sql.Date(r.getDate().getTime()));
            pstm.setInt(3, r.getLikes());
            pstm.setInt(4, r.getDislikes());

            pstm.executeUpdate();
            System.out.println(req);
            System.out.println(pstm);
        } catch (SQLException ex) {
            AlertBox.displayError("ReponseServices", "void modifierReponse(Reponse r)");
            ex.printStackTrace();
        }
    }

    public boolean isFound(String r) {
        try {
            Statement stm = connexion.createStatement();
            String req = "SELECT * FROM `reply` WHERE `content`='" + r + "'";
            ResultSet res = stm.executeQuery(req);
            if (res.next()) {
                return true;
            }
        } catch (SQLException ex) {
            AlertBox.displayError("ReponseServices", "boolean isFound(String r)");
            ex.printStackTrace();
        }
        return false;
    }

    public List<Reply> getRepliesQuestion(Question q) {
        List<Reply> listReponses = new ArrayList<>();
        try {
            Statement stm = connexion.createStatement();
            String req = "SELECT * FROM `reply` WHERE `id_question`= "+ q.getId()+" ORDER by (`likes`-`dislikes`) DESC";
            ResultSet resultat = stm.executeQuery(req);
            while (resultat.next()) {
                int id_rep = resultat.getInt("id_rep");
                String content = resultat.getString("content");
                Date date_reply = resultat.getDate("date_reply");
                int id_user = resultat.getInt("id_user");
                int likes = resultat.getInt("likes");
                int dislikes = resultat.getInt("dislikes");

                listReponses.add(new Reply(id_rep, content, date_reply, q, UserSession.getfos_userService().get_user_by_id(id_user), likes, dislikes));
            }
            return listReponses;
        } catch (SQLException ex) {
            AlertBox.displayError("ReponseServices", "List<Reponse> getRepliesQuestion(Question q)");
            ex.printStackTrace();
        }
        return listReponses;
    }

    public List<Reply> getMyResponses(fos_user f) {
        List<Reply> listReponses = new ArrayList<>();
        try {
            Statement stm = connexion.createStatement();
            String req = "SELECT * FROM reply WHERE `id_user`=" + f.getId();
            ResultSet resultat = stm.executeQuery(req);
            while (resultat.next()) {
                int id_rep = resultat.getInt("id_rep");
                String content = resultat.getString("content");
                Date date_reply = resultat.getDate("date_reply");
                int id_question = resultat.getInt("id_question");
                int likes = resultat.getInt("likes");
                int dislikes = resultat.getInt("dislikes");

                QuestionServices qs = UserSession.getQuestionService();
                Question q = qs.getQuestion(id_question);

                listReponses.add(new Reply(id_rep, content, date_reply, q, f, likes, dislikes));
            }
            return listReponses;
        } catch (SQLException ex) {
            AlertBox.displayError("ReponseServices", "List<Reponse> getMesReponses(FosUser f)");
            ex.printStackTrace();
        }
        return listReponses;
    }

    public void SetLike(Reply r) {
        try {
            String req = "UPDATE `reply` SET `likes`=`likes`+1 WHERE id_rep=" + r.getId();
            PreparedStatement pstm = connexion.prepareStatement(req);
            pstm.executeUpdate();
            
            String query = "SELECT `likes` FROM `reply` WHERE id_rep=" + r.getId();
            Statement st = connexion.createStatement();
            ResultSet rs = st.executeQuery(query);
            if (rs.next()) {
                r.setLikes(rs.getInt(1));
            }
        } catch (SQLException ex) {
            AlertBox.displayError("ReponseServices", "void SetLike(Reponse r)");
            ex.printStackTrace();
        }
    }

    public void SetDisLike(Reply r) {
        try {
            String req = "UPDATE `reply` SET `dislikes`=`dislikes`+1 WHERE id_rep=" + r.getId();
            PreparedStatement pstm = connexion.prepareStatement(req);
            pstm.executeUpdate();
            
            String query = "SELECT `dislikes` FROM `reply` WHERE id_rep=" + r.getId();
            Statement st = connexion.createStatement();
            ResultSet rs = st.executeQuery(query);
            if (rs.next()) {
                r.setDislikes(rs.getInt(1));
            }
            
        } catch (SQLException ex) {
            AlertBox.displayError("ReponseServices", "void SetDisLike(Reply r)");
            ex.printStackTrace();
        }
    }

    public String countVotes(Reply r) {
        try{
  String req = "SELECT (`likes`-`dislikes`) FROM `reply` WHERE `id_rep`="+r.getId();
  Statement st = connexion.createStatement();
            ResultSet rs = st.executeQuery(req);
            if (rs.next()) {
                return rs.getString(1);
            }
        } catch (SQLException ex) {
            AlertBox.displayError("ReponseServices", "String countVotes(Reply r)");
            ex.printStackTrace();
        }
        return "";
    }

}
