package com.houbenz.deeplearning.login;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.houbenz.deeplearning.R;
import com.houbenz.deeplearning.SettingsActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class LoginRegisterActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_register);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.loginBtn)
    void openLoginActivity(){
        Intent resultIntent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(resultIntent);
    }

    @OnClick(R.id.registerBtn)
    void openRegisterActivity(){
        Intent resultIntent = new Intent(getApplicationContext(), RegisterActivity.class);
        startActivity(resultIntent);
    }

    @OnClick(R.id.settings)
    void openSettingsActivity(){
        Intent resultIntent = new Intent(getApplicationContext(), SettingsActivity.class);
        startActivity(resultIntent);
    }
}

