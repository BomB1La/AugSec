package com.example.asd.a_s;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import static java.lang.System.out;
/**
 * Created by asd on 12/12/2016.
 */
public class Main extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final EditText Name = (EditText)findViewById(R.id.User_Input);//used only for multi account PC
        final EditText Pass = (EditText)findViewById(R.id.Pass);
        final EditText port_toSend = (EditText)findViewById(R.id.PORT);
        final EditText ip_toSend = (EditText)findViewById(R.id.IP_add);
        //final EditText in_put = (EditText)findViewById(R.id.in_put);
        final EditText out_put = (EditText)findViewById(R.id.out_put);
        final Button butt = (Button) findViewById(R.id.GO);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        butt.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
                if (networkInfo != null && networkInfo.isConnected()) {
                    try{
                        Socket socket = new Socket(ip_toSend.getText().toString(), Integer.parseInt(port_toSend.getText().toString()));
                        DataOutputStream DOS = new DataOutputStream(socket.getOutputStream());
                        DOS.writeUTF(Name.getText().length() +  Name.getText().toString() +
                                     Pass.getText().length() + Pass.getText().toString());

                        out_put.setText(socket.getInputStream().toString());
                        socket.close();
                    } catch (IOException asd) {
                        out.println(asd.getMessage());
                    }
                }
            }
        });
    }
}
