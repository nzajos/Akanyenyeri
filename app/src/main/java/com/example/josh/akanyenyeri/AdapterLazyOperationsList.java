package com.example.josh.akanyenyeri;

/**
 * Created by JOSH on 5/18/2017.
 */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;


import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
//import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class AdapterLazyOperationsList extends BaseAdapter {

    private Activity activity;
    //    private ArrayList<HashMap<String, String>> data;
    LinkedList<OPERATIONS> data = new LinkedList<OPERATIONS>();
    private static LayoutInflater inflater=null;
    public AdapterLazyOperationsList(Activity a, LinkedList<OPERATIONS> d ) {
        activity = a;
        data=d;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public int getCount() {
        return data.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }
    public String display(int position)
    {
        return data.get(position).NAME_OPERATION;
    }
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi=convertView;
        if(convertView==null)
            vi = inflater.inflate(R.layout.operation_list_row, null);

        TextView title = (TextView)vi.findViewById(R.id.op_title_info);
        TextView article = (TextView)vi.findViewById(R.id.op_article_info);
        TextView duration = (TextView)vi.findViewById(R.id.op_duration_info);
        TextView articleonlist = (TextView)vi.findViewById(R.id.op_articleOnList_info);

        ImageView thumb_image=(ImageView)vi.findViewById(R.id.op_list_image_info);


        OPERATIONS amadone = data.get(position);

        System.out.println("POSITION IS:"+position+" "+amadone.NAME_OPERATION);
        System.out.println("POSITION IS:"+position+" "+amadone.AMOUNT_OPERATION);


        title.setText(amadone.COMPANY_OPERATION);
        article.setText(amadone.NAME_OPERATION);
        duration.setText(""+amadone.AMOUNT_OPERATION+" FRW");


        String image = "success";
        int id = 0;

        id =  activity.getApplicationContext().getResources().getIdentifier("com.example.josh.akanyenyeri:drawable/" + image, null, null);
        thumb_image.setImageResource(id);

        return vi;
    }

}