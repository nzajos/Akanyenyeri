package com.example.josh.akanyenyeri;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import android.content.SharedPreferences;
import android.content.pm.PackageManager;

import android.support.v4.app.ActivityCompat;


public class MainFragment extends Fragment implements SearchView.OnQueryTextListener {

    public final static String ITEMS_COUNT_KEY = "PartThreeFragment$ItemsCount";
    public final static String SHORTCODE_LIST_INDEX = "listindex";
    RecyclerAdapter recyclerAdapter;
    public static  Shortcode selectedRoad =null;
    static LinkedList<Shortcode> listShortcode = new LinkedList<>();


    //variable za permission
    private static final int EXTERNAL_STORAGE_PERMISSION_CONSTANT = 100;
    private static final int REQUEST_PERMISSION_SETTING = 101;
    private boolean sentToSettings = false;
    private SharedPreferences permissionStatus;
    boolean isPermissionGranted = false;


    int chosendD = 0;
    AlertDialog alert = null;

    public static MainFragment createInstance(int shortcodelistindex) {
        MainFragment partThreeFragment = new MainFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ITEMS_COUNT_KEY, listShortcode.size());
        bundle.putInt(SHORTCODE_LIST_INDEX, shortcodelistindex);


        MainFragment.listShortcode = listShortcode;
        partThreeFragment.setArguments(bundle);
        return partThreeFragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        RecyclerView recyclerView = (RecyclerView) inflater.inflate(
                R.layout.fragment_groute, container, false);
        setupRecyclerView(recyclerView);
        return recyclerView;

    }


