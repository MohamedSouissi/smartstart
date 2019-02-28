package com.smartstart.services;

import com.smartstart.entities.UserSession;
import com.smartstart.util.ConnectionDb;
import com.smartstart.controllers.AlertBox;
import com.smartstart.entities.fos_user;
import com.smartstart.entities.Question;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author mohamed
 */
public class QuestionServices {

    private Connection connexion;

     public  QuestionServices() {
        connexion = ConnectionDb.getInstance().getCnx();
    }

    public void askAQuestion(Question q) {
        try {
            /*  String query = "SELECT MAX(`id_question`) FROM `questions`";
            Statement st = connexion.createStatement();
            ResultSet rs = st.executeQuery(query);
            if (rs.next()) {
            q.setId(rs.getInt(1));
            }*/

            String req = "INSERT INTO `questions`( `id_user`, `question`, `description`, `added_date`, `answered`, `subject`, `Vues`)"
                    + " VALUES (?,?,?,?,?,?,?)";
            PreparedStatement pstm = connexion.prepareStatement(req);
            pstm.setInt(1, q.getUser().getId());
            pstm.setString(2, q.getQuestion());
            pstm.setString(3, q.getDescription());
            pstm.setDate(4, new java.sql.Date(q.getDate().getTime()));
            pstm.setDate(5, null);
            pstm.setString(6, q.getSubject());
            pstm.setInt(7, 0);
           
            pstm.executeUpdate();

        } catch (SQLException ex) {
            AlertBox.displayError("QuestionServices", "void askQuestion(Question q)");
            ex.printStackTrace();
        }
    }

    public void deleteQuetion(Question q) {
        try {
            String req = "DELETE FROM `questions` WHERE `id_question`=" + q.getId();

            PreparedStatement pstm = connexion.prepareStatement(req);
            pstm.executeUpdate();
        } catch (SQLException ex) {
            AlertBox.displayError("QuestionServices", "void deleteQuetion(Question q)");
            ex.printStackTrace();
        }
    }

    public void deleteQuetion(int i) {
        try {
            String req = "DELETE FROM `questions` WHERE `id_question`=" + i;

            PreparedStatement pstm = connexion.prepareStatement(req);
            pstm.executeUpdate();
        } catch (SQLException ex) {
            AlertBox.displayError("QuestionServices", "void supprimerQuetion(Question q)");
            ex.printStackTrace();
        }
    }

    public void updateQuestion(Question q) {
        try {
            String req = "UPDATE `questions` SET "
                    + "`question`=?,`description`=?,"
                    + "`added_date`=?,"
                    + "`subject`=?,`Vues`=? WHERE id_question=" + q.getId();

            PreparedStatement pstm = connexion.prepareStatement(req);
            pstm.setString(1, q.getQuestion());
            pstm.setString(2, q.getDescription());
            pstm.setDate(3, new java.sql.Date(q.getDate().getTime()));
            pstm.setString(5, q.getSubject());
            pstm.setInt(6, q.getVues());

            pstm.executeUpdate();
        } catch (SQLException ex) {
            AlertBox.displayError("QuestionServices", "void updateQuestion(Question q)");
            ex.printStackTrace();
        }
    }

    public void anwserQuestion(Question q) {
        try {
            String req = "UPDATE `questions` SET `answered`=? WHERE `id_question`=" + q.getId();

            PreparedStatement pstm = connexion.prepareStatement(req);
            pstm.setDate(1, new java.sql.Date(q.getDate().getTime()));

            pstm.executeUpdate();
        } catch (SQLException ex) {
            AlertBox.displayError("QuestionServices", "questionAnwsered");
            System.out.println("modify Question: failed");
            ex.printStackTrace();
        }
    }

    public boolean isFoundQuestion(String q, String d, String s) {
        try {
            String req = "SELECT * FROM `questions` WHERE `question`='" + q + "' AND `description`='" + d + "' AND `subject`='" + s + "'";
            Statement stm = connexion.createStatement();
            ResultSet resl = stm.executeQuery(req);
            if (resl.next()) {
                return true;
            }
        } catch (SQLException ex) {
            AlertBox.displayError("QuestionServices", "boolean isFoundQuestion(String s)");
            ex.printStackTrace();
        }
        return false;
    }

