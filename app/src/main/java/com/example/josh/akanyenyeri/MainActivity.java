package com.example.josh.akanyenyeri;


        import android.app.AlertDialog;
        import android.content.Context;
        import android.content.DialogInterface;
        import android.net.Uri;
        import android.os.Build;
        import android.os.Bundle;
        import android.support.design.widget.TabLayout;
        import android.support.v4.app.Fragment;
        import android.support.v4.app.FragmentManager;
        import android.support.v4.app.FragmentPagerAdapter;
        import android.support.v4.view.ViewPager;
        import android.support.v7.app.AppCompatActivity;
        import android.support.v7.widget.Toolbar;

        import java.io.File;
        import java.sql.Timestamp;
        import java.text.SimpleDateFormat;
        import java.util.ArrayList;
        import java.util.Date;
        import java.util.HashMap;
        import java.util.LinkedList;
        import java.util.List;

        import org.apache.http.NameValuePair;
        import org.apache.http.message.BasicNameValuePair;
        import org.json.JSONArray;
        import org.json.JSONException;
        import org.json.JSONObject;

        import android.app.ListActivity;
        import android.app.ProgressDialog;
        import android.content.Intent;
        import android.os.AsyncTask;
        import android.os.Bundle;
        import android.telephony.TelephonyManager;
        import android.text.Editable;
        import android.text.TextWatcher;
        import android.util.Log;
        import android.view.View;
        import android.widget.AdapterView;
        import android.widget.AdapterView.OnItemLongClickListener;
        import android.widget.AdapterView.OnItemClickListener;
        import static com.example.josh.akanyenyeri.CommonUtilities.SERVER_URL;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.Filterable;
        import android.widget.ImageButton;
        import android.widget.LinearLayout;
        import android.widget.ListAdapter;
        import android.widget.ListView;
        import android.widget.SimpleAdapter;
        import android.widget.TextView;
        import android.widget.Toast;
        import android.support.design.widget.FloatingActionButton;

        import java.util.ArrayList;
        import java.util.List;
        import java.util.concurrent.TimeUnit;






public class MainActivity extends AppCompatActivity  {


    // Progress Dialog
    private ProgressDialog pDialog;

    // Creating JSON Parser object
    JSONParser jParser = new JSONParser();


    static LinkedList<Shortcode> shortcodeList;
    static LinkedList<Company> companyList;
    static LinkedList<Companydep> companyDepList;
    static LinkedList<Services> serviceList;
    static LinkedList<Shortcode> shortcodeListArray[];
    EditText inputSearch;
    LinearLayout downLinear;

    public static Company currentSelectedCompany;


    private static String url_all_shortcode = SERVER_URL+"get_all_shortcodes.php";
    private static String url_all_company = SERVER_URL+"get_all_companies.php";
    private static String url_sync_messages = SERVER_URL+"insert_messages.php";
    private static String url_all_companydep = SERVER_URL+"get_all_companies_dep.php";
    private static String url_all_langue = SERVER_URL+"get_all_langue.php";
    private static String url_all_categories = SERVER_URL+"get_all_categories.php";
    private static String url_all_countries = SERVER_URL+"get_all_countries.php";

    // JSON Node names
    public static final String TAG_SUCCESS = "success";
    public static final String TAG_company = "allcompanies";
    public static final String TAG_companydep = "allcompaniesdep";
    public static final String TAG_shortcode = "allshortcode";
    public static final String TAG_langue = "alllangue";
    public static final String TAG_categorie = "allcategories";
    public static final String TAG_country = "allcountries";
    public static final String TAG_messages = "messages";


    public static final String TAG_CODE_COMPANY = "CODE_COMPANY";
    public static final String TAG_NAME_COMPANY = "NAME_COMPANY";
    public static final String TAG_LOGO = "LOGO";
    public static final String TAG_COUNTRY = "COUNTRY";
    public static final String TAG_MONEY_SYSTEM = "MONEY_SYSTEM";
    public static final String TAG_SHORT_CODE = "SHORT_CODE";

