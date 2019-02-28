/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smartstart.controllers;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

/**
 *
 * @author acmou
 */
public class Test  extends Pane {
   private Timeline animation;
   private int tmp=60;
   private String S;
   Label lable=new Label("60");
  public Test()
   {
       getChildren().add(lable);
       animation=new Timeline(new KeyFrame(Duration.seconds(1),e->timelabel()));
       animation.setCycleCount(Timeline.INDEFINITE);
       animation.play();
       
   }
   private void timelabel(){
       if (tmp>0)
       {
           tmp--;
       }
       S=tmp+"";
       lable.setText(S);
   }
}

