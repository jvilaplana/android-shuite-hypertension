package com.android.bpcontrol.model;

/**
 * Created by Adrian Carrera on 06/02/2015.
 */
public class User {

    private static User instance;
    private String UUID;
    private String name;
    private String firstSurname;
    private String secondSurname;
    private String identityCard;
    private String birthDate;
    private String email;
    private String lastUpdate;
    private boolean active;
    private String dateCreated;
    private String notes;
    private String mobileNumber;
    private String mobileNumberPrefix;
    private String town;


    private User(){

    }

    public static User getInstance(){

        if(instance == null){

            instance =  new User();
        }
            return instance;
        }

    public String getUUID() {
            return UUID;
        }

    public void setUUID(String UUID) {
            this.UUID = UUID;
        }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getMobileNumberPrefix() {
        return mobileNumberPrefix;
    }

    public void setMobileNumberPrefix(String mobileNumberPrefix) {
        this.mobileNumberPrefix = mobileNumberPrefix;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(String lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getIdentityCard() {
        return identityCard;
    }

    public void setIdentityCard(String identityCard) {
        this.identityCard = identityCard;
    }

    public String getSecondSurname() {
        return secondSurname;
    }

    public void setSecondSurname(String secondSurname) {
        this.secondSurname = secondSurname;
    }

    public String getFirstSurname() {
        return firstSurname;
    }

    public void setFirstSurname(String firstSurname) {
        this.firstSurname = firstSurname;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
