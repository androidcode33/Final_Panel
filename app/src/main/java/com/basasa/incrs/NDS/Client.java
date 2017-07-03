package com.basasa.incrs.NDS;

import android.app.Application;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Created by Don Quan on 7/2/2017.
 */

public class Client extends Application{
    //private final static Client instance=new Client();
      int port;
     InetAddress host;
    PrintWriter out=null;
    BufferedReader in=null;
    public Client(){

    }
    public Client(InetAddress dst, int port1){
        port= port1;
        host=dst;
    }

    public void connection(){
        try {

            Socket socket = new Socket(host, port);
            try {

                // send the message to the server
                out = new PrintWriter(new BufferedWriter(
                        new OutputStreamWriter(socket.getOutputStream())), true);

                Log.e("TCP client", "C: Sent.");

                Log.e("TCP client", "C: Done.");

                // receive the message which the server sends back
                in = new BufferedReader(new InputStreamReader(
                        socket.getInputStream()));

            } catch (Exception e) {

                Log.e("TCP", "S: Error", e);

            } finally {
                // the socket must be closed. It is not possible to reconnect to
                // this socket
                // after it is closed, which means a new socket instance has to
                // be created.
                //socket.close();
            }

        } catch (Exception e) {

            Log.e("TCP", "C: Error", e);

        }}
    public PrintWriter startsending(){
        return out;
    }
    public BufferedReader startrecieving(){
        return in;
    }
//    public static Client getInstance(){
//    return instance;
//    }
   }

