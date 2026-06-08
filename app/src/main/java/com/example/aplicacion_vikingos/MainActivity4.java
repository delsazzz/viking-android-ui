package com.example.aplicacion_vikingos;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.button.MaterialButton;

public class MainActivity4 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main4);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main4), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        MaterialButton pagar_dinero = findViewById(R.id.button_1201);

        // Click listener
        pagar_dinero.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity4.this, MainActivity5.class);
            startActivity(intent);
        });

        ImageView atras1 = findViewById(R.id.atras1);

        // Click listener
        atras1.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity4.this, MainActivity3.class);
            startActivity(intent);
        });
    }
}