package com.example.l5q1;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    private Button playButton, pauseButton, forwardButton, rewindButton;
    private MediaPlayer mediaPlayer;
    private double startTime = 0, finalTime = 0;
    private Handler handler = new Handler();
    private int forwardTime = 5000, backwardTime = 5000;
    private SeekBar seekBar;
    private TextView currentTime, totalTime;
    public static int oneTimeOnly = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        playButton = findViewById(R.id.play);
        pauseButton = findViewById(R.id.pause);
        forwardButton = findViewById(R.id.forward);
        rewindButton = findViewById(R.id.rewind);
        seekBar = findViewById(R.id.seekBar);
        currentTime = findViewById(R.id.current_time);
        totalTime = findViewById(R.id.total_time);

        mediaPlayer = MediaPlayer.create(this, R.raw.song);  // Add an audio file in res/raw

        seekBar.setClickable(false);
        pauseButton.setEnabled(false);

        playButton.setOnClickListener(v -> {
            Toast.makeText(getApplicationContext(), "Playing Audio", Toast.LENGTH_SHORT).show();
            mediaPlayer.start();
            finalTime = mediaPlayer.getDuration();
            startTime = mediaPlayer.getCurrentPosition();

            if (oneTimeOnly == 0) {
                seekBar.setMax((int) finalTime);
                oneTimeOnly = 1;
            }

            totalTime.setText(formatTime(finalTime));
            currentTime.setText(formatTime(startTime));

            seekBar.setProgress((int) startTime);
            handler.postDelayed(updateSongTime, 100);
            pauseButton.setEnabled(true);
            playButton.setEnabled(false);
        });

        pauseButton.setOnClickListener(v -> {
            Toast.makeText(getApplicationContext(), "Pausing Audio", Toast.LENGTH_SHORT).show();
            mediaPlayer.pause();
            pauseButton.setEnabled(false);
            playButton.setEnabled(true);
        });

        forwardButton.setOnClickListener(v -> {
            int temp = (int) startTime;
            if ((temp + forwardTime) <= finalTime) {
                startTime = startTime + forwardTime;
                mediaPlayer.seekTo((int) startTime);
                Toast.makeText(getApplicationContext(), "Forwarded 5 seconds", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(), "Cannot forward", Toast.LENGTH_SHORT).show();
            }
        });

        rewindButton.setOnClickListener(v -> {
            int temp = (int) startTime;
            if ((temp - backwardTime) > 0) {
                startTime = startTime - backwardTime;
                mediaPlayer.seekTo((int) startTime);
                Toast.makeText(getApplicationContext(), "Rewinded 5 seconds", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(), "Cannot rewind", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private Runnable updateSongTime = new Runnable() {
        public void run() {
            startTime = mediaPlayer.getCurrentPosition();
            currentTime.setText(formatTime(startTime));
            seekBar.setProgress((int) startTime);
            handler.postDelayed(this, 100);
        }
    };

    private String formatTime(double millis) {
        return String.format("%d min, %d sec", TimeUnit.MILLISECONDS.toMinutes((long) millis), TimeUnit.MILLISECONDS.toSeconds((long) millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long) millis)));
    }
}
