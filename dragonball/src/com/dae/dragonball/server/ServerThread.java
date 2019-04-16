package com.dae.dragonball.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

import com.dae.dragonball.Play;
import com.dae.dragonball.PlayerVO;

public class ServerThread implements Runnable {
    HashMap<String, ObjectOutputStream> playerMap;
    ArrayList<PlayerVO> players;
    Socket socket;
    ObjectInputStream ois;
    ObjectOutputStream oos;
    PlayerVO player;

    public ServerThread(Socket socket, HashMap<String, ObjectOutputStream> playerMap, ArrayList<PlayerVO> players)
            throws ClassNotFoundException {
        this.socket = socket;
        this.playerMap = playerMap;
        this.players = players;

        try {
            ois = new ObjectInputStream(socket.getInputStream());
            oos = new ObjectOutputStream(socket.getOutputStream());

        } catch (IOException e) {

        }
    }

    private void broadcast(PlayerVO players) {
        try {
            int cnt = 0;
            for (ObjectOutputStream o : playerMap.values()) {
                o.writeObject(players);
                o.flush();
                System.out.println("Player " + players.getPlayerName() + " broadcast is done [" + (++cnt) + "/2]");
            }
        } catch (IOException e) {

        }
    }

    @Override
    public void run() {
        try {
            while (true) {
                // 역직렬화

                System.out.println("run() is now running" + Thread.currentThread());
                player = (PlayerVO) ois.readObject();
                playerMap.put(player.getPlayerName(), oos);
                System.out.println("Player " + player.getPlayerName() + " [actionValue]" + player.getAction());
                Play.kiCount(player);
                if (players.size() < 2) {
                    players.add(player);
                }
                if (players.size() == 2) {
                    System.out.println("attack() is now running");
                    PlayerVO player1 = players.get(0);
                    PlayerVO player2 = players.get(1);
                    players.clear();

                    Play.attack(player1, player2);
                    Play.conWinner(player1, player2);

                    broadcast(player1);
                    broadcast(player2);

                }

            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {

            System.out.println("f");

        }

    }

}