package com.example.sisterlyapp;


import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class SosActivity2 extends Fragment {

    private Button sos_button;
    private TextView countdownText;
    private Vibrator vibrator;
    private MediaPlayer alarmSound;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_sos, container, false);

        sos_button = view.findViewById(R.id.sos_button);
        countdownText = view.findViewById(R.id.countdown_text);

        // Initialize vibrator and alarm sound
        Context context = requireActivity();
        vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        alarmSound = MediaPlayer.create(context, R.raw.alarm_sound); // Ensure the file exists in res/raw
        alarmSound.start();

        sos_button.setOnClickListener(v -> startSOSCountdown());


        return view;
    }

    private void startSOSCountdown() {
        sos_button.setVisibility(View.GONE);
        countdownText.setVisibility(View.VISIBLE);

        new CountDownTimer(3000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                countdownText.setText(String.valueOf(millisUntilFinished / 1000));
            }

            @Override
            public void onFinish() {
                startAlarm();
            }
        }.start();
    }

    private void startAlarm() {
        countdownText.setVisibility(View.GONE);

        if (vibrator != null) {
            vibrator.vibrate(2000); // Vibrate for 2 seconds
        }

        if (alarmSound != null) {
            alarmSound.start(); // Play the alarm sound
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (alarmSound != null) {
            alarmSound.release();
        }
    }
}


