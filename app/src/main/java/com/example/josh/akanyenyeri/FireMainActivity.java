package com.example.josh.akanyenyeri;

/**
 * Created by JOSH on 6/30/2017.
 */
import android.*;
import android.Manifest;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.josh.akanyenyeri.R;
import com.google.firebase.messaging.FirebaseMessaging;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class FireMainActivity extends AppCompatActivity {

    private static final String TAG = FireMainActivity.class.getSimpleName();
    private BroadcastReceiver mRegistrationBroadcastReceiver;
    private TextView txtRegId, txtMessage;
    public String imeiNumber,regIdFireBase,serialNumber;
    boolean isPermissionGranted = false;
    boolean yafunguyeBwaMbere = false;//kuko iyo permission ari nyinshi yayifungura kenshi igatera window leaked error...


    //variable za permission
    private static final int EXTERNAL_STORAGE_PERMISSION_CONSTANT = 100;
    private static final int REQUEST_PERMISSION_SETTING = 101;
    private boolean sentToSettings = false;
    private SharedPreferences permissionStatus;


    private ProgressDialog pDialog;
    JSONParser jParser = new JSONParser();

    public static final String TAG_SUCCESS = "success";
    private static String url_insert_user = CommonUtilities.SERVER_URL+"insert_user.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firebase_id);

        txtRegId = (TextView) findViewById(R.id.txt_reg_id);
        txtMessage = (TextView) findViewById(R.id.txt_push_message);
        permissionStatus = getSharedPreferences("permissionStatus",MODE_PRIVATE);


        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                // checking for type intent filter
                if (intent.getAction().equals(FireConfig.REGISTRATION_COMPLETE)) {
                    // gcm successfully registered
                    // now subscribe to `global` topic to receive app wide notifications
                    FirebaseMessaging.getInstance().subscribeToTopic(FireConfig.TOPIC_GLOBAL);

//                    displayFirebaseRegId();
                    System.out.println("RESTTRATION COMPLETE FIREBASE");


                } else if (intent.getAction().equals(FireConfig.PUSH_NOTIFICATION)) {
                    // new push notification is received

                    String message = intent.getStringExtra("message");

                    Toast.makeText(getApplicationContext(), "Push notification: " + message, Toast.LENGTH_LONG).show();

                    txtMessage.setText(message);
                }
            }
        };


        Intent startmain = new Intent(getApplicationContext(),PermissionActivity.class);
        startmain.putExtra("IGIHUGU",FirstActivity.choosenCountry );
        // Close all views before launching Dashboard
        startActivity(startmain);


//        displayFirebaseRegId();
        MainActivity.permissionToCheck = android.Manifest.permission.READ_PHONE_STATE;
         checkPermission();
        MainActivity.permissionToCheck = Manifest.permission.SEND_SMS;
         checkPermission();
        MainActivity.permissionToCheck = Manifest.permission.RECEIVE_SMS;
         checkPermission();
        MainActivity.permissionToCheck = Manifest.permission.READ_SMS;
         checkPermission();
        MainActivity.permissionToCheck = Manifest.permission.WRITE_EXTERNAL_STORAGE;
        checkPermission();

//        displayFirebaseRegId();
    }

    // Fetches reg id from shared preferences
    // and displays on the screen
    private void displayFirebaseRegId() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences(FireConfig.SHARED_PREF, 0);
//        String regId = pref.getString("regId", null);




            System.out.println("NIKOMEREJE WANA");
            regIdFireBase = pref.getString("regId", null);
            TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);

            try {
                imeiNumber = telephonyManager.getDeviceId();
            }
            catch(SecurityException e)
            {
                System.out.println("IMEI SECURITY IS DISABLED");

            }
            serialNumber = Build.SERIAL;

            System.out.println("regIdFireBase:"+regIdFireBase);
            System.out.println("imeiNumber:"+imeiNumber);
            System.out.println("serialNumber:"+serialNumber);

            Log.e(TAG, "Firebase reg id: " + regIdFireBase);

            if (!TextUtils.isEmpty(regIdFireBase)) {
//            txtRegId.setText("Firebase Reg Id: " + regId);

                new sendInfoToserver().execute();

            } else
                txtRegId.setText("Reg Id is not received yet!");




    }

    @Override
    protected void onResume() {
        super.onResume();

        // register GCM registration complete receiver
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(FireConfig.REGISTRATION_COMPLETE));

        // register new push message receiver
        // by doing this, the activity will be notified each time a new message arrives
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(FireConfig.PUSH_NOTIFICATION));

        // clear the notification area when the app is opened
        FireNotificationUtils.clearNotifications(getApplicationContext());
    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        super.onPause();
    }



