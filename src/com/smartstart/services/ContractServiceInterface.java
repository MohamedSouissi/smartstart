/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smartstart.services;

import com.smartstart.entities.Contract;
import java.sql.SQLException;
import java.util.List;
import javafx.collections.ObservableList;

/**
 *
 * @author diabl
 */
public interface ContractServiceInterface {
    public void addContract(Contract c) throws SQLException ;
    public void updateContract(Contract c) throws SQLException ;
    public void removeContract(int id) throws SQLException ;
    public ObservableList<Contract> listContract(int idEntreprise) throws SQLException;
    public List<Contract> listContractOrdred(int idEntreprise) throws SQLException;
    public int CountContracts(int id_user);
    
}
