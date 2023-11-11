package com.example.citylaundry;

public class UserServicesModal {
    String id, item, washing, cleaning, ironing;

    public UserServicesModal() {
    }

    public UserServicesModal(String id, String item, String washing, String cleaning, String ironing) {
        this.id = id;
        this.item = item;
        this.washing = washing;
        this.cleaning = cleaning;
        this.ironing = ironing;
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
}