    public static final String TAG_ID_COMPANY_DEP = "ID_COMPANY_DEP";
    public static final String TAG_CODE_COMPANY_DEP = "CODE_COMPANY_DEP";
    public static final String TAG_NAME_COMPANY_DEP = "NAME_COMPANY_DEP";
    public static final String TAG_LOGO_DEP = "LOGO_DEP";

    public static final String TAG_ID_SHORTCODE = "ID_SHORTCODE";
    public static final String TAG_SHORTCODE = "SHORTCODE";
    public static final String TAG_ID_COMPANY = "ID_COMPANY";
    public static final String TAG_ID_COMPANY_OWNER = "CODE_COMPANY_OWNER";
    public static final String TAG_EXPLANATION = "EXPLANATION";
    public static final String TAG_INTERFACE = "INTERFACE";
    public static final String TAG_CHARGE_AMOUNT = "CHARGE_AMOUNT";
    public static final String TAG_IMAGE_PATH = "IMAGE_PATH";

    public static final String TAG_ID_LANGUE = "ID_LANGUE";
    public static final String TAG_KINYARWANDA = "KINYARWANDA";
    public static final String TAG_KIRUNDI = "KIRUNDI";
    public static final String TAG_FRANCAIS = "FRANCAIS";
    public static final String TAG_ENGLISH = "ENGLISH";


    public static final String TAG_ID_CATEGORIE = "ID_CATEGORIE";
    public static final String TAG_NAME_CATEGORIE = "NAME_CATEGORIE";
    public static final String TAG_HINT_CATEGORIE = "HINT_CATEGORIE";
    public static final String TAG_DIGIT_NUMBER_CATEGORIE= "DIGIT_NUMBER_CATEGORIE";
    public static int currentIndex = 0;



    JSONArray companyArray = null;
    JSONArray companydepArray = null;
    JSONArray shortcodeArray = null;
    JSONArray langueArray = null;
    JSONArray categorieArray = null;



    public static String permissionToCheck;

   // FloatingActionButton FAB;
    Toolbar mToolbar ;
    static DbHandler db;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppThemeBlue);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



       // db = new DbHandler(this);

       // FAB =(FloatingActionButton) findViewById(R.id.fabButton);
        downLinear = (LinearLayout) findViewById(R.id.lineardown);
        downLinear.setVisibility(View.INVISIBLE);




