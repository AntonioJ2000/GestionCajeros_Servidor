package com.gestioncajeros_servidor.gescon;

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
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Gescon extends Thread {

    private Socket socket;
    private DataOutputStream dos;
    private DataInputStream dis;
    private int idSessio;

    private ClienteDAO clDAO;
    private OperariosDAO oDAO;
    private CuentaDAO cDAO;
    private ClienteCuentaDAO ccDAO;

    public Gescon(Socket socket, int id, ClienteDAO clDAO, OperariosDAO oDAO, CuentaDAO cDAO, ClienteCuentaDAO ccDAO) {
        this.socket = socket;
        this.idSessio = id;
        this.clDAO = clDAO;
        this.oDAO = oDAO;
        this.cDAO = cDAO;
        this.ccDAO = ccDAO;

        try {
            dos = new DataOutputStream(socket.getOutputStream());
            dis = new DataInputStream(socket.getInputStream());
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public void run() {
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
                        Cliente c = clDAO.getClient(login, password);
                        if (c != null) {
                            dos.writeUTF("OK");
                            String op2 = dis.readUTF();
                            System.out.println(op2);
                            do {
                                switch (op2) {
                                    case "1":
                                        System.out.println("CASO 1");
                                        cuenta = this.cDAO.getAccountFromClient(c.getCodigoCliente());
                                        dos.writeUTF(cuenta.toString());
                                        break;
                                    case "2":
                                        System.out.println("CASO 2");
                                        cuenta = this.cDAO.getAccountFromClient(c.getCodigoCliente());
                                        String retirado = dis.readUTF();
                                        int dinero = Integer.parseInt(retirado);
                                        modifyClient(1, cuenta, dinero);
                                        dos.writeUTF("Se ha retirado dinero correctamente");
                                        break;
                                    case "3":
                                        System.out.println("CASO 3");
                                        cuenta = this.cDAO.getAccountFromClient(c.getCodigoCliente());
                                        String dineroIngresado = dis.readUTF();
                                        int dinero2 = Integer.parseInt(dineroIngresado);
                                        modifyClient(2, cuenta, dinero2);
                                        dos.writeUTF("Se ha ingresado dinero correctamente");
                                        break;
                                    case "0":
                                        break;
                                    default:
                                        break;
                                }
                                op2 = dis.readUTF();
                            } while (!op2.equals("0"));
                        } else {
                            dos.writeUTF("Usuario no existente");
                        }
                        break;
                    case "2":
                        login = dis.readUTF();
                        password = dis.readUTF();
                        Operarios o = this.oDAO.getOperario(login, password);
                        if (o != null) {
                            dos.writeUTF("OK");
                            String op2 = dis.readUTF();
                            do {
                                switch (op2) {
                                    case "1":
                                        modifyOperator(1);
                                        dos.writeUTF("El cliente se ha creado correctamente");
                                        break;
                                    case "2":
                                        List<Cliente> clList = this.clDAO.getAllClients();
                                        List<String> idList = new ArrayList<>();
                                        for (Cliente cl : clList) {
                                            idList.add(cl.getCodigoCliente() + "\n");
                                        }
                                        dos.writeUTF(idList.toString());
                                        modifyOperator(2);
                                        dos.writeUTF("La cuenta se ha creado correctamente");
                                        break;
                                    case "3":
                                        List<Cuenta> lc = this.cDAO.getAllAccounts();
                                        List<String> ids2 = new ArrayList<>();
                                        for (Cuenta elem : lc) {
                                            ids2.add(elem.getCodigoCuenta() + "\n");
                                        }
                                        dos.writeUTF(ids2.toString());
                                        String idC = dis.readUTF();
                                        int idCount = Integer.parseInt(idC);
                                        cuenta = this.cDAO.getAccountById(idCount);
                                        dos.writeUTF(cuenta.toString());
                                        break;
                                    case "4":
                                        List<Cliente> lclients = this.clDAO.getAllClients();
                                        List<String> ids3 = new ArrayList<>();
                                        for (Cliente elem : lclients) {
                                            ids3.add(elem.getCodigoCliente() + "\n");
                                        }
                                        dos.writeUTF(ids3.toString());
                                        String idCl = dis.readUTF();
                                        int idCli = Integer.parseInt(idCl);
                                        Cliente cli = this.clDAO.getClientByID(idCli);
                                        dos.writeUTF(cli.toString());
                                        break;
                                    case "5":
                                        List<Cuenta> lcu = this.cDAO.getAllAccounts();
                                        List<String> ids4 = new ArrayList<>();
                                        for (Cuenta elem : lcu) {
                                            ids4.add(elem.getCodigoCuenta() + "\n");
                                        }
                                        dos.writeUTF(ids4.toString());
                                        modifyOperator(3);
                                        dos.writeUTF("La cuenta se ha eliminado correctamente");
                                        break;
                                    case "0":
                                        break;
                                    default:
                                        break;
                                }
                                op2 = dis.readUTF();
                            } while (!op2.equals("0"));
                        } else {
                            dos.writeUTF("Usuario no existente");
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

    public synchronized void modifyClient(int n, Cuenta cuenta, int dinero) {
        switch (n) {
            case 1:
                cuenta.setSaldo(cuenta.getSaldo() - (float) dinero);
                this.cDAO.updateAccountById(cuenta);
                break;
            case 2:
                cuenta.setSaldo(cuenta.getSaldo() + (float) dinero);
                this.cDAO.updateAccountById(cuenta);
                break;
            default:
                break;
        }
    }

    public synchronized void modifyOperator(int n) throws IOException {
        switch (n) {
            case 1:
                String dni = dis.readUTF();
                String nombre = dis.readUTF();
                String apellidos = dis.readUTF();
                String fecha_nac = dis.readUTF();
                String telefono = dis.readUTF();
                String email = dis.readUTF();
                String log = dis.readUTF();
                String password = dis.readUTF();
                Date d = Date.valueOf(fecha_nac);
                Cliente cl = new Cliente(nombre, apellidos, dni, log, password, d, telefono, email);
                this.clDAO.createClient(cl);
                break;
            case 2:
                String idCliente = dis.readUTF();
                int id = Integer.parseInt(idCliente);
                Cliente c = this.clDAO.getClientByID(id);
                String saldo = dis.readUTF();
                int dinero = Integer.parseInt(saldo);
                Cuenta cuenta = new Cuenta(dinero, LocalDateTime.now(), LocalDateTime.now());
                int idCuenta = this.cDAO.insertAccount(cuenta);
                Cliente_Cuenta cc = new Cliente_Cuenta(c.getCodigoCliente(), idCuenta, LocalDateTime.now());
                this.ccDAO.insert(cc);
                break;
            case 3:
                String idAc = dis.readUTF();
                int idAccount = Integer.parseInt(idAc);
                this.cDAO.deleteAccountById(idAccount);
                break;
            default:
                break;
        }
    }

}