    public Question getQuestion(String s) {
        try {
            String req = "SELECT * FROM `questions` WHERE `description`='" + s + "'";
            Statement stm = connexion.createStatement();
            ResultSet resultat = stm.executeQuery(req);
            if (resultat.next()) {
                int id_question = resultat.getInt("id_question");
                int id_user = resultat.getInt("id_user");
                String question = resultat.getString("question");
                String description = resultat.getString("description");
                Date added_date = resultat.getDate("added_date");
                Date answered = resultat.getDate("answered");
                String image = resultat.getString("subject");
                int vues = resultat.getInt("vues");
                Question q = new Question(id_question,  UserSession.getfos_userService().get_user_by_id(id_user), question, description, added_date, answered, image, vues);
                ReplyServices rp = new ReplyServices();
                q.setReponses(rp.getRepliesQuestion(q));
                return q;
            }
        } catch (SQLException ex) {
            AlertBox.displayError("QuestionServices", "Question getQuestion(String s)");
            ex.printStackTrace();
        }
        return new Question();
    }

    public Question getQuestion(int id) {
        try {
            String req = " SELECT `id_question`, `id_user`, `question`, `description`, `added_date`, `answered`, `subject`, `vues`"
                    + " FROM `questions` WHERE `id_question`=" + id;
            Statement stm = connexion.createStatement();
            ResultSet resultat = stm.executeQuery(req);
            if (resultat.next()) {
                int id_question = resultat.getInt("id_question");
                int id_user = resultat.getInt("id_user");
                String subject = resultat.getString("question");
                String description = resultat.getString("description");
                Date added_date = resultat.getDate("added_date");
                Date answered = resultat.getDate("answered");
                String image = resultat.getString("subject");
                int vues = resultat.getInt("vues");
                Question q = new Question(id_question,  UserSession.getfos_userService().get_user_by_id(id_user), subject, description, added_date, answered, image, vues);
                ReplyServices rp = new ReplyServices();
                q.setReponses(rp.getRepliesQuestion(q));
                return q;
            }
        } catch (SQLException ex) {
            AlertBox.displayError("QuestionServices", "Question getQuestion(int id)");
            ex.printStackTrace();
        }
        return null;
    }

    public List<Question> getMyQuestions(fos_user f) {
        List<Question> retour = new ArrayList<>();
        try {
            Statement stm = connexion.createStatement();
            String req = "SELECT * FROM `questions` WHERE `id_user`=" + f.getId();
            ResultSet resultat = stm.executeQuery(req);
            while (resultat.next()) {
                makeRowList(retour, resultat);
            }
            return retour;
        } catch (SQLException ex) {
            AlertBox.displayError("QuestionServices", "List<Question> getMyQuestions(fos_user f)");
            ex.printStackTrace();
        }
        return retour;
    }

    public List<Question> getUserQuestions(fos_user who) {
        List<Question> retour = new ArrayList<>();
        try {
            Statement stm = connexion.createStatement();
            String req = "SELECT * FROM `questions` WHERE `id_user`=" + who.getId();
            ResultSet resultat = stm.executeQuery(req);
            while (resultat.next()) {

                makeRowList(retour, resultat);
            }
            return retour;
        } catch (SQLException ex) {
            AlertBox.displayError("QuestionServices", "List<Question> getUserQuestions(fos_user who)");
            ex.printStackTrace();
        }
        return retour;
    }

    public List<Question> getAnseweredQuestions() {
        List<Question> retour = new ArrayList<Question>();
        try {
            Statement stm = connexion.createStatement();
            String req = "SELECT * FROM `questions` WHERE `answered` IS NOT null ORDER BY `added_date` DESC";
            ResultSet resultat = stm.executeQuery(req);
            while (resultat.next()) {

                makeRowList(retour, resultat);
            }
            return retour;
        } catch (SQLException ex) {
            AlertBox.displayError("QuestionServices", "List<Question> getAnsewredQuestions()");
            ex.printStackTrace();
        }
        return retour;
    }
    public List<Question> getAnseweredQuestions(fos_user u) {
        List<Question> retour = new ArrayList<Question>();
        try {
            Statement stm = connexion.createStatement();
            String req = "SELECT * FROM `questions` WHERE `answered` IS NOT null "+"AND `id_user`="+u.getId() +" ORDER BY `added_date` DESC";
            ResultSet resultat = stm.executeQuery(req);
            while (resultat.next()) {

                makeRowList(retour, resultat);
            }
            return retour;
        } catch (SQLException ex) {
            AlertBox.displayError("QuestionServices", "List<Question> getAnsewredQuestions(fos_user u)");
            ex.printStackTrace();
        }
        return retour;
    }

