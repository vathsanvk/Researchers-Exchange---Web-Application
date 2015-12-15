/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.researchersexchange.business;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;

public class Study implements Serializable {

    private String code, name, creatorEmail, question, description, status,imageURL;
    private Timestamp dateCreated;
    private int requestedParticipants, numOfParticipants;
    private ArrayList<Answer> answers;

    public Study() {
        this.name = "";
        this.dateCreated = null;
        this.creatorEmail = "";
        this.question = "";
        this.description = "";
        this.status = "";
        this.code = "";
        this.imageURL="";
        this.requestedParticipants = 0;
        this.numOfParticipants = 0;
        this.answers = null;

    }

    
    public Study(String code, String name, Timestamp dateCreated, String creatorEmail, String question, String description,
            String status, String imageURL, int requestedParticipants, int numOfParticipants, ArrayList<Answer> answers) {

        this.code = code;
        this.name = name;
        this.dateCreated = dateCreated;
        this.creatorEmail = creatorEmail;
        this.question = question;
        this.description = description;
        this.status = status;
        this.imageURL = imageURL;
        this.requestedParticipants = requestedParticipants;
        this.numOfParticipants = numOfParticipants;
        this.answers = answers;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Timestamp getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Timestamp dateCreated) {
        this.dateCreated = dateCreated;
    }

   
    public String getCreatorEmail() {
        return creatorEmail;
    }

    public void setCreatorEmail(String creatorEmail) {
        this.creatorEmail = creatorEmail;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getRequestedParticipants() {
        return requestedParticipants;
    }

    public void setRequestedParticipants(int requestedParticipants) {
        this.requestedParticipants = requestedParticipants;
    }

    public int getNumOfParticipants() {
        return numOfParticipants;
    }

    public void setNumOfParticipants(int numOfParticipants) {
        this.numOfParticipants = numOfParticipants;
    }

    public ArrayList<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(ArrayList<Answer> answers) {
        this.answers = answers;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getImageURL() {

        return imageURL;
    }

    public double getAverage() {
        int sum = 0;
        double average;

        for (int i = 0; i < answers.size(); i++) {
            sum = sum + answers.get(i).getChoice();
        }
        average = (double) sum / answers.size();
        return average;
    }

    public double getMinimum() {
        int minValue = answers.get(0).getChoice();
        for (int i = 1; i < answers.size(); i++) {
            if (answers.get(i).getChoice() < minValue) {
                minValue = answers.get(i).getChoice();
            }
        }
        return minValue;
    }

    public double getMaximum() {
        int maxValue = answers.get(0).getChoice();
        for (int i = 1; i < answers.size(); i++) {
            if (answers.get(i).getChoice() > maxValue) {
                maxValue = answers.get(i).getChoice();

            }
        }
        return maxValue;
    }

    public double getSD() {
        if (answers.size() == 0) {
            return 0.0;
        }
        double sum = 0;
        double sq_sum = 0;
        for (int i = 0; i < answers.size(); ++i) {
            sum += answers.get(i).getChoice();
            sq_sum += answers.get(i).getChoice() * answers.get(i).getChoice();
        }
        double mean = sum / answers.size();
        double variance = sq_sum / answers.size() - mean * mean;
        return Math.sqrt(variance);
    }

}
