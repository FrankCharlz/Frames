package com.mj.frameapp.adapters;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.mj.frameapp.MyApp;
import com.mj.frameapp.R;
import com.mj.frameapp.activities.Context;

import java.io.File;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Frank on 11-Aug-17.
 */

public class TemplatesAdapter extends RecyclerView.Adapter<TemplatesAdapter.ViewHolder> {

    private final Context context;
    private List<File> templates;

    public TemplatesAdapter(Context context) {
        super();
        this.context = context;

        File folder = MyApp.getAppFolder();
        if (folder == null || folder.list() == null) {
            //// TODO: 11-Aug-17  should kill activity
            MyApp.log("app folder is null can't proceed");
            return;
        }

         templates = Arrays.asList(folder.listFiles());

    }

    @Override
    public TemplatesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyApp.log(this.toString()+" view holder created");
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_templates, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        File currentItem = templates.get(position);
        holder.tImgv.setImageURI(Uri.fromFile(currentItem));

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
