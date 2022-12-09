package com.example.main;


import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.main.EntityClasses.Pothole;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class PotholesAdapter extends RecyclerView.Adapter<PotholesAdapter.PotholesHolder> {
    private Context context;
    private ArrayList<Pothole> potholesArrayList;


    public PotholesAdapter(Context context, ArrayList<Pothole> pothArrayList) {
        this.context = context;
        this.potholesArrayList = pothArrayList;
    }

    @NonNull
    @Override
    public PotholesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_pothole, parent, false);
        return new PotholesHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull PotholesAdapter.PotholesHolder holder, int position) {
        Pothole pothole = potholesArrayList.get(position);
        holder.SetDetails(pothole);
    }


    @Override
    public int getItemCount() {
        return potholesArrayList.size();
    }


    //RecensioneHolder
    class PotholesHolder extends RecyclerView.ViewHolder {

        private TextView dateTextView, longitudeTextView , latitudeTextView, userTextView, cityTextView;

        public PotholesHolder(@NonNull View itemView) {
            super(itemView);
            dateTextView = itemView.findViewById(R.id.textViewDate);
            longitudeTextView = itemView.findViewById(R.id.textViewLongitude);
            latitudeTextView = itemView.findViewById(R.id.textViewLati);
            userTextView = itemView.findViewById(R.id.textViewUser);
            cityTextView = itemView.findViewById(R.id.textViewCityAdded);

        }

        void SetDetails(Pothole tmpPothole) {
            //Potremmo aggiungere la città dove è stata rilevata che si dovrebbe prendere dalle coordiante e magari la distanza
            //dall' utente

            dateTextView.setText(tmpPothole.getDate());
            longitudeTextView.setText(tmpPothole.getLongitude().toString());
            latitudeTextView.setText(tmpPothole.getLatitude().toString());
            userTextView.setText(tmpPothole.getUsername());
            cityTextView.setText(getCity(tmpPothole.getLatitude(), tmpPothole.getLongitude()));
        }

        private String getCity(double lat, double longit) {
            Geocoder geocoder = new Geocoder(context, Locale.getDefault());
            try {
                List<Address> addresses = geocoder.getFromLocation(lat, longit, 1);
                String cityName = addresses.get(0).getAddressLine(0);
                return cityName;
            }
            catch (Exception e){
                Log.d("ERRORE", "GEOCODER");
            }
            return null;
        }
    }


}

