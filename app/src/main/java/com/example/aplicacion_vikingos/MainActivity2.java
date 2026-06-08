package com.example.aplicacion_vikingos;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.speech.RecognizerIntent;
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

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity2 extends AppCompatActivity {

    private ImageView boton2;

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
        setContentView(R.layout.activity_main2);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main2), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ImageView micro2 = findViewById(R.id.boton2);

        // Click listener
        micro2.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity2.this, MainActivity3.class);
            startActivity(intent);
        });

        boton2 = findViewById(R.id.boton2);

        // 👉 Click normal (opcional)
        boton2.setOnClickListener(v ->
                startActivity(new Intent(this, MainActivity3.class))
        );

        // 👉 Mantener pulsado = escuchar
        boton2.setOnLongClickListener(v -> {
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
                "Di 'Iniciar sesión'"
        );

        speechLauncher.launch(intent);
    }

    // 🧠 COMANDO: "iniciar sesión"
    private void procesarComando(String comando) {

        if (comando.contains("iniciar sesión")
                || comando.contains("iniciar sesion")
                || comando.contains("login")) {

            Toast.makeText(this, "Iniciando sesión...", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, MainActivity3.class));
        }
        else {
            Toast.makeText(
                    this,
                    "Di 'Iniciar sesión' para continuar",
                    Toast.LENGTH_SHORT
            ).show();
        }
    }
}
