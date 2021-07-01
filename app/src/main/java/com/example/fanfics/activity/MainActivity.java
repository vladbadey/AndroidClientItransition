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
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private Spinner spinner;
    private Button sort;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<CompositionRequestDto> list;
    private final Context context = this;
    private List<Fandom> fandomList = new ArrayList<>();
    private List<String> fandomNames = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://172.20.216.146:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);


        spinner = findViewById(R.id.s_spinner);
        sort = findViewById(R.id.b_sort);
        recyclerView = findViewById(R.id.recyclerViewId);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        Call<List<CompositionRequestDto>> call = jsonPlaceHolderApi.getAllCompositions();

        call.enqueue(new Callback<List<CompositionRequestDto>>() {
            @Override
            public void onResponse(Call<List<CompositionRequestDto>> call,
                                   Response<List<CompositionRequestDto>> response) {
                list = response.body();
                adapter = new CompositionAdapter(context, list);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<CompositionRequestDto>> call, Throwable t) {
                Log.i("vlad", "Incorrect");
            }
        });

        sort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jsonPlaceHolderApi.getSortedCompositions().enqueue(new Callback<List<CompositionRequestDto>>() {
                    @Override
                    public void onResponse(Call<List<CompositionRequestDto>> call, Response<List<CompositionRequestDto>> response) {
                        list = response.body();
                        adapter = new CompositionAdapter(context, list);
                        recyclerView.setAdapter(adapter);
                    }

                    @Override
                    public void onFailure(Call<List<CompositionRequestDto>> call, Throwable t) {

                    }
                });
            }
        });

        jsonPlaceHolderApi.onboarding().enqueue(new Callback<List<Fandom>>() {
            @Override
            public void onResponse(Call<List<Fandom>> call, Response<List<Fandom>> response) {
                if (response.isSuccessful()) {
                    fandomList = response.body();
                    fandomNames.add("Все");
                    for (Fandom f : fandomList) {
                        fandomNames.add(f.getName());
                    }

                    ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, fandomNames);
                    stringArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner.setAdapter(stringArrayAdapter);
                    AdapterView.OnItemSelectedListener itemSelectedListener = new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            String item = (String) parent.getItemAtPosition(position);
                            if (item.equals("Все")) {
                                jsonPlaceHolderApi.getAllCompositions().enqueue(new Callback<List<CompositionRequestDto>>() {
                                    @Override
                                    public void onResponse(Call<List<CompositionRequestDto>> call, Response<List<CompositionRequestDto>> response) {
                                        list = response.body();
                                        adapter = new CompositionAdapter(context, list);
                                        recyclerView.setAdapter(adapter);
                                    }

                                    @Override
                                    public void onFailure(Call<List<CompositionRequestDto>> call, Throwable t) {

                                    }
                                });
                            } else {
                                jsonPlaceHolderApi.getCompositionsByFandom(item).enqueue(new Callback<List<CompositionRequestDto>>() {
                                    @Override
                                    public void onResponse(Call<List<CompositionRequestDto>> call, Response<List<CompositionRequestDto>> response) {
                                        list = response.body();
                                        adapter = new CompositionAdapter(context, list);
                                        recyclerView.setAdapter(adapter);
                                    }

                                    @Override
                                    public void onFailure(Call<List<CompositionRequestDto>> call, Throwable t) {

                                    }
                                });
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                        }
                    };
                    spinner.setOnItemSelectedListener(itemSelectedListener);
                }
            }

            @Override
            public void onFailure(Call<List<Fandom>> call, Throwable t) {

            }
        });
    }
}