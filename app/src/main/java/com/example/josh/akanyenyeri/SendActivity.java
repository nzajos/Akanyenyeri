package com.example.josh.akanyenyeri;

import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.Activity;
import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;


import android.app.AlertDialog;
import android.content.DialogInterface;

public class SendActivity extends Activity {

    private static final String TAG = SendActivity.class.getSimpleName();
    private static final int REQUEST_CODE_PICK_CONTACTS = 1;
    private Uri uriContact;
    private String contactID;     // contacts unique ID
    private  TextView txtContactName;
    private EditText txtFrw;
    private EditText txtReason;
    private Button btnSend,btnchangeContact;

    //variable za permission
    private static final int EXTERNAL_STORAGE_PERMISSION_CONSTANT = 100;
    private static final int REQUEST_PERMISSION_SETTING = 101;
    private boolean sentToSettings = false;
    private SharedPreferences permissionStatus;
    boolean isPermissionGranted = false;

    Shortcode shortcode;
    Services services;
    Intent intent;
    String PHONE_NUMBER;
    String CONTACT_NAME;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send);


          intent = getIntent();
        shortcode  = (Shortcode)intent.getSerializableExtra("SHORTCODE");


        permissionStatus = getSharedPreferences("permissionStatus",MODE_PRIVATE);

        txtContactName = (TextView) findViewById(R.id.txtnamecontact);
        txtFrw = (EditText) findViewById(R.id.txtfrw);
        txtReason = (EditText) findViewById(R.id.txtreason);
        btnSend = (Button) findViewById(R.id.btnsend) ;
        btnchangeContact = (Button) findViewById(R.id.btnchangecontact);

        //182*1*2*1*AMOUNT*PHONENUM#

        // using native contacts selection
        // Intent.ACTION_PICK = Pick an item from the data, returning what was selected.


        if(shortcode.INTERFACE.equals("INTER2")) {
            getcontact();
          }
        else
        if(shortcode.INTERFACE.equals("INTER3"))
        {
            services   = (Services) intent.getSerializableExtra("CHOOSEN_SERVICE");
            String str1 = "<font color=#00cc00>"+services.NAME_SERVICE+"</font> <br />" +
                    " <font color=#0000f1>"+services.CODE_SERVICE+"</font>";

            btnchangeContact.setVisibility(View.GONE);
            txtReason.setVisibility(View.GONE);

            txtContactName.setText(Html.fromHtml(str1));
        }
        else
        if(shortcode.INTERFACE.equals("INTER1"))
        {
            String phoneCompanyNum = "MY_"+MainActivity.currentSelectedCompany.NAME_COMPANY+"_"+FirstActivity.choosenCountry+"_NUMBER";
            System.out.println("MY NUMBER:"+phoneCompanyNum);
            Services services = MainActivity.db.getService(phoneCompanyNum);

            if(services != null) {

                PHONE_NUMBER = services.CODE_SERVICE;
                String str1 = "<font color=#00cc00>" + services.NAME_SERVICE + "</font> <br />" +
                        " <font color=#0000f1>" + services.CODE_SERVICE + "</font>";
                txtContactName.setText(Html.fromHtml(str1));
                txtReason.setVisibility(View.GONE);
            }else
            {
                Intent startmain = new Intent(getApplicationContext(), NewServiceActiviry.class);
                startmain.putExtra("SHORTCODE",shortcode );
                startActivity(startmain);
            }
        }


        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View arg0) {

                String shortcodeValue = shortcode.SHORT_CODE;
                String amount = txtFrw.getText().toString();
                String reason = txtReason.getText().toString();
                String phoneNum = PHONE_NUMBER;
                String phoneCompanyName = "MY_" + MainActivity.currentSelectedCompany.NAME_COMPANY + "_" + FirstActivity.choosenCountry + "_NUMBER";

                boolean dial = true;
                int maxId = MainActivity.db.getMaxID("SELECT MAX(ID_OPERATION) FROM OPERATIONS");
                int ID_OPERATION = maxId+1;
                String NAME_OPERATION = "";
                String TIER_PHONE_OPERATION ="";
                String REASON_OPERATION="";
                String COMPANY_OPERATION ="";
                double AMOUNT_OPERATION =0;
                String TIME_OPERATION ="";
                String STATUS_OPERATION ="NOT";

                if (shortcodeValue.contains("AMOUNT") && amount != null) {
                    try {
                        AMOUNT_OPERATION = Double.parseDouble(amount);
                    }catch (Exception e)
                    {
                        Toast.makeText(getApplicationContext(),"Amount should be a number pls!",Toast.LENGTH_LONG).show();
                    }
                    shortcodeValue = shortcodeValue.replace("AMOUNT", amount);
                }
                if (shortcodeValue.contains(phoneCompanyName) && phoneNum != null) {
                    TIER_PHONE_OPERATION = phoneNum;
                    shortcodeValue = shortcodeValue.replace(";;;" + phoneCompanyName + ";;;", phoneNum);
                }
                if (shortcodeValue.contains("CASH_POWER") && services.NAME_SERVICE != null) {
                    shortcodeValue = shortcodeValue.replace(";;;CASH_POWER;;;", services.CODE_SERVICE);
                }
                if (shortcodeValue.contains("REASON") && reason != null) {
                    shortcodeValue = shortcodeValue.replace("REASON", reason);
                }





                if (shortcode.SHORT_CODE.contains("AMOUNT") && (amount == null || amount.equals(""))) {
                    dial = false;
                    Toast.makeText(getApplicationContext(), "Enter amount pls!", Toast.LENGTH_LONG).show();
                }

                    if (phoneCompanyName != null && shortcode.SHORT_CODE.contains(phoneCompanyName) && phoneNum == null ) {
                        dial = false;
                        Toast.makeText(getApplicationContext(), "Select contact pls!", Toast.LENGTH_LONG).show();
                    }

                if(shortcode.SHORT_CODE.contains("CASH_POWER") && (services.NAME_SERVICE == null || services.NAME_SERVICE.equals(""))){
                    dial = false;
                    Toast.makeText(getApplicationContext(), "Register service code pls!", Toast.LENGTH_LONG).show();
                }
                if(shortcode.SHORT_CODE.contains("REASON") && (reason == null ||reason.equals(""))) {
                    dial = false;
                    Toast.makeText(getApplicationContext(), "Enter reason code pls!", Toast.LENGTH_LONG).show();

                }

                if(dial) {


                    MainActivity.db.addOperation(new OPERATIONS(ID_OPERATION, NAME_OPERATION, TIER_PHONE_OPERATION, REASON_OPERATION, COMPANY_OPERATION, AMOUNT_OPERATION, TIME_OPERATION, STATUS_OPERATION));

                    dialShortCode(shortcodeValue);
                }





            }});


        btnchangeContact.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View arg0) {

                try {
                    getcontact();
                }catch (Exception e)
                {

                    MainActivity.permissionToCheck = android.Manifest.permission.READ_CONTACTS;
                    checkPermission();
                }


            }});


    }


    public void showAlert(String message,String title)
    {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                SendActivity.this);

        // set title
        alertDialogBuilder.setTitle(title);

        // set dialog message
        alertDialogBuilder
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {


                        Intent intent = new Intent();
                        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package", getPackageName(), null);
                        intent.setData(uri);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("No",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        // if this button is clicked, just close
                        // the dialog box and do nothing
                        dialog.cancel();
                    }
                });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }

    void getcontact()
    {


        startActivityForResult(new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI), REQUEST_CODE_PICK_CONTACTS);


    }

    public  void dialShortCode(String codeToDial)
    {
        String encodedHash = Uri.encode("#");
        String ussd = "*" +codeToDial+ encodedHash;

        System.out.println("TO DIAL IS:"+ussd);
        startActivityForResult(new Intent("android.intent.action.CALL",
                Uri.parse("tel:" + ussd)), 1);
    }


    //    public void onClickSelectContact(View btnSelectContact) {
