package com.example.freq_gen;

import android.media.MediaRecorder;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.media.MediaPlayer;
import android.view.View;
import android.widget.Button;
import java.io.IOException;
import android.os.Handler;
import android.widget.CheckBox;

public class MainActivity extends AppCompatActivity {


    private Button play, p_r, stop;
    private CheckBox khz15 , white_noise, chirp, gaussian;
    private MediaRecorder rec;
    private int play_time = 2; // in sec
    private String outFile = Environment.getExternalStorageDirectory().getAbsolutePath()+ "/";
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final String khz15_name = "15khz_2sec";
        final String chirp_name = "chirp_2sec";
        final String white_noise_name = "white_noise_2sec";
        final String gaussian_name = "_with_gauss";

        play = (Button)findViewById(R.id.play);
        p_r = (Button)findViewById(R.id.p_r);
        stop = (Button)findViewById(R.id.stop);
        final MediaPlayer mp_khz15 = MediaPlayer.create(this , R.raw.khz15_2sec);
        final MediaPlayer mp_khz15_wg = MediaPlayer.create(this , R.raw.khz15_2sec_wg);
        final MediaPlayer mp_chirp = MediaPlayer.create(this , R.raw.chirp_2sec);
        final MediaPlayer mp_chirp_wg = MediaPlayer.create(this , R.raw.chirp_2sec_wg);
        final MediaPlayer mp_noise = MediaPlayer.create(this , R.raw.white_noise_2sec);
        final MediaPlayer mp_noise_wg = MediaPlayer.create(this , R.raw.white_noise_2sec_wg);

        khz15 = (CheckBox)findViewById(R.id.khz15_box);
        white_noise = (CheckBox)findViewById(R.id.white_noise_box);
        chirp = (CheckBox)findViewById(R.id.chirp_box);
        gaussian = (CheckBox)findViewById(R.id.gaussian_box);

        final int[] i = {1};



        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    if(gaussian.isChecked()){
                        if (khz15.isChecked())
                            mp_khz15_wg.start();
                        else if (white_noise.isChecked())
                            mp_noise_wg.start();
                        else if (chirp.isChecked())
                            mp_chirp_wg.start();
                    }
                    else {
                        if (khz15.isChecked())
                            mp_khz15.start();
                        else if (white_noise.isChecked())
                            mp_noise.start();
                        else if (chirp.isChecked())
                            mp_chirp.start();
                    }
                }
        });

        p_r.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                rec = new MediaRecorder();
                rec.setAudioSource(MediaRecorder.AudioSource.MIC);
                rec.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
                rec.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
                rec.setAudioEncodingBitRate(384000);
                rec.setAudioSamplingRate(44100);
                if(gaussian.isChecked()) {
                    if (khz15.isChecked())
                        rec.setOutputFile(outFile + khz15_name + gaussian_name + String.valueOf(i[0]) + ".m4a");
                    else if (white_noise.isChecked())
                        rec.setOutputFile(outFile + white_noise_name + gaussian_name + String.valueOf(i[0]) + ".m4a");
                    else if (chirp.isChecked())
                        rec.setOutputFile(outFile + chirp_name + gaussian_name + String.valueOf(i[0]) + ".m4a");
                }
                else {
                    if (khz15.isChecked())
                        rec.setOutputFile(outFile + khz15_name + String.valueOf(i[0]) + ".m4a");
                    else if (white_noise.isChecked())
                        rec.setOutputFile(outFile + white_noise_name + String.valueOf(i[0]) + ".m4a");
                    else if (chirp.isChecked())
                        rec.setOutputFile(outFile + chirp_name + String.valueOf(i[0]) + ".m4a");
                }
                try {
                    rec.prepare();
                }
                catch (IllegalStateException ise) { }
                catch (IOException ioe) { }

                rec.start();


                // play
                if(gaussian.isChecked()){
                    if (khz15.isChecked())
                        mp_khz15_wg.start();
                    else if (white_noise.isChecked())
                        mp_noise_wg.start();
                    else if (chirp.isChecked())
                        mp_chirp_wg.start();
                }
                else {
                    if (khz15.isChecked())
                        mp_khz15.start();
                    else if (white_noise.isChecked())
                        mp_noise.start();
                    else if (chirp.isChecked())
                        mp_chirp.start();
                }

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        rec.stop();
                        rec.release();
                        rec = null;
                    }
                }, play_time * 1000);   //2 seconds
                i[0]++;
            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    rec.stop();
                    rec.release();
                    rec = null;
                }
            });

    }
}


