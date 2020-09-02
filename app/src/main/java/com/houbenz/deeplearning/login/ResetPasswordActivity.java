package com.houbenz.deeplearning.login;

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
import com.houbenz.deeplearning.R;
import com.houbenz.deeplearning.retrofit.Message;
import com.houbenz.deeplearning.retrofit.Singleton;
import com.houbenz.deeplearning.retrofit.UploadService;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ResetPasswordActivity extends AppCompatActivity {



    @BindView(R.id.resetEmail)
    TextInputLayout resetEmail;

    @BindView(R.id.sendEmail)
    Button sendEmail;

    @BindView(R.id.progressBarReset)
    ProgressBar progressBarReset;

    TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

        @Override
        public void afterTextChanged(Editable editable) {

            String emailText= resetEmail.getEditText().getText().toString();
            String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

            if (!emailText.matches(emailPattern) && !emailText.equals("")){

                sendEmail.setEnabled(false);
                resetEmail.setError(getString(R.string.email_error));
            }else{
                sendEmail.setEnabled(true);
                resetEmail.setErrorEnabled(false);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        ButterKnife.bind(this);


        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
            ab.setTitle(R.string.reset);


//        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
//
//        resetEmail.getEditText().setOnFocusChangeListener((view, b) -> {
//
//            String email = resetEmail.getEditText().getText().toString().trim();
//
//            if (!email.matches(emailPattern) && !email.equals("")){
//                resetEmail.setErrorEnabled(true);
//                resetEmail.setError(getString(R.string.email_error));
//            }else
//                resetEmail.setErrorEnabled(false);
//        });

        resetEmail.getEditText().addTextChangedListener(textWatcher);


        sendEmail.setOnClickListener(view -> {

            String email =resetEmail.getEditText().getText().toString();

            if (!email.equals("")){
                progressBarReset.setVisibility(View.VISIBLE);
                sendEmail.setEnabled(false);
                resetEmail.setEnabled(false);
                Retrofit retrofit = Singleton.retorfitLaravel;
                UploadService userService =retrofit.create(UploadService.class);
                Call<Message> callReset =userService.sendEmailResetPassword(email);

                callReset.enqueue(new Callback<Message>() {
                    @Override
                    public void onResponse(Call<Message> call, Response<Message> response) {

                        progressBarReset.setVisibility(View.GONE);
                        Log.i("okii","send mail code :"+response.code());

                        if (response.code() == 200) {

                            Log.i("okii","send mail code :"+response.body().getMessage());
                            Toast.makeText(getApplicationContext(), R.string.mail_sent, Toast.LENGTH_LONG).show();

                        }else {

                            Log.i("okii","send mail code :"+response.errorBody());
                            Toast.makeText(getApplicationContext(), R.string.error_happened, Toast.LENGTH_LONG).show();

                            sendEmail.setEnabled(true);
                            resetEmail.setEnabled(true);
                        }
                    }

                    @Override
                    public void onFailure(Call<Message> call, Throwable t) {
                        Toast.makeText(getApplicationContext(),getString(R.string.verify_cnx),Toast.LENGTH_LONG).show();
                        sendEmail.setEnabled(true);
                        resetEmail.setEnabled(true);
                        progressBarReset.setVisibility(View.GONE);
                    }
                });
            }else{
                Toast.makeText(getApplicationContext(),getString(R.string.fill_form),Toast.LENGTH_LONG).show();
            }
        });
        }

    }
}
