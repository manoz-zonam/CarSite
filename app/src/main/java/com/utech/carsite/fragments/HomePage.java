package com.utech.carsite.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.RelativeLayout;

import androidx.appcompat.widget.SearchView;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.ahmadhamwi.tabsync.TabbedListMediator;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.JsonElement;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;
import com.utech.carsite.API.ApiHelper;
import com.utech.carsite.API.ApiService;
import com.utech.carsite.R;
import com.utech.carsite.adapters.BudgetAdapter;
import com.utech.carsite.adapters.SliderAdapter;
import com.utech.carsite.model.AllCarsModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomePage extends Fragment {
    ArrayList<Integer> itemCount;
    Call<JsonElement> call;
    ArrayList<AllCarsModel> allCarsModel;
    String id, min_val, max_val;
    BudgetAdapter budgetAdapter;



    @BindView(R.id.search_car)
    SearchView search_car;

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

    @BindView(R.id.tab_PopularPrice)
    TabLayout tab_PopularPrice;

    @BindView(R.id.viewPagerBudget)
    ViewPager viewPagerBudget;

    @BindView(R.id.popularBudgetRV)
    RecyclerView popularBudgetRV;

    @BindView(R.id.grid)
    RelativeLayout grid;

//

    int[] images = {R.drawable.car1,
            R.drawable.car2,
            R.drawable.car3,
            R.drawable.car4};

    public HomePage() {
        // Required empty public constructor
    }

    PagerAdapter pagerAdapter;


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


        //pagerAdapter = new PagerAdapter(getFragmentManager());
        //viewPagerBudget.setAdapter(pagerAdapter);
        //tab_PopularPrice.setupWithViewPager(viewPagerBudget);

        LinearLayoutManager linearLayoutManager = new GridLayoutManager(getContext(),1);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        popularBudgetRV.setLayoutManager(linearLayoutManager);

        getData();

        return rootView ;
    }

    private void getData() {

        ApiService apiService = (ApiService) ApiHelper.getInstance().getService(ApiService.class);
        call = apiService.getCarByBudget();
        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                JSONObject data = new JSONObject();
                if(response.isSuccessful()){
                    String responseValue = response.body().toString();
                    if(responseValue != null){
                        try {
                            JSONObject jsonObject = new JSONObject(responseValue);
                            String responseCode = jsonObject.getString("success");
                            if(responseCode.equals("true")){
                                JSONArray jsonArray = jsonObject.getJSONArray("data");
                                allCarsModel = new ArrayList<>();
                                itemCount = new ArrayList<>();
                                for (int i = 0; i < jsonArray.length(); i++){
                                    JSONObject json = jsonArray.getJSONObject(i);
                                    id = json.getString("id");
                                    min_val = json.getString("min_val");
                                    max_val = json.getString("max_val");
                                    allCarsModel.add(new AllCarsModel(id, min_val, max_val));
                                    tab_PopularPrice.addTab(tab_PopularPrice.newTab().setText(min_val + "-" + max_val));
                                    itemCount.add(i);
                                }
                                budgetAdapter = new BudgetAdapter(allCarsModel, getContext());
                                popularBudgetRV.setAdapter(budgetAdapter);
                                new TabbedListMediator(popularBudgetRV, tab_PopularPrice,itemCount, true).attach();



                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {

            }
        });

    }

//    private fun attach{
//        TabbedListMediator(
//                popularBudgetRV,
//                tab_PopularPrice
//        ).attach()
//    }

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