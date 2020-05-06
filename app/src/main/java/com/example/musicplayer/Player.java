package com.example.musicplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;

public class Player extends AppCompatActivity {
    Bundle songExtraData;
    ArrayList<File> songFileList;
    TextView txt;
    SeekBar sk;
    TextView cu,tot;
    int position;
    ImageView play,next,prev;
     static MediaPlayer mediaPlayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        txt=(TextView)findViewById(R.id.textView);
        sk=(SeekBar)findViewById(R.id.seekBar);
        play=(ImageView)findViewById(R.id.play);
        cu=(TextView)findViewById(R.id.curtimer);
        tot=(TextView)findViewById(R.id.totaltimer);
        next=(ImageView)findViewById(R.id.next);
        prev=(ImageView)findViewById(R.id.pre);
        if(mediaPlayer!=null){
            mediaPlayer.stop();
        }

        Intent songData=getIntent();
        songExtraData=songData.getExtras();
        songFileList=(ArrayList)songExtraData.getParcelableArrayList("songFileList");
        position= songExtraData.getInt("position",0);
      initMuicPlayer(position);

      play.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              plays();
          }
      });

      next.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              if(position<songFileList.size()-1)
              {
                position++;
              }else{
                  position=0;
              }
              initMuicPlayer(position);
          }
      });
        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(position<=0)
                {
                    position=songFileList.size()-1;
                }else{
                    position--;
                }
                initMuicPlayer(position);
            }
        });


    }
    private void initMuicPlayer(final int position) {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.reset();
        }
        final String name = songFileList.get(position).getName();
        txt.setText(name);

        Uri songResourceUri = Uri.parse(songFileList.get(position).toString());
        mediaPlayer = MediaPlayer.create(getApplicationContext(), songResourceUri);
        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                sk.setMax(mediaPlayer.getDuration());

                String total=createTimeLabel(mediaPlayer.getDuration());
                tot.setText(total);
                mediaPlayer.start();
                play.setImageResource(R.drawable.pause);
            }
        });
       mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
           @Override
           public void onCompletion(MediaPlayer mp) {
               play.setImageResource(R.drawable.play);

               //Repeat song
               int Currentsongpos=position;
               if(Currentsongpos<songFileList.size()-1)
               {
                   Currentsongpos++;
               }else{
                   Currentsongpos=0;
               }
               initMuicPlayer(Currentsongpos);

           }
       });
       sk.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
           @Override
           public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
               if(fromUser)
                   mediaPlayer.seekTo(progress);
               sk.setProgress(progress);
           }

           @Override
           public void onStartTrackingTouch(SeekBar seekBar) {

           }

           @Override
           public void onStopTrackingTouch(SeekBar seekBar) {

           }
       });
       new Thread(new Runnable() {
           @Override
           public void run() {
               while (mediaPlayer!=null)
               {
                   try {
                       if(mediaPlayer.isPlaying()){
                           Message message=new Message();
                           message.what=mediaPlayer.getCurrentPosition();
                           handler.sendMessage(message);
                           Thread.sleep(1000);
                       }
                   }catch (InterruptedException e)
                   {
                       e.printStackTrace();
                   }
               }
           }
       }).start();
    }
    @SuppressLint("HandlerLeak")
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg)
        {

            cu.setText(createTimeLabel(msg.what));
            sk.setProgress(msg.what);
        }
    };
        private void plays(){
            if(mediaPlayer.isPlaying())
            {
                mediaPlayer.pause();
                play.setImageResource(R.drawable.play);
        }else
            {
                mediaPlayer.start();
                play.setImageResource(R.drawable.pause);
            }
    }

    public String createTimeLabel(int duration)
    {
        String timelabel="";
        int min=duration/1000/60;
        int sec=duration/1000%60;
        timelabel+=min+":";
        if(sec<10)
            timelabel+="0";
        timelabel+=sec;
        return timelabel;
    }
}