//    @Override
//    public void onViewCreated(View view, Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//
//            Locale obj = new Locale("", countryCode);
//            mCountryModel.add(new CountryModel(obj.getDisplayCountry(), obj.getISO3Country()));
//        }
//
//        recyclerAdapter = new RecyclerAdapter(mCountryModel);
//        recyclerview.setAdapter(adapter);
//    }

    private void setupRecyclerView(RecyclerView recyclerView) {
        //  GridLayoutManager gr = new GridLayoutManager(getActivity(),1);
        LinearLayoutManager gr = new LinearLayoutManager(getActivity());

        recyclerView.setLayoutManager(gr);
        setHasOptionsMenu(true);
        recyclerAdapter = new RecyclerAdapter(createItemList(MainFragment.listShortcode),getContext());
        recyclerView.setAdapter(recyclerAdapter);
        recyclerAdapter.setOnItemClickListener(new OnRecyclerViewItemClickListener<Shortcode>() {

            @Override
            public void onItemClick(View view, Shortcode viewModel) {


                System.out.println("BANKOZEHO mvuye start:"+viewModel.CODE_COMPANY_OWNER);
                selectedRoad = viewModel;

                if(selectedRoad.INTERFACE.equals("INTER0"))
                {

                    showAlert(viewModel);
                   // showAlert(""+viewModel.getEXPLANATION()+"\n"+viewModel.CHARGE_AMOUNT+" FRW",viewModel.getSHORT_CODE());


                }
                else
                if(selectedRoad.INTERFACE.equals("INTER1") || selectedRoad.INTERFACE.equals("INTER2")) {
                    Intent startmain = new Intent(getActivity(), SendActivity.class);
                    startmain.putExtra("SHORTCODE", viewModel);
                    startActivity(startmain);
                }
                else
                if(selectedRoad.INTERFACE.equals("INTER3"))
                {

                Intent startmain = new Intent(getActivity(), MyinfoList.class);
                startmain.putExtra("SHORTCODE",viewModel );
                // Close all views before launching Dashboard
                startActivityForResult(startmain,1);

                }

            }
        });

    }




    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);

        final MenuItem item = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(this);

        MenuItemCompat.setOnActionExpandListener(item,
                new MenuItemCompat.OnActionExpandListener() {
                    @Override
                    public boolean onMenuItemActionCollapse(MenuItem item) {
                        // Do something when collapsed
                        recyclerAdapter.setFilter(MainActivity.shortcodeListArray[MainActivity.currentIndex]);
                        return true; // Return true to collapse action view
                    }

                    @Override
                    public boolean onMenuItemActionExpand(MenuItem item) {
                        // Do something when expanded
                        return true; // Return true to expand action view
                    }
                });
    }





    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_settings:
                Intent startmain = new Intent(getContext(), Operationslist.class);
                startmain.putExtra("SHORTCODE","" );
                startActivity(startmain);
                return true;
            case R.id.action_pay:
                AlertDialogChoseCompany();
                return true;
            case R.id.action_refresh:
                recyclerAdapter.imageLoader.clearCache();
                recyclerAdapter.notifyDataSetChanged();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public boolean onQueryTextChange(String newText) {
        final List<Shortcode> filteredModelList = filter(MainActivity.shortcodeListArray[MainActivity.currentIndex], newText);
        recyclerAdapter.setFilter(filteredModelList);
        return true;
    }

    private List<Shortcode> filter(List<Shortcode> models, String query) {
        query = query.toLowerCase();


        System.out.println("=================================================EXPLANATION HARIMO:"+models.size());

        final List<Shortcode> filteredModelList = new ArrayList<>();
        for (Shortcode model : models) {
            final String text = model.EXPLANATION.toLowerCase();
            if (text.contains(query)) {
                filteredModelList.add(model);
            }
        }
        return filteredModelList;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }




    public  void dialShortCode(String codeToDial)
    {
        String encodedHash = Uri.encode("#");
        String ussd = "*" +codeToDial+ encodedHash;

        try {
            System.out.println("TO DIAL IS:" + ussd);
            startActivityForResult(new Intent("android.intent.action.CALL",
                    Uri.parse("tel:" + ussd)), 1);
        }catch (Exception e)
            {

                MainActivity.permissionToCheck = android.Manifest.permission.READ_PHONE_STATE;
                checkPermission();

                }
    }

    public List<Shortcode> createItemList(LinkedList<Shortcode> shortcodes) {
        List<Shortcode> itemList = new ArrayList<>();
        Bundle bundle = getArguments();
        if(bundle!=null) {
            int itemsCount = bundle.getInt(ITEMS_COUNT_KEY);
            int shortcodelistindex = bundle.getInt(SHORTCODE_LIST_INDEX);

                for (int i = 0; i < MainActivity.shortcodeListArray[shortcodelistindex].size(); i++) {
                    itemList.add(MainActivity.shortcodeListArray[shortcodelistindex].get(i));
                }

        }
        return itemList;
    }


    public void showAlert(final Shortcode shortCode)
    {

        String message = ""+shortCode.getEXPLANATION()+"\n"+shortCode.CHARGE_AMOUNT+" FRW";

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                getActivity());

        // set title
        alertDialogBuilder.setTitle("Kanda yes Gukomeza");

        // set dialog message
        alertDialogBuilder
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {

                        String phoneCompanyNum = "MY_"+MainActivity.currentSelectedCompany.NAME_COMPANY+"_"+FirstActivity.choosenCountry+"_NUMBER";
                        Services services = MainActivity.db.getService(phoneCompanyNum);

                        String TIER_PHONE_OPERATION="";
                        int maxId = MainActivity.db.getMaxID("SELECT MAX(ID_OPERATION) FROM OPERATIONS");
                        int ID_OPERATION = maxId+1;
                        String NAME_OPERATION = shortCode.getEXPLANATION();
                         if(services != null)
                         TIER_PHONE_OPERATION =  services.CODE_SERVICE;
                        else
                            TIER_PHONE_OPERATION = "NOT SETED";
                         String REASON_OPERATION = shortCode.getEXPLANATION();
                        String COMPANY_OPERATION = MainActivity.currentSelectedCompany.NAME_COMPANY;
                        Double AMOUNT_OPERATION = shortCode.getCHARGE_AMOUNT();
                        String TIME_OPERATION = "";
                        String STATUS_OPERATION = "NOT";


                        if(AMOUNT_OPERATION >0) {
                            MainActivity.db.addOperation(new OPERATIONS(ID_OPERATION, NAME_OPERATION, TIER_PHONE_OPERATION, REASON_OPERATION, COMPANY_OPERATION, AMOUNT_OPERATION, TIME_OPERATION, STATUS_OPERATION));
                        }
                        dialShortCode(shortCode.SHORT_CODE);
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



    public  void checkPermission()
    {

        if (ActivityCompat.checkSelfPermission(getActivity(), MainActivity.permissionToCheck) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), MainActivity.permissionToCheck)) {
                //Show Information about why you need the permission
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Allow permission");
                builder.setMessage("Emeza iyi permission kugirango uhabwe info.");
                builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        isPermissionGranted = true;
                        dialog.cancel();
                        ActivityCompat.requestPermissions(getActivity(), new String[]{MainActivity.permissionToCheck}, EXTERNAL_STORAGE_PERMISSION_CONSTANT);

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
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Allow permission");
                builder.setMessage("Emeza iyi permission kugirango uhabwe info.");
                builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        sentToSettings = true;
                        isPermissionGranted = true;
                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package", getActivity().getPackageName(), null);
                        intent.setData(uri);
                        startActivityForResult(intent, REQUEST_PERMISSION_SETTING);
                        Toast.makeText(getActivity().getApplicationContext(), "Go to Permissions to Grant Storage", Toast.LENGTH_LONG).show();

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
                ActivityCompat.requestPermissions(getActivity(), new String[]{MainActivity.permissionToCheck}, EXTERNAL_STORAGE_PERMISSION_CONSTANT);
            }

            SharedPreferences.Editor editor = permissionStatus.edit();
            editor.putBoolean(MainActivity.permissionToCheck,true);
            editor.commit();


        } else {
            //You already have the permission, just go ahead.


        }
    }


    String [] getCompanyInArray(LinkedList <Company> companylist)
    {
        String arrayToReturn [] = new String[companylist.size()];
        for(int i=0;i<companylist.size();i++)
        {
            arrayToReturn [i] = companylist.get(i).MONEY_SYSTEM;
        }

        return arrayToReturn;
    }




    public String AlertDialogChoseCompany() {

        if (MainActivity.companyList == null) {

            Toast.makeText(getActivity(),"No company Available",Toast.LENGTH_LONG).show();

        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());//ERROR ShowDialog cannot be resolved to a type
        builder.setTitle("PAY 1000 FRW IF YOU ARE HAPPY WITH APP");
        builder.setSingleChoiceItems(getCompanyInArray(MainActivity.companyList), -1,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        Toast.makeText(getActivity(), MainActivity.companyList.get(item).getMONEY_SYSTEM(),
                                Toast.LENGTH_SHORT).show();
                        chosendD = item;
                    }
                });

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                Company choosenCompany = MainActivity.companyList.get(chosendD);
                dialShortCode(choosenCompany.getSHORT_CODE());

                alert.dismiss();
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










    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == EXTERNAL_STORAGE_PERMISSION_CONSTANT) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //The External Storage Write Permission is granted to you... Continue your left job...


            } else {
                if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), MainActivity.permissionToCheck)) {
                    //Show Information about why you need the permission
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle("Need Storage Permission");
                    builder.setMessage("This app needs storage permission");
                    builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();

                            ActivityCompat.requestPermissions(getActivity(), new String[]{MainActivity.permissionToCheck}, EXTERNAL_STORAGE_PERMISSION_CONSTANT);

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
                    Toast.makeText(getActivity(),"Unable to get Permission",Toast.LENGTH_LONG).show();
                }
            }
        }
    }
}
