package com.example.socialmedia;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.List;

public class LoginActivity extends AppCompatActivity {

    private static int RC_SIGN_IN = 1;
    static FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();

        mAuth = FirebaseAuth.getInstance();
        mAuth.signOut();


        createSignInIntent();
    }

    private void createSignInIntent() {

        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.GoogleBuilder().build(),
                new AuthUI.IdpConfig.FacebookBuilder().build(),
                new AuthUI.IdpConfig.TwitterBuilder().build());

        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .setIsSmartLockEnabled(false)
                        .setAlwaysShowSignInMethodScreen(true)
                        .setTheme(R.style.Theme)
                        .setLogo(R.drawable.welcome)
                        .build(),
                RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == RC_SIGN_IN) {

            if(resultCode == RESULT_OK) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                String url = user.getPhotoUrl().toString();

                Log.i("ProviderID",user.getProviderId()+" ");

                if(url != null) {
                    if (url.contains("google")) {
                        url = url.replace("s96-c", "s300-c");
                    } else if (url.contains("facebook")) {
                        url = url.concat("?type=large");
                    } else if(url.contains("twimg")){
                        url = url.replace("_normal","");
                    }
                }

                Intent gotoPersonalInfo = new Intent(getApplicationContext(),PersonalInfoActivity.class);
                gotoPersonalInfo.putExtra("name",user.getDisplayName());
                gotoPersonalInfo.putExtra("email",user.getProviderData().get(1).getEmail());

                if(url!=null)
                    gotoPersonalInfo.putExtra("photo",url);

                startActivity(gotoPersonalInfo);
                finish();
            } else {
                Toast.makeText(this, "Sign in failed!", Toast.LENGTH_SHORT).show();
            }
        }
    }



}
