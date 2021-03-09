package com.gestioncajeros_servidor.gescon;

import com.gestioncajeros_servidor.dao.ClienteDAO;
import com.gestioncajeros_servidor.dao.OperariosDAO;
import com.gestioncajeros_servidor.model.Cliente;
import com.gestioncajeros_servidor.model.Cuenta;
import com.gestioncajeros_servidor.model.Operarios;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Gescon extends Thread{
    private Socket socket;
    //private DAO dao;
    private DataOutputStream dos;
    private DataInputStream dis;
    private int idSessio;
    
    private ClienteDAO cDAO;
    private OperariosDAO oDAO;
    
    public Gescon(Socket socket, int id) {
        this.socket = socket;
        this.idSessio = id;
        //this.dao = dao;
        
        cDAO = new ClienteDAO();
        oDAO = new OperariosDAO();
        
        try {
            dos = new DataOutputStream(socket.getOutputStream());
            dis = new DataInputStream(socket.getInputStream());
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }
    
    @Override
    public void run(){
        String op = "";
        String login = "";
        String password = "";
        Cuenta cuenta = new Cuenta();

        try {
            op = dis.readUTF();
            do {
                switch (op) {
                    case "1":
                        login = dis.readUTF();
                        password = dis.readUTF();
                        Cliente c = cDAO.getClient(login, password);
                        if (c != null) {
                            dos.writeUTF("OK");
                            String op2 = dis.readUTF();
                            System.out.println(op2);
                            /*do {
                                switch (op2) {
                                    case "1":
                                        System.out.println("CASO 1");
                                        cuenta = this.dao.getCountByClient(c.getCodigoCliente());
                                        dos.writeUTF(cuenta.toString());
                                        break;
                                    case "2":
                                        System.out.println("CASO 2");
                                        cuenta = this.dao.getCountByClient(c.getCodigoCliente());
                                        String dineroRetirado = dis.readUTF();
                                        int dinero = Integer.parseInt(dineroRetirado);
                                        accesoCliente(1, cuenta, dinero);
                                        dos.writeUTF("Se ha retirado el dinero corretamente");
                                        break;
                                    case "3":
                                        System.out.println("CASO 3");
                                        cuenta = this.dao.getCountByClient(c.getCodigoCliente());
                                        String dineroIngresado = dis.readUTF();
                                        int dinero2 = Integer.parseInt(dineroIngresado);
                                        accesoCliente(2, cuenta, dinero2);
                                        dos.writeUTF("Se ha ingresado el dinero corretamente");
                                        break;
                                    case "0":
                                        break;
                                    default:
                                        break;
                                }
                                op2 = dis.readUTF();
                            } while (!op2.equals("0"));*/
                        } else {
                            dos.writeUTF("No existe el usuario con esas credenciales");
                        }
                        break;
                    case "2":
                        login = dis.readUTF();
                        password = dis.readUTF();
                        Operarios o = this.oDAO.getOperario(login, password);
                        if (o != null) {
                            dos.writeUTF("OK");
                            String op2 = dis.readUTF();
                            /*do {
                                switch (op2) {
                                    case "1":
                                        accesoOperario(1);
                                        dos.writeUTF("Se ha creado satisfactoriamente el cliente");
                                        break;
                                    case "2":
                                        List<Cliente> lcl = this.dao.getAllCliente();
                                        List<String> ids = new ArrayList<>();
                                        for (Cliente elem : lcl) {
                                            ids.add(elem.getCodigoCliente() + "\n");
                                        }
                                        dos.writeUTF(ids.toString());
                                        accesoOperario(2);
                                        dos.writeUTF("Se ha creado satisfactoriamente la cuenta");
                                        break;
                                    case "3":
                                        List<Cuenta> lc = this.dao.getAllCuenta();
                                        List<String> ids2 = new ArrayList<>();
                                        for (Cuenta elem : lc) {
                                            ids2.add(elem.getCodigoCuenta() + "\n");
                                        }
                                        dos.writeUTF(ids2.toString());
                                        String idC = dis.readUTF();
                                        int idCount = Integer.parseInt(idC);
                                        cuenta = this.dao.getByIDCuenta(idCount);
                                        dos.writeUTF(cuenta.toString());
                                        break;
                                    case "4":
                                        List<Cliente> lclients = this.dao.getAllCliente();
                                        List<String> ids3 = new ArrayList<>();
                                        for (Cliente elem : lclients) {
                                            ids3.add(elem.getCodigoCliente() + "\n");
                                        }
                                        dos.writeUTF(ids3.toString());
                                        String idCl = dis.readUTF();
                                        int idCli = Integer.parseInt(idCl);
                                        Cliente cli = this.dao.getByIDCliente(idCli);
                                        dos.writeUTF(cli.toString());
                                        break;
                                    case "5":
                                        List<Cuenta> lcu = this.dao.getAllCuenta();
                                        List<String> ids4 = new ArrayList<>();
                                        for (Cuenta elem : lcu) {
                                            ids4.add(elem.getCodigoCuenta() + "\n");
                                        }
                                        dos.writeUTF(ids4.toString());
                                        accesoOperario(3);
                                        dos.writeUTF("Se ha eliminafo correctamente la cuenta");
                                        break;
                                    case "0":
                                        break;
                                    default:
                                        break;
                                }
                                op2 = dis.readUTF();
                            } while (!op2.equals("0"));*/
                        } else {
                            dos.writeUTF("No existe el usuario con esas credenciales");
                        }
                        break;
                    case "0":

                        break;
                    default:
                        break;
                }
                op = dis.readUTF();
            } while (op != "0");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
    }

}
