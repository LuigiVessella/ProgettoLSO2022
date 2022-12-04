package com.example.main.ClientServer;

import java.net.*;
import java.util.Scanner;
import java.io.*;

public class  ClientServer
{
    private String host = null;
    private int port = 0;
    private Socket socket = null;
    private BufferedReader inStream = null;
    private OutputStream ostream = null;
    private PrintWriter pwrite = null;

    public ClientServer(String host, int port){
        this.host = host;
        this.port = port;
    }
    public void setUp()
    {
        System.out.println("TalkToC.setUp() invoked");

        try
        {
            socket = new Socket(host, port);
            inStream = new BufferedReader(new InputStreamReader(
                    socket.getInputStream()));
        }
        catch(UnknownHostException e)
        {
            System.err.println("Cannot find host called: " + host);
            e.printStackTrace();
            System.exit(-1);
        }
        catch(IOException e)
        {
            System.err.println("Could not establish connection for " + host);
            e.printStackTrace();
            System.exit(-1);
        }
    }

    public void sendSomeMessage(String stringa) {
        try {
            ostream = socket.getOutputStream();
            pwrite = new PrintWriter(ostream, true);
            pwrite.println(stringa);
            pwrite.flush();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void converse()
    {
        System.out.println("TalkToC.converse() invoked");

        if (socket != null && inStream != null)
        {
            try
            {
                System.out.println(inStream.readLine());
            }
            catch(IOException e)
            {
                System.err.println("Conversation error with host " + host);
                e.printStackTrace();
            }
        }
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