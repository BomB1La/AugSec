package augsec.augsec;

import android.app.AlertDialog;
import android.content.DialogInterface;
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

    private String HOST = "89.34.99.46";
    private int PORT = 8090;

    private Socket socket;
    private PrintWriter writer;
    private BufferedReader reader;
    private String username = "", key = "", keystatus = "";
    private boolean usercon = false;
    private String status_str =  "";

    public NetworkManager() {
        // Read Settings from file (HOST, PORT NUMBER)
    }

    public void setup() {
        if (isConnected()) {
            return;
        }
        new Thread(this).start();
    }

    public void setKey(String key){
        this.key = key;
    }

    public String getKey(){
        return key;
    }

    public String getKeyStatus(){
        return keystatus;
    }

    public String getStatus(){ return status_str;}

    public void setUsername(String username){
        this.username = username;
    }

    public String getUsername(){
        return username;
    }

    public boolean getUserConn(){
        return usercon;
    }

    @Override
    public void run() {
        try {
            socket = new Socket(HOST, PORT);
            writer = new PrintWriter(socket.getOutputStream(), true);
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        String rec = null;
        while (isConnected()) {
            rec = recive();
            if (rec == null) {
                continue;
            }
            handle(rec);
        }
    }

    public void handle(String str) {
        if(str.startsWith("201")) {
            while (getUsername().equals(""));
            Log.v("NM", "Sending Username");
            send("150" + getUsername());
            usercon = true;
        } else if(str.startsWith("411")){ // There are no users - send key to register as an admin
            while (getKey().equals(""));
            Log.v("NM", "Sending Key");
            send("411" + getKey());
            keystatus = "Sent";
        }else if(str.startsWith("401")){ // User found - send login key
            while (getKey().equals(""));
            Log.v("NM", "Sending Key");
            send("401" + getKey());
            keystatus = "Sent";
        }else if(str.startsWith("404")){ // User not found
            Log.v("NM", "User not found");
            keystatus = "Not found";
        } else if(str.startsWith("101")){
            status_str = "ok";
        } else if(str.startsWith("ZZZ")){
            status_str = "bad";
        }else if(str.startsWith("YYY")){
            status_str = "taken";
        }

    }

    public void disconnect() {
        send("DISCONNECT");
    }

    public void send(String msg) {
        if(!isConnected()) {
            return;
        }
        writer.println(msg);
        System.out.println("Sending: " + msg);
    }

    public String recive() {
        if(!isConnected()){
            return null;
        }
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
