package com.example.dell.clone;

public class AddToCartModalClass {

        String image;
        int count;
        String title;
        String pubName;
        int price;


    public AddToCartModalClass(String image, int count, String title, String pubName, int price) {
        this.image = image;
        this.count = count;
        this.title = title;
        this.pubName = pubName;
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public int getCount() {
        return count;
    }

    public String getTitle() {
        return title;
    }

    public String getPubName() {
        return pubName;
    }

    public int getPrice() {
        return price;
    }
}
