package com.dae.dragonball.client;

import java.io.DataOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import com.dae.dragonball.play.Play;
import com.dae.dragonball.play.PlayerVO;

public class ClientThread implements Runnable {
    Socket socket;
    ObjectOutputStream oos;
    ObjectInputStream ois;
    PlayerVO player;

    public ClientThread(Socket socket, PlayerVO player) {
        this.socket = socket;
        this.player = player;
        try {
            oos = new ObjectOutputStream(socket.getOutputStream());
            ois = new ObjectInputStream(socket.getInputStream());

        } catch (Exception e) {
        }
    }

    @Override
    public void run() {

        try {
            int cnt = 1;
            System.out.println("\n\n게임을 시작합니다.");
            while (true) {
                System.out.println("========== " + (cnt++) + " 라운드===============");
                Play.initStatus(player);
                Play.inputAction(player);
                oos.writeObject(player);
                oos.flush();
                PlayerVO temp;
                PlayerVO enemy = null;
                for (int i = 0; i < 2; i++) {
                    temp = (PlayerVO) ois.readObject();
                    String tempName = temp.getPlayerName();
                    String playerName = player.getPlayerName();

                    if (tempName.equals(playerName)) {
                        player = temp;
                    } else {
                        enemy = temp;
                    }
                }
                enemy.setPlayerName(enemy.getPlayerName()+"(상대방)");
                Play.printMessage(enemy);
                Play.printMessage(player);
                if (player.isWin() || enemy.isWin()) {
//                    DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
//                    dos.writeUTF("Exit");
                    return;
                }
                System.out.println("================================\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println("경기를 종료합니다.");

        }

    }

}
