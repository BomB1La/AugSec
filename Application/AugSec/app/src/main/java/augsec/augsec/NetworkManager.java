package augsec.augsec;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class NetworkManager implements Runnable {

    private static NetworkManager instance = new NetworkManager();

    private static final String HOST = "localhost";
    private static final int PORT = 8080;

    private static Socket socket = null;
    private static PrintWriter writer = null;
    private static BufferedReader reader = null;

    public void setUp() {
        try {
            socket = new Socket(HOST, PORT);
            writer = new PrintWriter(socket.getOutputStream(), true);
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        new Thread(this).start();
    }

    @Override
    public void run() {
        while (isConnected()) {
            String rec = receive();
            if (rec == null) {
                continue;
            }
            handle(rec);
        }
    }

    private void handle(String str) {
        if (str.startsWith("101")) {

        } else if (str.startsWith("111")) {

        } else if (str.startsWith("121")) {

        } else if (str.startsWith("131")) {

        } else if (str.startsWith("141")) {

        } else if (str.startsWith("151")) {

        } else if (str.startsWith("153")) {

        } else if (str.startsWith("981")) {

        } else if (str.startsWith("102")) {

        }
    }

    public void send(String msg) {
        writer.println(msg);
    }

    public String receive() {
        try {
            if (!reader.ready()) {
                return null;
            }
            return reader.readLine();
        } catch (Exception e) {

        }
        return null;
    }

    private boolean isConnected() {
        return (socket != null) && socket.isConnected();
    }

    public static NetworkManager getInstance() {
        return instance;
    }
}
