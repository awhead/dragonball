package com.dae.dragonball.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

import com.dae.dragonball.Play;
import com.dae.dragonball.PlayerVO;

public class Client {
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        String serverIp = "127.0.0.1";
        int port = 7777;
        Socket socket;
        Play play = new Play();
        PlayerVO player;
        Boolean isBot;
        String playerName;
        int hp;
        isBot = false;
        System.out.println("이름 입력");
        playerName = scanner.next();
        hp = 3;
        player = new PlayerVO(isBot, playerName, hp);
        System.out.println("1client");

        try {

            System.out.println("2client");

            socket = new Socket(serverIp, port);
            System.out.println("서버와 연결되었습니다");
            Thread sender = new Thread(new ClientSender(socket, player));
            Thread receiver = new Thread(new ClientReceiver(socket));
            sender.start();
            receiver.start();

        } catch (Exception e) {
            // TODO: handle exception
        } finally {

        }
    }

    static class ClientSender implements Runnable {
        Socket socket;
        ObjectOutputStream oos;

        PlayerVO player;
        Boolean isBot;
        String playerName;
        int hp;

        public ClientSender(Socket socket, PlayerVO player) {
            this.socket = socket;
            this.player = player;

            try {
                oos = new ObjectOutputStream(socket.getOutputStream());
                oos.writeObject(player);
                oos.flush();
            } catch (Exception e) {
            }
        }

        @Override
        public void run() {

            System.out.println("csrun");
            Play.clientSender(player);
            try {
                oos.writeObject(player);
                oos.flush();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

    static class ClientReceiver extends Thread {
        Socket socket;
        ObjectInputStream ois;
        PlayerVO player;

        public ClientReceiver(Socket socket) {
            this.socket = socket;
            try {
                ois = new ObjectInputStream(socket.getInputStream());
            } catch (Exception e) {
                // TODO: handle exception
            }
        }

        public synchronized void run() {
            try {

                player = (PlayerVO) ois.readObject();
                System.out.println("crrun");
                Play.printMessage(player);
            } catch (ClassNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } finally {
            }
        }
    }

}
