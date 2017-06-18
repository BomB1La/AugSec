package augsec.augsec;

import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Logger;

import static android.webkit.ConsoleMessage.MessageLevel.LOG;

/**
 * Created by asd on 6/17/2017.
 */

public class NetworkManager implements Runnable {
    private static NetworkManager instance = new NetworkManager();

    private String HOST = "localhost";
    private int PORT = 8090;
    private static final String TAG = "Test";

    private Socket socket;
    private PrintWriter writer;
    private BufferedReader reader;

    public NetworkManager() {
        // Read Settings from file (HOST, PORT NUMBER)
    }

    public void setup() {
        Log.v(TAG,"setup");
        if (isConnected()) {
            return;
        }
        new Thread(this).start();
    }

    @Override
    public void run() {
        Log.v(TAG,"before try");
        try {
            socket = new Socket(HOST, PORT);
            writer = new PrintWriter(socket.getOutputStream(), true);
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        Log.v(TAG,"Network Manager is running"); //ok, good to go! run
        //String rec = null;
        //while (isConnected()) {
        //    rec = recive();
        //    if (rec == null) {
        //        continue;
        //    }
        //    handle(rec);
        //}
    }

    //public void handle(String str) {
       // if(str.startsWith("201")){
        //    send("150"); // Client Connection
        //}
    //}

    public void disconnect() {
        send("DISCONNECT");
    }

    public void send(String msg) {
        writer.println(msg);
        System.out.println("Sending: " + msg);
    }

    public String recive() {
        try {
            if (!reader.ready()) {
                return null;
            }
            String rec = reader.readLine();
            System.out.println("Recived: " + rec);
            return rec;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean isConnected() {
        return (socket != null) && socket.isConnected();
    }

    public static NetworkManager getInstance() {
        return instance;
    }
}
