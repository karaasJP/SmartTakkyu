package jp.miyatano.smarttakkyu;

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

    public void BlueToothTest(View v) {
        Intent intent = new Intent(this, BlueToothTestActivity.class);
        // intent.putExtra("mapdata", mapdata);
        startActivity(intent);
    }

    public void PlayTest(View v) {
        Intent intent = new Intent(this, PlayTestActivity.class);
        startActivity(intent);
    }

}
