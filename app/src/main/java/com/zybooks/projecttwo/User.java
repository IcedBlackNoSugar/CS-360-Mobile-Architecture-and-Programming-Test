package com.zybooks.projecttwo;

public class User {

    int id;
    String user_name;
    String user_password;
    String user_email;
    String user_phone;

    public User(){
        super();
    }

    public User(int i, String name, String password, String email, String phone){
        super();
        this.id = i;
        this.user_name = name;
        this.user_password = password;
        this.user_email = email;
        this.user_phone = phone;

    }

    //Constructor to create the user
    public User(String name, String password, String email, String phone){
        this.user_name = name;
        this.user_password = password;
        this.user_email = email;
        this.user_phone = phone;

    }

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getUserName(){
        return user_name;
    }

    public void setUserName(String name){
        this.user_name = name;
    }

    public String getUserPassword(){
        return user_password;
    }

    public void setUserPassword(String password){
        this.user_password = password;
    }

    public String getUserEmail(){
        return user_email;
    }

    public void setUserEmail(String email){
        this.user_email = email;
    }

    public String getUserPhone(){
        return user_phone;
    }

    public void setUserPhone(String phone){
        this.user_phone = phone;
    }
}
