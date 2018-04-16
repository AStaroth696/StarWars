package com.example.android.starwars;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private LinearLayout detailLayout;
    private View myView;
    private Animation hideAnim;
    private Animation removeAnim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final RecyclerView characterList = findViewById(R.id.character_list);
        detailLayout = findViewById(R.id.detail_layout);
        myView = findViewById(R.id.dark_view);
        final Animation showAnim = AnimationUtils.loadAnimation(this, R.anim.show);
        hideAnim = AnimationUtils.loadAnimation(this, R.anim.hide);
        final Animation createAnim = AnimationUtils.loadAnimation(this, R.anim.create);
        removeAnim = AnimationUtils.loadAnimation(this, R.anim.remove);

        final TextView nameText = findViewById(R.id.name_text);
        final TextView heightText = findViewById(R.id.height_text);
        final TextView genderText = findViewById(R.id.gender_text);
        final TextView massText = findViewById(R.id.mass_text);
        final TextView starshipsText = findViewById(R.id.starships_text);
        final TextView filmsText = findViewById(R.id.films_text);
        final TextView vehiclesText = findViewById(R.id.vehicles_text);

        final Button openInBrowserButton = findViewById(R.id.browser_button);

        ConnectivityManager cm = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        if (cm.getActiveNetworkInfo() != null
                && cm.getActiveNetworkInfo().isAvailable()
                && cm.getActiveNetworkInfo().isConnected()) {
            new AsyncTask<Void, Void, Void>() {
                List<Character> characters;

                @Override
                protected Void doInBackground(Void... voids) {
                    characters = RequestServiceImpl.getCharacterList();
                    return null;
                }

                @Override
                protected void onPostExecute(Void aVoid) {
                    super.onPostExecute(aVoid);
                    CharacterAdapter adapter = new CharacterAdapter(characters);
                    adapter.setListener(new CharacterAdapter.Listener() {
                        @Override
                        public void itemClicked(final Character character) {
                            nameText.setText(character.getName());
                            heightText.setText(character.getHeight());
                            genderText.setText(character.getGender());
                            massText.setText(character.getMass());
                            starshipsText.setText(String.valueOf(character.getStarships()));
                            filmsText.setText(String.valueOf(character.getFilms()));
                            vehiclesText.setText(String.valueOf(character.getVehicles()));

                            openInBrowserButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Intent intent = new Intent(Intent.ACTION_VIEW);
                                    intent.setData(Uri.parse(character.getUrl()));
                                    startActivity(intent);
                                }
                            });

                            detailLayout.setVisibility(View.VISIBLE);
                            detailLayout.startAnimation(showAnim);
                            myView.setVisibility(View.VISIBLE);
                            myView.startAnimation(createAnim);
                        }
                    });
                    characterList.setAdapter(adapter);
                }
            }.execute();
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            characterList.setLayoutManager(layoutManager);
        }else {
            Toast.makeText(this, getResources().getString(R.string.connectivity_error), Toast.LENGTH_LONG).show();
        }
    }

    public void closeDetails(View view){
        close();
    }

    private void close(){
        detailLayout.startAnimation(hideAnim);
        detailLayout.setVisibility(View.INVISIBLE);
        myView.startAnimation(removeAnim);
        myView.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onBackPressed() {
        if (detailLayout.getVisibility() == View.VISIBLE){
            close();
        }else {
            super.onBackPressed();
        }
    }
}
