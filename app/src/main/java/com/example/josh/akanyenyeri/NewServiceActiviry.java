package com.example.josh.akanyenyeri;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.LinkedList;

/**
 * Created by JOSH on 5/15/2017.
 */


public class NewServiceActiviry extends AppCompatActivity  /*implements
        OnItemSelectedListener */{

    EditText txtNameService,txtCodeService;
    Button btnSaveService , btnEditService;
    TextView textViewCategorie;
    Intent intent;
    Shortcode shortcode;
   // Spinner spinnerCategorie;
    Categorie currentCategorie;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_service_interface_activiry);

        txtCodeService = (EditText)findViewById(R.id.txtservicecode);
        txtNameService = (EditText)findViewById(R.id.txtservicename);
        btnSaveService = (Button) findViewById(R.id.btnsaveservice);
        btnEditService = (Button) findViewById(R.id.btneditservice);
        textViewCategorie = (TextView) findViewById(R.id.textviewcategorie);
//        spinnerCategorie = (Spinner) findViewById(R.id.categorie_spinner);
//        spinnerCategorie.setOnItemSelectedListener(this);

        intent = getIntent();
        shortcode  = (Shortcode)intent.getSerializableExtra("SHORTCODE");


        loadCategories(shortcode.SHORT_CODE);

        String tosearchArray []= shortcode.SHORT_CODE.split(";;;");
        System.out.println("...................HARIMO:"+shortcode.SHORT_CODE);
        System.out.println("...................HARIMO:"+tosearchArray[1]);
         currentCategorie = MainActivity.db.getCategorieByName(tosearchArray[1]);
        textViewCategorie.setText(currentCategorie.toString());

        String PhoneNum = "MY_"+MainActivity.currentSelectedCompany.NAME_COMPANY+"_"+FirstActivity.choosenCountry+"_NUMBER";
System.out.println("MY NUMBER:"+PhoneNum);
        if(currentCategorie.toString().equals(PhoneNum))
        {
            txtNameService.setHint("Numero yawe ya "+MainActivity.currentSelectedCompany.NAME_COMPANY);
        }




        btnSaveService.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View arg0) {

                String textService = txtNameService.getText().toString();
                String textCode = txtCodeService.getText().toString();
                String categorie = currentCategorie.NAME_CATEGORIE.trim();

                if(textService != null && textCode !=null)
                {
                    int maxId = MainActivity.db.getMaxID("SELECT MAX(ID_SERVICE) FROM SERVICE");
                    MainActivity.db.addService(new Services((maxId+1),textCode,textService,categorie));
                    Toast.makeText(getApplicationContext(),"Successfuly Added",Toast.LENGTH_LONG).show();

                }
                else
                    Toast.makeText(getApplicationContext(),"Please fill all required fields!",Toast.LENGTH_LONG).show();

            }});

    }


    private void loadCategories(String toSearch) {


//        LinkedList<Categorie> categorieLinkedList = MainActivity.db.getAllCategories();
//        ArrayAdapter<Categorie> dataAdapter = new ArrayAdapter<Categorie>(this,
//                android.R.layout.simple_spinner_item, categorieLinkedList);
//        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinnerCategorie.setAdapter(dataAdapter);

        String tosearchArray []= toSearch.split(";;;");


        Categorie categorie = MainActivity.db.getCategorieByName(tosearchArray[1]);
    }


//    @Override
//    public void onItemSelected(AdapterView<?> parent, View view,
//                               int position, long id) {
//
//            currentCategorie = (Categorie) spinnerCategorie.getSelectedItem();
//        txtNameService.setHint(currentCategorie.HINT_CATEGORIE);
//
//    }
//
//    @Override
//    public void onNothingSelected(AdapterView<?> parent) {
//
//    }
}

