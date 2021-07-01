package com.example.fanfics.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.fanfics.JsonPlaceHolderApi;
import com.example.fanfics.R;
import com.example.fanfics.dto.request.SignupRequestDto;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SignupActivity extends AppCompatActivity {

    TextView mResponseTv;
    JsonPlaceHolderApi jsonPlaceHolderApi;
    private EditText eUsername;
    private EditText eEmail;
    private EditText ePassword;
    private Button eSignup;
    String username;
    String email;
    String password;
    private final Context context = this;

    public SignupActivity() {}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        mResponseTv = (TextView) findViewById(R.id.tv_response);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://172.20.216.146:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

        eUsername = findViewById(R.id.et_signup_username);
        eEmail = findViewById(R.id.et_signup_email);
        ePassword = findViewById(R.id.et_signup_password);
        eSignup = findViewById(R.id.b_signup_signup);

        eSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                username = eUsername.getText().toString();
                email = eEmail.getText().toString();
                password = ePassword.getText().toString();

                signup(username, email, password);
            }
        });
    }

    private void signup(String username, String email, String password) {
        SignupRequestDto signup = new SignupRequestDto(username, email, password);
        Call<SignupRequestDto> call = jsonPlaceHolderApi.signup(signup);
        call.enqueue(new Callback<SignupRequestDto>() {
            @Override
            public void onResponse(Call<SignupRequestDto> call, Response<SignupRequestDto> response) {
                if (response.isSuccessful()) {
                    Intent intent = new Intent(context, OnboardingActivity.class);
                    intent.putExtra("currentUserEmail", signup.getEmail());
                    context.startActivity(intent);
                } else {
                    showResponse("BAD CREDENTIALS");
                }
            }

            @Override
            public void onFailure(Call<SignupRequestDto> call, Throwable t) {

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