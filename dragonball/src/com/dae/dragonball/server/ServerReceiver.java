package com.dae.dragonball.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import com.dae.dragonball.PlayerVO;

public class ServerReceiver {

//    Socket socket;
//    ObjectInputStream ois;
//    ObjectOutputStream oos;
//    PlayerVO player;
//
//    public ServerReceiver(Socket socket) {
//	this.socket = socket;
//
//	try {
//	    ois = new ObjectInputStream(socket.getInputStream());
//	    oos = new ObjectOutputStream(socket.getOutputStream());
//	} catch (IOException e) {
//
//	}
//    }
//
//    @Override
//    public void run() {
//	try {
//	    player = (PlayerVO) ois.readObject();
//	} catch (ClassNotFoundException e) {
//	    // TODO: handle exception
//	} catch (IOException e) {
//	    // TODO Auto-generated catch block
//	    e.printStackTrace();
//	}
//    }

}
