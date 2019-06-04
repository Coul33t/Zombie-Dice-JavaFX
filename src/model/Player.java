package model;

public class Player {
    private int score;
    private int score_tmp;
    private int shotgun;
    private String name;

    public Player(String name) {
        this.name = name;
        this.score = 0;
        this.score_tmp = 0;
        this.shotgun = 0;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getScore_tmp() {
        return score_tmp;
    }

    public void setScore_tmp(int score_tmp) {
        this.score_tmp = score_tmp;
    }

    public int getShotgun() {
        return shotgun;
    }

    public void setShotgun(int shotgun) {
        this.shotgun = shotgun;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTotalPoints() {
        return this.score + this.score_tmp;
    }
    public void addPoints(int number) {
        this.score += number;
    }

    public void addPointsTmp(int number) {
        this.score_tmp += number;
    }

    public boolean addOneShotgun() {
        this.shotgun += 1;

        if (this.shotgun > 2) {
            this.shotgun = 0;
            this.score_tmp = 0;
            return true;
        }

        return false;
    }

    public void validatePoints() {
        this.score += this.score_tmp;
        this.score_tmp = 0;
        this.shotgun = 0;
    }
}
