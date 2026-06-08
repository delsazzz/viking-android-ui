package com.example.aplicacion_vikingos;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.Locale;

import android.Manifest;

public abstract class BaseVoiceActivity extends AppCompatActivity {

    protected SpeechRecognizer speechRecognizer;
    protected Intent recognizerIntent;
    protected boolean isListening = false;
    protected TextToSpeech tts;
    private static final int REQUEST_CODE_MIC = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Inicializar TTS
        tts = new TextToSpeech(this, status -> {
            if (status == TextToSpeech.SUCCESS) {
                tts.setLanguage(new Locale("es", "ES"));
            }
        });

        setupSpeechRecognizer();
    }

    protected void setupSpeechRecognizer() {
        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);

        recognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        recognizerIntent.putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
        );
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "es-ES");
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS, true);

        speechRecognizer.setRecognitionListener(new RecognitionListener() {
            @Override
            public void onReadyForSpeech(Bundle params) {
            }

            @Override
            public void onBeginningOfSpeech() {
            }

            @Override
            public void onRmsChanged(float rmsdB) {
            }

            @Override
            public void onBufferReceived(byte[] buffer) {
            }

            @Override
            public void onEndOfSpeech() {
            }

            @Override
            public void onError(int error) {
                isListening = false;
                reiniciarEscucha();
            }

            @Override
            public void onResults(Bundle results) {
                isListening = false;
                manejarResultados(results);
                reiniciarEscucha();
            }

            @Override
            public void onPartialResults(Bundle partialResults) {
            }

            @Override
            public void onEvent(int eventType, Bundle params) {
            }
        });
    }

    private void manejarResultados(Bundle results) {
        ArrayList<String> matches =
                results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
        if (matches == null || matches.isEmpty()) return;

        for (String texto : matches) {
            String normalizado = texto.toLowerCase(new Locale("es", "ES")).trim();
            onVoiceCommand(normalizado);
        }
    }

    protected abstract void onVoiceCommand(String comando);

    protected void hablar(String texto) {
        if (tts != null) {
            tts.speak(texto, TextToSpeech.QUEUE_FLUSH, null, "TTS_ID");
        }
    }

    protected void comprobarPermisoYEmpezar() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(
                    this,
                    new String[]{Manifest.permission.RECORD_AUDIO},
                    REQUEST_CODE_MIC
            );
        } else {
            iniciarEscucha();
        }
    }

    protected void iniciarEscucha() {
        if (speechRecognizer != null && !isListening) {
            isListening = true;
            speechRecognizer.startListening(recognizerIntent);
        }
    }

    protected void reiniciarEscucha() {
        iniciarEscucha();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (speechRecognizer != null) {
            speechRecognizer.stopListening();
            isListening = false;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (speechRecognizer != null) {
            speechRecognizer.destroy();
            speechRecognizer = null;
        }
    }
}
