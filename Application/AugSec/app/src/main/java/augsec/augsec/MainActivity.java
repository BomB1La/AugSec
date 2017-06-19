package augsec.augsec;

import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.List;

import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private GoogleApiClient client;
    private String user_name = null;
    private boolean stopper = false;
    protected OnBackPressedListener onBackPressedListener;
    private static final String TAG = "MyActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        TextView username_txt = (TextView) findViewById( R.id.user_text );
        if (username_txt != null) {
            username_txt.setText(user_name);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ButterKnife.bind(this);
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (onBackPressedListener != null) {
            onBackPressedListener.doBack();
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.c_key) {
            Intent launchIntent = getPackageManager().getLaunchIntentForPackage("com.augsec.AugSec_Key");
            if (launchIntent != null) {
                startActivity(launchIntent);//null pointer check in case package name was not found
            }else{
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("ERROR:\nCan't load key maker!" )
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {}
                        });
            }
            while(NetworkManager.getInstance().getKeyStatus().equals(""));
            if(NetworkManager.getInstance().getKeyStatus().equals("Sent")) {
                Toast toast = Toast.makeText(getApplicationContext(), "all ok!!", Toast.LENGTH_LONG);
                toast.show();
            }else {
                Toast toast = Toast.makeText(getApplicationContext(), "not ok..", Toast.LENGTH_LONG);
                toast.show();
            }
        } else if (id == R.id.r_key) {
            NetworkManager.getInstance().send("970");
            while(NetworkManager.getInstance().getStatus().equals(""));
            if(NetworkManager.getInstance().getStatus().equals("ok")) {
                Intent i = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(i);
                finish();
            }else {
                Toast toast = Toast.makeText(getApplicationContext(), "not ok..", Toast.LENGTH_LONG);
                toast.show();
            }
        } else if (id == R.id.sign_off) {
            NetworkManager.getInstance().send("980");
            while(NetworkManager.getInstance().getStatus().equals(""));
            if(NetworkManager.getInstance().getStatus().equals("ok")) {
                Intent i = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(i);
                finish();
            }else {
                Toast toast = Toast.makeText(getApplicationContext(), "not ok..", Toast.LENGTH_LONG);
                toast.show();
            }
        } else if (id == R.id.off_pc) {
            NetworkManager.getInstance().send("990");
            while(NetworkManager.getInstance().getStatus().equals(""));
            if(NetworkManager.getInstance().getStatus().equals("ok")) {
                Intent i = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(i);
                finish();
            }else {
                Toast toast = Toast.makeText(getApplicationContext(), "not ok..", Toast.LENGTH_LONG);
                toast.show();
            }
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder().setName("Main Page").setUrl(Uri.parse("https://www.google.com/")).build();
        return new Action.Builder(Action.TYPE_VIEW).setObject(object).setActionStatus(Action.STATUS_TYPE_COMPLETED).build();
    }

    public interface OnBackPressedListener { void doBack(); }


    @Override
    public void onStop() {
        super.onStop();
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }
    @Override

    public void onStart() {
        super.onStart();

        File main_file = new File("/storage/emulated/0/Um97");
        if (!main_file.exists()) {
            return;
        }
        readAndDeleteFile(main_file);
    }

    public void readAndDeleteFile(File file) {
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

        NetworkManager.getInstance().setKey(key);
    }
    private boolean isAppRunning(final Context context, final String packageName) {
        final ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        final List<ActivityManager.RunningAppProcessInfo> procInfos = activityManager.getRunningAppProcesses();
        if (procInfos != null) {
            for (final ActivityManager.RunningAppProcessInfo processInfo : procInfos) {
                if (processInfo.processName.equals(packageName)) return true;
            }
        }
        return false;
    }
}

