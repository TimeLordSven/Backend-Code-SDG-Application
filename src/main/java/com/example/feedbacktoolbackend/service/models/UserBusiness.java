package com.example.feedbacktoolbackend.service.models;

public class UserBusiness {
    private Long id;
    private String firstName;
    private String lastName;


    public UserBusiness(Long id, String firstName, String lastName){
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Long getId(){
        return id;
    }
    public void setId(Long id){
        this.id=id;
    }
    public String getFirstName(){
        return firstName;
    }
    public void setFirstName(){
        this.firstName= firstName;
    }
    public String getLastName(){
        return lastName;
    }
    public void setLastName(){
        this.lastName= lastName;
    }
}
