package com.example.android.starwars;

/**
 * Character list model
 */
public class Character {
    private String name;
    private String height;
    private String mass;
    private String birthYear;
    private String gender;
    private int films;
    private int vehicles;
    private int starships;
    private String url;

    public Character(String name, String height, String mass, String birthYear, String gender, int films, int vehicles, int starships, String url) {
        this.name = name;
        this.height = height;
        this.mass = mass;
        this.birthYear = birthYear;
        this.gender = gender;
        this.films = films;
        this.vehicles = vehicles;
        this.starships = starships;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public String getHeight() {
        return height;
    }

    public String getMass() {
        return mass;
    }

    public String getBirthYear() {
        return birthYear;
    }

    public String getGender() {
        return gender;
    }

    public int getFilms() {
        return films;
    }

    public int getVehicles() {
        return vehicles;
    }

    public int getStarships() {
        return starships;
    }

    public String getUrl() {
        return url;
    }
}
