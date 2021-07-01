package com.example.fanfics;

import com.example.fanfics.dto.request.ChapterRequestDto;
import com.example.fanfics.dto.request.CompositionRequestDto;
import com.example.fanfics.dto.request.FandomRequestDto;
import com.example.fanfics.dto.request.LoginRequestDto;
import com.example.fanfics.dto.request.SignupRequestDto;
import com.example.fanfics.model.Composition;
import com.example.fanfics.model.Fandom;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface JsonPlaceHolderApi {

    @GET("api/fandoms/all")
    Call<List<Fandom>> onboarding();

    @GET("api/compositions/sorted")
    Call<List<CompositionRequestDto>> getSortedCompositions();

    @GET("api/compositions/fandom")
    Call<List<CompositionRequestDto>> getCompositionsByFandom(@Query("name") String name);

    @POST("api/auth/signin")
    Call<LoginRequestDto> signin(@Body LoginRequestDto loginRequestDto);

    @GET("api/compositions/all")
    Call<List<CompositionRequestDto>> getAllCompositions();

    @GET("api/compositions/getChapterByName")
    Call<List<ChapterRequestDto>> getChaptersByCompositionName(@Query("name") String name);

    @POST("api/auth/signup")
    Call<SignupRequestDto> signup(@Body SignupRequestDto signupRequestDto);

    @POST("api/fandoms/fill")
    Call<List<FandomRequestDto>> fillUserFandoms(@Query("email") String email, @Body List<FandomRequestDto> fandomRequestDto);

    @POST("api/favorites/add")
    Call<List<Composition>> addNewFavorite(@Query("username") String username, @Query("compositionName") String compositionName);

    @GET("api/favorites/all")
    Call<List<CompositionRequestDto>> getFavoriteCompositions(@Query("name") String name);

    @GET("api/fandoms/user/all")
    Call<List<Fandom>> getUserFandoms(@Query("name") String name);

    @POST("api/fandoms/update")
    Call<List<FandomRequestDto>> updateUserFandoms(@Query("name") String name, @Body List<FandomRequestDto> fandomRequestDto);
}
