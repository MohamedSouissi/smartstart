/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smartstart.services;

import com.smartstart.entities.Contract;
import com.smartstart.entities.Opportunity;
import com.smartstart.entities.fos_user;
import com.smartstart.util.ConnectionDb;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author diabl
 */
public class ContractServiceImpl implements ContractServiceInterface {

    java.util.Date utilDate = new java.util.Date();
    java.sql.Date date = new java.sql.Date(utilDate.getTime());

    @Override
    public void addContract(Contract c) throws SQLException {
        ConnectionDb db = ConnectionDb.getInstance();
        Connection cn = db.getCnx();
        String query = "INSERT INTO `contract`(payment_method, Start_date, finish_date, sum, prio, id_application) VALUES (?,?,?,?,?,?)";
        PreparedStatement st = cn.prepareStatement(query);
        st.setString(1, c.getPayment_method());
        java.sql.Date dateS = new java.sql.Date(c.getStart_date().getTime());
        st.setString(2, dateS.toString());
        java.sql.Date dateF = new java.sql.Date(c.getStart_date().getTime());
        st.setString(3, dateF.toString());
        st.setString(4,String.valueOf(c.getSum()));
        st.setInt(5,c.getPrio());
        st.setInt(6,9);
        
        st.execute();
    }

    @Override
    public void updateContract(Contract c) throws SQLException {
        ConnectionDb db = ConnectionDb.getInstance();
        Connection cn = db.getCnx();
        String query = "UPDATE `contract` SET `Start_date`=?,`finish_date`=?,`sum`=?,`prio`=?,`payment_method`=? WHERE `id_contract` = " + c.getId_contract();
        PreparedStatement st = cn.prepareStatement(query);
        java.sql.Date dateS = new java.sql.Date(c.getStart_date().getTime());
        st.setString(1, dateS.toString());
        java.sql.Date dateF = new java.sql.Date(c.getStart_date().getTime());
        st.setString(2, dateF.toString());
        st.setString(3,String.valueOf(c.getSum()));
        st.setInt(4,c.getPrio());
        st.setString(5,c.getPayment_method());
        st.executeUpdate();
    }

    @Override
    public void removeContract(int id) throws SQLException {
        ConnectionDb db = ConnectionDb.getInstance();
        Connection cn = db.getCnx();
        String query = "DELETE FROM `contract` WHERE id_contract = " + id;
        PreparedStatement st = cn.prepareStatement(query);
        st.execute();
    }

