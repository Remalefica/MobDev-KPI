package com.example.mediaplayer.fragments;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.fragment.app.ListFragment;

import com.example.mediaplayer.MusicPlayerActivity;
import com.example.mediaplayer.R;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.File;
import java.util.ArrayList;
import java.util.Objects;

public class MusicFragment extends ListFragment {

    private ListView listView;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        listView = (ListView) getView().findViewById(android.R.id.list);

        Dexter.withActivity(getActivity())
                .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        display();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        if (response.isPermanentlyDenied()) {

                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_music, null);
    }


    private ArrayList<File> findSong(java.io.File root){
        ArrayList<java.io.File> songs = new ArrayList<java.io.File>();
        java.io.File[] files = root.listFiles();

        assert files != null;

        for(java.io.File singleFile : files){
            if(singleFile.isDirectory() && !singleFile.isHidden())
                songs.addAll(findSong(singleFile));
            else{
                if(singleFile.getName().endsWith(".mp3") || singleFile.getName().endsWith(".wav"))
                    songs.add(singleFile);
            }
        }
        return songs;
    }
    private void display(){
        final ArrayList<File> songs = findSong(Environment.getExternalStorageDirectory());
        String[] items = new String[songs.size()];

        for(int i = 0;i < songs.size(); i++){
            items[i] = songs.get(i).getName().toString().replace(".mp3","").replace(".wav","");
        }

        ArrayAdapter<String> adp = new
                ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1, items);
        listView.setAdapter(adp);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int
                    position, long l) {

                String songName = listView.getItemAtPosition(position).toString();
                startActivity(new Intent(getContext().getApplicationContext(),MusicPlayerActivity.class)
                        .putExtra("pos",position).putExtra("songs", songs).putExtra("songname",songName));
            }
        });
    }
}
