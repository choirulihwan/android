package com.choirul.martialartclubsqllite.Model;

public class MartialArt {

    private String martialArtName;
    private int martialArtPrice;
    private String martialArtColor;
    private int martialArtId;

    public MartialArt(int id, String name, int price, String color){
        setMartialArtId(id);
        setMartialArtName(name);
        setMartialArtPrice(price);
        setMartialArtColor(color);
    }

    public String getMartialArtName() {
        return martialArtName;
    }

    public void setMartialArtName(String martialArtName) {
        this.martialArtName = martialArtName;
    }

    public int getMartialArtPrice() {
        return martialArtPrice;
    }

    public void setMartialArtPrice(int martialArtPrice) {
        this.martialArtPrice = martialArtPrice;
    }

    public String getMartialArtColor() {
        return martialArtColor;
    }

    public void setMartialArtColor(String martialArtColor) {
        this.martialArtColor = martialArtColor;
    }

    public int getMartialArtId() {
        return martialArtId;
    }

    public void setMartialArtId(int martialArtId) {
        this.martialArtId = martialArtId;
    }
}
