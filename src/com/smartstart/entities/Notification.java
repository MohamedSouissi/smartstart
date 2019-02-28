package com.smartstart.entities;

import java.util.Date;

/**
 *
 * @author mohamed
 */
public class Notification {

    private int id,id_user, idDeclancheur;
    private String content;
    private Date dateDeclanched, dateViwed;
    private fos_user user, sender;

    public Notification() {
        user = new fos_user();
    }

    public Notification(int id, fos_user id_user, String content, int id_declancheur, Date dateDeclanched, Date dateViwed) {
        this.id = id;
        this.user = id_user;
        this.content = content;
        this.idDeclancheur = id_declancheur;
        this.dateDeclanched = dateDeclanched;
        this.dateViwed = dateViwed;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public fos_user getUser() {
        return user;
    }

    public void setUser(fos_user idUser) {
        this.user = idUser;
    }

    public int getIdDeclancheur() {
        return idDeclancheur;
    }

    public void setIdDeclancheur(int idDeclancheur) {
        this.idDeclancheur = idDeclancheur;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getDateDeclanched() {
        return dateDeclanched;
    }

    public void setDateDeclanched(Date dateDeclanched) {
        this.dateDeclanched = dateDeclanched;
    }

    public Date getDateViwed() {
        return dateViwed;
    }

    public void setDateViwed(Date dateViwed) {
        this.dateViwed = dateViwed;
    }
 @Override
    public String toString() {
        String ch="";
        if(content.equals("reply")){
            ch= idDeclancheur+ " someone reply your question";
        }
        if(content.equals("question")){
            ch= idDeclancheur+ " someone suggest you a question";
        }
               return ch;
    }
}
