package com.example.josh.akanyenyeri;

/**
 * Created by JOSH on 5/16/2017.
 */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class MyinfoList extends Activity {



    // List view
    private ListView lv;
    LinkedList<Services> servicesLinkedList;

    // Listview Adapter

    AdapterLazyInfoList adapter;
    String choosenData = "";

    // Search EditText
    EditText inputSearch;
    Shortcode shortcode;
    Button btnaddOnList;


    // ArrayList for Listview
    ArrayList<HashMap<String, String>> productList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myinfo_list);


        Intent intent = getIntent();
        shortcode  = (Shortcode)intent.getSerializableExtra("SHORTCODE");

        // Listview Data
//        String products[] = {"Dell Inspiron", "HTC One X", "HTC Wildfire S", "HTC Sense", "HTC Sensation XE",
//                "iPhone 4S", "Samsung Galaxy Note 800",
//                "Samsung Galaxy S3", "MacBook Air", "Mac Mini", "MacBook Pro"};

        lv = (ListView) findViewById(R.id.list_view_myinfo);
        inputSearch = (EditText) findViewById(R.id.inputSearch_myinfo);
        btnaddOnList = (Button) findViewById(R.id.addinfo_button);



        getSearchResult("","","");




        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                System.out.println("BANKOZEHO NJYEWE:");


                Services services = servicesLinkedList.get(position);

                System.out.println("CHOOOOOOSEN DATA NIZI:"+services.NAME_SERVICE);
//
                Intent startmain = new Intent(getApplicationContext(), SendActivity.class);
                startmain.putExtra("CHOOSEN_SERVICE", services);
                startmain.putExtra("SHORTCODE", shortcode);
                startActivity(startmain);
                System.out.println("NIHANO");

            }});


        btnaddOnList.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View arg0) {

                Intent startmain = new Intent(getApplicationContext(), NewServiceActiviry.class);
                startmain.putExtra("SHORTCODE",shortcode );
                startActivity(startmain);

            }});

        /**
         * Enabling Search Filter
         * */
        inputSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                // When user changed the Text
              //  MyinfoList.this.adapter.getFilter().filter(cs);
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
            }
        });
    }



    void getSearchResult(String choosen,String start,String tmrw) {

        System.out.println("SHORTCODE NI:"+shortcode.SHORT_CODE);
        String tosearchArray [] = shortcode.SHORT_CODE.split(";;;");
         servicesLinkedList = MainActivity.db.getAllServicesByCategorie(tosearchArray[1]);

        System.out.println("sssssssssssssssssssssssssssssssss      "+ servicesLinkedList.size());

        if(servicesLinkedList.size()>0) {

            adapter = new AdapterLazyInfoList(this, servicesLinkedList);
            lv.setAdapter(adapter);
        }else
        {
            Intent startmain = new Intent(getApplicationContext(), NewServiceActiviry.class);
            startmain.putExtra("SHORTCODE",shortcode );
            startActivity(startmain);
        }
    }
}