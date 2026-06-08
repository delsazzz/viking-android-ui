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

import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity6 extends AppCompatActivity {

    private ImageView boton5;

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
        setContentView(R.layout.activity_main6);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main6), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        MaterialButton continue_shopping = findViewById(R.id.continue_shopping);

        // Click listener
        continue_shopping.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity6.this, MainActivity3.class);
            startActivity(intent);
        });

        boton5 = findViewById(R.id.boton5);

        // 👉 Click normal (opcional)
        boton5.setOnClickListener(v ->
                startActivity(new Intent(this, MainActivity3.class))
        );

        // 👉 Mantener pulsado = escuchar
        boton5.setOnLongClickListener(v -> {
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
                "Di 'Continuar comprando"
        );

        speechLauncher.launch(intent);
    }

    // 🧠 COMANDO: "iniciar sesión"
    private void procesarComando(String comando) {

        if (comando.contains("continuar comprando")) {

            Toast.makeText(this, "Volviendo a la tienda...", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, MainActivity3.class));
        }
        else {
            Toast.makeText(
                    this,
                    "Di 'Continuar comprando' para continuar",
                    Toast.LENGTH_SHORT
            ).show();
        }
    }
}