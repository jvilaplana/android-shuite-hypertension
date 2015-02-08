package com.android.bpcontrol.model;

/**
 * Created by Adrian on 06/02/2015.
 */
public class User {

      private static User instance;
      private String UUID;
      private String name;
      private String firstSurname;
      private String secondSurname;
      private String identityCard;

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
    }
