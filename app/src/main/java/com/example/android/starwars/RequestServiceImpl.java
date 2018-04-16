package com.example.android.starwars;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Implementation of the retrofit request interface
 */
public class RequestServiceImpl {
    private static final String BASE_URL = "https://swapi.co/api/";
    private static GsonBuilder gsonBuilder = new GsonBuilder();
    private static Gson gson = gsonBuilder.create();
    private static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build();
    private static RequestService service = retrofit.create(RequestService.class);

    //Receive list of all characters
    public static List<Character> getCharacterList(){
        List<Character> characters = new ArrayList<>();
        Call<JsonElement> call = service.getCharacterList();
        try {
            Response<JsonElement> response = call.execute();
            Log.d("REQUESTSERVICE", response.body().toString());
            JsonObject jsonObject = response.body().getAsJsonObject();
            JsonArray jsonArray = jsonObject.get("results").getAsJsonArray();
            for (JsonElement jsonElement : jsonArray) {
                //Creating Character instance
                jsonObject = jsonElement.getAsJsonObject();
                String name = jsonObject.get("name").getAsString();
                String height = jsonObject.get("height").getAsString();
                String mass = jsonObject.get("mass").getAsString();
                String birthYear = jsonObject.get("birth_year").getAsString();
                String gender = jsonObject.get("gender").getAsString();
                int films = jsonObject.get("films").getAsJsonArray().size();
                int vehicles = jsonObject.get("vehicles").getAsJsonArray().size();
                int starships = jsonObject.get("starships").getAsJsonArray().size();
                String url = jsonObject.get("url").getAsString();
                characters.add(new Character(name, height, mass, birthYear, gender,
                        films, vehicles, starships, url));
            }
        } catch (IOException e) {
            e.printStackTrace();
            Log.d("REQUESTSERVICE", "error: " + e.getMessage());
            return null;
        }
        return characters;
    }
}
