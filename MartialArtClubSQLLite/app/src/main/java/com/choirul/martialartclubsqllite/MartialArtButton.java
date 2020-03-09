package com.choirul.martialartclubsqllite;

import android.content.Context;
import android.support.v7.widget.AppCompatButton;

import com.choirul.martialartclubsqllite.Model.MartialArt;

class MartialArtButton extends AppCompatButton {

    private MartialArt martialArtObj;

    public MartialArtButton(Context context, MartialArt martialArt){
        super(context);
        martialArtObj = martialArt;
    }

    public String getMartialArtColor() {
        return martialArtObj.getMartialArtColor();
    }

    public double getMartialArtPrice() {
        return martialArtObj.getMartialArtPrice();
    }


}
