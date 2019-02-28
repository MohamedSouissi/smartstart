package com.smartstart.entities;

import java.util.Date;

/**
 *
 * @author mohamed
 */
public class Reply implements Comparable<Reply>{

    private int id, likes, dislikes;
    private String content;
    private Date date;
    private Question question;
    private fos_user user;

    public Reply() {
        question = new Question();
    }

    public Reply(String content, Date date_reply, Question q, fos_user idUser, int likes, int dislikes) {
        this.content = content;
        this.date = date_reply;
        this.question = q;
        this.user = idUser;
        this.likes = likes;
        this.dislikes = dislikes;
    }

    public Reply(int id_rep, String content, Date date_reply, Question q, fos_user idUser, int likes, int dislikes) {
        this.id = id_rep;
        this.content = content;
        this.date = date_reply;
        this.question = q;
        this.user = idUser;
        this.likes = likes;
        this.dislikes = dislikes;
    }

    public Reply(String textReply, Question q, fos_user fosUser) {
        this.content = textReply;
        this.question = q;
        this.user = fosUser;
        this.date= new Date();
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

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getDislikes() {
        return dislikes;
    }

    public void setDislikes(int dislikes) {
        this.dislikes = dislikes;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }
     @Override
    public String toString() {
        return "idREponse:"+id+"content: "+content
                +" , date:"+date+" , likes= "+this.likes+" , dislikes= "+this.dislikes;
    }
    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (this.getClass() != o.getClass()) {
            return false;
        }
        Reply r= (Reply) o;
        return this.content.equals(r.content);

    }

    @Override
    public int compareTo(Reply r) {
    return (this.likes - this.dislikes)-(r.likes-r.dislikes);
    }
}