//        FAB.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Toast.makeText(MainActivity.this,"Hello Worl",Toast.LENGTH_SHORT).show();
//
//                Intent startmain = new Intent(getApplicationContext(), NewServiceActiviry.class);
//                startmain.putExtra("shortcode","" );
//                // Close all views before launching Dashboard
//                startActivity(startmain);
//
//            }
//
//
//
//        });



        shortcodeList = new LinkedList<Shortcode>();
        companyList = new LinkedList<Company>();
        companyDepList = new LinkedList<Companydep>();
        serviceList = new LinkedList<Services>();

        loadDataFromDb();
        initToolbar();






        Date date = new Date();
        Services lastSyncService = MainActivity.db.getServiceByCode("V_LAST_SYNC");

        String toDayTimeStamp = new Timestamp(date.getTime()).toString();
        String lastSyncTimeStamp = lastSyncService.NAME_SERVICE;


        System.out.println("BACKGROUND SYNC IRI GUKORA:"+lastSyncTimeStamp);
        System.out.println("LAST SYNC TIME NI:"+lastSyncTimeStamp);
        System.out.println("TODAY TIME NI:"+toDayTimeStamp);

        if(lastSyncTimeStamp != null) {
            long dateDiff = dateDifference(lastSyncTimeStamp, toDayTimeStamp);
            System.out.println("DATE DIFFERENCE IS:"+dateDiff);
            if(dateDiff >= 30)//IZAJYA I SCYNCINGA 1 MU MINSI 30
            {

                new syncMessages().execute();
                new LoadAllCompanies().execute();
                new LoadAllCompaniesDep().execute();
                new LoadAllShortCode().execute();
                new LoadAllLangue().execute();
                new LoadAllCategories().execute();

                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    // Restore interrupt status.
                    Thread.currentThread().interrupt();
                }
            }
        }












        System.out.println("SHORTCODE SIZE"+shortcodeList.size());
        System.out.println("COMPANY SIZE"+companyList.size());
        System.out.println("COMPANYDEP SIZE"+companyDepList.size());
        System.out.println("COMPANYDEP SIZE"+shortcodeListArray.length);
        System.out.println("VALUE YA ISFIRTSTIEM IS:"+FirstActivity.isItTheFirstTime);

        if(!FirstActivity.isItTheFirstTime)
        initViewPagerAndTabs();

    }



    long dateDifference(String d1,String d2)
    {
        long numberOfDays = 0;
        SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");
        String inputString1 = d1.split(" ")[0];
        String inputString2 = d2.split(" ")[0];

        try {
            Date date1 = myFormat.parse(inputString1);
            Date date2 = myFormat.parse(inputString2);
            long diff = date2.getTime() - date1.getTime();

            numberOfDays =TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
            System.out.println ("Days: " + numberOfDays);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return numberOfDays;
    }



    private static boolean doesDatabaseExist(Context context, String dbName) {
        File dbFile = context.getDatabasePath(dbName);
        return dbFile.exists();
    }

    private void initToolbar() {
         mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        setTitle(getString(R.string.app_name));

        String image = "mtnlogo";
        int id = 0;

        id =  getApplicationContext().getResources().getIdentifier("com.ishyiga.mini:drawable/" + image, null, null);

        mToolbar.setBackgroundResource(id);
        mToolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
    }


    private void initViewPagerAndTabs() {
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
        PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager());
System.out.println("NDAHAMAGAWE NANONE=========");
        currentSelectedCompany = companyList.get(0);
        for(int i=0;i<companyList.size();i++)
        {

            shortcodeListArray[i] = db.getAllShortCode(" WHERE CODE_COMPANY = '"+companyList.get(i).getCODE_COMPANY()+"'");
            pagerAdapter.addFragment(MainFragment.createInstance(i), companyList.get(i).NAME_COMPANY);

        }
//        pagerAdapter.addFragment(MainFragment.createInstance(shortcodeList.size()), getString(R.string.tab_4));
//        pagerAdapter.addFragment(MainFragment.createInstance(shortcodeList.size()), getString(R.string.tab_5));
        viewPager.setAdapter(pagerAdapter);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);



        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {

                currentIndex = position;
                Toast.makeText(MainActivity.this,
                        "COMPANY IS: " + companyList.get(currentIndex).NAME_COMPANY, Toast.LENGTH_SHORT).show();
                currentSelectedCompany = companyList.get(currentIndex);
//                mToolbar.setLogo(R.drawable.mtnlogo);
            }
            // This method will be invoked when the current page is scrolled
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                // Code goes here
            }
            // Called when the scroll state changes:
            // SCROLL_STATE_IDLE, SCROLL_STATE_DRAGGING, SCROLL_STATE_SETTLING
            @Override
            public void onPageScrollStateChanged(int state) {
                // Code goes here
            }
        });
    }

    static class PagerAdapter extends FragmentPagerAdapter {

        private final List<Fragment> fragmentList = new ArrayList<>();
        private final List<String> fragmentTitleList = new ArrayList<>();

        public PagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        public void addFragment(Fragment fragment, String title) {
            fragmentList.add(fragment);
            fragmentTitleList.add(title);
        }



        String getMessageToSend()
        {
            String messagesToSend="";

            TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);

            String imeiNumber="";
            try {
                imeiNumber = telephonyManager.getDeviceId();
            }
            catch(SecurityException e)
            {
                System.out.println("IMEI SECURITY IS DISABLED");

            }

            String serialNumber = Build.SERIAL;

            LinkedList<Messages> messagesLinkedList = db.getAllMessageToSync();

            if(messagesLinkedList.size()>0)
            {
                for(int i=0;i<messagesLinkedList.size();i++) {
                    Messages messages = messagesLinkedList.get(i);
                    messagesToSend = messagesToSend+messages.CONTENT_MESSAGE+";;;"+messages.SENDER_MESSAGE+";;;"+
                            messages.TIME_MESSAGE+";;;"+serialNumber+";;;"+"NONE"+";;;"+messages.ID_MESSAGE+"###";
                }
            }
            return messagesToSend;
        }




        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }
        @Override
        public CharSequence getPageTitle(int position) {
            return fragmentTitleList.get(position);
        }



    }









    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Background Async Task to Load all products by making HTTP Request
     * */
    class syncMessages extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Loading all products. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            // pDialog.show();
        }

        protected String doInBackground(String... args) {
            List<NameValuePair> params = new ArrayList<NameValuePair>();

            String messagesToSend = getMessageToSend();
            params.add(new BasicNameValuePair("COUNTRY_NAME", FirstActivity.choosenCountry));
            params.add(new BasicNameValuePair("messages", messagesToSend));

            JSONObject json = jParser.makeHttpRequest(url_sync_messages, "POST", params);
            // Check your log cat for JSON reponse
//            Log.d("All product: ", json.toString());

            System.out.println("NOHEREJE MURI MESSAGE:"+messagesToSend);

            try {
                int success = 0;
                String response = "";
                if(json!= null)
                    success = json.getInt(TAG_SUCCESS);
                if (success == 1) {
                    response = json.getString(TAG_messages);

                    if(response.contains("###")) {
                        String idArray[] = response.split("###");


                    for (int i = 0; i < idArray.length; i++) {

                        db.updateMessage(idArray[i]);
                    }
                    }
                } else {
                    System.out.println("company-----------------------------------------------SUCCESS IS 0");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(String file_url) {
            // dismiss the dialog after getting all product
            //  pDialog.dismiss();
            // updating UI from Background Thread
            runOnUiThread(new Runnable() {
                public void run() {
                    // updatelist(productList);
                }
            });

        }
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Background Async Task to Load all products by making HTTP Request
     * */
    class LoadAllCompanies extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Loading all products. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            // pDialog.show();
        }

        protected String doInBackground(String... args) {
            List<NameValuePair> params = new ArrayList<NameValuePair>();

            params.add(new BasicNameValuePair("COUNTRY_NAME", FirstActivity.choosenCountry));

            JSONObject json = jParser.makeHttpRequest(url_all_company, "GET", params);
            // Check your log cat for JSON reponse
//            Log.d("All product: ", json.toString());

            try {
                int success = 0;
                if(json!= null)
                    success = json.getInt(TAG_SUCCESS);
                if (success == 1) {
                    companyArray = json.getJSONArray(TAG_company);

                    //kugirango tutagira error ya contraint key
                    if(companyArray.length()>0)
                        db.deleteAllFromTable(DbHandler.TABLE_COMPANY);


                    for (int i = 0; i < companyArray.length(); i++) {
                        JSONObject c = companyArray.getJSONObject(i);
                        int ID_COMPANY = Integer.parseInt(c.getString(TAG_ID_COMPANY));
                        String CODE_COMPANY = c.getString(TAG_CODE_COMPANY);
                        String NAME_COMPANY = c.getString(TAG_NAME_COMPANY);
                        String LOGO = c.getString(TAG_LOGO);
                        String COUNTRY =c.getString(TAG_COUNTRY);
                        String MONEY_SYSTEM =c.getString(TAG_MONEY_SYSTEM);
                        String SHORT_CODE =c.getString(TAG_SHORT_CODE);
                        Company map = new Company(ID_COMPANY,CODE_COMPANY,NAME_COMPANY,LOGO,COUNTRY,MONEY_SYSTEM,SHORT_CODE);
                        System.out.println("name: "+CODE_COMPANY);
                        db.addCompany(map);
                    }
                } else {
System.out.println("company-----------------------------------------------SUCCESS IS 0");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(String file_url) {
            // dismiss the dialog after getting all product
            //  pDialog.dismiss();
            // updating UI from Background Thread
            runOnUiThread(new Runnable() {
                public void run() {
                    // updatelist(productList);
                }
            });

        }
    }


////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Background Async Task to Load all products by making HTTP Request
     * */




    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Background Async Task to Load all products by making HTTP Request
     * */
    class LoadAllLangue extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Loading all langue. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            // pDialog.show();
        }

        protected String doInBackground(String... args) {
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            JSONObject json = jParser.makeHttpRequest(url_all_langue, "GET", params);
            // Check your log cat for JSON reponse
//            Log.d("All product: ", json.toString());

            try {
                int success = 0;
                if(json!= null)
                    success = json.getInt(TAG_SUCCESS);
                if (success == 1) {
                    langueArray = json.getJSONArray(TAG_langue);

                    //kugirango tutagira error ya contraint key
                    if(langueArray.length()>0)
                        db.deleteAllFromTable(DbHandler.TABLE_LANGUE);

                    for (int i = 0; i < langueArray.length(); i++) {
                        JSONObject c = langueArray.getJSONObject(i);
                        int ID_LANGUE = Integer.parseInt(c.getString(TAG_ID_LANGUE));
                        String KINYARWANDA = c.getString(TAG_KINYARWANDA);
                        String KIRUNDI = c.getString(TAG_KIRUNDI);
                        String FRANCAIS = c.getString(TAG_FRANCAIS);
                        String ENGLISH =c.getString(TAG_ENGLISH);
                        Langue map = new Langue(ID_LANGUE,KINYARWANDA,KIRUNDI,FRANCAIS,ENGLISH);
                        System.out.println("name: "+KINYARWANDA);
                        db.addLangue(map);
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
            //  pDialog.dismiss();
            // updating UI from Background Thread

            runOnUiThread(new Runnable() {
                public void run() {
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




    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Background Async Task to Load all products by making HTTP Request
     * */
    class LoadAllService extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Loading all langue. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            // pDialog.show();
        }

        protected String doInBackground(String... args) {
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            JSONObject json = jParser.makeHttpRequest(url_all_langue, "GET", params);
            // Check your log cat for JSON reponse
//            Log.d("All product: ", json.toString());

            try {
                int success = 0;
                if(json!= null)
                    success = json.getInt(TAG_SUCCESS);
                if (success == 1) {
                    langueArray = json.getJSONArray(TAG_langue);

                    //kugirango tutagira error ya contraint key
                    if(langueArray.length()>0)
                        db.deleteAllFromTable(DbHandler.TABLE_LANGUE);

                    for (int i = 0; i < langueArray.length(); i++) {
                        JSONObject c = langueArray.getJSONObject(i);
                        int ID_LANGUE = Integer.parseInt(c.getString(TAG_ID_LANGUE));
                        String KINYARWANDA = c.getString(TAG_KINYARWANDA);
                        String KIRUNDI = c.getString(TAG_KIRUNDI);
                        String FRANCAIS = c.getString(TAG_FRANCAIS);
                        String ENGLISH =c.getString(TAG_ENGLISH);
                        Langue map = new Langue(ID_LANGUE,KINYARWANDA,KIRUNDI,FRANCAIS,ENGLISH);
                        System.out.println("name: "+KINYARWANDA);
                        db.addLangue(map);
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
            //  pDialog.dismiss();
            // updating UI from Background Thread

            runOnUiThread(new Runnable() {
                public void run() {
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


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


}
