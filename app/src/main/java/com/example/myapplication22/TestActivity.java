package com.example.myapplication22;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.FileNotFoundException;
import java.io.IOException;

public class TestActivity extends AppCompatActivity {
    ImageView pic;
    TextView name, email;
    GoogleSignInClient mGoogleSignInClient;
    Button cancel, ok;
    DatabaseHelper databaseHelper;
    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        databaseHelper = new DatabaseHelper(this);
        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        pic = findViewById(R.id.pic);
        cancel = findViewById(R.id.cancel);
        ok = findViewById(R.id.ok);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        if (acct != null) {
            String personName = acct.getDisplayName();
            String personEmail = acct.getEmail();
            Uri personPhoto = acct.getPhotoUrl();
            name.setText(personName);
            email.setText(personEmail);
            Glide.with(this)
                    .asBitmap()
                    .load(String.valueOf(personPhoto))
                    .into(new CustomTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                            pic.setImageBitmap(resource);
                        }

                        @Override
                        public void onLoadCleared(@Nullable Drawable placeholder) {
                        }
                    });
//
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TestActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

            ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String usernameValue = name.getText().toString();
                    String emailValue = email.getText().toString();
                    String i = null;
                    if (databaseHelper.isLoginSuccessful(usernameValue)) {
                        i = databaseHelper.getUserID(usernameValue);
                        Toast.makeText(getApplicationContext(), "User exists", Toast.LENGTH_SHORT).show();
                    } else {
//                        bitmap=RegisterActivity.getBitmap(pic);
//                        try {
//                            bitmap = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), personPhoto);
//                        } catch (FileNotFoundException e) {
//                            e.printStackTrace();}
                        BitmapDrawable drawable = (BitmapDrawable) pic.getDrawable();
                        bitmap = drawable.getBitmap();

                        ContentValues contentValues = new ContentValues();
                        contentValues.put("username", usernameValue);
                        contentValues.put("email", emailValue);
                        if (bitmap != null) {
                            contentValues.put("image", RegisterActivity.getBlob(bitmap));
                        }

                        Long l = databaseHelper.insertUser(contentValues);
                        i = l.toString();
                        Toast.makeText(getApplicationContext(), "User registered", Toast.LENGTH_SHORT).show();
                    }
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }}
                    Intent intent = new Intent(TestActivity.this, ViewPagerActivity.class);
                    intent.putExtra("id", i);
                    startActivity(intent);
                    finish();
                }

            });



        }
    }
}