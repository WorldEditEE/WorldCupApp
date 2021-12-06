package com.example.worldcupapp;

public class Match {

    private String firstTeam;

    private String secondTeam;

    private String time;

    private String stadiumName;

    private String urlImage;

    public Match(String firstTeam, String secondTeam, String time, String stadiumName, String urlImage) {
        this.firstTeam = firstTeam;
        this.secondTeam = secondTeam;
        this.time = time;
        this.stadiumName = stadiumName;
        this.urlImage = urlImage;
    }

    public Match() {
    }

    public String getFirstTeam() {
        return firstTeam;
    }

    public void setFirstTeam(String firstTeam) {
        this.firstTeam = firstTeam;
    }

    public String getSecondTeam() {
        return secondTeam;
    }

    public void setSecondTeam(String secondTeam) {
        this.secondTeam = secondTeam;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getStadiumName() {
        return stadiumName;
    }

    public void setStadiumName(String stadiumName) {
        this.stadiumName = stadiumName;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    @Override
    public String toString() {
        return "Match{" +
                "firstTeam='" + firstTeam + '\'' +
                ", secondTeam='" + secondTeam + '\'' +
                ", time='" + time + '\'' +
                ", stadiumName='" + stadiumName + '\'' +
                ", urlImage='" + urlImage + '\'' +
                '}';
    }
}
