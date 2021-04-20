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

public class AdapterLazyInfoList extends BaseAdapter {

    private Activity activity;
//    private ArrayList<HashMap<String, String>> data;
    LinkedList<Services> data = new LinkedList<Services>();
    private static LayoutInflater inflater=null;
    public AdapterLazyInfoList(Activity a, LinkedList<Services> d ) {
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
        return data.get(position).CODE_SERVICE;
    }
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi=convertView;
        if(convertView==null)
            vi = inflater.inflate(R.layout.myinfo_list_row, null);

        TextView title = (TextView)vi.findViewById(R.id.invoice_title_info);
        TextView article = (TextView)vi.findViewById(R.id.list_article_info);
        TextView duration = (TextView)vi.findViewById(R.id.duration);
        TextView articleonlist = (TextView)vi.findViewById(R.id.articleOnList);

        ImageView thumb_image=(ImageView)vi.findViewById(R.id.list_image_info);


        Services amadone = data.get(position);

        System.out.println("POSITION IS:"+position+" "+amadone.NAME_SERVICE);
        System.out.println("POSITION IS:"+position+" "+amadone.CODE_SERVICE);


        title.setText(amadone.NAME_SERVICE);
        article.setText(amadone.CODE_SERVICE);


        String image = "success";
        int id = 0;

            id =  activity.getApplicationContext().getResources().getIdentifier("com.ishyiga.mini:drawable/" + image, null, null);
            thumb_image.setImageResource(id);

        return vi;
    }

}