package com.houbenz.deeplearning;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

import com.houbenz.deeplearning.login.LoginActivity;
import com.houbenz.deeplearning.login.LoginRegisterActivity;
import com.houbenz.deeplearning.retrofit.Message;
import com.houbenz.deeplearning.retrofit.Singleton;
import com.houbenz.deeplearning.retrofit.UploadService;

import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SplashScreen extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        ButterKnife.bind(this);


        //checkLogin();


        Intent intent = new Intent(getApplicationContext(), LoginRegisterActivity.class);
        startActivity(intent);
        finish();
    }




    private void checkLogin(){
        Retrofit retrofit = Singleton.retorfit;
        UploadService userService =retrofit.create(UploadService.class);

        String token = getSharedPreferences("user", Context.MODE_PRIVATE).getString("token","null");
        Call<Message> checkLoginCall = userService.checkLogin("Bearer "+token);

        checkLoginCall.enqueue(new Callback<Message>() {
            @Override
            public void onResponse(Call<Message> call, Response<Message> response) {

                Log.i("okii","checklogin response code : "+response.code());

                if (response.code() == 200){
                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(intent);
                    finish();
                }else {
                    call.cancel();
                    Log.i("okii","error check login"+ response.errorBody()+" "+response.raw());

                    Intent intent = new Intent(getApplicationContext(), LoginRegisterActivity.class);
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void onFailure(Call<Message> call, Throwable t) {
                Log.i("okii","exception checklogin : "+t.getMessage());


                Intent intent = new Intent(getApplicationContext(),LoginRegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

}