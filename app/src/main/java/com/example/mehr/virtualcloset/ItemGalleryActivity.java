package com.example.mehr.virtualcloset;

/**
 * Created by Mehr on 2017-12-30.
 */

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.List;

public class ItemGalleryActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private ItemGalleryActivity.ImageGalleryAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_gallery);

        mRecyclerView = (RecyclerView) findViewById(R.id.rv_images);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new GridLayoutManager(this, 2);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new ItemGalleryActivity.ImageGalleryAdapter(this, ItemPhoto.getItemPhotos());
        mRecyclerView.setAdapter(mAdapter);


    }

    private class ImageGalleryAdapter extends RecyclerView.Adapter<ImageGalleryAdapter.MyViewHolder> {

        private ItemPhoto[] mItemPhotos;
        private Context mContext;

        public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

            public ImageView mPhotoImageView;

            public MyViewHolder(View itemView) {

                super(itemView);
                mPhotoImageView = (ImageView) itemView.findViewById(R.id.iv_photo);
                itemView.setOnClickListener(this);

            }

            @Override
            public void onClick(View view) {

                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    ItemPhoto itemPhoto = mItemPhotos[position];

                    Intent intent = new Intent(mContext, ItemPhotoActivity.class);
                    intent.putExtra(ItemPhotoActivity.EXTRA_ITEM_PHOTO, itemPhoto);
                    startActivity(intent);
                }

            }
        }

        public ImageGalleryAdapter(Context context, ItemPhoto[] itemPhotos) {
            mContext = context;
            mItemPhotos = itemPhotos;

        }

        @Override
        public ImageGalleryAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            Context context = parent.getContext();
            LayoutInflater inflater = LayoutInflater.from(context);

            //Inflate layout
            View photoView = inflater.inflate(R.layout.item_photo, parent, false);

            ImageGalleryAdapter.MyViewHolder viewHolder = new ImageGalleryAdapter.MyViewHolder(photoView);
            return viewHolder;

        }

        @Override
        public void onBindViewHolder(ImageGalleryAdapter.MyViewHolder holder, int position) {

            ItemPhoto itemPhoto = mItemPhotos[position];
            ImageView imageView = holder.mPhotoImageView;

            Glide.with(mContext)
                    .load(itemPhoto.getmPhotoPath())
                    .placeholder(R.drawable.ic_launcher_background)
                    .into(imageView);

        }

        @Override
        public int getItemCount() {

            return mItemPhotos.length;
        }
    }

}
