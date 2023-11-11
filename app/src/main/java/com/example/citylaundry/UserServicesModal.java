package com.example.citylaundry;

public class UserServicesModal {
    String id, userID, item, washing, cleaning, ironing, city, state, apartment, info, currentDate, status, price, key;

    public UserServicesModal() {
    }

    public UserServicesModal(String id, String userID, String item, String washing, String cleaning, String ironing, String city, String state, String apartment, String info, String currentDate, String status, String price) {
        this.id = id;
        this.userID=userID;
        this.item = item;
        this.washing = washing;
        this.cleaning = cleaning;
        this.ironing = ironing;
        this.city=city;
        this.state=state;
        this.apartment=apartment;
        this.info=info;
        this.currentDate=currentDate;
        this.status=status;
        this.price=price;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getWashing() {
        return washing;
    }

    public void setWashing(String washing) {
        this.washing = washing;
    }

    public String getCleaning() {
        return cleaning;
    }

    public void setCleaning(String cleaning) {
        this.cleaning = cleaning;
    }

    public String getIroning() {
        return ironing;
    }

    public void setIroning(String ironing) {
        this.ironing = ironing;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getApartment() {
        return apartment;
    }

    public void setApartment(String apartment) {
        this.apartment = apartment;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(String currentDate) {
        this.currentDate = currentDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
}
