package com.example.asd.a_s;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;

import static java.lang.System.out;
/**
 * Created by asd on 12/12/2016.
 */

public class Main extends AppCompatActivity {
    private static final String TAG = "Main";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final EditText Name = (EditText)findViewById(R.id.User_Input);//used only for multi account PC
        final EditText Pass = (EditText)findViewById(R.id.Pass);
        final EditText in_put = (EditText)findViewById(R.id.in_put);
        final EditText ip_snd = (EditText)findViewById(R.id.IP_ToSnd);
        final Button GO_butt = (Button)findViewById(R.id.GO);
        final Button QR_butt = (Button)findViewById(R.id.QR);
        final Button TD_butt = (Button)findViewById(R.id.TD);


        final TextView out_put = (TextView)findViewById(R.id.txtV);
        final Activity activity = this;
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        // when we tap the GO button
        GO_butt.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
                if (networkInfo != null && networkInfo.isConnected()) {
                    try {
                        String rcvString;
                        Socket clientSocket = new Socket(ip_snd.getText().toString(), 8090);
                        DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
                        BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                        outToServer.writeBytes(Name.getText().toString() + " " + Pass.getText().toString() + " " + in_put.getText().toString()+ '\n');
                        rcvString = inFromServer.readLine();
                        Log.v(TAG,"FROM SERVER: " + rcvString);
                        out_put.setText(rcvString);
                        clientSocket.close();
                    } catch (Exception asd) {
                        out.println(asd.getMessage());
                    }
                }
            }
        });

        // when we tap the QR button
        QR_butt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentIntegrator integrator = new IntentIntegrator(activity);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
                integrator.setPrompt("Scan");
                integrator.setCameraId(0);
                integrator.setBeepEnabled(false);
                integrator.setBarcodeImageEnabled(false);
                integrator.initiateScan();
            }
        });

        // when we tap the QR button

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.global, menu);
        return true;
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null) {
            if(result.getContents() == null) {
                Log.d("MainActivity", "Cancelled scan");
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
            } else {
                Log.d("MainActivity", "Scanned");
                Toast.makeText(this, "Scanned: " + result.getContents(), Toast.LENGTH_LONG).show();
            }
        } else {
            // This is important, otherwise the result will not be passed to the fragment
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}

