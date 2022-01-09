package com.nonameteam.flip_piano;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    public List<PianoPiece> pieces;
    private Timer timer;
    private Timer frameTimer;
    private static MainActivity context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MainActivity.context = this;
        RotationService.initialize(this);
        this.pieces = new ArrayList<>();
        this.timer = new Timer();
        this.timer.schedule(new PiecesCreatorTask(), 3000, 5000);


        frameTimer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        for (int i = 0; i < MainActivity.context.pieces.size(); i++) {
                            float y = MainActivity.context.pieces.get(i).view.getTranslationY();
                            MainActivity.context.pieces.get(i).view.setTranslationY(y + 2);
                        }

                    }
                });
            }
        };
        frameTimer.schedule(task, 0, 50);
    }
}

class PiecesCreatorTask extends TimerTask {
    @Override
    public void run() {

    }
}

class PianoPiece {
    public View view;
    public RotationType lane;
}