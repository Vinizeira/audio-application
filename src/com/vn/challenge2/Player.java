package com.vn.challenge2;
import java.lang.Comparable;

public class Player extends Person implements Comparable<Player>{
    private int score;
    private String tournament;

    public Player(String name, int score, String tournament){
        super(name);
        this.score = score;
        this.tournament = tournament;
    }



    @Override
    public int compareTo(Player otherPlayer) {
        return Integer.compare(otherPlayer.score, this.score);
    }

    @Override
    public String toString(){
        return getName() + " - " + score + " pts (" + tournament + ")";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Player otherPlayer) {
            return this.getName().equals(otherPlayer.getName());
        }
        return false;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getTournament() {
        return tournament;
    }

    public void setTournament(String tournament) {
        this.tournament = tournament;
    }
}
