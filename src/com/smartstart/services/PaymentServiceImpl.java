/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smartstart.services;

import com.smartstart.entities.fos_user;
import com.stripe.Stripe;
import com.stripe.exception.APIConnectionException;
import com.stripe.exception.AuthenticationException;
import com.stripe.exception.CardException;
import com.stripe.exception.InvalidRequestException;
import com.stripe.exception.RateLimitException;
import com.stripe.exception.StripeException;

import com.stripe.model.Charge;
import com.stripe.model.Customer;
import com.stripe.model.Order;
import java.util.HashMap;
import java.util.Map;
import org.eclipse.jetty.server.Authentication.User;

/**
 *
 * @author diabl
 */
public class PaymentServiceImpl implements PaymentService{
    private static final String TEST_STRIPE_SECRET_KEY = "sk_test_VN03E8Pk9mgk4PwapZUbIOfQ";
    public PaymentServiceImpl() {
    Stripe.apiKey = TEST_STRIPE_SECRET_KEY;
  }

    @Override
    public String createCustomer(fos_user user) {
        Map<String, Object> customerParams = new HashMap<String, Object>();
    customerParams.put("description", 
      user.getName() + " " + user.getLast_name());
	customerParams.put("email", user.getEmail());
		
	String id = null;
		
	try { 
      // Create customer
	  Customer stripeCustomer = Customer.create(customerParams);
	  id = stripeCustomer.getId();
	  System.out.println(stripeCustomer);
	} catch (CardException e) {
	  // Transaction failure
	}catch (RateLimitException e) {
	  // Too many requests made to the API too quickly
	} catch (AuthenticationException e) {
	  // Authentication with Stripe's API failed (wrong API key?)
	} 
        catch (StripeException e) {
	  // Generic error
	} catch (Exception e) {
	// Something else happened unrelated to Stripe
	}
	
    return id;
    }

    @Override
    public void chargeCreditCard(Order order) {
       // Stripe requires the charge amount to be in cents
    long chargeAmountCents =  order.getAmount();

   

	Map<String, Object> chargeParams = new HashMap<String, Object>();
	chargeParams.put("amount", chargeAmountCents);
	chargeParams.put("currency", "usd");
	chargeParams.put("description", "Monthly Charges");		
	chargeParams.put("customer", "cus_EbKGBvsfd0ToDW");
			
	try {
	  // Submit charge to credit card 
	  Charge charge = Charge.create(chargeParams);
      System.out.println(charge);
    } catch (CardException e) {
	  // Transaction was declined
	  System.out.println("Status is: " + e.getCode());
	  System.out.println("Message is: " + e.getMessage());
	} catch (RateLimitException e) {
	  // Too many requests made to the API too quickly
	} catch (InvalidRequestException e) {
	  // Invalid parameters were supplied to Stripe's API
    } catch (AuthenticationException e) {
	  // Authentication with Stripe's API failed (wrong API key?)
	} catch (APIConnectionException e) {
	  // Network communication with Stripe failed
	 } catch (StripeException e) {
	  // Generic error
	} catch (Exception e) {
	  // Something else happened unrelated to Stripe
	}	
    }
    
}
