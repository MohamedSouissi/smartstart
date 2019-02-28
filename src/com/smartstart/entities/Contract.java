/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smartstart.entities;

import java.util.Date;

/**
 *
 * @author diabl
 */
public class Contract {

    private int id_contract;
    private String payment_method;
    private Date start_date;
    private Date finish_date;
    private float sum;
    private Application application;
    private fos_user user;
    private int prio;

    public Contract() {
        application = new Application();
        user = new fos_user();
    }

    public Contract(int id_contract, String payment_method, Date start_date, Date finish_date, float sum, Application application) {
        this.payment_method = payment_method;
        this.start_date = start_date;
        this.finish_date = finish_date;
        this.sum = sum;
        this.application = application;
    }

    public int getId_contract() {
        return id_contract;
    }

    public void setId_contract(int id_contract) {
        this.id_contract = id_contract;
    }

    public String getPayment_method() {
        return payment_method;
    }

    public void setPayment_method(String payment_method) {
        this.payment_method = payment_method;
    }

    public Date getStart_date() {
        return start_date;
    }

    public void setStart_date(Date start_date) {
        this.start_date = start_date;
    }

    public Date getFinish_date() {
        return finish_date;
    }

    public void setFinish_date(Date finish_date) {
        this.finish_date = finish_date;
    }

    public float getSum() {
        return sum;
    }

    public void setSum(float sum) {
        this.sum = sum;
    }

    public Application getApplication() {
        return application;
    }

    public void setId_application(Application application) {
        this.application = application;
    }

    public fos_user getUser() {
        return user;
    }

    public void setUser(fos_user user) {
        this.user = user;
    }

    public int getPrio() {
        return prio;
    }

    public void setPrio(int prio) {
        this.prio = prio;
    }

}
