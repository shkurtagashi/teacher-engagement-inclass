package com.example.android.teacher.AudioRecording;

import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Environment;
import android.view.View;
import android.widget.Button;

import java.io.File;

/**
 * Created by shkurtagashi on 17.02.17.
 */

public class AudioRecorder {

    //Declaration of media player, recorder and storing file.
    private MediaPlayer mediaPlayer;
    private MediaRecorder recorder;
    private String OUTPUT_FILE;
    private Button recordButton;
    private Button playButton;

//    //File to save the audio recording
//    OUTPUT_FILE = Environment.getExternalStorageDirectory() + "/audiorecorder.3gpp";
//
//    //Handles user's buttons clicks
//    public void buttonPressed(View view){
//        recordButton = (Button) findViewById(R.id.microphone_record);
//        playButton = (Button) findViewById(R.id.microphone_play);
//
//        switch (view.getId()){
//            case R.id.microphone_record:
//                try {
//                    if(recordButton.getText() == "Record"){
//                        recordButton.setText("Stop Recording");
//                        beginRecording();
//                    }else{
//                        recordButton.setText("Record");
//                        stopRecording();
//                    }
//
//                }catch(Exception e){
//                    e.printStackTrace();
//                }
//                break;
//
//            case R.id.microphone_play:
//                try {
//                    if(playButton.getText() == "Play") {
//                        playButton.setText("Stop Playing");
//                        beginPlaying();
//                    }
//                    else{
//                        playButton.setText("Play");
//                        stopPlaying();
//                    }
//                }catch(Exception e){
//                    e.printStackTrace();
//                }
//                break;
//
//        }
//
//    }

    //Records voice
    private void beginRecording() throws Exception{

        ditchMediaRecorder();
        File outputFile = new File(OUTPUT_FILE);

        if(outputFile.exists()){
            outputFile.delete();
        }

        recorder = new MediaRecorder();
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
        recorder.setOutputFile(OUTPUT_FILE);
        recorder.prepare();
        recorder.start();

    }

    private void stopRecording(){
        if(recorder != null){
            recorder.stop();
        }

    }

    private void beginPlaying() throws Exception{
        ditchMediaPlayer();
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setDataSource(OUTPUT_FILE);
        mediaPlayer.prepare();
        mediaPlayer.start();


    }

    private void stopPlaying(){
        if(mediaPlayer != null){
            mediaPlayer.stop();
        }
    }

    private void ditchMediaRecorder(){
        if(recorder != null){
            recorder.release();
        }
    }

    private void ditchMediaPlayer(){
        if(mediaPlayer != null){
            try {
                mediaPlayer.release();
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }

}
