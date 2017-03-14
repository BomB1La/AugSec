package augsec.augsec;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class LoginActivity extends AppCompatActivity{
    private EditText mUsername;
    private View mProgressView;
    private View mLoginFormView;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mUsername = (EditText) findViewById(R.id.username);
        Button mUserSignInButton = (Button) findViewById(R.id.sign_in_button);
        mUserSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });
        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);

    }

    private void attemptLogin() {
        mUsername.setError(null);
        username = mUsername.getText().toString();
        if (TextUtils.isEmpty(username)) {
            mUsername.setError(getString(R.string.error_field_required));
            mUsername.requestFocus();
            return;
        }
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.initiateScan();

        /*
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            try {
                String rcvString;
                Socket clientSocket = new Socket(ip_send, 8090);
                DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
                BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                outToServer.writeBytes("100");
                rcvString = inFromServer.readLine();
                Log.v(TAG,"FROM SERVER: " + rcvString);
                clientSocket.close();
            } catch (Exception asd) {
                out.println(asd.getMessage());
            }
        }
        */
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        try{
            Pattern p = Pattern.compile("^([0-9A-Fa-f]{2}[:-]){5}([0-9A-Fa-f]{2})$");
            Matcher m = p.matcher(scanResult.getContents());
            if (scanResult != null&& m.find()) {
                SettingsManager.getInstance().setUsername(username);
                showProgress(true);
                Intent i = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
            else{
                Toast toast = Toast.makeText(getApplicationContext(), scanResult.getContents() + "\nIs NOT a mac address!", Toast.LENGTH_LONG);
                toast.show();
            }
        } catch (Exception ex){
            Toast toast = Toast.makeText(getApplicationContext(), "No barcode was found..", Toast.LENGTH_LONG);
            toast.show();
        }
    }
}

