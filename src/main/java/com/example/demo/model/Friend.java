package com.example.demo.model;
import javax.persistence.*;
import javax.persistence.Id;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
@Entity
public class Friend {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String price;
    private String image;
    private String itemName;
    private String description;
    private int myRank;
private String avail;

    public String getAvail() {
        return avail;
    }

    public void setAvail(String avail) {
        this.avail = avail;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    @ManyToOne
    private AppUser myFriend;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }




    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getMyRank() {
        return myRank;
    }

    public void setMyRank(int myRank) {
        this.myRank = myRank;
    }

    public AppUser getMyFriend() {
        return myFriend;
    }

    public void setMyFriend(AppUser myFriend) {
        this.myFriend = myFriend;
    }



    @Override
    public String toString() {
        return "Friend{" +
                "id=" + id +
                ", price='" + price + '\'' +
                ", image='" + image + '\'' +
                ", itemName='" + itemName + '\'' +
                ", description='" + description + '\'' +
                ", myRank=" + myRank +
                ", myFriend=" + myFriend +
                '}';
    }
}