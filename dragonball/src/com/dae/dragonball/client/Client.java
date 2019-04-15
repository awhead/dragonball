package com.dae.dragonball.client;

import java.net.Socket;

public class Client {
    public static void main(String[] args) {
	String serverIp = "127.0.0.1";
	int port = 7777;
	Socket socket = new Socket(serverIp, port);

	try {

	    System.out.println("서버와 연결되었습니다");
	    Thread sender = new Thread(new ClientSender(socket, name));
	    Thread receiver = new Thread(new ClientReceiver(socket));
	} catch (Exception e) {
	    // TODO: handle exception
	}
    }
}
