package com.dae.dragonball.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Set;

import com.dae.dragonball.Play;
import com.dae.dragonball.PlayerVO;

public class Server {
    HashMap<String, ObjectOutputStream> playerMap;
    ArrayList<PlayerVO> players;
//    HashMap<String, PlayerVO> players;
    ServerSocket serverSocket;
    Socket socket;
    ObjectInputStream ois;
    ObjectOutputStream oos;
    Set<String> keySet;

    public Server() {
        playerMap = new HashMap<String, ObjectOutputStream>();
        Collections.synchronizedMap(playerMap);
        players = new ArrayList<PlayerVO>();
        Collections.synchronizedList(players);

    }

    public static void main(String[] args) {
        new Server().start();
    }

    public void start() {

        try {
            serverSocket = new ServerSocket(7777);
            System.out.println("서버 초기화");
            while (true) {

                socket = serverSocket.accept();
                ServerReceiver serverReceiver = new ServerReceiver(socket, playerMap);
                Thread thread = new Thread(serverReceiver);
                thread.start();

            }
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    class ServerReceiver implements Runnable {
        HashMap<String, ObjectOutputStream> playerMap;
        Socket socket;
        ObjectInputStream ois;
        ObjectOutputStream oos;
        PlayerVO player;

        public ServerReceiver(Socket socket, HashMap<String, ObjectOutputStream> playerMap)
                throws ClassNotFoundException {
            this.socket = socket;
            this.playerMap = playerMap;

            try {
                ois = new ObjectInputStream(socket.getInputStream());
                oos = new ObjectOutputStream(socket.getOutputStream());
                player = (PlayerVO) ois.readObject();
                playerMap.put(player.getPlayerName(), oos);
            } catch (IOException e) {

            }
        }

        private void broadcast(PlayerVO players) {
            try {
                for (ObjectOutputStream o : playerMap.values()) {
                    o.writeObject(players);
                    o.flush();
                }
            } catch (IOException e) {

            }
        }

        @Override
        public void run() {
            try {
                while (true) {
                    // 역직렬화

                    System.out.println(Thread.currentThread() + "srrun");
                    player = (PlayerVO) ois.readObject();

                    // 맵에 저장
                    if (players.size() < 2) {
                        System.out.println("add works");
                        players.add(player);
//                        players.add((PlayerVO) playerMap.values());

                    }

                    // 맵에 두 명 넘게 들어오지 못하도록
                    else {

                        System.out.println("게임이 진행중입니다.");
                    }

                    // 두 명이 들어왔으면 상태 비교

                    if (players.size() == 2) {
                        System.out.println(Thread.currentThread() + "atkstart");
                        PlayerVO player1 = players.get(0);
                        PlayerVO player2 = players.get(1);
                        Play.attack(player1, player2);
                        players.clear();
//                        oos.writeObject(player1);
//                        oos.flush();
//                        oos.writeObject(player2);
//                        oos.flush();
                        broadcast(player1);
                        broadcast(player2);

                        System.out.println(Thread.currentThread() + "atkend");

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

}
