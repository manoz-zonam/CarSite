package com.utech.carsite.fragments;

import android.app.LauncherActivity;
import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.RelativeLayout;

import com.google.android.material.button.MaterialButton;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;
import com.utech.carsite.MainActivity;
import com.utech.carsite.NewCars;
import com.utech.carsite.NewsReview;
import com.utech.carsite.R;
import com.utech.carsite.SellCar;
import com.utech.carsite.UsedCars;
import com.utech.carsite.adapters.SliderAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomePage extends Fragment {

    @BindView(R.id.imageSlider)
    SliderView imageSlider;

    @BindView(R.id.new_car_cardView)
    CardView card1;

    @BindView(R.id.used_car_cardView)
    CardView card2;

    @BindView(R.id.sell_car_cardView)
    CardView card3;

    @BindView(R.id.news_cardView)
    CardView card4;

    @BindView(R.id.expandableButton)
    MaterialButton expandableButton;

    @BindView(R.id.lessButton)
    MaterialButton lessButton;

    @BindView(R.id.main_grid)
    GridLayout main_grid;

    @BindView(R.id.secondary_grid)
    GridLayout secondary_grid;

    @BindView(R.id.Button1)
    MaterialButton Button1;

    @BindView(R.id.Button2)
    MaterialButton Button2;

    @BindView(R.id.Button3)
    MaterialButton Button3;

    @BindView(R.id.Button4)
    MaterialButton Button4;

    @BindView(R.id.Button5)
    MaterialButton Button5;

    @BindView(R.id.Button6)
    MaterialButton Button6;

    @BindView(R.id.Button7)
    MaterialButton Button7;

    @BindView(R.id.grid)
    RelativeLayout grid;


    @BindView(R.id.under5lakhFl)
    RelativeLayout under5lakhFl;

    @BindView(R.id.under10lakhFl)
    RelativeLayout under10lakhFl;

    int[] images = {R.drawable.car1,
            R.drawable.car2,
            R.drawable.car3,
            R.drawable.car4};

    public HomePage() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home_page, container, false);
        ButterKnife.bind(this, rootView);

        SliderAdapter sliderAdapter = new SliderAdapter(images,getContext());
        imageSlider.setSliderAdapter(sliderAdapter);
        imageSlider.setIndicatorAnimation(IndicatorAnimationType.WORM);
        imageSlider.setSliderTransformAnimation(SliderAnimations.DEPTHTRANSFORMATION);
        imageSlider.startAutoCycle();


        return rootView ;


    }





    @OnClick(R.id.expandableButton)
    public void showMore(){
        expandableButton.setVisibility(View.GONE);
        lessButton.setVisibility(View.VISIBLE);
        main_grid.setVisibility(View.VISIBLE);
        secondary_grid.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.lessButton)
    public void showLess(){
        expandableButton.setVisibility(View.VISIBLE);
        lessButton.setVisibility(View.GONE);
        main_grid.setVisibility(View.VISIBLE);
        secondary_grid.setVisibility(View.GONE);
    }
}