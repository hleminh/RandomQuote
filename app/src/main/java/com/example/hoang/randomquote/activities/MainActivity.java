package com.example.hoang.randomquote.activities;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.hoang.randomquote.R;
import com.example.hoang.randomquote.fragments.QuoteFragment;

public class MainActivity extends AppCompatActivity {

    private QuoteFragment quoteFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        displayStartScreen();
    }

    private void displayStartScreen() {
        quoteFragment = new QuoteFragment();
        changeScreen(quoteFragment, false);
    }

    public void changeScreen(Fragment fragment, boolean addToBackStack) {
        if (addToBackStack)
            getSupportFragmentManager().beginTransaction().replace(R.id.fl_main, fragment).addToBackStack(null).commit();
        else
            getSupportFragmentManager().beginTransaction().replace(R.id.fl_main, fragment).commit();
    }
}
