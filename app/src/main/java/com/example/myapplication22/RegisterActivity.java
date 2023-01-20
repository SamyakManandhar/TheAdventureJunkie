package com.example.myapplication22;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import java.io.ByteArrayOutputStream;

public class RegisterActivity extends AppCompatActivity {

    EditText username, password, email, address, phone;
    RadioGroup gender;
    Button register, cancel;
    DatabaseHelper databaseHelper;
    ImageView image;
    Bitmap bitmap;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_layout);
        databaseHelper = new DatabaseHelper(this);
        cancel = findViewById(R.id.cancel);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        email = findViewById(R.id.email);
        address = findViewById(R.id.address);
        phone = findViewById(R.id.phone);
        gender = findViewById(R.id.gender);
        register = findViewById(R.id.register);
        image = findViewById(R.id.image);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = getRegisterValues();
                Intent intent = new Intent(RegisterActivity.this, ViewPagerActivity.class);
                intent.putExtra("id", id);
                startActivity(intent);
                finish();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 5);
            }
        });
    }


//    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
//        try {
//            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
//            GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
//            if (acct != null) {
//                String personName = acct.getDisplayName();
//                //String personEmail = acct.getEmail();
//                //Uri personPhoto = acct.getPhotoUrl();
//                String nameValue=personName.toString();
//                try {
//                   // Bitmap bitmap1 = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), personPhoto);
//
//                    ContentValues contentValues1 = new ContentValues();
//                    contentValues1.put("username", nameValue);
////                    if (bitmap1 != null) {
////                        contentValues1.put("image", getBlob(bitmap1));
////                    }
//                    Long l1 = databaseHelper.insertUser(contentValues1);
//                    String s = l1.toString();
//                    Toast.makeText(this, "Google user registered", Toast.LENGTH_SHORT).show();
//                    Intent intent = new Intent(RegisterActivity.this, ViewPagerActivity.class);
//                    intent.putExtra("id", s);
//                    startActivity(intent);
//                    finish();
//
//                } catch (Exception e) {
//                    Toast.makeText(this, "message", Toast.LENGTH_SHORT);
//                }
//            }
//
//            // Signed in successfully, show authenticated UI.
//            // updateUI(account);
//        } catch (ApiException e) {
//            // The ApiException status code indicates the detailed failure reason.
//            // Please refer to the GoogleSignInStatusCodes class reference for more information.
//            Log.d("message", e.toString());
//        }
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 5 && resultCode == RESULT_OK) {
            bitmap = (Bitmap) data.getExtras().get("data");
            image.setImageBitmap(bitmap);
        }
    }

    public String getRegisterValues() {
        String usernameValue = username.getText().toString();
        String passwordValue = password.getText().toString();
        String emailValue = email.getText().toString();
        String addressValue = address.getText().toString();
        String phoneValue = phone.getText().toString();
        RadioButton checkedBtn = findViewById(gender.getCheckedRadioButtonId());
        String genderValue = checkedBtn.getText().toString();

        ContentValues contentValues = new ContentValues();
        contentValues.put("username", usernameValue);
        contentValues.put("password", passwordValue);
        contentValues.put("email", emailValue);
        contentValues.put("address", addressValue);
        contentValues.put("phone", phoneValue);
        contentValues.put("gender", genderValue);
        if (bitmap != null) {
            contentValues.put("image", getBlob(bitmap));
        }
        Long l = databaseHelper.insertUser(contentValues);
        String i = l.toString();
        Toast.makeText(this, "User registered", Toast.LENGTH_SHORT).show();
        return i;

    }

    public static byte[] getBlob(Bitmap bitmap) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
        byte[] bArray = bos.toByteArray();
        StringBuilder sb = new StringBuilder();
        sb.append("bytearray:");
        sb.append(bArray);
        Log.i("bytearray", sb.toString());
        return bArray;
    }

    public static Bitmap getBitmap(byte[] byteArray) {
        return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
    }
}
