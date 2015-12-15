/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.researchersexchange.business;

import java.io.Serializable;

public class User implements Serializable {

    private String name, email;
    private int coins, participants, participation;

    public User() {
        this.name = "";
        this.email = "";
        this.coins = 0;
        this.participants = 0;
        this.participation = 0;
    }
    
     public User(String name,String email,int coins,int participants,int participation) {
        this.name = name;
        this.email = email;
        this.coins = coins;
        this.participants = participants;
        this.participation = participation;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getCoins() {
        return coins;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

    public int getParticipants() {
        return participants;
    }

    public void setParticipants(int participants) {
        this.participants = participants;
    }

    public int getParticipation() {
        return participation;
    }

    public void setParticipation(int participation) {
        this.participation = participation;
    }

}
