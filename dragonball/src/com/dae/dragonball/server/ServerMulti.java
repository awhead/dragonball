package com.dae.dragonball.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Set;

import com.dae.dragonball.play.PlayerVO;

public class ServerMulti {
    private HashMap<String, ObjectOutputStream> playerMap;
    private ArrayList<PlayerVO> players;
    ServerSocket serverSocket;
    Socket socket;
    ObjectInputStream ois;
    ObjectOutputStream oos;
    Set<String> keySet;

    public ServerMulti() {
        playerMap = new HashMap<String, ObjectOutputStream>();
        players = new ArrayList<PlayerVO>();

        Collections.synchronizedMap(playerMap);
        Collections.synchronizedList(players);

    }

    public static void main(String[] args) {
        new ServerMulti().start();
    }

    public void start() {

        try {
            serverSocket = new ServerSocket(7777);

            System.out.println("서버 초기화");
            int cnt = 0;
            while (true) {
                System.out.println("접속자 수 : " + cnt);

                socket = serverSocket.accept();
                DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
                DataInputStream dis = new DataInputStream(socket.getInputStream());
                if (cnt < 2) {
                    dos.writeUTF("[Notice]서버와 연결되었습니다");
                } else {
                    dos.writeUTF("[Error]서버가 가득 찼습니다.");
                    return;
                }
                cnt++;
                ServerThread serverReceiver = new ServerThread(socket, playerMap, players);
                Thread thread = new Thread(serverReceiver);
                thread.start();

            }

        } catch (Exception e) {
            // TODO: handle exception
        }
    }

}