public  void checkPermission()
{

    if (ActivityCompat.checkSelfPermission(FireMainActivity.this, MainActivity.permissionToCheck) != PackageManager.PERMISSION_GRANTED) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(FireMainActivity.this, MainActivity.permissionToCheck)) {
            //Show Information about why you need the permission
            AlertDialog.Builder builder = new AlertDialog.Builder(FireMainActivity.this);
            builder.setTitle("Allow permission");
            builder.setMessage("Emeza iyi permission kugirango uhabwe info.");
            builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    isPermissionGranted = true;
                    dialog.cancel();
                    ActivityCompat.requestPermissions(FireMainActivity.this, new String[]{MainActivity.permissionToCheck}, EXTERNAL_STORAGE_PERMISSION_CONSTANT);

                    System.out.println("NDI MURI POSITIVEBUTON 1");

                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                    isPermissionGranted = false;
                }
            });
            builder.show();

        } else if (permissionStatus.getBoolean(MainActivity.permissionToCheck,false)) {
            //Previously Permission Request was cancelled with 'Dont Ask Again',
            // Redirect to Settings after showing Information about why you need the permission
            AlertDialog.Builder builder = new AlertDialog.Builder(FireMainActivity.this);
            builder.setTitle("Allow permission");
            builder.setMessage("Emeza iyi permission kugirango uhabwe info.");
            builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                    sentToSettings = true;
                    isPermissionGranted = true;
                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    Uri uri = Uri.fromParts("package", getPackageName(), null);
                    intent.setData(uri);
                    startActivityForResult(intent, REQUEST_PERMISSION_SETTING);
                    Toast.makeText(getBaseContext(), "Go to Permissions to Grant Storage", Toast.LENGTH_LONG).show();

                    System.out.println("NDI MURI POSITIVEBUTON 1");
                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                    isPermissionGranted = false;
                }
            });
            builder.show();
        } else {
            //just request the permission
            ActivityCompat.requestPermissions(FireMainActivity.this, new String[]{MainActivity.permissionToCheck}, EXTERNAL_STORAGE_PERMISSION_CONSTANT);
        }

        SharedPreferences.Editor editor = permissionStatus.edit();
        editor.putBoolean(MainActivity.permissionToCheck,true);
        editor.commit();


    } else {
        //You already have the permission, just go ahead.


    }
}



    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == EXTERNAL_STORAGE_PERMISSION_CONSTANT) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //The External Storage Write Permission is granted to you... Continue your left job...

                if(!yafunguyeBwaMbere) {//kuko iyo permission ari nyinshi yayifungura kenshi igatera window leaked error....
                    yafunguyeBwaMbere = true;
                    displayFirebaseRegId();
                }
            } else {
                if (ActivityCompat.shouldShowRequestPermissionRationale(FireMainActivity.this, MainActivity.permissionToCheck)) {
                    //Show Information about why you need the permission
                    AlertDialog.Builder builder = new AlertDialog.Builder(FireMainActivity.this);
                    builder.setTitle("Need Storage Permission");
                    builder.setMessage("This app needs storage permission");
                    builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();

                            ActivityCompat.requestPermissions(FireMainActivity.this, new String[]{MainActivity.permissionToCheck}, EXTERNAL_STORAGE_PERMISSION_CONSTANT);

                        }
                    });
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    builder.show();
                } else {
                    Toast.makeText(getBaseContext(),"Unable to get Permission",Toast.LENGTH_LONG).show();
                }
            }
        }
    }


    class sendInfoToserver extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(FireMainActivity.this);
            pDialog.setMessage("Connecting to Server. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
             pDialog.show();
        }

        protected String doInBackground(String... args) {
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("imei",imeiNumber));
            params.add(new BasicNameValuePair("serial",serialNumber));
            params.add(new BasicNameValuePair("keyfirebase",regIdFireBase));
            params.add(new BasicNameValuePair("country",FirstActivity.choosenCountry ));
            JSONObject json = jParser.makeHttpRequest(url_insert_user, "POST", params);
            // Check your log cat for JSON reponse
//            Log.d("All product: ", json.toString());

            try {
                int success = 0;
                if(json!= null)
                    success = json.getInt(TAG_SUCCESS);
                if (success == 1) {
                   System.out.println("BIGEZEMO TAYARI");
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
                    Intent startmain = new Intent(getApplicationContext(),MainActivity.class);
                    startmain.putExtra("IGIHUGU",FirstActivity.choosenCountry );
                    // Close all views before launching Dashboard
                    startActivity(startmain);
                    finish();
                   // AlertDialogDpt();
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
