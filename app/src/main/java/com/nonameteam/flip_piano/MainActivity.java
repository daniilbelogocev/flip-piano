package com.nonameteam.flip_piano;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    public List<PianoPiece> pieces;
    private Timer timer;
    private Timer frameTimer;
    private static MainActivity context;
    public ViewGroup piecesParent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MainActivity.context = this;
        RotationService.initialize(this);
        this.pieces = new ArrayList<>();
        this.piecesParent = findViewById(R.id.pieces_parent);

        this.timer = new Timer();
        this.timer.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(() -> {
                    View v = new SimpleInflater(MainActivity.context.piecesParent).inflate(R.layout.piano_piece);
                    RotationType l = getRandomLane();
                    v.setTranslationX(getLaneOffset(l));
                    MainActivity.context.pieces.add(new PianoPiece(v, l));
                });
            }
        }, 3000, 5000);

        frameTimer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(() -> {
                    for (int i = 0; i < MainActivity.context.pieces.size(); i++) {
                        float y = MainActivity.context.pieces.get(i).view.getTranslationY();
                        MainActivity.context.pieces.get(i).view.setTranslationY(y + 12);
                    }
                });
            }
        };
        frameTimer.schedule(task, 0, 20);
    }

    private RotationType getRandomLane() {
        return RotationType.values()[(int) Math.floor(Math.random() * 3)];
    }

    private float getLaneOffset(RotationType l) {
        if (l == RotationType.middle) return 0f;
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return (l == RotationType.left ? -1 : 1) * size.x / 3f;
    }
}

class PianoPiece {
    public View view;
    public RotationType lane;

    public PianoPiece(View view, RotationType lane) {
        this.view = view;
        this.lane = lane;
    }
}