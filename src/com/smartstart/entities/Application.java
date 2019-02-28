/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smartstart.entities;

/**
 *
 * @author Marrr
 */
public class Application {
    private int id;
    private Opportunity opp;
    private fos_user freelancer;
    private String state;
  
public Application(){
    opp=new Opportunity();
    freelancer=new fos_user();
}

public Application(int id,Opportunity opp/*,Fos_user freelancer*/,String state)
{this.id=id;
this.opp=opp;
//this.freelancer=freelancer;
this.state=state;

}
public Application(int id, Opportunity opp,fos_user freelancer,String state) {
       this.id=id;
       this.opp=opp;
       this.freelancer=freelancer;
       this.state=state;
    }
public Application(Opportunity opp,fos_user freelancer,String state) {
       
       this.opp=opp;
       this.freelancer=freelancer;
       this.state=state;
    }

public Application(Opportunity opp,fos_user freelancer) {
       
       this.opp=opp;
       this.freelancer=freelancer;
       this.state="APPLIED";
    }


   
    public Opportunity getOpportunity()
    {return opp;}
    
     public fos_user getFreelancer()
    {return freelancer;}
    
public int getId()
{return id;}
public void setId(int id)
{this.id=id;}
public String getState()
{return state;}

public void setOpportunity(Opportunity opp)
{
 this.opp=opp;
}
public void setFreelancer(fos_user freelancer)
{
 this.freelancer=freelancer;
}
public void setState(String state)
{
 this.state=state;
}



/*@Override
    public String toString()
    {
        return "Application id :"+id+" Opportunity Id :"+opp.getId_Opp()+" Freelancer Id: "+freelancer.getId()+" State : "+state;
    }*/





}
