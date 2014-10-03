/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.convey.game;

import java.util.ArrayList;

/**
 *
 * @author jgonzalez
 */
public class Question {
    String text;
    ArrayList<Option> options;
    
    public Question(String text, Option a,Option b,Option c, Option d){
        this.text = text;
        this.options = new ArrayList<Option>();
        this.options.add(a);
        this.options.add(b);
        this.options.add(c);
        this.options.add(d);
    }
    
    public boolean evalResponse(String response){
        for(Option actual : this.options){
            if(actual.text.trim().equalsIgnoreCase(response.trim()) && actual.isAnswer == true){
                return true;
            }
        }
        return false;
    }
    
    public String getQuestionText(){
        return this.text;
    }
    public String getOptionRed(){
        return this.options.get(0).text;
    }
    
    public String getOptionYellow(){
        return this.options.get(1).text;
    }
    
    public String getOptionBlue(){
        return this.options.get(2).text;
    }
    
    public String getOptionGreen(){
        return this.options.get(3).text;
    }
    
}
