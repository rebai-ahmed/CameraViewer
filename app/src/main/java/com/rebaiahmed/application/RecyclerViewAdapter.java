package com.rebaiahmed.application;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by Rebai Ahmed on 08/09/2018.
 */


public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    private Context mContext;
    private List<CameraInfos> mData;


    public RecyclerViewAdapter(Context mContext, List<CameraInfos> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.cardview_item_camera, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        holder.cameraNameTxtView.setText(mData.get(position).getName());
//        holder.img_book_thumbnail.setImageResource(mData.get(position).getThumbnail());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cameraStream = new Intent(mContext, MainActivity.class);
                cameraStream.putExtra("Id",mData.get(position).getId());
                cameraStream.putExtra("Name", mData.get(position).getName());
                cameraStream.putExtra("IP", mData.get(position).getIp());
                cameraStream.putExtra("Port", mData.get(position).getPort());
                cameraStream.putExtra("Password", mData.get(position).getPassword());
                mContext.startActivity(cameraStream);
            }
        });
        holder.cardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Intent modifyCamera = new Intent(mContext, NewCameraActivity.class);
                modifyCamera.putExtra("Activity", "Modify");
                modifyCamera.putExtra("Id", mData.get(position).getId());
                modifyCamera.putExtra("Name", mData.get(position).getName());
                modifyCamera.putExtra("IP", mData.get(position).getIp());
                modifyCamera.putExtra("Port", mData.get(position).getPort());
                modifyCamera.putExtra("Password", mData.get(position).getPassword());

                mContext.startActivity(modifyCamera);
                return true;
            }
        });


    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView cameraNameTxtView;
        ImageView cameraThumbnail;
        CardView cardView;
//        private CustomItemOnClickListener itemOnClickListener;

        public MyViewHolder(View itemView) {
            super(itemView);

            cameraNameTxtView = (TextView) itemView.findViewById(R.id.cameraNameId);
            cameraThumbnail = (ImageView) itemView.findViewById(R.id.cameraImageId);
            cardView = (CardView) itemView.findViewById(R.id.cardview_id);
//            itemView.setOnClickListener(this);


        }
        /*public void setItemOnClickListener (CustomItemOnClickListener itemOnClickListener){
            this.itemOnClickListener = itemOnClickListener;
        }
        @Override
        public void onClick(View view) {
            itemOnClickListener.onItemClick(view,getAdapterPosition());
        }*/
    }


}
