package jp.miyatano.smarttakkyu6;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void play(View v) {
        Intent intent = new Intent(this, play.class);
        startActivity(intent);
    }

    public void finishend(View v) {
        Intent intent = new Intent(this, finish.class);
        startActivity(intent);
    }
}