    public List<Question> getNotAnseweredQuestions() {
        List<Question> retour = new ArrayList<Question>();
        try {
            Statement stm = connexion.createStatement();
            String req = "SELECT * FROM `questions` WHERE `answered` IS null  ORDER BY `added_date` DESC";
            ResultSet resultat = stm.executeQuery(req);
            while (resultat.next()) {

                makeRowList(retour, resultat);
            }
            return retour;
        } catch (SQLException ex) {
            AlertBox.displayError("QuestionServices", "List<Question> getNotAnsweredQuestions()");
            ex.printStackTrace();
        }
        return retour;
    }
   public List<Question> getNotAnseweredQuestions(fos_user u) {
        List<Question> retour = new ArrayList<Question>();
        try {
            Statement stm = connexion.createStatement();
            String req = "SELECT * FROM `questions` WHERE `answered` IS null "+"AND `id_user`="+u.getId() +" ORDER BY `added_date` DESC";
            ResultSet resultat = stm.executeQuery(req);
            while (resultat.next()) {

                makeRowList(retour, resultat);
            }
            return retour;
        } catch (SQLException ex) {
            AlertBox.displayError("QuestionServices", "List<Question> getNotAnsweredQuestions(fos_user u)");
            ex.printStackTrace();
        }
        return retour;
    }
    public List<Question> getAnseweredQuestionsBySubject(String s) {
        List<Question> retour = new ArrayList<Question>();
        try {
            Statement stm = connexion.createStatement();
            String req = "SELECT * FROM `questions` WHERE `subject`='" + s + "' AND `answered` IS NOT null ORDER BY `added_date` DESC";
            ResultSet resultat = stm.executeQuery(req);
            while (resultat.next()) {

                makeRowList(retour, resultat);
            }
            return retour;
        } catch (SQLException ex) {
            AlertBox.displayError("QuestionServices", "List<Question> getAnsweredQuestionsBysubject(String s)");
            ex.printStackTrace();
        }
        return retour;
    }

    public List<Question> getNotAnsweredQuestionsBySubject(String s) {
        List<Question> retour = new ArrayList<Question>();
        try {
            Statement stm = connexion.createStatement();
            String req = "SELECT * FROM `questions` WHERE `subject`='" + s + "' AND `answered` IS null  ORDER BY `added_date` DESC";
            ResultSet resultat = stm.executeQuery(req);
            while (resultat.next()) {

                makeRowList(retour, resultat);
            }
            return retour;
        } catch (SQLException ex) {
            AlertBox.displayError("QuestionServices", "List<Question> getNotAnsweredQuestionsBysubjects(fString s)");
            ex.printStackTrace();
        }
        return retour;
    }
public List<Question> getAnseweredQuestionsBySubject(fos_user u,String s) {
        List<Question> retour = new ArrayList<Question>();
        try {
            Statement stm = connexion.createStatement();
            String req = "SELECT * FROM `questions` WHERE `subject`='" + s + "' AND `answered` IS NOT null "+"AND `id_user`="+u.getId() +" ORDER BY `added_date` DESC";
            ResultSet resultat = stm.executeQuery(req);
            while (resultat.next()) {

                makeRowList(retour, resultat);
            }
            return retour;
        } catch (SQLException ex) {
            AlertBox.displayError("QuestionServices", "List<Question> getAnsweredQuestionsBysubject(fos_user u,String s)");
            ex.printStackTrace();
        }
        return retour;
    }

    public List<Question> getNotAnsweredQuestionsBySubject(fos_user u,String s) {
        List<Question> retour = new ArrayList<Question>();
        try {
            Statement stm = connexion.createStatement();
            String req = "SELECT * FROM `questions` WHERE `subject`='" + s + "' AND `answered` IS null "+"AND `id_user`="+u.getId() +" ORDER BY `added_date` DESC";
            ResultSet resultat = stm.executeQuery(req);
            while (resultat.next()) {

                makeRowList(retour, resultat);
            }
            return retour;
        } catch (SQLException ex) {
            AlertBox.displayError("QuestionServices", "List<Question> getNotAnsweredQuestionsBysubjects(fos_user u,String s)");
            ex.printStackTrace();
        }
        return retour;
    }
    public List<Question> getNewestQuestions() {
        List<Question> retour = new ArrayList<Question>();
        try {
            Statement stm = connexion.createStatement();
            String req = "SELECT * FROM `questions` ORDER BY `added_date` DESC";
            ResultSet resultat = stm.executeQuery(req);
            while (resultat.next()) {

                makeRowList(retour, resultat);
            }
            return retour;
        } catch (SQLException ex) {
            AlertBox.displayError("QuestionServices", "List<Question> getNewestQuestions()");
            ex.printStackTrace();
        }
        return retour;
    }
   

