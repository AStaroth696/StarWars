package com.example.android.starwars;

import com.google.gson.JsonElement;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RequestService {

    @GET("people/")
    Call<JsonElement> getCharacterList();

}
