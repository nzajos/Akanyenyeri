package com.example.josh.akanyenyeri;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;

import static com.example.josh.akanyenyeri.CommonUtilities.SERVER_URL;

public class FirstActivity extends AppCompatActivity {


    // Progress Dialog
    private ProgressDialog pDialog;
    JSONParser jParser = new JSONParser();
    JSONArray countriesArray = null;
    public static final String TAG_country = "allcountries";
    AlertDialog alert = null;



    static String[] countries;
    public static String choosenCountry;
    static Country []countryArray;
    public static final String TAG_ID_COUNTRY = "ID_COUNTRY";
    public static final String TAG_NAME_COUNTRY = "NAME_COUNTRY";
    public static final String TAG_PHONE_CODE = "PHONE_CODE";
    int chosendD = 0;
    public static boolean isItTheFirstTime = false;


    // JSON Node names
    public static final String TAG_SUCCESS = "success";
    private static String url_all_countries = SERVER_URL+"get_all_countries.php";

    EditText txtPhone;
    TextView tvPhoneCode;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);



        boolean checkIfDbExist = doesDatabaseExist (getApplicationContext(),"BYIHUSEDB");
        System.out.println("NSANZE DB IZANA :"+checkIfDbExist);


        MainActivity.db = new DbHandler(this);
        System.out.println("NGIYE KUREBA KO DB IHARI:"+checkIfDbExist);

        if(!checkIfDbExist)
        {

            System.out.println("NSANZE  DB IHARI");

            FirstActivity.isItTheFirstTime = true;
            new LoadAllCountries().execute();

        }
        else
        {

            System.out.println("NGIYE KUREBA KO DB NTAYIHARI");
            FirstActivity.choosenCountry = MainActivity.db.getServiceByCode("V_IGIHUGU").NAME_SERVICE;

            Intent startmain = new Intent(getApplicationContext(), MainActivity.class);
            startmain.putExtra("IGIHUGU",FirstActivity.choosenCountry );
            // Close all views before launching Dashboard
            startActivity(startmain);
            finish();
        }




    }


    private static boolean doesDatabaseExist(Context context, String dbName) {
        File dbFile = context.getDatabasePath(dbName);
        return dbFile.exists();
    }




    public String AlertDialogDpt() {


        if (FirstActivity.countryArray == null) {

            FirstActivity.countryArray = new Country[1];
            FirstActivity.countries = new String[1];

            FirstActivity.countryArray[0] = (new Country(1,"RWANDA","+250"));
            FirstActivity.countries[0] = "RWANDA";

        }

        AlertDialog.Builder builder = new AlertDialog.Builder(FirstActivity.this);//ERROR ShowDialog cannot be resolved to a type
        builder.setTitle("SELECT A COUNTRY");


        LinearLayout layout = new LinearLayout(this);
        LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layout.setOrientation(LinearLayout.HORIZONTAL);
        layout.setLayoutParams(parms);
        layout.setGravity(Gravity.CLIP_VERTICAL);
        layout.setPadding(2, 2, 2, 2);


         txtPhone= new EditText(this);
        txtPhone.setInputType(InputType.TYPE_CLASS_PHONE);
        tvPhoneCode = new TextView(this);
        tvPhoneCode.setText("+250");
        tvPhoneCode.setTextSize(20);

        LinearLayout.LayoutParams tv1Params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        tv1Params.bottomMargin = 5;
        layout.addView(tvPhoneCode,tv1Params);
        layout.addView(txtPhone, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));



        builder.setSingleChoiceItems(FirstActivity.countries, -1,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        Toast.makeText(getApplicationContext(), FirstActivity.countryArray[item].NAME_COUNTRY,
                                Toast.LENGTH_SHORT).show();
                        chosendD = item;
                        tvPhoneCode.setText(FirstActivity.countryArray[item].PHONE_CODE);
                    }
                });


        builder.setView(layout);//gushyiraho phone txtview


        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                FirstActivity.choosenCountry = FirstActivity.countryArray[chosendD].NAME_COUNTRY;

                int maxId = MainActivity.db.getMaxID("SELECT MAX(ID_SERVICE) FROM SERVICE");
                MainActivity.db.addService(new Services((maxId+1),"V_IGIHUGU",FirstActivity.choosenCountry,"INVISIBLE"));
                 maxId = MainActivity.db.getMaxID("SELECT MAX(ID_SERVICE) FROM SERVICE");
                MainActivity.db.addService(new Services((maxId+1),"V_ADMIN_NUM","0783753235","INVISIBLE"));
                 maxId = MainActivity.db.getMaxID("SELECT MAX(ID_SERVICE) FROM SERVICE");
                MainActivity.db.addService(new Services((maxId+1),"V_LAST_SYNC","2017-01-01 03:14:07","INVISIBLE"));

                System.out.println("FireMainActivity IHISE IHAMAGARWA WANNA");
                Intent startmain = new Intent(getApplicationContext(), FireMainActivity.class);
                startmain.putExtra("IGIHUGU",FirstActivity.choosenCountry );
                // Close all views before launching Dashboard
                startActivity(startmain);
                alert.dismiss();
                finish();
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                alert.dismiss();
            }
        });

        alert = builder.create();
        alert.show();
        return "";
    }










    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    class LoadAllCountries extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(FirstActivity.this);
            pDialog.setMessage("Loading all counties. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
             pDialog.show();
        }

        protected String doInBackground(String... args) {
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            JSONObject json = jParser.makeHttpRequest(url_all_countries, "GET", params);
            // Check your log cat for JSON reponse
//            Log.d("All product: ", json.toString());

            try {
                int success = 0;
                if(json!= null)
                    success = json.getInt(TAG_SUCCESS);
                if (success == 1) {
                    countriesArray = json.getJSONArray(TAG_country);

                    FirstActivity.countryArray = new Country[countriesArray.length()];
                    FirstActivity.countries = new String[countriesArray.length()];

                    for (int i = 0; i < countriesArray.length(); i++) {
                        JSONObject c = countriesArray.getJSONObject(i);
                        int ID_COUNTRY = Integer.parseInt(c.getString(TAG_ID_COUNTRY));
                        String NAME_COUNTRY = c.getString(TAG_NAME_COUNTRY);
                        String PHONE_CODE = c.getString(TAG_PHONE_CODE);
                        System.out.println("IGIHUGU NI "+NAME_COUNTRY);

                        countryArray[i] =  new Country(ID_COUNTRY,NAME_COUNTRY,PHONE_CODE);
                        FirstActivity.countries[i] = NAME_COUNTRY;
                    }
                } else {
                    System.out.println("langue-----------------------------------------------SUCCESS IS 0");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(String file_url) {
            // dismiss the dialog after getting all product
              pDialog.dismiss();
            // updating UI from Background Thread

            runOnUiThread(new Runnable() {
                public void run() {
                    pDialog.dismiss();

                    AlertDialogDpt();
                    // updatelist(productList);
//                    shortcodeList = db.getAllShortCode();
//                    companyList = db.getAllCompany();
//                    companyDepList = db.getAllCompanyDep();
//                    serviceList = db.getAllServices();
//                    initViewPagerAndTabs();
                }
            });

        }
    }


}
