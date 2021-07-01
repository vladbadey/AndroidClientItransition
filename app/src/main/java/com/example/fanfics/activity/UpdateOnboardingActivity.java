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

public class UpdateOnboardingActivity extends AppCompatActivity {

    private Button button;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<Fandom> list;
    private final Context context = this;
    private Bundle extras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding);

        JsonPlaceHolderApi jsonPlaceHolderApi = FanficsUtils.getJsonPlaceholder();

        Bundle args = FanficsUtils.getBundle();
        extras = getIntent().getExtras();
        button = findViewById(R.id.b_onboarding);
        recyclerView = findViewById(R.id.rv_onboarding);
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        Call<List<Fandom>> call = jsonPlaceHolderApi.onboarding();

        call.enqueue(new Callback<List<Fandom>>() {
            @Override
            public void onResponse(Call<List<Fandom>> call,
                                   Response<List<Fandom>> response) {
                if (!response.isSuccessful()) {
                    Log.i("vlad", "response not suc");
                    return;
                }
                list = response.body();
                adapter = new OnboardingAdapter(context, list);
                Log.i("vlad", "" + adapter.toString());
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
                ArrayList<Boolean> status = OnboardingAdapter.getStatus();
                List<FandomRequestDto> fandomList = new ArrayList<>();
                for (int i = 0; i < status.size(); i++) {
                    if (status.get(i)) {
                        Fandom fandom = list.get(i);
                        fandomList.add(new FandomRequestDto(fandom.getName(), fandom.getImage()));
                    }
                }
                jsonPlaceHolderApi.updateUserFandoms(args.getString("username"), fandomList).enqueue(new Callback<List<FandomRequestDto>>() {
                    @Override
                    public void onResponse(Call<List<FandomRequestDto>> call, Response<List<FandomRequestDto>> response) {

                    }

                    @Override
                    public void onFailure(Call<List<FandomRequestDto>> call, Throwable t) {

                    }
                });
                Intent intent = new Intent(context, MainActivity.class);
                context.startActivity(intent);
            }
        });
    }
}