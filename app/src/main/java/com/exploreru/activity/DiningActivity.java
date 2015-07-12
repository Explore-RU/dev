package com.exploreru.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.exploreru.R;
import com.exploreru.adapter.DiningAdapter;
import com.exploreru.api.Dining;
import com.exploreru.net.HTTPNetwork;
import com.exploreru.net.JSONParser;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DiningActivity extends FragmentActivity {

    HTTPNetwork net = new HTTPNetwork(this,"https://rumobile.rutgers.edu/1/rutgers-dining.txt");
    List<Dining> dataParsed;
    String data;
    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List> listDataChild;
    List<List<String>> itemList;
    List<List<Boolean>> availList;
    Thread network;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dining);

        getData();

        // get the listview
        expListView = (ExpandableListView) findViewById(R.id.diningListView);

        // preparing list data
        try {
            network.join();
            prepareListData();
            listAdapter = new DiningAdapter(this, listDataHeader, listDataChild, availList, dataParsed);
            // setting list adapter
            expListView.setAdapter(listAdapter);
            expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
                @Override
                public boolean onChildClick(ExpandableListView expandableListView, View view, int groupPos, int childPos, long l) {
                    if(childPos == 1){
                        new AlertDialog.Builder(DiningActivity.this)
                                .setTitle("Your Alert")
                                .setMessage("Your Message")
                                .setCancelable(false)
                                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        // whatever...
                                    }
                                }).create().show();
                        return true;
                    }
                    return false;
                }
            });

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private void getData(){

        network = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    net.start();
                    net.join();
                    dataParsed = JSONParser.parseDiningAPI(net.data);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        });
        network.start();
    }

    /*
     * Preparing the list data
     */
    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        itemList = new ArrayList<List<String>>();
        availList = new ArrayList<List<Boolean>>();
        listDataChild = new HashMap<String, List>();

        // Adding child data
        for(int i = 0;i < dataParsed.size();i++){
            listDataHeader.add(dataParsed.get(i).location_name);
            List<String> dataList = new ArrayList<String>();
            List<Boolean> isAvail = new ArrayList<Boolean>();
            for(int j = 0;j < dataParsed.get(i).meals.size();j++){
                dataList.add(dataParsed.get(i).meals.get(j).meal_name);
                isAvail.add(dataParsed.get(i).meals.get(j).meal_avail);
            }
            dataList.add(null);
            itemList.add(dataList);
            availList.add(isAvail);
        }

        for(int i = 0;i < listDataHeader.size();i++) {
            listDataChild.put(listDataHeader.get(i), itemList.get(i)); // Header, Child data
        }

    }


}
