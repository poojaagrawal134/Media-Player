package com.example.musicplayer;


import android.Manifest;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.File;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class Allmusic extends Fragment {
     ListView allmusiclist;
     ArrayAdapter<String> musicarrayadapter;
     String songs[];
     ArrayList<File> musics;

    public Allmusic() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_allmusic, container, false);
        allmusiclist=view.findViewById(R.id.List1);
        Dexter.withActivity(getActivity()).withPermission(Manifest.permission.READ_EXTERNAL_STORAGE).withListener(new PermissionListener() {
            @Override
            public void onPermissionGranted(PermissionGrantedResponse response) {
                musics = filemusicfiles(Environment.getExternalStorageDirectory());
                songs = new String[musics.size()];
                for (int i = 0; i < musics.size(); i++) {
                    songs[i] = musics.get(i).getName();
                }
                musicarrayadapter=new ArrayAdapter<>(getActivity(),android.R.layout.simple_list_item_1,songs);
                allmusiclist.setAdapter(musicarrayadapter);
                allmusiclist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent player=new Intent(getActivity(),Player.class);
                        player.putExtra("songFileList",musics);
                        player.putExtra("position",position);


                        startActivity(player);
                    }
                });
            }

            @Override
            public void onPermissionDenied(PermissionDeniedResponse response) {

            }

            @Override
            public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
             token.continuePermissionRequest();
            }
        }).check();
        return view;
    }
    private ArrayList<File> filemusicfiles (File file)
    {
        ArrayList<File> allmusicfile=new ArrayList<>();
        File []files=file.listFiles();
        for(File currentfile:files)
            if(currentfile.isDirectory() && !currentfile.isHidden())
            {
                allmusicfile.addAll(filemusicfiles(currentfile));
            }
        else
            {   if(currentfile.getName().endsWith(".mp3") || currentfile.getName().endsWith(".mp4a") || currentfile.getName().endsWith(".wav"))
                allmusicfile.add(currentfile);
            }
        return allmusicfile;
    }
    @Override
    public void onCreate(@NonNull Bundle savedInstances)
    {
        setHasOptionsMenu(true);
        super.onCreate(savedInstances);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu,MenuInflater menuInflater)
    {
        menuInflater.inflate(R.menu.menu_main,menu);
        super.onCreateOptionsMenu(menu,menuInflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem)
    {   int id=menuItem.getItemId();
    if(id==R.id.search)
    {
        //
    }
    if(id==R.id.Settings)
    {
        startActivityForResult(new Intent(android.provider.Settings.ACTION_SETTINGS), 0);
    }
        return super.onOptionsItemSelected(menuItem);
    }


    }
