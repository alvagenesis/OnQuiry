/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.onsemi.onquiry.bean;

import com.onsemi.onquiry.model.Question;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import org.apache.log4j.Logger;

/**
 *
 * @author ffymyp
 */
@ManagedBean
@RequestScoped
public class QuestionBean {

    /**
     *
     */
    private Question question;
    private Logger logger =  Logger.getLogger(QuestionBean.class);

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }
    
    
    
    /**
     * Creates a new instance of QuestionBean
     */
    public QuestionBean() {
        question = new Question();
    }
    
    /*
     *  Add new Question
     *  
     */
    public String addNewQuestion(){
       System.out.println("TEST2");
       return "";
    }
}
