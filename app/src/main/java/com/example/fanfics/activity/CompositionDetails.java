package com.example.fanfics.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fanfics.JsonPlaceHolderApi;
import com.example.fanfics.R;
import com.example.fanfics.dto.request.ChapterRequestDto;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CompositionDetails extends AppCompatActivity {

    private ImageView image;

    private TextView name;

    private TextView description;

    private TextView chapterName;

    private TextView chapter;

    private Bundle extras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_composition_details);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://172.20.216.146:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

        extras = getIntent().getExtras();

        image = findViewById(R.id.iv_compositionImage);
        name = findViewById(R.id.tv_compositionName);
        description = findViewById(R.id.tv_compositionDesc);
        chapterName = findViewById(R.id.tv_chapterName);

        if (extras != null) {
            Picasso.with(this).load(extras.getString("image")).into(image);
            name.setText(extras.getString("name"));
            description.setText(extras.getString("description"));


            jsonPlaceHolderApi.getChaptersByCompositionName(extras.getString("name")).enqueue(new Callback<List<ChapterRequestDto>>() {
                @Override
                public void onResponse(Call<List<ChapterRequestDto>> call, Response<List<ChapterRequestDto>> response) {
                    List<ChapterRequestDto> list = response.body();
                    chapterName.setText(list.get(0).getName());
                    chapter = findViewById(R.id.tv_chapter);
                    chapter.setMovementMethod(new ScrollingMovementMethod());
                    chapter.setText(list.get(0).getContent());
                    final int[] i = {1};
                    Button nextChapter = findViewById(R.id.b_nextChapter);
                    nextChapter.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Log.i("vlad", i[0] + " " + list.size());
                            if (i[0] == list.size() - 1) {
                                chapterName.setText(list.get(list.size() - 1).getName());
                                chapter = findViewById(R.id.tv_chapter);
                                chapter.setMovementMethod(new ScrollingMovementMethod());
                                chapter.setText(list.get(list.size() - 1).getContent());
                            } else {
                                chapterName.setText(list.get(i[0]).getName());
                                chapter = findViewById(R.id.tv_chapter);
                                chapter.setMovementMethod(new ScrollingMovementMethod());
                                chapter.setText(list.get(i[0]).getContent());
                                i[0] = i[0] + 1;
                            }
                        }
                    });
                }

                @Override
                public void onFailure(Call<List<ChapterRequestDto>> call, Throwable t) {

                }
            });
        }
    }
}