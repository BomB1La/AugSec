package augsec.augsec;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.regex.Pattern;


public  class LoginActivity extends AppCompatActivity {
    public static NetworkManager networks = NetworkManager.getInstance();
    private EditText mUsername;
    private String username;
    public int REQUEST_PERMISSIONS = 0;
    private static final String TAG = "MyActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v(TAG, "onCreate");
        networks.setup();
        Log.v(TAG, "networks working");
        setContentView(R.layout.activity_login);
        mUsername = (EditText) findViewById(R.id.username);
        Button mUserSignInButton = (Button) findViewById(R.id.sign_in_button );
        mUserSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptSignIn();

            }
        });
        requestAppPermissions();
    }

    @Override
    protected void onStart() {
        Log.v(TAG, "onStart");
        File main_file = new File("/storage/emulated/0/Um97");
        if (!main_file.exists()) {
            return;
        }
        Log.v(TAG, "File detected");
        readAndDeleteFile(main_file);
        super.onStart();
    }

    @Override
    protected void onStop() {
        Log.v("->", "onStop");
        super.onStop();
    }

    public void readAndDeleteFile(File file) {
        Log.v(TAG, "readFile");
        String key = "";
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(file));
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append("\n");
                line = br.readLine();
            }

            key = sb.toString();
            br.close();
        } catch(Exception e){
            e.printStackTrace();
        }
        file.deleteOnExit();

        networks.setKey(key);
        Log.v(TAG, "setKey");
    }

    private void attemptSignIn() {
        mUsername.setError(null);
        username = mUsername.getText().toString();
        if (TextUtils.isEmpty(username)) {
            mUsername.setError(getString(R.string.error_field_required));
            mUsername.requestFocus();
            return;
        }

        networks.setUsername(username);
        while(!networks.getUserConn());
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.initiateScan();
    }


    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        try {
            Pattern p = Pattern.compile("^([0-9A-Fa-f]{2}[:-]){5}([0-9A-Fa-f]{2})$");
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            Log.v(TAG, scanResult.getContents());
            networks.send("101"+scanResult.getContents());
            Intent launchIntent = getPackageManager().getLaunchIntentForPackage("com.augsec.AugSec_Key");
            //if (launchIntent != null) {
                startActivityForResult(launchIntent, 1);//null pointer check in case package name was not found
                
            //}
            // Log.v(TAG, "waiting");
            //while(networks.getKeyStatus().equals(""));
            // Log.v(TAG, "done");

            while(networks.getStatus().equals("")){
                Log.v("->", "Waiting for status");
            }

            if(networks.getKeyStatus().equals("Not found")){
                Toast toast = Toast.makeText(getApplicationContext(), "User not found on Lockscreen", Toast.LENGTH_LONG);
                toast.show();
            }else if(networks.getKeyStatus().equals("Sent")){
                Toast toast = Toast.makeText(getApplicationContext(), "User was found or created", Toast.LENGTH_LONG);
                toast.show();

                Intent i = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(i);
                finish();
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

