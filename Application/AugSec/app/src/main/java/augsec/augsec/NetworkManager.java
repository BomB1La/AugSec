package augsec.augsec;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class NetworkManager implements Runnable {

    private static NetworkManager instance = new NetworkManager();

    private String HOST = "localhost";
    private int PORT = 8080;

    private Socket socket;
    private PrintWriter writer;
    private BufferedReader reader;

    public NetworkManager() {
        // Read Settings from file (HOST, PORT NUMBER)
    }

    public void setup() {
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

    public void handle(String str) {
        if (str.startsWith("101")) { //ok (PC IS NOW ACTIVE)

        } else if (str.startsWith("102")) { // denied

        } else if (str.startsWith("103")) { //  error/incorrect

        } else if (str.startsWith("111")) { // REQUEST TO END A STREAM (TeamViewer)

        } else if (str.startsWith("112")) { // Trying to connect

        } else if (str.startsWith("121")) { // POWER OFF PC

        } else if (str.startsWith("122")) { // CREATE LOGIN KEY

        } else if (str.startsWith("131")) { // REQUEST TO START A STREAM (TeamViewer)

        } else if (str.startsWith("132")) { // REQUEST TO END A STREAM (TeamViewer)

        } else if (str.startsWith("141")) { // Trying to connect

        } else if (str.startsWith("142")) { // POWER OFF PC

        } else if (str.startsWith("151")) { // POWER OFF PC

        } else if (str.startsWith("152")) { // POWER OFF PC

        } else if (str.startsWith("153")) { // POWER OFF PC

        } else if (str.startsWith("154")) { // POWER OFF PC

        } else if (str.startsWith("991")) { // POWER OFF PC

        } else if (str.startsWith("992")) { // POWER OFF PC

        } else if (str.startsWith("400")) { // POWER OFF PC

        } else if (str.startsWith("410")) { // POWER OFF PC

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

    public boolean isConnected() {
        return (socket != null) && socket.isConnected();
    }

    public static NetworkManager getInstance() {
        return instance;
    }
}
