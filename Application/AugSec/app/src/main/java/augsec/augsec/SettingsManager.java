package augsec.augsec;

/**
 * Created by asd on 3/10/2017.
 */

public class SettingsManager {
    private static SettingsManager instance = new SettingsManager();

    private String username = "";
    private String host = "localhost";
    private int port = 8080;

    private SettingsManager(){

    }

    /*public void setup() {

    }*/

    public void setUsername(String username){
        this.username = username;
    }

    public String getUsername(){
        return username;
    }

    public String getHost(){
        return host;
    }

    public int getPort(){
        return port;
    }


    public static  SettingsManager getInstance(){
        return instance;
    }
}
