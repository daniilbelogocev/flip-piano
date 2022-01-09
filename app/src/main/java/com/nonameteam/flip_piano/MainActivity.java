package com.nonameteam.flip_piano;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    private List<PianoPiece> pieces;
    private Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RotationService.initialize(this);
        this.pieces = new ArrayList<>();
        this.timer = new Timer();
        this.timer.schedule(new PiecesCreatorTask(), 3000, 5000);
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