    public List<Question> getQuestionsBySubject(String s) {
        List<Question> retour = new ArrayList<Question>();
        try {
            Statement stm = connexion.createStatement();
            String req = "SELECT * FROM `questions` WHERE `subject`='" + s + "'";
            ResultSet resultat = stm.executeQuery(req);
            while (resultat.next()) {

                makeRowList(retour, resultat);
            }
            return retour;
        } catch (SQLException ex) {
            AlertBox.displayError("QuestionServices", "List<Question> getQuestionsBySubject(String s)");
            ex.printStackTrace();
        }
        return retour;
    }
public List<Question> getQuestionsBySubject(fos_user u,String s) {
        List<Question> retour = new ArrayList<Question>();
        try {
            Statement stm = connexion.createStatement();
            String req = "SELECT * FROM `questions` WHERE `subject`='" + s + "' AND `id_user`="+u.getId();
            ResultSet resultat = stm.executeQuery(req);
            while (resultat.next()) {

                makeRowList(retour, resultat);
            }
            return retour;
        } catch (SQLException ex) {
            AlertBox.displayError("QuestionServices", "List<Question> getQuestionsBySubject(fos_user u,String s)");
            ex.printStackTrace();
        }
        return retour;
    }
    public List<Question> getQuestionsOrderedBySubject(String s) {
        List<Question> retour = new ArrayList<Question>();
        try {
            Statement stm = connexion.createStatement();
            String req = "SELECT * FROM `questions` ORDER BY `subject`";
            ResultSet resultat = stm.executeQuery(req);
            while (resultat.next()) {
                makeRowList(retour, resultat);
            }
            return retour;
        } catch (SQLException ex) {
            AlertBox.displayError("QuestionServices", "List<Question> getQuestionsOrderedBySubject(String s)");
            ex.printStackTrace();
        }
        return retour;
    }

    public List<String> getsubjects() {
        List<String> retour = new ArrayList<String>();
        try {
            Statement stm = connexion.createStatement();
            String req = "SELECT `name_skill` FROM `skills`";
            ResultSet resultat = stm.executeQuery(req);
            while (resultat.next()) {
                String subject = resultat.getString("name_skill");
                retour.add(subject);
            }
            return retour;
        } catch (SQLException ex) {
            AlertBox.displayError("QuestionServices", "List<String> getSubjectss()");
            ex.printStackTrace();
        }
        return retour;
    }

    private void makeRowList(List<Question> retour, ResultSet resultat) throws SQLException {
        int id_question = resultat.getInt("id_question");
        int id_user = resultat.getInt("id_user");
        String question = resultat.getString("question");
        String description = resultat.getString("description");
        Date added_date = resultat.getDate("added_date");
        Date answered = resultat.getDate("answered");
        String subject = resultat.getString("subject");
        int vues = resultat.getInt("vues");

        Question q = new Question(id_question, UserSession.getfos_userService().get_user_by_id(id_user), question, description, added_date, answered, subject, vues);
        ReplyServices rp = new ReplyServices();
        q.setReponses(rp.getRepliesQuestion(q));
        retour.add(q);

    }
    

    public Set<String> getQuestionsPosted() {
  Set<String> retour = new HashSet<String>();
        try {
            Statement stm = connexion.createStatement();
            String req = "SELECT * FROM `questions` ORDER BY `subject`";
            ResultSet resultat = stm.executeQuery(req);
            while (resultat.next()) {
               retour.add(resultat.getString("question"));
            }
            return retour;
        } catch (SQLException ex) {
            AlertBox.displayError("QuestionServices", "Set<String> getQuestionsPosted()");
            ex.printStackTrace();
        }
        return retour;
    }

    public String getEmail(String value) {
   String mail="";
        try {
            Statement stm = connexion.createStatement();
            String req = "SELECT `email_canonical` FROM `fos_user` WHERE `username_canonical`='"+value+"'";
            ResultSet resultat = stm.executeQuery(req);
            while (resultat.next()) {
              return resultat.getString("email_canonical");
            }
            return mail;
        } catch (SQLException ex) {
            AlertBox.displayError("QuestionServices", "String getEmail()");
            ex.printStackTrace();
        }
        return mail;
    }

}
