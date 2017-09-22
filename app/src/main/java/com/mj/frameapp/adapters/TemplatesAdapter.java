package com.mj.frameapp.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.mj.frameapp.MyApp;
import com.mj.frameapp.R;
import com.mj.frameapp.activities.ChooseTemplateActivity;
import com.mj.frameapp.utils.ImageMaker;

import java.util.List;

/**
 * Created by Frank on 11-Aug-17.
 */

public class TemplatesAdapter extends RecyclerView.Adapter<TemplatesAdapter.ViewHolder> {

    private final Context context;
    private final Bitmap selectedImage;
    private List<String> templates;


    public TemplatesAdapter(ChooseTemplateActivity chooseTemplateActivity, Bitmap selectedImage, List<String> templates) {
        this.context = chooseTemplateActivity;
        this.templates = templates;
        this.selectedImage = selectedImage;
        MyApp.log("templates adapter created");
    }


    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        MyApp.log("adapter attached to recycler view");
    }

    @Override
    public TemplatesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyApp.log(this.toString()+" view holder created");
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_templates, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        //String currentItem = templates.get(position);
        //MyApp.log(currentItem);
        //holder.tImgv.setImageDrawable(context.getDrawable(templates.get(position)));
        //holder.tImgv.setImageDrawable(ContextCompat.getDrawable(context, templates.get(position)));


        Bitmap bitmap = ImageMaker.make(templates.get(position), selectedImage);
        holder.tImgv.setImageBitmap(bitmap);

    }

    @Override
    public int getItemCount() {
        return templates.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        final ImageView tImgv;

        ViewHolder(View itemView) {
            super(itemView);
            tImgv = (ImageView) itemView.findViewById(R.id.item_image);

        }
    }
}
