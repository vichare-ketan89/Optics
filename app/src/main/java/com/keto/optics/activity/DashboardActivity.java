package com.keto.optics.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.keto.optics.R;
import com.keto.optics.utils.DownloadImageTask;
import com.keto.optics.utils.ExcelUtils;

import java.io.File;
import java.io.IOException;
import java.util.Locale;

import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;


@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
public class DashboardActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    TextView userNameTextView, userEmailIdTextView;
    ImageView userProfileImageView;
    GoogleSignInOptions mSignInOptions;
    GoogleApiClient mGoogleApiClient;

    // Storage Permissions
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            android.Manifest.permission.READ_EXTERNAL_STORAGE,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View navHeaderView = navigationView.getHeaderView(0);

        initViews(navHeaderView);
        getData();

        verifyStoragePermissions(this);
    }

    private void initViews(View navigationHeaderView){

        //View navigationHeaderView = getLayoutInflater().inflate(R.layout.nav_header_dashboard,null);
        userNameTextView = (TextView) navigationHeaderView.findViewById(R.id.user_full_name_text_view);
        userEmailIdTextView  = (TextView) navigationHeaderView.findViewById(R.id.user_email_id_text_view);
        userProfileImageView = (ImageView) navigationHeaderView.findViewById(R.id.user_profile_imageView);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mSignInOptions = new GoogleSignInOptions.Builder()
                .requestIdToken(getString(R.string.client_id))
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, mSignInOptions)
                .build();

        mGoogleApiClient.connect();
    }

    private void getData(){
        Bundle accountBundle = getIntent().getExtras().getBundle(LoginActivity.ACCOUNT_BUNDLE_KEY);

        if(accountBundle != null) {
            GoogleSignInAccount account = (GoogleSignInAccount) accountBundle.get(LoginActivity.SIGN_IN_DATA_KEY);

            if(account != null){
                userNameTextView.setText(account.getDisplayName());
                userEmailIdTextView.setText(account.getEmail());
                if(account.getPhotoUrl() != null) {
                    new DownloadImageTask(userProfileImageView).execute(account.getPhotoUrl().toString());
                }

            }
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.dashboard, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_generate_excel) {
            generateExcelFile();

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        } else if (id == R.id.nav_signout) {
            signOut();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void signOut(){
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(@NonNull Status status) {
                if(status != null){
                    if(status.isSuccess()){
                        finish();
                    }
                }
            }
        });
    }

    private void generateExcelFile(){
        File file = new File(Environment.getExternalStorageDirectory() + File.separator + "test.xls");
        try {
            file.createNewFile();

            WorkbookSettings wbSettings = new WorkbookSettings();
            wbSettings.setLocale(new Locale("en", "EN"));
            WritableWorkbook workbook = Workbook.createWorkbook(file, wbSettings);

            workbook.createSheet("Report", 0);
            WritableSheet excelSheet = workbook.getSheet(0);
            ExcelUtils.createLabel(excelSheet);
            ExcelUtils.createContent(excelSheet);

            workbook.write();
            workbook.close();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (WriteException ex){
            ex.printStackTrace();
        }
    }

    /**
     * Checks if the app has permission to write to device storage
     *
     * If the app does not has permission then the user will be prompted to grant permissions
     *
     * @param activity
     */
    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }

}
