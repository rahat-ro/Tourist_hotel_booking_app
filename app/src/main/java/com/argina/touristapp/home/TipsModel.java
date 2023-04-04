package com.argina.touristapp.home;


public class TipsModel {
    private int id;
    private String tipsImg,tipsTitle,tipsDescription;

    public TipsModel(int id, String tipsImg, String tipsTitle, String tipsDescription){

        this.id = id;
        this.tipsImg = tipsImg;
        this.tipsTitle = tipsTitle;
        this.tipsDescription = tipsDescription;

    }

    public int getId () { return id; }

    public String getTipsImg () { return tipsImg; }

    public String getTipsTitle () { return tipsTitle; }

    public String getTipsDescription () { return tipsDescription; }
}
