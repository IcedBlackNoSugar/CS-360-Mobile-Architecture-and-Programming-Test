package com.zybooks.projecttwo;

public class Item {

    int id;
    String user_email;
    String name;
    String quantity;
    String unit;

    public Item(){
        super();
    }

    public Item(int i, String email, String name, String quantity, String unit) {

        super();
        this.id = i;
        this.user_email = email;
        this.name = name;
        this.quantity = quantity;
        this.unit = unit;
    }

    //This is the constructor
    public Item(String email, String name, String quantity, String unit) {
        this.user_email = email;
        this.name = name;
        this.quantity = quantity;
        this.unit = unit;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCount() {
        return quantity;
    }

    public void setCount(String count) {
        this.quantity = count;
    }

    public String getUnit(){ return unit;}

    public void setUnit(String unit){ this.unit = unit;}
}
