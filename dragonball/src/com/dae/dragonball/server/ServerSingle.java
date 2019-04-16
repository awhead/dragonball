package com.dae.dragonball.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import com.dae.dragonball.Play;
import com.dae.dragonball.PlayerVO;

public class ServerSingle {
    static HashMap<String, ObjectOutputStream> playerMap;
    private static ServerSocket serverSocket;

    public static void main(String[] args) {

        ArrayList<PlayerVO> players;
        PlayerVO player;
        ObjectInputStream ois;
        ObjectOutputStream oos;
        Socket socket;

        playerMap = new HashMap<String, ObjectOutputStream>();
        Collections.synchronizedMap(playerMap);
        players = new ArrayList<PlayerVO>();
        Collections.synchronizedList(players);
        try {

            serverSocket = new ServerSocket(7777);
//            ois = new ObjectInputStream(socket.getInputStream());
//            oos = new ObjectOutputStream(socket.getOutputStream());
            System.out.println("서버 초기화");

            while (true) {
                socket = serverSocket.accept();

                ois = new ObjectInputStream(socket.getInputStream());
                oos = new ObjectOutputStream(socket.getOutputStream());

                player = (PlayerVO) ois.readObject();
                playerMap.put(player.getPlayerName(), oos);
                System.out.println("p1" + player.getAction());
                Play.kiCount(player);
                if (players.size() < 2) {
                    players.add(player);
                }
                if (players.size() == 2) {
                    System.out.println(Thread.currentThread() + "atkstart");
                    PlayerVO player1 = players.get(0);
                    PlayerVO player2 = players.get(1);
                    Play.attack(player1, player2);
                    players.clear();

                    broadcast(player1);
                    broadcast(player2);

                }
            }

        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    private static void broadcast(PlayerVO players) {
        try {
            for (ObjectOutputStream o : playerMap.values()) {
                o.writeObject(players);
                o.flush();
            }
        } catch (IOException e) {

        }
    }
}
