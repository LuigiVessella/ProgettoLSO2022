package com.example.main.ClientServer;

import android.util.Log;
import android.widget.Toast;

import com.example.main.MainActivity;

import java.net.*;
import java.io.*;
import java.util.ArrayList;

public class  ClientServer
{
    private String host = null;
    private int port = 0;
    private Socket socket = null;
    private BufferedReader inStream = null;
    private OutputStream ostream = null;
    private PrintWriter pwrite = null;
    public static boolean isOn = false;

    public ClientServer(String host, int port){
        this.host = host;
        this.port = port;
    }
    public void setUp()
    {
        isOn = true;
        System.out.println("Connessione con socket stabilita");

        try
        {
            MainActivity.isHostOnline = true;
            socket = new Socket(host, port);
            inStream = new BufferedReader(new InputStreamReader(
                    socket.getInputStream()));
        }
        catch(UnknownHostException e)
        {
            System.err.println("Cannot find host called: " + host);
            e.printStackTrace();
            MainActivity.isHostOnline = false;

        }
        catch(IOException e)
        {
            System.err.println("Could not establish connection for " + host);
            e.printStackTrace();
            MainActivity.isHostOnline = false;

        }
    }

    public void sendSomeMessage(String stringa) {
        try {
            Log.v("sto inviando ", stringa);
            ostream = socket.getOutputStream();
            pwrite = new PrintWriter(ostream, true);
            pwrite.println(stringa);
            pwrite.flush();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public ArrayList<String> converse()
    {
        //ricevo i record dal server
        System.out.println("sto ricevendo i record");
        ArrayList<String> records = new ArrayList<>();
        String line = "";

        do {
            try {
                line = inStream.readLine();
                if (line == null) return null; // socket closed
                if (line.isEmpty()) break; // end of headers reached
                // process line as needed...
                records.add(line);
                System.out.println(line);
            } catch (Exception i) {};
        } while (true);
        System.out.println("il primo:" + records.get(1).toString());
        return records;
    }

    public void cleanUp()
    {
        try
        {
            if (inStream != null)
                inStream.close();
            if (socket != null)
                socket.close();
        }
        catch(IOException e)
        {
            System.err.println("Error in cleanup");
            e.printStackTrace();
            System.exit(-1);
        }
    }
}