package com.gestioncajeros_servidor.server;

import com.gestioncajeros_servidor.dao.ClienteCuentaDAO;
import com.gestioncajeros_servidor.dao.ClienteDAO;
import com.gestioncajeros_servidor.dao.CuentaDAO;
import com.gestioncajeros_servidor.dao.OperariosDAO;
import com.gestioncajeros_servidor.gescon.Gescon;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private static final int port = 24242;
    private static ServerSocket server;

    private static ClienteDAO clDAO;
    private static OperariosDAO oDAO;
    private static CuentaDAO cDAO;
    private static ClienteCuentaDAO ccDAO;

    public static void init() {
        try {
            System.out.println("Inicializando servidor...");
            server = new ServerSocket(port);
            System.out.println("\t[OK]");
            clDAO = new ClienteDAO();
            oDAO = new OperariosDAO();
            cDAO = new CuentaDAO();
            ccDAO = new ClienteCuentaDAO();
            int idSession = 0;
            while (true) {
                Socket socket;
                socket = server.accept();
                System.out.println("Nueva conexión entrante: " + socket);
                ((Gescon) new Gescon(socket, idSession, clDAO, oDAO, cDAO, ccDAO)).start();
                idSession++;
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
