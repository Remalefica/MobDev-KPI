package com.example.mediaplayer;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.util.ArrayList;
public class MusicPlayerActivity extends AppCompatActivity {

    static MediaPlayer mp;
    int position;
    String sname;
    SeekBar sb;
    Thread updateSeekBar;
    Button pause,next,previous;
    TextView songNameText;
    ArrayList<File> songs;

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_player);

        songNameText = (TextView) findViewById(R.id.txtSongLabel);
        pause = (Button)findViewById(R.id.pause);
        previous = (Button)findViewById(R.id.previous);
        next = (Button)findViewById(R.id.next);
        sb=(SeekBar)findViewById(R.id.seekBar);

        setSupportActionBar();

        updateSeekBar = seekBarUpdate();

        if(mp != null){
            mp.stop();
            mp.release();
        }

        Uri u = setSong();
        setMusicPlayer(u);

        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    mp.seekTo(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mp.seekTo(seekBar.getProgress());
            }
        });

        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sb.setMax(mp.getDuration());

                if (mp.isPlaying()) {
                    pause.setBackgroundResource(R.drawable.ic_play_black_24dp);
                    mp.pause();
                }
                else {
                    pause.setBackgroundResource(R.drawable.ic_pause_black_24dp);
                    mp.start();
                }
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mp.stop();
                mp.release();

                position = ((position + 1) % songs.size());
                Uri u = Uri.parse(songs.get( position).toString());
                mp = MediaPlayer.create(getApplicationContext(), u);
                sname = songs.get(position).getName().toString();

                songNameText.setText(sname);

                mp.start();
            }
        });

        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mp.stop();
                mp.release();

                position = ((position - 1) < 0)?(songs.size() - 1):(position - 1);
                Uri u = Uri.parse(songs.get(position).toString());
                mp = MediaPlayer.create(getApplicationContext(), u);
                sname = songs.get(position).getName().toString();

                songNameText.setText(sname);

                mp.start();
            }
        });
    }

    public void setSupportActionBar() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Now Playing");
    }

    public Thread seekBarUpdate() {
        updateSeekBar = new Thread(){
            @Override
            public void run(){
                int totalDuration = mp.getDuration();
                int currentPosition = 0;
                while(currentPosition < totalDuration){
                    try{
                        sleep(500);
                        currentPosition = mp.getCurrentPosition();
                        sb.setProgress(currentPosition);
                    }
                    catch (InterruptedException e){

                    }
                }
            }
        };
        return updateSeekBar;
    }

    public Uri setSong() {
        Intent i = getIntent();
        Bundle b = i.getExtras();

        songs = (ArrayList) b.getParcelableArrayList("songs");

        sname = songs.get(position).getName().toString();

        String SongName = i.getStringExtra("songname");
        songNameText.setText(SongName);
        songNameText.setSelected(true);

        position = b.getInt("pos",0);
        Uri u = Uri.parse(songs.get(position).toString());

        return u;
    }

    public void setMusicPlayer(Uri u) {
        mp = MediaPlayer.create(getApplicationContext(),u);
        mp.start();

        sb.setMax(mp.getDuration());
        updateSeekBar.start();
        sb.getProgressDrawable().setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.MULTIPLY);
        sb.getThumb().setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_IN);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home)
            onBackPressed();
        return super.onOptionsItemSelected(item);
    }
}