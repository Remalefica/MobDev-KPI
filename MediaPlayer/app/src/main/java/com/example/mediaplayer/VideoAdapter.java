package com.example.mediaplayer;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mediaplayer.fragments.VideoFragment;

import java.io.File;
import java.util.ArrayList;

public class VideoAdapter extends RecyclerView.Adapter<VideoHolder> {

    private Context context;
    ArrayList<File> videoArrayList;

    //constructor
    public VideoAdapter(Context context, ArrayList<File> videoArrayList) {
        this.context = context;
        this.videoArrayList = videoArrayList;
    }

    @NonNull
    @Override
    public VideoHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View vView = LayoutInflater.from(parent.getContext()).inflate(R.layout.video_item, parent,false);
        return new VideoHolder(vView);
    }

    @Override
    public void onBindViewHolder(@NonNull final VideoHolder holder, int position) {
        holder.txtFileName.setText(VideoFragment.fileArrayList.get(position).getName());

        Bitmap bitmapThumbnail = ThumbnailUtils.createVideoThumbnail(videoArrayList.get(position).getPath(), MediaStore.Images.Thumbnails.MINI_KIND);
        holder.imageThumbnail.setImageBitmap(bitmapThumbnail);

        holder.vCardView.setOnClickListener(new View.OnClickListener() {
            public void onClick (View v) {
                Intent intent = new Intent (context, VideoPlayerActivity.class);
                intent.putExtra("position", holder.getAdapterPosition());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (videoArrayList.size()>0)
            return videoArrayList.size();
        else
            return 1;
    }
}

class VideoHolder extends RecyclerView.ViewHolder {

    TextView txtFileName;
    ImageView imageThumbnail;
    CardView vCardView;

    VideoHolder(View view) {
        super(view);

        txtFileName = view.findViewById(R.id.txt_videoFileName);
        imageThumbnail = view.findViewById(R.id.thumbnail);
        vCardView = view.findViewById(R.id.videoCardView);
    }
}
