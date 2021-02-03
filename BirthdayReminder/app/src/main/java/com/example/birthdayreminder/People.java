package com.example.birthdayreminder;

import java.util.Date;

class People {
    private int id;
    private String nama, panggilan;
    private Date tgl_lahir;

    public People(){
        super();
    }

    public People(int id, String nama, String panggilan, Date tgl_lahir){
        this.id = id;
        this.nama = nama;
        this.panggilan = panggilan;
        this.tgl_lahir = tgl_lahir;
    }

    public People(String nama, String panggilan, Date tgl_lahir){
        this.nama = nama;
        this.panggilan = panggilan;
        this.tgl_lahir = tgl_lahir;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getPanggilan() {
        return panggilan;
    }

    public void setPanggilan(String panggilan) {
        this.panggilan = panggilan;
    }

    public Date getTgl_lahir() {
        return tgl_lahir;
    }

    public void setTgl_lahir(Date tgl_lahir) {
        this.tgl_lahir = tgl_lahir;
    }
}
