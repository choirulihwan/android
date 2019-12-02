package com.choirul.computersdata.Model;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Computer {

    private String compName, compScreen, compCPU, compKeyboard;
    private int compPower, compSpeed, compRam;



    public Computer(){

    }

    public Computer(String compName, String compScreen, String compCPU,
                    String compKeyboard, int compPower, int compSpeed,
                    int compRam) {
        this.compName = compName;
        this.compScreen = compScreen;
        this.compCPU = compCPU;
        this.compKeyboard = compKeyboard;
        this.compPower = compPower;
        this.compSpeed = compSpeed;
        this.compRam = compRam;
    }

    public String getCompName() {
        return compName;
    }

    public void setCompName(String compName) {
        this.compName = compName;
    }

    public String getCompScreen() {
        return compScreen;
    }

    public void setCompScreen(String compScreen) {
        this.compScreen = compScreen;
    }

    public String getCompCPU() {
        return compCPU;
    }

    public void setCompCPU(String compCPU) {
        this.compCPU = compCPU;
    }

    public String getCompKeyboard() {
        return compKeyboard;
    }

    public void setCompKeyboard(String compKeyboard) {
        this.compKeyboard = compKeyboard;
    }

    public int getCompPower() {
        return compPower;
    }

    public void setCompPower(int compPower) {
        this.compPower = compPower;
    }

    public int getCompSpeed() {
        return compSpeed;
    }

    public void setCompSpeed(int compSpeed) {
        this.compSpeed = compSpeed;
    }

    public int getCompRam() {
        return compRam;
    }

    public void setCompRam(int compRam) {
        this.compRam = compRam;
    }
}
