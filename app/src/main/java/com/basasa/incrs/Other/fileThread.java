package com.basasa.incrs.Other;

import android.content.Context;
import android.widget.Toast;

import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by Don Quan on 7/3/2017.
 */

public class fileThread extends Thread{
    public static InetAddress SERVERIP;
    public static int SERVERPORT;
    public final static int SOCKET_PORT = 8888;      // you may change this
    Context makeText;
    // localhost
    public final static String
            FILE_TO_RECEIVED = "c:/temp/source-downloaded.pdf";  // you may change this, I give a
    // different name because i don't want to
    // overwrite the one used by server...
    public static void setVariable(InetAddress dst){
        SERVERIP=dst;
    }
    public final static int FILE_SIZE = 6022386; // file size temporary hard coded
    // should bigger than the file to be downloaded
    public void run() {
// Find the server using UDP broadcast
        int bytesRead;
        int current = 0;
        FileOutputStream fos = null;
        BufferedOutputStream bos = null;
        Socket sock = null;
        try {
            sock = new Socket(SERVERIP, SOCKET_PORT);
            System.out.println("Connecting...");

            // receive file
            byte [] mybytearray  = new byte [FILE_SIZE];
            InputStream is = sock.getInputStream();
            fos = new FileOutputStream(FILE_TO_RECEIVED);
            bos = new BufferedOutputStream(fos);
            bytesRead = is.read(mybytearray,0,mybytearray.length);
            current = bytesRead;

            do {
                bytesRead =
                        is.read(mybytearray, current, (mybytearray.length-current));
                if(bytesRead >= 0) current += bytesRead;
            } while(bytesRead > -1);

            bos.write(mybytearray, 0 , current);
            bos.flush();
            System.out.println("File " + FILE_TO_RECEIVED
                    + " downloaded (" + current + " bytes read)");

            Toast.makeText(makeText,"You A New File Shared"+current,Toast.LENGTH_LONG);

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) try {
                fos.close();
                if (bos != null) bos.close();
                if (sock != null) sock.close();
            }
            catch (IOException e) {
                e.printStackTrace();
            }

        }



    }

    public static fileThread getInstance() {
        return DiscoveryThreadHolder.INSTANCE;
    }

    private static class DiscoveryThreadHolder {

        private static final fileThread INSTANCE = new fileThread();
    }

}
