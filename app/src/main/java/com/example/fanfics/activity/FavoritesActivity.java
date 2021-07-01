package com.example.fanfics.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.fanfics.FanficsUtils;
import com.example.fanfics.JsonPlaceHolderApi;
import com.example.fanfics.R;
import com.example.fanfics.dto.request.CompositionRequestDto;

import java.util.ArrayList;
import java.util.List;

import com.example.fanfics.adapter.CompositionAdapter;
import com.example.fanfics.model.Fandom;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FavoritesActivity extends AppCompatActivity {

    private Spinner spinner;
    private Button sort;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<CompositionRequestDto> list;
    private final Context context = this;
    private List<Fandom> fandomList = new ArrayList<>();
    private List<String> fandomNames = new ArrayList<>();
    private Bundle extras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        JsonPlaceHolderApi jsonPlaceHolderApi = FanficsUtils.getJsonPlaceholder();

        Bundle args = FanficsUtils.getBundle();
        extras = getIntent().getExtras();
        spinner = findViewById(R.id.s_spinner);
        sort = findViewById(R.id.b_sort);
        recyclerView = findViewById(R.id.recyclerViewId);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        Call<List<CompositionRequestDto>> call = jsonPlaceHolderApi.getFavoriteCompositions(args.getString("username"));

        call.enqueue(new Callback<List<CompositionRequestDto>>() {
            @Override
            public void onResponse(Call<List<CompositionRequestDto>> call, Response<List<CompositionRequestDto>> response) {
                list = response.body();
                adapter = new CompositionAdapter(context, list);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<CompositionRequestDto>> call, Throwable t) {
                Log.i("vlad", "Incorrect");
            }
        });
    }
}
