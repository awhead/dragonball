package com.dae.dragonball.client;

import java.io.ObjectOutputStream;
import java.net.Socket;

import com.dae.dragonball.Play;
import com.dae.dragonball.PlayerVO;

public class ClientSender implements Runnable {
    Socket socket;
    PlayerVO player;
    ObjectOutputStream oos;
    Play play = new Play();

    public ClientSender(Socket socket, PlayerVO player) {
	this.socket = socket;
	this.player = player;

	try {
	    oos = new ObjectOutputStream(socket.getOutputStream());

	} catch (Exception e) {
	}
    }

    @Override
    public void run() {
	try {
	    play.clientSender(player);
	    oos.writeObject(player);
	    oos.flush();

	} catch (Exception e) {
	    e.printStackTrace();
	}

    }

}
