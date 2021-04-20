package com.example.josh.akanyenyeri;

/**
 * Created by JOSH on 5/16/2017.
 */
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.text.SimpleDateFormat;
import java.util.Date;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.LinearLayout;



public class Operationslist extends Activity {



    // List view
    private ListView lv;
    LinkedList<OPERATIONS> operationsLinkedList;

    // Listview Adapter

    AdapterLazyOperationsList adapter;
    String choosenData = "";

    // Search EditText
    EditText inputSearch;
    OPERATIONS operations;
    Button btnaddOnList;


    // ArrayList for Listview
    ArrayList<HashMap<String, String>> productList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.operation_list);


        Intent intent = getIntent();
        operations  = (OPERATIONS) intent.getSerializableExtra("OPERATION");

        // Listview Data
//        String products[] = {"Dell Inspiron", "HTC One X", "HTC Wildfire S", "HTC Sense", "HTC Sensation XE",
//                "iPhone 4S", "Samsung Galaxy Note 800",
//                "Samsung Galaxy S3", "MacBook Air", "Mac Mini", "MacBook Pro"};

        lv = (ListView) findViewById(R.id.list_view_op);
        inputSearch = (EditText) findViewById(R.id.inputSearch_op);
        btnaddOnList = (Button) findViewById(R.id.addop_button);



        getSearchResult("","","");




        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

//                System.out.println("BANKOZEHO NJYEWE:");
//
//
//                OPERATIONS operations = operationsLinkedList.get(position);
//
//                System.out.println("CHOOOOOOSEN DATA NIZI:"+operations.NAME_OPERATION);
////
//                Intent startmain = new Intent(getApplicationContext(), SendActivity.class);
//                startmain.putExtra("CHOOSEN_SERVICE", operations);
////                startmain.putExtra("SHORTCODE", shortcode);
//                startActivity(startmain);
//                System.out.println("NIHANO");

            }});


        btnaddOnList.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View arg0) {

               getIntervalleHeure();

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


}