    @Override
    public ObservableList<Contract> listContract(int idEntreprise)   {
        List<Contract> lc = new ArrayList<Contract>();
        try {
        ConnectionDb db = ConnectionDb.getInstance();
        Connection cn = db.getCnx();
        String query = "SELECT * FROM `contract`,`application`,`opportunity` WHERE ((contract.id_application = application.id_application) AND (application.id_opportunity = opportunity.id_opp) AND (opportunity.id_entreprise = " + idEntreprise + ") AND (contract.prio=1))";
        Statement st = cn.createStatement();
        ResultSet rs = st.executeQuery(query);
        
        while (rs.next()) {
                Contract c = new Contract();
                c.setId_contract(rs.getInt("id_contract"));
                c.setPayment_method(rs.getString("payment_method"));
                c.setStart_date(rs.getDate("Start_date"));
                c.setFinish_date(rs.getDate("finish_date"));
                c.setSum(rs.getFloat("sum"));
                c.getApplication().setId(rs.getInt("id_application"));
                OpportunityService os=new OpportunityService();
                Opportunity o = os.Display_One_Opportunity(rs.getInt("id_opp"));
                c.getApplication().setOpportunity(o);
                //c.getApplication().getOpportunity().setJob_description(rs.getString("opportunity.job_description"));               
                fos_userService us = new fos_userService();
                fos_user u = us.get_user_by_id(rs.getInt("application.id_freelancer"));
                c.getUser().setUsername(u.getUsername());
                c.getUser().setId(u.getId());
                c.setPrio(rs.getInt("prio"));
                lc.add(c);
        }
            } catch (SQLException ex) {
                Logger.getLogger(ContractServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        
        ObservableList lcf = FXCollections.observableArrayList(lc);
        return lcf;

    }

    @Override
    public int CountContracts(int id_user) {
        int toretrun = 0;
        PreparedStatement ps = null;
        try {
            ConnectionDb db = ConnectionDb.getInstance();
            Connection cn = db.getCnx();
            String query = "SELECT count(*) FROM `contract`,`application`,`opportunity` WHERE ((contract.id_application = application.id_application) AND (application.id_opportunity = opportunity.id_opp) AND (opportunity.id_entreprise = " + id_user + "))";
            ps = cn.prepareStatement(query);
            System.out.println(ps);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                toretrun = rs.getInt(1);
            }

        } catch (Exception e) {
            System.out.println(e);
        }
        return toretrun;
    }

    @Override
    public ObservableList<Contract> listContractFreelancer(int idfreelancer) throws SQLException {
        List<Contract> lc = new ArrayList<Contract>();
        try {
        ConnectionDb db = ConnectionDb.getInstance();
        Connection cn = db.getCnx();
        String query = "SELECT * FROM `contract`,`application`,`opportunity` WHERE ((contract.id_application = application.id_application) AND (application.id_opportunity = opportunity.id_opp) AND (application.id_freelancer = " + idfreelancer + ") AND (contract.prio=1))";
        Statement st = cn.createStatement();
        ResultSet rs = st.executeQuery(query);
        
        while (rs.next()) {
                Contract c = new Contract();
                c.setId_contract(rs.getInt("id_contract"));
                c.setPayment_method(rs.getString("payment_method"));
                c.setStart_date(rs.getDate("Start_date"));
                c.setFinish_date(rs.getDate("finish_date"));
                c.setSum(rs.getFloat("sum"));
                c.getApplication().setId(rs.getInt("id_application"));
                OpportunityService os=new OpportunityService();
                Opportunity o = os.Display_One_Opportunity(rs.getInt("id_opp"));
                c.getApplication().setOpportunity(o);
                //c.getApplication().getOpportunity().setJob_description(rs.getString("opportunity.job_description"));               
                fos_userService us = new fos_userService();
                fos_user u = us.get_user_by_id(rs.getInt("opportunity.id_entreprise"));
                c.getUser().setUsername(u.getUsername());
                c.getUser().setId(u.getId());
                c.setPrio(rs.getInt("prio"));
                lc.add(c);
        }
            } catch (SQLException ex) {
                Logger.getLogger(ContractServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        
        ObservableList lcf = FXCollections.observableArrayList(lc);
        return lcf;

    }

    @Override
    public ObservableList<Contract> lowPrioContracts(int id) throws SQLException {
        List<Contract> lc = new ArrayList<Contract>();
        try {
        ConnectionDb db = ConnectionDb.getInstance();
        Connection cn = db.getCnx();
        String query = "SELECT * FROM `contract`,`application`,`opportunity` WHERE ((contract.id_application = application.id_application) AND (application.id_opportunity = opportunity.id_opp) AND (opportunity.id_entreprise = " + id + ") AND (contract.prio=0))";
        Statement st = cn.createStatement();
        ResultSet rs = st.executeQuery(query);
        
        while (rs.next()) {
                Contract c = new Contract();
                c.setId_contract(rs.getInt("id_contract"));
                c.setPayment_method(rs.getString("payment_method"));
                c.setStart_date(rs.getDate("Start_date"));
                c.setFinish_date(rs.getDate("finish_date"));
                c.setSum(rs.getFloat("sum"));
                c.getApplication().setId(rs.getInt("id_application"));
                OpportunityService os=new OpportunityService();
                Opportunity o = os.Display_One_Opportunity(rs.getInt("id_opp"));
                c.getApplication().setOpportunity(o);
                //c.getApplication().getOpportunity().setJob_description(rs.getString("opportunity.job_description"));               
                fos_userService us = new fos_userService();
                fos_user u = us.get_user_by_id(rs.getInt("application.id_freelancer"));
                c.getUser().setUsername(u.getUsername());
                c.getUser().setId(u.getId());
                c.setPrio(rs.getInt("prio"));
                lc.add(c);
        }
            } catch (SQLException ex) {
                Logger.getLogger(ContractServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        
        ObservableList lcf = FXCollections.observableArrayList(lc);
        return lcf;
    }

    @Override
    public void autoGeneratedContract(int id_application) throws SQLException {
        ConnectionDb db = ConnectionDb.getInstance();
        Connection cn = db.getCnx();
        String query = "INSERT INTO `contract`(`id_application`, `prio`) VALUES ("+id_application+",0)";
        PreparedStatement st = cn.prepareStatement(query);
        st.execute();
    }



    
}
