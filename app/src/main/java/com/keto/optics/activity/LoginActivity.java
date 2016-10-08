package com.keto.optics.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.keto.optics.R;

public class LoginActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener,
        View.OnClickListener{


    private static final int GOOGLE_SIGN_IN = 1000;
    private static final String className = "LOGIN_ACTIVITY";
    public static final String SIGN_IN_DATA_KEY= "sign_in_data_key";
    public static final String ACCOUNT_BUNDLE_KEY= "account_key";
    GoogleSignInOptions mSignInOptions;
    GoogleApiClient mGoogleApiClient;
    SignInButton mSignInButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initGoogleAPI();
        initViews();
    }

    private void initGoogleAPI(){
        mSignInOptions = new GoogleSignInOptions.Builder()
                .requestEmail()
                .requestIdToken(getString(R.string.client_id))
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, mSignInOptions)
                .enableAutoManage(this,this)
                .build();
    }

    private void initViews(){
        mSignInButton = (SignInButton)findViewById(R.id.sign_in_button);
        mSignInButton.setOnClickListener(this);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.sign_in_button :{
                signIn();
                break;
            }

            default :
        }
    }

    private void signIn(){
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, GOOGLE_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == GOOGLE_SIGN_IN){
            GoogleSignInResult signInResult = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if(signInResult != null && signInResult.isSuccess()){

                GoogleSignInAccount signInAccount = signInResult.getSignInAccount();

                if(signInAccount != null) {
                    Log.d(className, signInAccount.getDisplayName());
                    Log.d(className, signInAccount.getEmail());
                    Log.d(className, signInAccount.getPhotoUrl().toString());
                    openDashboard(signInAccount);
                }
            }
        }
    }

    private void openDashboard(GoogleSignInAccount account){
        Intent intent = new Intent(this, DashboardActivity.class);

        Bundle bundle = new Bundle();
        bundle.putParcelable(SIGN_IN_DATA_KEY, account);
        intent.putExtra(ACCOUNT_BUNDLE_KEY, bundle);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
