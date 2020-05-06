package com.example.musicplayer;

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

import java.io.File;
import java.util.ArrayList;

public class videoadapter extends RecyclerView.Adapter<Videoholder> {
    private Context context;
    ArrayList<File> vdoarra;

    public videoadapter(Context context, ArrayList<File> vdoarra) {
        this.context = context;
        this.vdoarra = vdoarra;
    }

    @NonNull
    @Override
    public Videoholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mview= LayoutInflater.from(parent.getContext()).inflate(R.layout.videoitem,parent,false);
        return new Videoholder(mview);
    }

    @Override
    public void onBindViewHolder(@NonNull final Videoholder holder, int position) {
        holder.textView.setText(Albums.fileArrayList.get(position).getName());
        Bitmap bitmap= ThumbnailUtils.createVideoThumbnail(vdoarra.get(position).getPath(), MediaStore.Images.Thumbnails.MINI_KIND);
        holder.imageView.setImageBitmap(bitmap);
        holder.mcview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,VideoPlayer.class);
                intent.putExtra("position",holder.getAdapterPosition());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        if(vdoarra.size()>0) {
            return vdoarra.size();
        }
        else {
            return 1;
        }

    }
}
class Videoholder extends RecyclerView.ViewHolder{
    TextView textView;
    ImageView imageView;
    CardView mcview;
    Videoholder(View view)
    {
        super(view);
        textView=view.findViewById(R.id.textFILE);
        imageView=view.findViewById(R.id.viewthumb);
        mcview=view.findViewById(R.id.mycard);
    }
}