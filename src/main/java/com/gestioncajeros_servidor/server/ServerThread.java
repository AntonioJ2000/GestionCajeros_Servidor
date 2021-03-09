package com.gestioncajeros_servidor.server;

import com.gestioncajeros_servidor.dao.ClienteCuentaDAO;
import com.gestioncajeros_servidor.dao.ClienteDAO;
import com.gestioncajeros_servidor.dao.CuentaDAO;
import com.gestioncajeros_servidor.dao.OperariosDAO;
import com.gestioncajeros_servidor.model.Cliente;
import com.gestioncajeros_servidor.model.Cliente_Cuenta;
import com.gestioncajeros_servidor.model.Cuenta;
import com.gestioncajeros_servidor.model.Operarios;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ServerThread {
    private final Socket socket;
    private final int idSession;
    private DataOutputStream dos;
    private DataInputStream dis;
    private final ClienteCuentaDAO ccDAO = new ClienteCuentaDAO();
    private final ClienteDAO clDAO = new ClienteDAO();
    private final CuentaDAO cDAO = new CuentaDAO();
    private final OperariosDAO oDAO = new OperariosDAO();
    private Cliente_Cuenta cc;
    private Cliente cl;
    private Cuenta c;
    private Operarios o;

    public ServerThread(Socket socket, int idSession) {
        this.socket = socket;
        this.idSession = idSession;
        try {
            dos = new DataOutputStream(socket.getOutputStream());
            dis = new DataInputStream(socket.getInputStream());
        } catch (IOException ex) {
            disconnect();
        }
    }

    public void disconnect() {
        
    }
    
    public boolean isLogged(){
        boolean result = false;
        String log = "";
        String pass = "";
        
        try{
            log = dis.readUTF();
            if(!log.isEmpty()){
                dos.writeUTF("Login data OK");
            }
            pass = dis.readUTF();
            if(!pass.isEmpty()){
                dos.writeUTF("Password data OK");
            }
            if(!log.isEmpty() && !pass.isEmpty()){
                Operarios op = new Operarios();
                if(o!=null){
                    dos.writeUTF("Login OK");
                    dos.writeUTF(o.getNombre());
                    result=true;
                }else{
                    dos.writeUTF("Login NOT OK");
                }
            }
        }catch(IOException ex){
            ex.printStackTrace();
            disconnect();
        }
        
        return result;
    }
}
