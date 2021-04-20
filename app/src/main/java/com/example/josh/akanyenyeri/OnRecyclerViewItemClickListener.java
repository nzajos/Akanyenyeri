package com.example.josh.akanyenyeri;

/**
 * Created by JOSH on 5/4/2017.
 */
import android.view.View;

public interface OnRecyclerViewItemClickListener<Model> {

    public void onItemClick(View view, Model model);

}