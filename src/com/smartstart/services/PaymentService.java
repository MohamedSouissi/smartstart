/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smartstart.services;

import com.smartstart.entities.fos_user;
import com.stripe.model.Order;

/**
 *
 * @author diabl
 */
public interface PaymentService {

    public String createCustomer(fos_user user);

    public void chargeCreditCard(Order order);

}
