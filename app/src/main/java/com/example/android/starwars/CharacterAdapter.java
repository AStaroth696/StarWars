package com.example.android.starwars;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Adapter for filling in the list of characters
 */
public class CharacterAdapter extends RecyclerView.Adapter<CharacterAdapter.ViewHolder> {

    private Listener listener;
    private List<Character> characters;

    public interface Listener{
        public void itemClicked(Character character);
    }

    public CharacterAdapter(List<Character> characters) {
        this.characters = characters;
    }

    public void setListener(Listener listener){
        this.listener = listener;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardView cv = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_layout, parent, false);
        return new ViewHolder(cv);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        CardView cv = holder.cv;
        TextView name = cv.findViewById(R.id.name);
        TextView birthDate = cv.findViewById(R.id.birth_date);
        ImageView gender = cv.findViewById(R.id.gender);
        name.setText(characters.get(position).getName());
        birthDate.setText(characters.get(position).getBirthYear());
        //Set the right gender icon
        switch (characters.get(position).getGender()){
            case "male":
                gender.setImageResource(R.drawable.ic_human_male);
                break;
            case "female":
                gender.setImageResource(R.drawable.ic_human_female);
                break;
        }
        cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.itemClicked(characters.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return characters.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        CardView cv;
        public ViewHolder(CardView cv) {
            super(cv);
            this.cv = cv;
        }
    }
}
