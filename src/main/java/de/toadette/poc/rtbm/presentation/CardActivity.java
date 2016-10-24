package de.toadette.poc.rtbm.presentation;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import de.toadette.poc.rtbm.R;


public class CardActivity extends FragmentActivity {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card);
    }
}