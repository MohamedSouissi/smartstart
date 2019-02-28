package com.smartstart.services;

import com.smartstart.entities.Opportunity;
import com.smartstart.entities.fos_user;
import java.sql.SQLException;
import java.util.List;
import javafx.collections.ObservableList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author acmou
 */
public interface OpporunityServiceInterface {

    public ObservableList<Opportunity> Display_Opportunity();

    public ObservableList<Opportunity> DisplayMy_Opportunities(int id_user);

    public void create_Opportunity(Opportunity o, int id_entreprise);

    public void create_Opportunitydraft(Opportunity o, int id_entreprise);

    public void delete_opporunity(int id_opp);

    public void update_opportunity(Opportunity o, int id_opportunityC);

    public Opportunity Display_One_Opportunity(int id_opp);

    ObservableList<Opportunity> DisplayMy_OpportunitiesDrafts(int id_user);

    public void SetDraftTo_Opp(Opportunity o);

    public ObservableList<Opportunity> getOpportunitiesFromApplications(int id_opp);

    public Opportunity getOpportunityById(int id_opp);

    public void sendAcceptanceToUser(String mail, String appName) throws SQLException;

    public fos_user getUserByIdOpp(int id_op);
     public int CountDraft (int id_user);
    public int CountOpportunities (int id_user);
    public int CountApplication (int id_app);
    public int CountAppliedApp (int id_user);
   


}
