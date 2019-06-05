package jp.miyatano.smarttakkyu6;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioAttributes;
import android.media.SoundPool;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class play extends AppCompatActivity
        implements SensorEventListener {

    private SensorManager sensorManager;
    private TextView textView ;

    private SoundPool soundPool;    // 効果音を鳴らす本体
    private int soundOne, soundTwo;          // 効果音データ（mp3）

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);


        AudioAttributes audioAttributes = new AudioAttributes.Builder()
                // USAGE_MEDIA
                // USAGE_GAME
                .setUsage(AudioAttributes.USAGE_GAME)
                // CONTENT_TYPE_MUSIC
                // CONTENT_TYPE_SPEECH, etc.
                .setContentType(AudioAttributes.CONTENT_TYPE_SPEECH)
                .build();

        soundPool = new SoundPool.Builder()
                .setAudioAttributes(audioAttributes)
                // ストリーム数に応じて
                .setMaxStreams(2)
                .build();

        // one.wav をロードしておく
        soundOne = soundPool.load(this, R.raw.lefta, 1);

        // two.wav をロードしておく
        soundTwo = soundPool.load(this, R.raw.righta, 1);

        // load が終わったか確認する場合
        soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                Log.d("debug","sampleId="+sampleId);
                Log.d("debug","status="+status);
            }
        });


        // Get an instance of the SensorManager
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        // Get an instance of the TextView
        textView = findViewById(R.id.text_view);
        Vibrator vib = (Vibrator)getSystemService(VIBRATOR_SERVICE);
        vib.vibrate(200);
    }


    @Override
    protected void onResume() {
        super.onResume();
        // Listenerの登録
        Sensor accel = sensorManager.getDefaultSensor(
                Sensor.TYPE_ACCELEROMETER);

        // sensorManager.registerListener(this, accel, SensorManager.SENSOR_DELAY_NORMAL);
        // sensorManager.registerListener(this, accel, SensorManager.SENSOR_DELAY_FASTEST);
        sensorManager.registerListener(this, accel, SensorManager.SENSOR_DELAY_GAME);
        // sensorManager.registerListener(this, accel, SensorManager.SENSOR_DELAY_UI);
    }

    // 解除するコードも入れる!
    @Override
    protected void onPause() {
        super.onPause();
        // Listenerを解除
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float sensorX, sensorY, sensorZ;

        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            sensorX = event.values[0];
            sensorY = event.values[1];
            sensorZ = event.values[2];

            // double before = sensorX;
            // String after = String.format("%.2f", sensorX);
            // System.out.println(sensorX);

            if (sensorZ < -12 && sensorX > 7 && -6 < sensorY && sensorY < 6) {
                Vibrator vib = (Vibrator)getSystemService(VIBRATOR_SERVICE);
                vib.vibrate(30);
                soundPool.play(soundOne,1.0f , 1.0f, 0, 0, 1.0f);
                soundPool.play(soundTwo,1.0f , 1.0f, 0, 0, 1.0f);
                // soundPool.play(mp3b,1f , 1f, 0, 0, 1f);
            }


            String strTmp = "加速度センサー\n"
                    + " X: " + String.format("%.2f", sensorX) + "\n"
                    + " Y: " + String.format("%.2f", sensorY) + "\n"
                    + " Z: " + String.format("%.2f", sensorZ);
            textView.setText(strTmp);

        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
