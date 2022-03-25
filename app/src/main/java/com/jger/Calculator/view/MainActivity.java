package com.jger.Calculator.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.jger.groupe5v2.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button boutonCalculer = findViewById(R.id.button_add);
        boutonCalculer.setOnClickListener(view -> lancerActivityCalcul());

    }

    private void lancerActivityCalcul() {
        Intent intent = new Intent(this, CalculActivity.class);
        startActivity(intent);
    }
}