package com.dae.dragonball.client;

import java.io.DataInputStream;
import java.net.Socket;
import java.util.Scanner;

import com.dae.dragonball.play.PlayerVO;

public class Client {
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        String serverIp = "127.0.0.1";
        int port = 7777;
        Socket socket;
        PlayerVO player;
        Boolean isBot;
        String playerName;
        int hp;

        isBot = false;
        System.out.println("이름 입력");
        playerName = scanner.next();
        hp = 3;
        player = new PlayerVO(isBot, playerName, hp);

        try {
            socket = new Socket(serverIp, port);

            /*
             * TODO: 서버에서 error 메세지 띄우면 종료하도록 읽기
             */
            DataInputStream dis = new DataInputStream(socket.getInputStream());
            System.out.println(dis.readUTF());

            ClientThread clientThread = new ClientThread(socket, player);
            Thread thread = new Thread(clientThread);
            while (true) {
                thread.start();
            }

        } catch (Exception e) {
        } finally {

        }
    }

}
