package com.uploadcare.android.widget.adapter;

import com.squareup.picasso.Picasso;
import com.uploadcare.android.widget.BuildConfig;
import com.uploadcare.android.widget.R;
import com.uploadcare.android.widget.controller.UploadcareWidget;
import com.uploadcare.android.widget.data.Thing;
import com.uploadcare.android.widget.interfaces.ItemTapListener;
import com.uploadcare.android.widget.view.SquaredImageView;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

public class FilesLinearAdapter extends FilesAdapter<FilesLinearAdapter.ThingViewHolder> {


    public FilesLinearAdapter(Context context, ItemTapListener listener, @UploadcareWidget.FileType String fileType) {
        super(context,listener,fileType);
    }

public static class ThingViewHolder extends RecyclerView.ViewHolder {

    public TextView title;

    public SquaredImageView tb;

    public FrameLayout itemRoot;

    public ThingViewHolder(View v) {
        super(v);
        title = (TextView) v.findViewById(R.id.ucw_item_title);
        tb = (SquaredImageView) v.findViewById(R.id.ucw_item_tb);
        itemRoot = (FrameLayout) v.findViewById(R.id.ucw_item_root);
    }
}

    @Override
    public FilesLinearAdapter.ThingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(mContext)
                .inflate(R.layout.ucw_file_linear_item, parent, false);
        return new ThingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final FilesLinearAdapter.ThingViewHolder holder,
            final int position) {
        final Thing thing = mDataset.get(position);

        int placeHolderResource = getLinearPlaceHolderResource(thing);

        if (thing.thumbnail != null) {
            String url= thing.thumbnail;
            if(!url.startsWith("http")||!url.startsWith("https")){
                url= BuildConfig.SOCIAL_API_ENDPOINT+url;
            }
            Picasso.with(mContext).load(url)
                    .placeholder(placeHolderResource)
                    .into(holder.tb);
        } else {
            Picasso.with(mContext).load(placeHolderResource)
                    .into(holder.tb);
        }

        if (thing.title != null && !thing.title.isEmpty()) {
            holder.title.setText(thing.title);
        } else {
            holder.title.setText(null);
        }

        holder.itemRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mItemTapListener.itemTap(thing);
            }
        });
    }

}
