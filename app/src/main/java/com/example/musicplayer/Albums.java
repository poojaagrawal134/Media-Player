package com.example.musicplayer;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

import static android.widget.Toast.LENGTH_LONG;
import static android.widget.Toast.LENGTH_SHORT;


/**
 * A simple {@link Fragment} subclass.
 */
public class Albums extends Fragment {

    RecyclerView myrecycler;
    videoadapter  obj_videoad;
    public static int REQUEST_PERMISSION=1;
    File directory;
    public static ArrayList<File> fileArrayList=new ArrayList<>();
    boolean perm;
    public Albums() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v= inflater.inflate(R.layout.fragment_albums, container, false);
        myrecycler=v.findViewById(R.id.videorecy);
        directory=new File("/mnt/");

        GridLayoutManager manager=new GridLayoutManager(getActivity(),2);
        myrecycler.setLayoutManager(manager);
        permissionforvideo();
        return v;
    }

    private void permissionforvideo() {
        if((ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED)){
            if((ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),Manifest.permission.READ_EXTERNAL_STORAGE))){

            }else
            {
                ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},REQUEST_PERMISSION);
            }

        }
        else
        {
            perm=true;
            getFile(directory);
            obj_videoad=new videoadapter(getActivity(),fileArrayList);
            myrecycler.setAdapter(obj_videoad);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode==REQUEST_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                perm=true;
                getFile(directory);
                obj_videoad=new videoadapter(getActivity(),fileArrayList);
                myrecycler.setAdapter(obj_videoad);
            }
            else {
                Toast.makeText(getContext(), "Please allow the permission",Toast.LENGTH_SHORT).show();
            }
        }
    }

   public  ArrayList<File> getFile(File directory) {
       File listfile[] = directory.listFiles();
       if(listfile!=null && listfile.length>0){
           for(int i=0;i<listfile.length;i++)
           {
               if(listfile[i].isDirectory())
               {
                   getFile(listfile[i]);
               }
               else
               {
                   perm=false;
                   if(listfile[i].getName().endsWith(".mp4"))
                   {
                       for (int j=0;j<fileArrayList.size();j++)
                       {
                           if(fileArrayList.get(j).getName().equals(listfile[i].getName()))
                           {
                               perm=true;
                           }
                           else {

                           }
                       }
                       if (perm)
                       {
                           perm=false;
                       }else {
                           fileArrayList.add(listfile[i]);
                       }
                   }
               }
           }

       }
       return fileArrayList;
   }


    @Override
    public void onCreate(@NonNull Bundle savedInstances)
    {
        setHasOptionsMenu(true);
        super.onCreate(savedInstances);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater)
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
