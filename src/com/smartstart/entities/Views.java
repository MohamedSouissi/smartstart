package com.smartstart.entities;

import java.util.Date;

/**
 *
 * @author mohamed
 */
public class Views {

    private int id_view, id_user, type, view_identification;
    private Date dateView;

    public Views() {
        this.dateView = new Date();
        this.dateView.getTime();
    }

    public Views(int id_user, int type, int view_identification) {
        this.id_user = id_user;
        this.type = type;
        this.view_identification = view_identification;
        this.dateView = new Date();
        this.dateView.getTime();

    }

    public Views(int id_view, int id_user, int type, int view_identification, Date dateView) {
        this.id_view = id_view;
        this.id_user = id_user;
        this.type = type;
        this.view_identification = view_identification;
        this.dateView = dateView;

    }

    public int getId() {
        return id_view;
    }

    public void setId(int id_view) {
        this.id_view = id_view;
    }

    public int getIdUser() {
        return id_user;
    }

    public void setIdUser(int id_user) {
        this.id_user = id_user;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getViewIdentification() {
        return view_identification;
    }

    public void setViewIdentification(int view_identification) {
        this.view_identification = view_identification;
    }

    public Date getDateView() {
        return dateView;
    }

    public void setDateView(Date dateView) {
        this.dateView = dateView;
    }

}
