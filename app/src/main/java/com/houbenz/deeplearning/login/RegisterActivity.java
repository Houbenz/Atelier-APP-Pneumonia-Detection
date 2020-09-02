package com.houbenz.deeplearning.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.houbenz.deeplearning.MainActivity;
import com.houbenz.deeplearning.R;
import com.houbenz.deeplearning.retrofit.ResultUser;
import com.houbenz.deeplearning.retrofit.Singleton;
import com.houbenz.deeplearning.retrofit.UploadService;
import com.houbenz.deeplearning.retrofit.User;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RegisterActivity extends AppCompatActivity {


    @BindView(R.id.emailInput)
    TextInputLayout emailInput;

    @BindView(R.id.passwordInput)
    TextInputLayout passwordInput;

    @BindView(R.id.usernameInput)
    TextInputLayout usernameInput;

    @BindView(R.id.progresBarRegister)
    ProgressBar progresBarRegister;

    @BindView(R.id.registerBtn)
    Button registerBtn;


    TextWatcher textWatcher =new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

        @Override
        public void afterTextChanged(Editable editable) {

            String username=usernameInput.getEditText().getText().toString();
            String password=passwordInput.getEditText().getText().toString();
            String email=emailInput.getEditText().getText().toString();
            if (!username.equals("") && !password.equals("") && !email.equals("")){

                registerBtn.setEnabled(true);
            }else
                registerBtn.setEnabled(false);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);




        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
            ab.setTitle(getString(R.string.register));
        }


            //For checking patterns

        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        emailInput.getEditText().setOnFocusChangeListener((view, b) -> {

            String email = emailInput.getEditText().getText().toString().trim();

            if (!email.matches(emailPattern) && !email.equals("")){
                emailInput.setErrorEnabled(true);
                emailInput.setError(getString(R.string.email_error));
            }else
                emailInput.setErrorEnabled(false);
        });

        passwordInput.getEditText().setOnFocusChangeListener((view, b) -> {
            String password = passwordInput.getEditText().getText().toString();

            if(!(password.length() > 7) && !password.equals("")){
                passwordInput.setError(getString(R.string.password_error));
            } else
                passwordInput.setErrorEnabled(false);
        });

        usernameInput.getEditText().setOnFocusChangeListener((view, b) -> {
            String username = usernameInput.getEditText().getText().toString();

            if(!(username.length() > 7) && !username.equals("")){
                usernameInput.setError(getString(R.string.username_error));
            } else
                usernameInput.setErrorEnabled(false);
        });

        usernameInput.getEditText().addTextChangedListener(textWatcher);
        passwordInput.getEditText().addTextChangedListener(textWatcher);
        emailInput.getEditText().addTextChangedListener(textWatcher);
    }

    @OnClick(R.id.registerBtn)
    void registerBtnClicked(){

        String username=usernameInput.getEditText().getText().toString();
        String password=passwordInput.getEditText().getText().toString();
        String email=emailInput.getEditText().getText().toString();

        if (!username.equals("") && !password.equals("") && !email.equals("")){

            progresBarRegister.setVisibility(View.VISIBLE);

            User user = new User(username, email, password,password);

            Retrofit retrofit = Singleton.retorfitLaravel;
            UploadService uploadService = retrofit.create(UploadService.class);

            Call<ResultUser> callRegister= uploadService.registerUser(user);

            callRegister.enqueue(new Callback<ResultUser>() {
                @Override
                public void onResponse(Call<ResultUser> call, Response<ResultUser> response) {

                    if (response.code() == 200){
                        Log.i("okii",response.body().toString());
                        Toast.makeText(getApplicationContext(),getString(R.string.you_are_registerd),Toast.LENGTH_SHORT).show();
                        getSharedPreferences("user",MODE_PRIVATE).edit().putString("token",response.body().getToken()).apply();

                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);

                    }else{
                        Log.i("okii",response.errorBody()+"  "+response.message()+" "+response.code());
                        Toast.makeText(getApplicationContext(),getString(R.string.error_happended),Toast.LENGTH_SHORT).show();
                    }
                    progresBarRegister.setVisibility(View.GONE);
                }
                @Override
                public void onFailure(Call<ResultUser> call, Throwable t) {
                    Log.i("okii ",t.getMessage());
                    progresBarRegister.setVisibility(View.GONE);
                }});


        }else {
            Toast.makeText(getApplicationContext(),getString(R.string.fill_form),Toast.LENGTH_SHORT).show();
        }
    }



    @OnClick(R.id.loginTextView)
    public void startLoginActivity(){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}



