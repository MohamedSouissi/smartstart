package com.smartstart.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author mohamed
 */
public class Question {

    private int id, Vues;
    private String question, description, subject;
    private Date date,answered;
    private fos_user user;

    private List<Reply> reponses;

    public Question() {
        this.reponses = new ArrayList<Reply>();
    }
public Question(fos_user id_user, String question, String description, String subject) {
        this.reponses = new ArrayList<Reply>();
        this.user = id_user;
        this.question = question;
        this.description = description;
        this.date = new Date();
 
        this.Vues = 0;
        this.subject = subject;

    }
    public Question(int id, fos_user id_user, String question, String description, Date added_date, String subject) {
        this.reponses = new ArrayList<Reply>();
        this.id = id;
        this.user = id_user;
        this.question = question;
        this.description = description;
        this.date = added_date;
        this.Vues = 0;
        this.subject = subject;

    }

    public Question(int id_question, fos_user id_user, String question, String description, Date added_date, Date answered, String subject, int vues) {
        this.reponses = new ArrayList<Reply>();
        this.id = id_question;
        this.user = id_user;
        this.question = question;
        this.description = description;
        this.date = added_date;
        this.answered = answered;
        this.subject = subject;
        this.Vues = vues;
    }

    public Question(int id_question, fos_user id_user, String question, String description, Date added_date, Date answered, String subject, int vues, ArrayList<Reply> r) {
        this.reponses = r;
        this.id = id_question;
        this.user = id_user;
        this.question = question;
        this.description = description;
        this.date = added_date;
        this.answered = answered;
        this.subject = subject;
        this.Vues = vues;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getVues() {
        return Vues;
    }

    public void setVues(int nombreDeVue) {
        this.Vues = nombreDeVue;
    }

    public fos_user getUser() {
        return user;
    }

    public void setUser(fos_user idUser) {
        this.user = idUser;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String s) {
        this.question = s;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getAnswered() {
        return answered;
    }

    public void setAnswered(Date answered) {
        this.answered = answered;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public List<Reply> getReponses() {
        return reponses;
    }

    public void setReponses(List<Reply> reponses) {
        this.reponses = reponses;
    }

    @Override
    public String toString() {
        return "id Question: "+id+" , question: "+question+" , description: "+description+", subject: "+subject
                +" , date:"+date+" , answered:"+answered+" , vue: "+Vues;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (this.getClass() != o.getClass()) {
            return false;
        }
        Question q = (Question) o;
        return this.description.equals(q.description) && this.question.equals(q.question);

    }

}