//
//        // using native contacts selection
//        // Intent.ACTION_PICK = Pick an item from the data, returning what was selected.
//        startActivityForResult(new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI), REQUEST_CODE_PICK_CONTACTS);
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_PICK_CONTACTS && resultCode == RESULT_OK) {
            Log.d(TAG, "Response: " + data.toString());
            uriContact = data.getData();


            String contactName = "";
            String phoneNumber = "";
            try {
                  contactName = retrieveContactName();
                  phoneNumber = retrieveContactNumber();
            }catch (Exception e)
            {
                MainActivity.permissionToCheck = android.Manifest.permission.READ_CONTACTS;
                 checkPermission();

            }

           //  retrieveContactPhoto();
            if(contactName != null && phoneNumber!= null)
            {

                phoneNumber = checkNum(phoneNumber);

                PHONE_NUMBER = phoneNumber;
                CONTACT_NAME = contactName;

                String str1 = "<font color=#00cc00>"+contactName+"</font> <br />" +
                        " <font color=#0000f1>"+phoneNumber+"</font>";

                txtContactName.setText(Html.fromHtml(str1));
            }
            else
                txtContactName.setText("CONTACT NOT AVAILABLE");





        }
    }




    private String checkNum(String contactNumber)
    {
        contactNumber = contactNumber.replace(" ","");
        if(contactNumber.length()>=10)
        {
            contactNumber = contactNumber.substring(contactNumber.length()-10,contactNumber.length());
        }
        return contactNumber;
    }




    public  void checkPermission()
    {

        if (ActivityCompat.checkSelfPermission(SendActivity.this, MainActivity.permissionToCheck) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(SendActivity.this, MainActivity.permissionToCheck)) {
                //Show Information about why you need the permission
                AlertDialog.Builder builder = new AlertDialog.Builder(SendActivity.this);
                builder.setTitle("Allow permission");
                builder.setMessage("Emeza iyi permission kugirango uhabwe info.");
                builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        isPermissionGranted = true;
                        dialog.cancel();
                        ActivityCompat.requestPermissions(SendActivity.this, new String[]{MainActivity.permissionToCheck}, EXTERNAL_STORAGE_PERMISSION_CONSTANT);

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
                AlertDialog.Builder builder = new AlertDialog.Builder(SendActivity.this);
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
                ActivityCompat.requestPermissions(SendActivity.this, new String[]{MainActivity.permissionToCheck}, EXTERNAL_STORAGE_PERMISSION_CONSTANT);
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

            } else {
                if (ActivityCompat.shouldShowRequestPermissionRationale(SendActivity.this, MainActivity.permissionToCheck)) {
                    //Show Information about why you need the permission
                    AlertDialog.Builder builder = new AlertDialog.Builder(SendActivity.this);
                    builder.setTitle("Need Storage Permission");
                    builder.setMessage("This app needs storage permission");
                    builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();

                            ActivityCompat.requestPermissions(SendActivity.this, new String[]{MainActivity.permissionToCheck}, EXTERNAL_STORAGE_PERMISSION_CONSTANT);

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


}
