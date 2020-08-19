package com.example.socialmedia;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

public class PersonalInfoActivity extends AppCompatActivity {

    TextView nameTextView,emailTextView;
    ImageView profileImageView,bgImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_info);
        getSupportActionBar().hide();

        nameTextView = findViewById(R.id.nameTextView);
        emailTextView =findViewById(R.id.emailTextView);
        bgImageView = findViewById(R.id.bgImageView);
        profileImageView = findViewById(R.id.profileImageView);

        Log.i("Name"," "+getIntent().getStringExtra("name"));
        Log.i("Email"," "+getIntent().getStringExtra("email"));
        Log.i("Photo"," "+getIntent().getStringExtra("photo"));

        nameTextView.setText(getIntent().getStringExtra("name"));
        emailTextView.setText(getIntent().getStringExtra("email"));

        if(getIntent().getStringExtra("photo") != null) {
            Glide.with(this).load(new ColorDrawable(Color.parseColor("#0091ea"))).circleCrop().into(bgImageView);
            Glide.with(this).load(getIntent().getStringExtra("photo")).circleCrop().apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.NONE)
            .skipMemoryCache(true)).into(profileImageView);
        }
    }

    public void signOut(View view){
        LoginActivity.mAuth.signOut();
        Intent back = new Intent(this,LoginActivity.class);
        startActivity(back);
        finish();
    }
}
