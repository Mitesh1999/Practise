package com.example.dell.clone;

public class Books {
    private String image;
    private String name;
    private int id;
    private String details;
    private int price;
    private String pub_year;
    private String pub_name;
    private int contact;

    public Books(int id,String name,String details,int price,String pub_name,String pub_year,int contact,String image)
    {
        this.image =  image;
        this.name = name;
        this.id = id;
        this.details = details ;
        this.price = price ;
        this.pub_year = pub_year;
        this.pub_name = pub_name;
        this.contact = contact ;
    }

    public String getImage()
    {
        return image;
    }
    public String getName(){
        return name;
    }
    public int getId()
    {
        return id;
    }
    public String getDetails(){
        return details;
    }
    public int getPrice()
    {
        return price;
    }
    public String getPubYear(){
        return pub_year;
    }
    public String getPubName()
    {
        return pub_name;
    }
    public int getContect(){
        return contact;
    }
}
