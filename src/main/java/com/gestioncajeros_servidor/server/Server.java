package com.gestioncajeros_servidor.server;

import com.gestioncajeros_servidor.gescon.Gescon;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private static final int port = 24242;
    private static ServerSocket server;
    
    public static void init(){
        try{
            System.out.println("Inicializando servidor...");
            server = new ServerSocket(port);
            System.out.println("\t[OK]");
            int idSession = 0;
            while(true){
                Socket socket;
                socket = server.accept();
                System.out.println("Nueva conexión entrante: "+socket);
                ((Gescon) new Gescon(socket, idSession)).start();
                idSession++;
            }
        }catch(IOException ex){
            ex.printStackTrace();
        }
    }
}
