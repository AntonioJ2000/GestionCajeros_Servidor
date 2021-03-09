package com.gestioncajeros_servidor.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ServerThread extends Thread {

    private Socket socket;
    private DataOutputStream dos;
    private DataInputStream dis;
    private int id;

    public ServerThread(int id) {
        this.id = id;
    }

    @Override
    public void run() {
        try {
            socket = new Socket("127.0.0.1", 24242);
            dos = new DataOutputStream(socket.getOutputStream());
            dis = new DataInputStream(socket.getInputStream());
            System.out.println(id);
            dos.writeUTF("hola");
            String res = "";
            res = dis.readUTF();
            System.out.println(id + ", " + res);
            dos.close();
            dis.close();
            socket.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
