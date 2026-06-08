package com.example.aplicacion_vikingos;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.speech.RecognizerIntent;
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

import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity5 extends AppCompatActivity {

    private ImageView boton4;

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
        setContentView(R.layout.activity_main5);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main5), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        MaterialButton boton_apple = findViewById(R.id.button_apple);

        // Click listener
        boton_apple.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity5.this, MainActivity6.class);
            startActivity(intent);
        });

        MaterialButton boton_android = findViewById(R.id.button_android);

        // Click listener
        boton_android.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity5.this, MainActivity6.class);
            startActivity(intent);
        });

        ImageView atras2 = findViewById(R.id.atras2);

        // Click listener
        atras2.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity5.this, MainActivity4.class);
            startActivity(intent);
        });

        boton4 = findViewById(R.id.boton4);

        // 👉 Click normal (opcional)
        boton4.setOnClickListener(v ->
                startActivity(new Intent(this, MainActivity6.class))
        );

        // 👉 Mantener pulsado = escuchar
        boton4.setOnLongClickListener(v -> {
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
                "Di 'Pagar"
        );

        speechLauncher.launch(intent);
    }

    // 🧠 COMANDO: "iniciar sesión"
    private void procesarComando(String comando) {

        if (comando.contains("pagar")) {

            Toast.makeText(this, "Pagando...", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, MainActivity6.class));
        }
        else {
            Toast.makeText(
                    this,
                    "Di 'Pagar' para continuar",
                    Toast.LENGTH_SHORT
            ).show();
        }
    }
}