package com.dae.dragonball;

public class Main {

    public static void main(String[] args) {
        // PlayerVO(boolean isPlayerBot, String playerName, int hp)
        PlayerVO[] players = { new PlayerVO(false, "손오공", 3), new PlayerVO(true, "피콜로bot", 3) };
        Play play = new Play();
        play.singlePlay(players);
    }

}
