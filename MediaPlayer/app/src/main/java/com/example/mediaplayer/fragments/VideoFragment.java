package com.example.mediaplayer.fragments;

import android.Manifest;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mediaplayer.R;
import com.example.mediaplayer.VideoAdapter;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.File;
import java.util.ArrayList;
import java.util.Objects;

public class VideoFragment extends Fragment {

    public static ArrayList<File> fileArrayList = new ArrayList<>();

    private RecyclerView videoRecyclerView;
    private VideoAdapter obj_adapter;
    private File directory;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        directory = new File ("/mnt/");

        videoRecyclerView = (RecyclerView) getActivity().findViewById(R.id.listVideoRecycler);
        GridLayoutManager manager = new GridLayoutManager(getActivity(), 2);
        videoRecyclerView.setLayoutManager(manager);

        Dexter.withActivity(getActivity())
                .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        display();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();

    }

    private void display() {
        if (fileArrayList.isEmpty())
            findVideo(directory);

        obj_adapter = new VideoAdapter(getContext().getApplicationContext(), fileArrayList);
        videoRecyclerView.setAdapter(obj_adapter);
    }

    public ArrayList<File> findVideo (File directory) {
        File listFile[] = directory.listFiles();

        if (listFile != null && listFile.length > 0) {
            for (int i = 0; i < listFile.length; i++) {

                if (listFile[i].isDirectory())
                    findVideo(listFile[i]);
                else
                if (listFile[i].getName().endsWith(".mp4"))
                    fileArrayList.add(listFile[i]);
            }
        }

        return fileArrayList;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_video, null);
    }
}