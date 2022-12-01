package com.example.main.ClientServer;

import java.io.DataInputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ClientServer {

    public void foo(){
        String str;
        try {

            ServerSocket ss = new ServerSocket(6666);
            System.out.println("Socket in attesa di messaggi");
            Socket s = ss.accept();
            do {
                DataInputStream dis = new DataInputStream(s.getInputStream());
                str = (String)dis.readUTF();
                System.out.println("message from client: " + str);

            }while(str.equals("termina"));

            ss.close();
        }

        catch(Exception e) {
            e.printStackTrace();
        }
    }
}
