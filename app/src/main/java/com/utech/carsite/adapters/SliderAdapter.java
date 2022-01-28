package com.utech.carsite.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.smarteist.autoimageslider.SliderViewAdapter;
import com.utech.carsite.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SliderAdapter extends SliderViewAdapter<SliderAdapter.Holder> {

    int[] images;
    Context context;
    public SliderAdapter(int[] images, Context context){
        this.images = images;
        this.context = context;
    }

//    public SliderAdapter(int[] images) {
//    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.slider_items, parent, false);
        return new Holder(itemView,context);

    }

    @Override
    public void onBindViewHolder(Holder viewHolder, int position) {

        viewHolder.sliderImageView.setImageResource(images[position]);

    }

    @Override
    public int getCount() {
        return images.length;
    }



    public class Holder extends SliderViewAdapter.ViewHolder{
        @BindView(R.id.sliderImageView)
        ImageView sliderImageView;

        public Holder(View itemView, final Context context) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}