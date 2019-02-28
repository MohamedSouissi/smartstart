/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smartstart.util;

import com.smartstart.entities.Contract;
import com.smartstart.services.ContractServiceImpl;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;

/**
 *
 * @author diabl
 */
public class testconnection {

    public static void main(String[] args) throws ParseException {
        ContractServiceImpl cs = new ContractServiceImpl();
        Date ds = new Date();
        Date df = new Date();
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            ds = dateFormat.parse("2019-02-13");
            ds = dateFormat.parse("2019-02-17");
        } catch (Exception e) {
            Logger.getLogger(testconnection.class.getName()).log(Level.SEVERE, null, e);
        }
        ObservableList<Contract> listContract = cs.listContract(1);
        listContract.stream().forEach(con -> System.out.println(con.getApplication().getFreelancer().getId()));
        
    }

}
