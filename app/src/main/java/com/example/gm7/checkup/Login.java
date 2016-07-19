package com.example.gm7.checkup;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * Created by GM7 on 03/07/2016.
 */

public class Login extends Activity {
    EditText txt1, txt2;
    ImageView img;
    Button btn1, btn2, btn3, btn4, btn5;
    LoginDataBaseAdapter loginDataBaseAdapter;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loginDataBaseAdapter = new LoginDataBaseAdapter(this);
        loginDataBaseAdapter = loginDataBaseAdapter.open();
        txt1 = (EditText) findViewById(R.id.editText);
        txt2 = (EditText) findViewById(R.id.editText2);
        img = (ImageView) findViewById(R.id.errormessage);
        btn1 = (Button) findViewById(R.id.btn);
        btn2 = (Button) findViewById(R.id.btn_face);
        btn3 = (Button) findViewById(R.id.btn_twitter);
        btn4 = (Button) findViewById(R.id.btn_google);
        btn5 = (Button) findViewById(R.id.btn_signup);
        btn1.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // get The User name and Password
                String userName = txt1.getText().toString();
                String password = txt2.getText().toString();

                // fetch the Password form database for respective user name
                String storedPassword = loginDataBaseAdapter.getSinlgeEntry(userName);
                String storedPassword1 = loginDataBaseAdapter.getSinlgeEnt(userName);
                //
                if (txt1.getText().toString().isEmpty()) {
                    txt1.setError("UserName Should not be blank");
                    return;
                }
                if (txt2.getText().toString().isEmpty()) {
                    txt2.setError("Password Should not be blank");
                    return;
                }

                // check if the Stored password matches with  Password entered by user
                else if (password.equals(storedPassword) || password.equals(storedPassword1)) {
                    // progressDialog = ProgressDialog.show(Login.this,"Please wait", "Sending mail", true, false);
                    // progressDialog = ProgressDialog.show(Login.this, "", "Loading...");
                    final ProgressDialog progressDialog = new ProgressDialog(Login.this);
                    progressDialog.setIndeterminate(true);
                    progressDialog.setMessage("Authenticating...");
                    progressDialog.show();
                    new android.os.Handler().postDelayed(
                            new Runnable() {
                                public void run() {
                                    // On complete call either onLoginSuccess or onLoginFailed
                                    //onLoginSuccess();
                                    // onLoginFailed();
                                    // Toast.makeText(getBaseContext(), "Congrats: Login Successfull", Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(Login.this, MainActivity.class);
                                    startActivity(intent);
                                    img.setVisibility(View.INVISIBLE);
                                    progressDialog.dismiss();
                                }
                            }, 3000);


                } else {
                    img.setVisibility(View.VISIBLE);
                    Toast.makeText(getBaseContext(), "User Name or Password does not match", Toast.LENGTH_LONG).show();
                }


            }
        });
        //


        // }
        //});
        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentSignUP = new Intent(getApplicationContext(), SignUPActivity.class);
                startActivity(intentSignUP);
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onResume() {
        super.onPostResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Close The Database
        loginDataBaseAdapter.close();
    }
}
