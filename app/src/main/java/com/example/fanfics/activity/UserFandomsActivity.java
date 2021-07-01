package com.example.fanfics.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.fanfics.FanficsUtils;
import com.example.fanfics.JsonPlaceHolderApi;
import com.example.fanfics.R;
import com.example.fanfics.adapter.OnboardingAdapter;

import java.util.ArrayList;
import java.util.List;

import com.example.fanfics.dto.request.FandomRequestDto;
import com.example.fanfics.model.Fandom;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserFandomsActivity extends AppCompatActivity {

    private Button button;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<Fandom> list;
    private final Context context = this;
    private Bundle extras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_fandoms);

        JsonPlaceHolderApi jsonPlaceHolderApi = FanficsUtils.getJsonPlaceholder();

        extras = getIntent().getExtras();
        Bundle args = FanficsUtils.getBundle();
        button = findViewById(R.id.b_setFandoms);
        recyclerView = findViewById(R.id.rv_onboarding);
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        Call<List<Fandom>> call = jsonPlaceHolderApi.getUserFandoms(args.getString("username"));

        call.enqueue(new Callback<List<Fandom>>() {
            @Override
            public void onResponse(Call<List<Fandom>> call, Response<List<Fandom>> response) {
                list = response.body();
                adapter = new OnboardingAdapter(context, list);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<Fandom>> call, Throwable t) {
                Log.i("vlad", "Incorrect");
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, UpdateOnboardingActivity.class);
//                intent.putExtra("currentUserEmail", args.getString("email"));
                context.startActivity(intent);
            }
        });
    }
}