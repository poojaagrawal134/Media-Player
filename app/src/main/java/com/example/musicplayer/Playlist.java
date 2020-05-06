package com.example.musicplayer;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class Playlist extends Fragment {
    private static ExpandableListView expandableListView;
    private static ExpandableListAdapter adapter;
    public Playlist() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v=inflater.inflate(R.layout.fragment_playlist, container, false);
        expandableListView = (ExpandableListView)v.findViewById(R.id.simple_expandable_listview);

        // Setting group indicator null for custom indicator
        expandableListView.setGroupIndicator(null);

        setItems();
        setListener();
        return v;
    }

    private void setListener() {
        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView listview, View view,
                                        int group_pos, long id) {

                Toast.makeText(getActivity(),
                        "You clicked : " + adapter.getGroup(group_pos),
                        Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        // This listener will expand one group at one time
        // You can remove this listener for expanding all groups
        expandableListView
                .setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

                    // Default position
                    int previousGroup = -1;

                    @Override
                    public void onGroupExpand(int groupPosition) {
                        if (groupPosition != previousGroup)

                            // Collapse the expanded group
                            expandableListView.collapseGroup(previousGroup);
                        previousGroup = groupPosition;
                    }

                });

        // This listener will show toast on child click
        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView listview, View view,
                                        int groupPos, int childPos, long id) {
                Toast.makeText(
                        getActivity(),
                        "You clicked : " + adapter.getChild(groupPos, childPos),
                        Toast.LENGTH_SHORT).show();
                return false;
            }
        });
    }


    private void setItems() {
        ArrayList<String> header = new ArrayList<String>();

        // Array list for child items
        List<String> child1 = new ArrayList<String>();
        List<String> child2 = new ArrayList<String>();
        List<String> child3 = new ArrayList<String>();
        List<String> child4 = new ArrayList<String>();

        // Hash map for both header and child
        HashMap<String, List<String>> hashMap = new HashMap<String, List<String>>();

        // Adding headers to list
            header.add("Mohammad Rafi");

            header.add("Lata Mangeshkar");
            header.add("Udit Narayan");
            header.add("Kishor Kumar");
            header.add("Asha Bhoshle");


        // Adding child data
        for (int i = 1; i < 5; i++) {
            child1.add("Group 1  - " + " : Child" + i);

        }
        // Adding child data
        for (int i = 1; i < 5; i++) {
            child2.add("Group 2  - " + " : Child" + i);

        }
        // Adding child data
        for (int i = 1; i < 6; i++) {
            child3.add("Group 3  - " + " : Child" + i);

        }
        // Adding child data
        for (int i = 1; i < 7; i++) {
            child4.add("Group 4  - " + " : Child" + i);

        }

        // Adding header and childs to hash map
        hashMap.put(header.get(0), child1);

        hashMap.put(header.get(1), child2);
        hashMap.put(header.get(2), child3);
        hashMap.put(header.get(3), child4);
        hashMap.put(header.get(4),child1);

        adapter = new ExpandableListAdapter(getActivity(), header, hashMap);

        // Setting adpater over expandablelistview
        expandableListView.setAdapter(adapter);
    }

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
            search();
        }
        if(id==R.id.Settings)
        {
            startActivityForResult(new Intent(android.provider.Settings.ACTION_SETTINGS), 0);
        }
        return super.onOptionsItemSelected(menuItem);
    }

    private void search() {
    }


}
