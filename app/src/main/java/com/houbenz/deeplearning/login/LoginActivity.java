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
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.houbenz.deeplearning.MainActivity;
import com.houbenz.deeplearning.R;
import com.houbenz.deeplearning.retrofit.Message;
import com.houbenz.deeplearning.retrofit.ResultUser;
import com.houbenz.deeplearning.retrofit.RetrofitFactory;
import com.houbenz.deeplearning.retrofit.Singleton;
import com.houbenz.deeplearning.retrofit.UploadService;

import java.io.IOException;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LoginActivity extends AppCompatActivity {


    @BindView(R.id.username)
    TextInputLayout emailInput;

    @BindView(R.id.password)
    TextInputLayout passwordInput;

     @BindView(R.id.loading)
     ProgressBar loadingProgressBar;

     @BindView(R.id.login)
     Button login;

     @BindView(R.id.passwordForgottenTextView)
      TextView passwordForgottenTextView;


     TextWatcher textWatcher = new TextWatcher() {
         @Override
         public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

         @Override
         public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

         @Override
         public void afterTextChanged(Editable editable) {

             String usernameText= emailInput.getEditText().getText().toString();
             String passwordText= passwordInput.getEditText().getText().toString();

             if (!usernameText.equals("") && !passwordText.equals("")){
                 login.setEnabled(true);
             }else
                 login.setEnabled(false);
         }
     };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);


        //to remove TODO
        login.setEnabled(true);

        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
            ab.setTitle(getString(R.string.authentification));
        }


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


        emailInput.getEditText().addTextChangedListener(textWatcher);
        passwordInput.getEditText().addTextChangedListener(textWatcher);



        passwordForgottenTextView.setOnClickListener(view ->{

            Intent intent = new Intent(this, ResetPasswordActivity.class);
            startActivity(intent);
        });
    }


    @OnClick(R.id.login)
    public void LoginButtonClick(){

        //to remove TODO
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        finish();

        String usernameText= emailInput.getEditText().getText().toString();
        String passwordText= passwordInput.getEditText().getText().toString();

        if (!usernameText.equals("") && !passwordText.equals("")){

            loadingProgressBar.setVisibility(View.VISIBLE);

            Retrofit retrofit = Singleton.retorfit;
            UploadService userService =retrofit.create(UploadService.class);

            Call<ResultUser> callLogin =userService.loginUser(usernameText,passwordText);

            callLogin.enqueue(new Callback<ResultUser>() {
                @Override
                public void onResponse(Call<ResultUser> call, Response<ResultUser> response) {
                    if (response.code() == 200){
                        ResultUser resultUser = response.body();
                        Toast.makeText(getApplicationContext(),
                                String.format(Locale.US,"%s %s",
                                        getString(R.string.bonjour), resultUser.getUser().getName()),Toast.LENGTH_LONG).show();

                        getSharedPreferences("user", Context.MODE_PRIVATE)
                                        .edit().putString("token",resultUser.getToken()).apply();

                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                        finish();

                    }else{

                        if (response.code() == 422){


                            try {
                                String error=response.errorBody().string();
                                Log.i("okii"," response errorbody"+ error);
                                Gson gson = new GsonBuilder().setLenient().create();
                                Message message = gson.fromJson(error, Message.class);

                                Toast.makeText(getApplicationContext(),message.getMessage(),Toast.LENGTH_LONG).show();
                            } catch (IOException e) {
                                e.printStackTrace();
                                Log.e("okii",e.getMessage());
                            }
                        }

                    }
                    loadingProgressBar.setVisibility(View.GONE);
                }
                @Override
                public void onFailure(Call<ResultUser> call, Throwable t) {

                    call.cancel();
                    Log.e("okii",t.getMessage());
                    loadingProgressBar.setVisibility(View.GONE);
                    Toast.makeText(getApplicationContext(),getString(R.string.verify_cnx),Toast.LENGTH_LONG).show();
                }
            });
        }else
            Toast.makeText(getApplicationContext(),getString(R.string.fill_form),Toast.LENGTH_LONG).show();




}


    @OnClick(R.id.registerTextView)
    public void startRegisterActivity(){
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
        finish();
    }

    @OnClick(R.id.passwordForgottenTextView)
    public void startPasswordForgottenActivity(){



    }

}
