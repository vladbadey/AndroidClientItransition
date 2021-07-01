package com.example.fanfics.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fanfics.FanficsUtils;
import com.example.fanfics.JsonPlaceHolderApi;
import com.example.fanfics.R;
import com.example.fanfics.dto.request.LoginRequestDto;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    TextView mResponseTv;
    JsonPlaceHolderApi jsonPlaceHolderApi;
    private EditText eUsername;
    private EditText ePassword;
    private Button eLogin;
    private Button eSignup;
    private Button eGuest;
    String username;
    String password;
    private final Context context = this;

    public LoginActivity() {}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Bundle args = FanficsUtils.getBundle();
        mResponseTv = (TextView) findViewById(R.id.tv_response);

        jsonPlaceHolderApi = FanficsUtils.getJsonPlaceholder();

        eUsername = findViewById(R.id.et_username);
        ePassword = findViewById(R.id.et_password);
        eLogin = findViewById(R.id.b_login);
        eSignup = findViewById(R.id.b_signup);
        eGuest = findViewById(R.id.b_guest);

        eLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                username = eUsername.getText().toString();

                args.putString("username", username);
                Log.i("ing", args.getString("username"));
                password = ePassword.getText().toString();

                signin(username, password);
            }
        });

        eSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SignupActivity.class);
                context.startActivity(intent);
            }
        });

        eGuest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, GuestMainActivity.class);
                context.startActivity(intent);
            }
        });
    }

    private void signin(String username, String password) {
        LoginRequestDto login = new LoginRequestDto(username, password);
        Call<LoginRequestDto> call = jsonPlaceHolderApi.signin(login);
        call.enqueue(new Callback<LoginRequestDto>() {
            @Override
            public void onResponse(Call<LoginRequestDto> call, Response<LoginRequestDto> response) {
                if (response.isSuccessful()) {
                    Intent intent = new Intent(context, MainActivity.class);
                    context.startActivity(intent);
                } else {
                    showResponse("BAD CREDENTIALS");
                }
            }

            @Override
            public void onFailure(Call<LoginRequestDto> call, Throwable t) {

            }
        });
    }

    public void showResponse(String response) {
        if (mResponseTv.getVisibility() == View.GONE) {
            mResponseTv.setVisibility(View.VISIBLE);
        }
        mResponseTv.setText(response);
    }
}
