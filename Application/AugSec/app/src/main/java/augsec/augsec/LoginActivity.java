package augsec.augsec;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Network;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.NetworkInterface;
import java.net.Socket;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.System.out;


public  class LoginActivity extends AppCompatActivity {
    public static NetworkManager networks = NetworkManager.getInstance();
    private EditText mUsername;
    private String username;
    public int REQUEST_PERMISSIONS = 0;

    /*private static final String HOST = "89.34.99.46";
    private static final int PORT = 8090;
    private static Socket socket;
    private static PrintWriter DOS;
    private static DataInputStream DIS;*/

    /*public void send(String msg) {
        out.flush();out.close();
        try { DOS.write(msg); } catch (Exception e) { e.printStackTrace(); }
    }*/
    /*public String receive(){
        try{
           // if (DIS.available() == ) return null;
            String rec = DIS.readUTF();
            return rec;
        } catch (Exception e) { e.printStackTrace(); }
        return null;
    }*/
    /*private boolean isConnected() { return (socket != null) && socket.isConnected(); }*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        networks.setup();


        setContentView(R.layout.activity_login);
        mUsername = (EditText) findViewById(R.id.username);
        Button mUserSignInButton = (Button) findViewById(R.id.sign_in_button );
        mUserSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                //attemptSignIn();
                Intent i = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });
        TextView mUserSignUpButton  = (TextView) findViewById(R.id.sign_up_button);
        mUserSignUpButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptSignUp();
            }
        });
        requestAppPermissions();
    }
    private void attemptSignUp() {
        mUsername.setError(null);
        username = mUsername.getText().toString();
        if (TextUtils.isEmpty(username)) {
            mUsername.setError(getString(R.string.error_field_required));
            mUsername.requestFocus();
            return;
        }
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.initiateScan();
    }

    private void attemptSignIn() {
        mUsername.setError(null);
        username = mUsername.getText().toString();
        if (TextUtils.isEmpty(username)) {
            mUsername.setError(getString(R.string.error_field_required));
            mUsername.requestFocus();
            return;
        }

    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        try {
            Pattern p = Pattern.compile("^([0-9A-Fa-f]{2}[:-]){5}([0-9A-Fa-f]{2})$");
            Matcher m = p.matcher(scanResult.getContents());
            if (scanResult != null && m.find() && networks.isConnected()) {
                networks.send("150"  + mUsername.getText().toString());
                if(networks.recive().startsWith("YYY")){
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setMessage("ERROR:\nSaid user is already made!" )
                    .setPositiveButton("Sign In", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {}
                    });
                } else if(networks.recive().startsWith("ZZZ")){
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setMessage("ERROR:\nThere is no said user!" )
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {}
                    });
                } else if(networks.recive().startsWith("101")){
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setMessage("Good to go:\nWould you like to make a key?" )
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {}
                    });
                }
                //Intent i = new Intent(LoginActivity.this, MainActivity.class);
                //startActivity(i);
                //finish();
            } else {
                Toast toast = Toast.makeText(getApplicationContext(), scanResult.getContents() + "\nIs NOT a MAC address!", Toast.LENGTH_LONG);
                toast.show();
            }
        } catch (Exception ex) {
            Toast toast = Toast.makeText(getApplicationContext(), "No MAC address was found..", Toast.LENGTH_LONG);
            toast.show();
        }

    }
    public void requestAppPermissions() {
        if (ContextCompat.checkSelfPermission(LoginActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) +
            ContextCompat.checkSelfPermission(LoginActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) +
                ContextCompat.checkSelfPermission(LoginActivity.this,  Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale
                    (LoginActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) ||
                    ActivityCompat.shouldShowRequestPermissionRationale
                            (LoginActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) ||
                    ActivityCompat.shouldShowRequestPermissionRationale
                            (LoginActivity.this, Manifest.permission.CAMERA)) {
                Snackbar.make(findViewById(android.R.id.content),
                        "Please Grant Permissions",
                        Snackbar.LENGTH_INDEFINITE).setAction("ENABLE",
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ActivityCompat.requestPermissions(LoginActivity.this,
                                        new String[]{Manifest.permission
                                                .WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.CAMERA},
                                        REQUEST_PERMISSIONS);
                            }
                        }).show();
            } else {
                ActivityCompat.requestPermissions(LoginActivity.this,
                        new String[]{Manifest.permission
                                .WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.CAMERA},
                        REQUEST_PERMISSIONS);
            }
        }
    }
}

