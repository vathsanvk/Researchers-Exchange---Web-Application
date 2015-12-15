package com.researchersexchange.business;

import java.io.Serializable;
import java.sql.Timestamp;

public class Answer implements Serializable {

    private String emailParticipant,code;
    private int choice;
    private Timestamp dateSubmitted;

    public Answer() {
        this.emailParticipant = "";
        this.code="";
        this.choice = 0;
        this.dateSubmitted = null;
    }

    public Answer(String emailParticipant, int choice, String code, Timestamp dateSubmitted) {
        this.emailParticipant = emailParticipant;
        this.choice = choice;
        this.dateSubmitted = dateSubmitted;
        this.code= code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Timestamp getDateSubmitted() {
        return dateSubmitted;
    }

    public void setDateSubmitted(Timestamp dateSubmitted) {
        this.dateSubmitted = dateSubmitted;
    }

   

    public String getEmailParticipant() {
        return emailParticipant;
    }

    public void setEmailParticipant(String emailParticipant) {
        this.emailParticipant = emailParticipant;
    }

    public int getChoice() {
        return choice;
    }

    public void setChoice(int choice) {
        this.choice = choice;
    }

}
