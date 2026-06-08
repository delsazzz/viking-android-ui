package com.example.aplicacion_vikingos;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity3 extends AppCompatActivity {

    private ImageView boton3;

    // 🎙️ Speech to Text launcher
    private ActivityResultLauncher<Intent> speechLauncher =
            registerForActivityResult(
                    new ActivityResultContracts.StartActivityForResult(),
                    result -> {
                        if (result.getResultCode() == RESULT_OK && result.getData() != null) {

                            ArrayList<String> matches =
                                    result.getData().getStringArrayListExtra(
                                            RecognizerIntent.EXTRA_RESULTS);

                            if (matches != null && !matches.isEmpty()) {
                                String comando = matches.get(0).toLowerCase();
                                procesarComando(comando);
                            }
                        }
                    }
            );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main3);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main3), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ImageButton compra_espada = findViewById(R.id.boton_compra_espadas);

        // Click listener
        compra_espada.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity3.this, MainActivity4.class);
            startActivity(intent);
        });

        MaterialCardView perfil_usuario = findViewById(R.id.materialCardView_perfil);

        // Click listener
        perfil_usuario.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity3.this, MainActivity7.class);
            startActivity(intent);
        });

        ImageButton transmision = findViewById(R.id.campana1);

        // Click listener
        transmision.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity3.this, MainActivity10.class);
            startActivity(intent);
        });

        ImageButton incursion = findViewById(R.id.cartera);

        // Click listener
        incursion.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity3.this, MainActivity13.class);
            startActivity(intent);
        });

        boton3 = findViewById(R.id.boton3);

        // 👉 Click normal (opcional)
        boton3.setOnClickListener(v ->
                startActivity(new Intent(this, MainActivity4.class))
        );

        // 👉 Mantener pulsado = escuchar
        boton3.setOnLongClickListener(v -> {
            iniciarEscucha();
            return true;
        });

        // 👉 Permiso micrófono
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{Manifest.permission.RECORD_AUDIO},
                    2
            );
        }
    }

    // 🎤 Inicia reconocimiento de voz
    private void iniciarEscucha() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
        );
        intent.putExtra(
                RecognizerIntent.EXTRA_LANGUAGE,
                Locale.getDefault()
        );
        intent.putExtra(
                RecognizerIntent.EXTRA_PROMPT,
                "Di 'comprar espada', 'mi usuario', 'Transmisión' o 'Incursion'"
        );

        speechLauncher.launch(intent);
    }

    // 🧠 COMANDO: "iniciar sesión"
    private void procesarComando(String comando) {

        if (comando.contains("comprar espada")) {

            Toast.makeText(this, "Comprando espada...", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, MainActivity4.class));
        }
        else if (comando.contains("mi usuario")) {

            Toast.makeText(this, "Accediendo al usuario...", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, MainActivity7.class));

        }
        else if (comando.contains("transmisión")) {

            Toast.makeText(this, "Accediendo a transmisiones...", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, MainActivity10.class));

        }
        else if (comando.contains("incursión")) {

            Toast.makeText(this, "Accediendo a incursiones...", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, MainActivity13.class));

        }
        else {
            Toast.makeText(
                    this,
                    "Comando no reconocido. Di 'comprar espada', 'mi usuario', 'transmisión' o 'incursión",
                    Toast.LENGTH_SHORT
            ).show();
        }
    }

}