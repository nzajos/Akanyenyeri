package com.example.josh.akanyenyeri;


/**
 * Created by JOSH-TOSH on 9/12/2015.
 */
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import static com.example.josh.akanyenyeri.CommonUtilities.SERVER_URL;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> implements View.OnClickListener{

    private List<Shortcode> mItemList;
    Context context;
    private OnRecyclerViewItemClickListener<Shortcode>	mItemClickListener;

    public ImgImageLoader imageLoader;

    public RecyclerAdapter(List<Shortcode> itemList,Context context) {
        mItemList = itemList;
        this.context = context;
        imageLoader=new ImgImageLoader(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_item, parent, false);
        view.setOnClickListener(this);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        Shortcode itemText = mItemList.get(position);

String imagesUrl =SERVER_URL+"images/";

        Companydep companydep = MainActivity.db.getCompanyDep(itemText.CODE_COMPANY_OWNER);

       if(companydep != null)
           System.out.println("companydep ntago ari null");
        else
       System.out.println("companydep irimo null");


        imageLoader.DisplayImage(imagesUrl+itemText.IMAGE_PATH, viewHolder.imageView);

       // viewHolder.imageView.setImageResource(getImageId(context, companydep.LOGO_DEP))
        viewHolder.Txtitem.setText(companydep.NAME_COMPANY_DEP );
        viewHolder.rtitle.setText(itemText.EXPLANATION);
        viewHolder.articleOnList.setText("");
        viewHolder.duration.setText(itemText.CHARGE_AMOUNT+" RWF");
        viewHolder.itemView.setTag(itemText);
    }

    public static int getImageId(Context context, String imageName) {
        return context.getResources().getIdentifier("drawable/" + imageName, null, context.getPackageName());
    }

    @Override
    public void onClick(View view) {
        System.out.println("mItemClickListener" + mItemClickListener.toString());
        if (mItemClickListener != null) {
            Shortcode road = (Shortcode) view.getTag();
            mItemClickListener.onItemClick(view, road);
        }
    }


    public void setOnItemClickListener(OnRecyclerViewItemClickListener<Shortcode> listener) {
        mItemClickListener = listener;
    }


    @Override
    public int getItemCount() {
        return mItemList == null ? 0 : mItemList.size();
    }


    public void setFilter(List<Shortcode> shortcodeList) {
        mItemList = new ArrayList<>();
        mItemList.addAll(shortcodeList);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView Txtitem,rtitle,articleOnList,duration;
        ImageView imageView;

        public ViewHolder(View view) {
            super(view);

            Txtitem = (TextView) view.findViewById(R.id.itemTextView);
            imageView = (ImageView) view.findViewById(R.id.image);
            rtitle = (TextView) view.findViewById(R.id.rtitle);
            articleOnList = (TextView) view.findViewById(R.id.articleOnList);
            duration = (TextView) view.findViewById(R.id.duration);
        }


    }}